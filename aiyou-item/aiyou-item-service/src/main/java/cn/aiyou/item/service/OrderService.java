package cn.aiyou.item.service;

import cn.aiyou.item.mapper.FlightMapper;
import cn.aiyou.item.mapper.FlightSeatMapper;
import cn.aiyou.item.mapper.OrderMapper;
import cn.aiyou.item.mapper.OrderPassengerMapper;
import cn.aiyou.item.pojo.FlightSeat;
import cn.aiyou.item.pojo.Flight_Info1;
import cn.aiyou.item.pojo.Order;
import cn.aiyou.item.pojo.OrderPassenger;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                continue;
            }
            seats.add(start_seat++);
            if (seats.size() == orderPassengers.size()) {
                break;
            }
        }
        if (seats.size() != orderPassengers.size()) {
            return "订购失败，余票不足";
        }

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
        return "订购成功";
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
        List<String> list = new ArrayList<>();
        select.forEach(pass -> {
            list.add(pass.getPassengerName());
        });
        Order order = this.orderMapper.selectByPrimaryKey(orderId);
        order.setPassengers(list);
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

        if (orderPassengers.size() == 1) {
            orderMapper.delete(queryOrder);
            return true;
        }

        Integer ticketPrice = order.getTicketPrice();
        order.setTicketPrice((ticketPrice / orderPassengers.size()) * orderPassengers.size() - 1);
        orderMapper.updateByPrimaryKeySelective(order);
        return true;
    }
}
