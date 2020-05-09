package cn.aiyou.auth.controller;

import cn.aiyou.auth.config.JwtProperties;
import cn.aiyou.auth.service.AuthService;
import cn.aiyou.common.utils.CookieUtils;
import cn.aiyou.common.utils.JwtUtils;
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
    public ResponseEntity<UserInfo> verify(@RequestBody JSONObject object){

        String token = object.getAsString("token");
        UserInfo user = null;
        try {
            user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());

            if(user==null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            }
            //刷新jwt中有效时间
           // token= JwtUtils.generateToken(user, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());
            //刷新cookie中的有效时间
           // CookieUtils.setCookie(request,response,token,this.jwtProperties.getCookieName(),this.jwtProperties.getExpire()*60);
            return ResponseEntity.ok(user);
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
