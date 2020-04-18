package cn.aiyou.item.controller;

import cn.aiyou.auth.config.JwtProperties;
import cn.aiyou.common.entity.Result;
import cn.aiyou.common.pojo.UserInfo;
import cn.aiyou.common.utils.JwtUtils;
import cn.aiyou.item.pojo.Order;
import cn.aiyou.item.pojo.OrderPassenger;
import cn.aiyou.item.service.OrderService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//import net.minidev.json.JSONObject;

@Controller
@RequestMapping("order")
@EnableConfigurationProperties(JwtProperties.class)
public class OrderController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("insert/{flightNo}/{status}")
    @Transactional
    public ResponseEntity<Result> insertOrder(@RequestBody JSONObject jsonObject, @PathVariable Long flightNo, @PathVariable Integer status) {
        System.out.println(status);

        UserInfo user = null;
        try {
            user = JwtUtils.getInfoFromToken(jsonObject.getString("token"), this.jwtProperties.getPublicKey());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Long orderId = Long.valueOf(getOrderId());
            //System.out.println(user.getUsername());
            JSONArray users = jsonObject.getJSONArray("users");
            /// System.out.println(users.toJSONString());
            List<OrderPassenger> orderPassengers = JSONObject.parseArray(users.toJSONString(), OrderPassenger.class);
            //System.out.println(orderPassengers);
            int pay = Integer.parseInt(jsonObject.getString("pay"));
            this.orderService.insertOrder(orderPassengers, flightNo, user.getId(), orderId, pay);

            return ResponseEntity.ok(new Result(false, 201, "用户信息错误", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("findAllOrders/{page}")
    public ResponseEntity<PageInfo<Order>> queryAllOrders(@PathVariable Integer page) {

        PageInfo<Order> orders = this.orderService.queryAllOrders(page);
        return ResponseEntity.ok(orders);

    }

    @PostMapping("findAllOrdersByUser")
    public ResponseEntity<List<Order>> queryAllOrdersByUser(@RequestBody JSONObject jsonObject) {
        UserInfo user = null;
        try {
            user = JwtUtils.getInfoFromToken(jsonObject.getString("token"), this.jwtProperties.getPublicKey());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Long userId = user.getId();
            List<Order> orders = this.orderService.queryAllOrdersByUser(userId);

            return ResponseEntity.ok(orders);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @GetMapping("orderDetails/{orderId}")
    public ResponseEntity<Order> queryOrdersDetails(@PathVariable Long orderId) {
        Order order = this.orderService.queryOrdersDetails(orderId);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("order/{orderId}/{idcard}")
    public ResponseEntity<Boolean> queryOrdersDetails(@PathVariable Long orderId, @PathVariable String idcard) {
        return ResponseEntity.ok(this.orderService.deleteOrder(orderId, idcard));
    }


    //生成订单号
    public String getOrderId() {
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String localDate = LocalDateTime.now().format(ofPattern);

        String ran = RandomStringUtils.randomNumeric(4);
        String orderId = localDate + ran;
        System.out.println(orderId);
        return orderId;
    }
}
