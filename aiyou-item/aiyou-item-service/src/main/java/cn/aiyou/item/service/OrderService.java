package cn.aiyou.item.service;

import cn.aiyou.item.mapper.FlightMapper;
import cn.aiyou.item.mapper.FlightSeatMapper;
import cn.aiyou.item.mapper.OrderMapper;
import cn.aiyou.item.mapper.OrderPassengerMapper;
import cn.aiyou.item.pojo.FlightSeat;
import cn.aiyou.item.pojo.Flight_Info1;
import cn.aiyou.item.pojo.Order;
import cn.aiyou.item.pojo.OrderPassenger;
import cn.aiyou.item.utils.HttpClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private OrderPassengerMapper orderPassengerMapper;

    @Autowired
    private FlightSeatMapper flightSeatMapper;

    @Transactional
    public String insertOrder(List<OrderPassenger> orderPassengers, Long flightNo, Long userId, Long orderId, Integer pay) {
        //获取座位号
        List<Integer> seats = new ArrayList<>(orderPassengers.size());
        int passengerNum = orderPassengers.size();
        int price = pay / passengerNum;
        Flight_Info1 queryFlight_info1 = new Flight_Info1();
        queryFlight_info1.setFlightNo(flightNo);
        queryFlight_info1 = flightMapper.selectOne(queryFlight_info1);
        int start_seat = 1, end_seat = queryFlight_info1.getHeadNum() + queryFlight_info1.getEconomyNum();
        if (price == queryFlight_info1.getHeadPrice()) {
            end_seat = queryFlight_info1.getHeadNum();//头等舱结束座位号
        } else {//
            start_seat += queryFlight_info1.getHeadNum();//经济舱开始座位号
        }
        FlightSeat queryFlightSeat = new FlightSeat();
        queryFlightSeat.setFlightNo(flightNo);
        List<FlightSeat> flightSeats = flightSeatMapper.select(queryFlightSeat);


        Set<Integer> existSeats = flightSeats.stream().map(FlightSeat::getSeatNo).collect(Collectors.toSet());
        while (start_seat <= end_seat) {
            if (existSeats.contains(start_seat)) {
                start_seat++;
                continue;
            }
            seats.add(start_seat++);
            if (seats.size() == orderPassengers.size()) {
                break;
            }
        }
        if (seats.size() != orderPassengers.size()) {
            return "预订失败，余票不足。";
        }
        FlightSeat flightSeat = new FlightSeat();
        flightSeat.setFlightNo(flightNo);
        seats.forEach(seatNo ->{
            flightSeat.setSeatNo(seatNo);
            flightSeatMapper.insert(flightSeat);
        });

        OrderPassenger orderPassenger = new OrderPassenger();
        Order order = new Order();
        orderPassenger.setOrderId(orderId);
        int i = 0;
        for (OrderPassenger passenger : orderPassengers) {
            orderPassenger.setPassengerName(passenger.getPassengerName());
            orderPassenger.setPassengerTel(passenger.getPassengerTel());
            orderPassenger.setPassengerIdcard(passenger.getPassengerIdcard());
            orderPassenger.setSeatNo(seats.get(i++));
            this.orderPassengerMapper.insertSelective(orderPassenger);
        }
//        orderPassengers.forEach(passenger -> {
//            orderPassenger.setPassengerName(passenger.getPassengerName());
//            orderPassenger.setPassengerTel(passenger.getPassengerTel());
//            orderPassenger.setPassengerIdcard(passenger.getPassengerIdcard());
//            orderPassenger.setSeatNo(seats.get(i));
//            this.orderPassengerMapper.insertSelective(orderPassenger);
//        });
        Flight_Info1 flight_info1 = this.flightMapper.selectByPrimaryKey(flightNo);
        order.setOrderId(orderId);
        order.setUesrId(userId);
        order.setFlightNo(flightNo);
        order.setTicketPrice(pay);
        order.setFlightDate(flight_info1.getFlightDate());
        order.setFromCity(flight_info1.getFromCity());
        order.setToCity(flight_info1.getToCity());
        order.setFromTime(flight_info1.getFromTime());
        order.setToTime(flight_info1.getToTime());
        this.orderMapper.insertSelective(order);
        return "预定成功";
    }

    public List<Order> queryAllOrdersByUser(Long userId) {
        Order result = new Order();
        result.setUesrId(userId);
        List<Order> orders = this.orderMapper.select(result);
        return orders;
    }

    public Order queryOrdersDetails(Long orderId) {
        OrderPassenger orderPassenger = new OrderPassenger();
        orderPassenger.setOrderId(orderId);
        List<OrderPassenger> select = this.orderPassengerMapper.select(orderPassenger);
       // List<String> list = new ArrayList<>();
        Map<String,Integer> map=new HashMap<>();
        select.forEach(pass -> {
           // list.add(pass.getPassengerName());
            map.put(pass.getPassengerName(),pass.getSeatNo());

        });
        Order order = this.orderMapper.selectByPrimaryKey(orderId);
        order.setPassengers(map);
        return order;
    }

    public PageInfo<Order> queryAllOrders(Integer page) {
        PageHelper.startPage(page, 10);
        List<Order> orders = this.orderMapper.selectAll();
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return pageInfo;

    }

    @Transactional
    public Boolean deleteOrder(Long orderId, String idcard) {
        Order queryOrder = new Order();
        queryOrder.setOrderId(orderId);
        OrderPassenger queryOrderPassenger = new OrderPassenger();
        queryOrderPassenger.setOrderId(orderId);
        FlightSeat flightSeat = new FlightSeat();

        List<OrderPassenger> orderPassengers = orderPassengerMapper.select(queryOrderPassenger);
        queryOrderPassenger.setPassengerIdcard(idcard);
        OrderPassenger orderPassenger = orderPassengerMapper.selectOne(queryOrderPassenger);
        Order order = orderMapper.selectOne(queryOrder);

        flightSeat.setFlightNo(order.getFlightNo());
        flightSeat.setSeatNo(orderPassenger.getSeatNo());
        flightSeatMapper.delete(flightSeat);
        orderPassengerMapper.delete(orderPassenger);

        if (orderPassengers.size() == 1) {
            orderMapper.delete(queryOrder);
            return true;
        }

        Integer ticketPrice = order.getTicketPrice();
        order.setTicketPrice((ticketPrice / orderPassengers.size()) * (orderPassengers.size() - 1));
        orderMapper.updateByPrimaryKeySelective(order);
        return true;
    }

    public Map createNative(String orderNo) {
        try {
            //根据订单id获取订单信息
//            QueryWrapper<Order> wrapper = new QueryWrapper<>();
//            wrapper.eq("order_no",orderNo);
//            Order order = orderService.getOne(wrapper);
            Order order1=new Order();
            order1.setOrderId( Long.valueOf(orderNo));
            BigDecimal bigDecimal=new BigDecimal("0.01");
            Order order = orderMapper.selectOne(order1);
            Map m = new HashMap();
            //1、设置支付参数
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body",String.valueOf(order.getFlightNo()) );
            m.put("out_trade_no", orderNo);
            m.put("total_fee",bigDecimal.multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");
            //2、HTTPClient来根据URL访问第三方接口并且传递参数

            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

            //client设置参数

            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            System.out.println(resultMap);
            //4、封装返回结果集

            System.out.println(bigDecimal);
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            //map.put("course_id", order.getCourseId());
           map.put("total_fee",bigDecimal.multiply(new BigDecimal("100")).longValue()+"");
            //map.put("total_fee", 2000011900);
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));
            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return map;
        } catch (Exception e) {

            e.printStackTrace();

             return new HashMap<>();

        }

    }


    @Transactional
    public Boolean deleteTicket(Long flightNo, int seatNo,Long orderId) {
        OrderPassenger orderPassenger=new OrderPassenger();
        orderPassenger.setOrderId(orderId);
        orderPassenger.setSeatNo(seatNo);
        int delete1 = this.orderPassengerMapper.delete(orderPassenger);
        FlightSeat flightSeat=new FlightSeat();
        flightSeat.setFlightNo(flightNo);
        flightSeat.setSeatNo(seatNo);
        int delete = this.flightSeatMapper.delete(flightSeat);
        if(delete==1&&delete1==1){
            return true;
        }else {
            return false;
        }
    }
}
