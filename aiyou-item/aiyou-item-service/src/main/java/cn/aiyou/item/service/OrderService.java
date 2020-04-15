package cn.aiyou.item.service;

import cn.aiyou.item.mapper.FlightMapper;
import cn.aiyou.item.mapper.OrderMapper;
import cn.aiyou.item.mapper.OrderPassengerMapper;
import cn.aiyou.item.pojo.Flight_Info1;
import cn.aiyou.item.pojo.Order;
import cn.aiyou.item.pojo.OrderPassenger;
import cn.aiyou.item.pojo.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private OrderPassengerMapper orderPassengerMapper;


    @Transactional
    public void insertOrder(List<OrderPassenger> orderPassengers, Long flightNo, Long userId, Long orderId,Integer pay) {
        OrderPassenger orderPassenger=new OrderPassenger();
        Order order=new Order();
        orderPassenger.setOrderId(orderId);
        orderPassengers.forEach(passenger->{
            orderPassenger.setPassengerName(passenger.getPassengerName());
            orderPassenger.setPassengerTel(passenger.getPassengerTel());
            orderPassenger.setPassengerIdcard(passenger.getPassengerIdcard());
            this.orderPassengerMapper.insertSelective(orderPassenger);
        });
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

    }

    public List<Order> queryAllOrdersByUser(Long userId) {
        Order result=new Order();
        result.setUesrId(userId);
        List<Order> orders = this.orderMapper.select(result);
      return orders;
    }

    public Order queryOrdersDetails(Long orderId) {
        OrderPassenger orderPassenger=new OrderPassenger();
        orderPassenger.setOrderId(orderId);
        List<OrderPassenger> select = this.orderPassengerMapper.select(orderPassenger);
        List<String> list =new ArrayList<>();
        select.forEach(pass->{
            list.add(pass.getPassengerName());
        });
        Order order = this.orderMapper.selectByPrimaryKey(orderId);
        order.setPassengers(list);
        return order;
    }


    public PageInfo<Order> queryAllOrders(Integer page) {
        PageHelper.startPage(page,10);
        List<Order> orders = this.orderMapper.selectAll();
        PageInfo<Order> pageInfo=new PageInfo<>(orders);
        return pageInfo;

    }
}
