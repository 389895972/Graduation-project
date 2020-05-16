package cn.aiyou.auth.controller;

import cn.aiyou.auth.client.UserClient;
import cn.aiyou.auth.client.UserClient1;
import cn.aiyou.auth.config.JwtProperties;
import cn.aiyou.auth.service.AuthService;

import cn.aiyou.common.utils.JwtUtils;
import cn.aiyou.user.pojo.User;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import cn.aiyou.common.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
//@CrossOrigin
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserClient userClient;
//    //登录
//    @PostMapping("accredit")
//    public ResponseEntity<Void> accredit(
//            @RequestParam("username")String username,
//            @RequestParam("password")String password,
//            HttpServletRequest request,
//            HttpServletResponse response
//    ){
//        String token= this.authService.accredit(username,password);
//        if(StringUtils.isBlank(token)){
//            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        //CookieUtils.setCookie(request,response,this.jwtProperties.getCookieName(),token,this.jwtProperties.getExpire()*60);
//        return ResponseEntity.ok(null);
//    }

    //登录
    @PostMapping("accredit")
    public ResponseEntity<String> accredit(
            @RequestParam("phone")String phone,
            @RequestParam("password")String password
    ){

        String token= this.authService.accredit(phone,password);
        if(StringUtils.isBlank(token)){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println(token);
        return ResponseEntity.ok(token);
    }

    @PostMapping("verify")
    public ResponseEntity<User> verify(@RequestBody JSONObject object){

        String token = object.getAsString("token");
        UserInfo user = null;
        try {
            user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());

            User users=this.authService.queryUser(user.getId());
            if(users==null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            }
            //刷新jwt中有效时间
           // token= JwtUtils.generateToken(user, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());
            //刷新cookie中的有效时间
           // CookieUtils.setCookie(request,response,token,this.jwtProperties.getCookieName(),this.jwtProperties.getExpire()*60);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }



    @PostMapping("updatePwd")
    @ResponseBody
    public ResponseEntity<Integer> updatePwd(@RequestBody JSONObject object){
        String token = object.getAsString("token");
        String oldPwd = object.getAsString("oldPwd");
        String newPwd = object.getAsString("newPwd");
        UserInfo user = null;
        System.out.println("dddd"+newPwd);
        try {
            user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            Integer i=this.userClient.modifyPwd(Long.valueOf(user.getId()),oldPwd,newPwd);
            return ResponseEntity.ok(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping("modifyUser")
    public ResponseEntity<Integer> modifyUser(
            @RequestBody JSONObject object,
            @RequestParam(value = "username",required =false) String username,
            @RequestParam(value = "name",required =false) String name,
            @RequestParam(value = "idcard",required =false) String idcard,
            @RequestParam(value = "phone",required =false) String phone
    ){

        String token = object.getAsString("token");
        UserInfo user = null;
        try {
            user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());

            if(!StringUtils.isEmpty(username)){
                Integer flag = userClient.modifyUser(Long.valueOf(user.getId()), username,null,null,null);
                return ResponseEntity.ok(flag);
            }
            if(!StringUtils.isEmpty(name)){
                Integer flag = userClient.modifyUser(Long.valueOf(user.getId()), null,name,null,null);
                return ResponseEntity.ok(flag);
            }
            if(!StringUtils.isEmpty(idcard)){
                Integer flag = userClient.modifyUser(Long.valueOf(user.getId()), null,null,idcard,null);
                return ResponseEntity.ok(flag);
            }
            if(!StringUtils.isEmpty(phone)){
                Integer flag = userClient.modifyUser(Long.valueOf(user.getId()), null,null,null,phone);
                return ResponseEntity.ok(flag);
            }
            return ResponseEntity.ok(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
//    @GetMapping("verify")
//    public ResponseEntity<UserInfo> verify(
//            @CookieValue("LY_TOKEN")String token,
//            HttpServletRequest request,
//            HttpServletResponse response){
//        UserInfo user = null;
//        try {
//            user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
//
//            if(user==null){
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//
//            }
//            //刷新jwt中有效时间
//            token= JwtUtils.generateToken(user, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());
//            //刷新cookie中的有效时间
//            CookieUtils.setCookie(request,response,token,this.jwtProperties.getCookieName(),this.jwtProperties.getExpire()*60);
//            return ResponseEntity.ok(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }

}
