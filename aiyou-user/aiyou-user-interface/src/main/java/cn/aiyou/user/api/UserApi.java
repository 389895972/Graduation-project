package cn.aiyou.user.api;

import cn.aiyou.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {

    @GetMapping("query")
    public User queryUser(@RequestParam("phone")String phone, @RequestParam("password")String password);
}
