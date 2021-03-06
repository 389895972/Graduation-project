package cn.aiyou.auth.client;

import cn.aiyou.user.api.UserApi;
import cn.aiyou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserClient extends UserApi {

}
