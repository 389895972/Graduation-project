package cn.aiyou.user.api;

import cn.aiyou.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {

    @GetMapping("query")
    User queryUser(@RequestParam("phone") String phone, @RequestParam("password") String password);

    @GetMapping("personGetUser")
    User getUser(@RequestParam("id") Long id);

    @GetMapping("modifyUser")
    Integer  modifyUser(
            @RequestParam("id") Long id,
            @RequestParam(value = "username",required =false) String username,
            @RequestParam(value = "name",required =false) String name,
            @RequestParam(value = "idcard",required =false) String idcard,
            @RequestParam(value = "phone",required =false) String phone
    );

    @GetMapping("updatePwd")
    Integer  modifyPwd(
            @RequestParam("id") Long id,
            @RequestParam(value = "oldPwd",required =false) String oldPwd,
            @RequestParam(value = "newPwd",required =false) String newPwd
    );
}
