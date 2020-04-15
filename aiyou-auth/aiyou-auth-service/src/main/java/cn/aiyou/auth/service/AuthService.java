package cn.aiyou.auth.service;

import cn.aiyou.auth.client.UserClient;
import cn.aiyou.auth.config.JwtProperties;
import cn.aiyou.common.pojo.UserInfo;
import cn.aiyou.common.utils.JwtUtils;
import cn.aiyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    public String accredit(String phone, String password) {
        //1.根据用户名和密码查询
        User user = this.userClient.queryUser(phone, password);

        //2.判断user是否为空
        if(user==null){
            return null;
        }
        try {
            //3.jwtutils生成jwt类别的token
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            return JwtUtils.generateToken(userInfo,this.jwtProperties.getPrivateKey(),this.jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
