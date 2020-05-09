package cn.aiyou.auth.client;

import cn.aiyou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("user-service")
public interface UserClient1 {

    @GetMapping("query")
    public User queryUser(@RequestParam("phone")String phone, @RequestParam("password")String password);
}
