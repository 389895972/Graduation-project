package cn.aiyou.user.controller;

import cn.aiyou.common.entity.Result;
import cn.aiyou.user.pojo.User;
import cn.aiyou.user.service.UserService;

import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

@Controller
//@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data")String data,@PathVariable("type") Integer type){

        Boolean bool=this.userService.checkUser(data,type);
       if(bool==null){
           return ResponseEntity.badRequest().build();
       }
       return ResponseEntity.ok(bool);
    }



    @GetMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone")String phone){
        this.userService.sendVerifyCode(phone);
        System.out.println(1111);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

//    @PostMapping("register")
//    public ResponseEntity<Void> register(User user, @RequestParam("code")String code){
//        System.out.println(user.getUsername());
//        System.out.println("dd"+code);
//       this.userService.register(user,code);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    //用户注册
    @PostMapping("register/{code}")
    public ResponseEntity<Result> register(@RequestBody User user, @PathVariable("code")String code){
        Boolean bool=this.userService.checkUser(user.getPhone(),2);
        if(!bool){
            return ResponseEntity.ok(new Result(false,201,"手机号已注册",null));
        }
        Result result = this.userService.register(user, code);
        return ResponseEntity.ok(result);
    }

//    @PostMapping("login")
//    public ResponseEntity<Result> login(@RequestParam("username")String username,@RequestParam("password")String password){
//        User user=this.userService.queryUser(username,password);
//
//        if(user==null){
//
//            return ResponseEntity.badRequest().body(new Result(false,201,"用户名或密码错误",null));
//        }
//
//        return ResponseEntity.ok(new Result(true,200,"登录成功",user));
//    }

    @PostMapping("login")

   // public ResponseEntity<Result> login(@RequestParam("phone")String phone,@RequestParam("password")String password){
   public ResponseEntity<Result> login(@RequestBody JSONObject json)  {
//    public ResponseEntity<Result> login(@RequestBody Map<String,String> map ) throws JSONException {
//        System.out.println(map.toString());
//        System.out.println(map);
//        User user=this.userService.queryUser(map.get("phone"),map.get("password"));

        User user=this.userService.queryUser(json.getAsString("phone"),json.getAsString("password"));

        if(user==null){

            return ResponseEntity.badRequest().body(new Result(false,201,"用户名或密码错误",null));
        }

        return ResponseEntity.ok(new Result(true,200,"登录成功",null));
    }


    @PostMapping("admin/login")
    public ResponseEntity<Result> adminLogin(@RequestBody JSONObject json)  {
        if("admin".equals(json.getAsString("username")) &&"admin".equals(json.getAsString("password")) ){
            return ResponseEntity.ok(new Result(true,200,"登录成功",null));
        }
        return ResponseEntity.badRequest().build();
    }
    @RequestMapping("test")
    public ResponseEntity<Result> test(){
         User user = new User();
         user.setPhone("15536830820");
         user.setId(12123l);
         user.setPassword("123456");
        Result result = new Result();
        result.setCode(2000);
        result.setData(user);
        return ResponseEntity.ok(result);
     }

    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("phone")String phone,@RequestParam("password")String password){
        User user=this.userService.queryUser(phone,password);
        System.out.println("1111111"+phone);
        System.out.println("22222"+password);
        System.out.println(user);
        if(user==null){
            System.out.println(9999999);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }
    @GetMapping("queryAllUser/{page}")
    public ResponseEntity<List<User>> queryAllUser(@PathVariable int page){
        List<User> users= this.userService.queryAllUser(page);
        return ResponseEntity.ok(users);
    }


    @GetMapping("personGetUser")
    @ResponseBody
    public User getUser(@RequestParam("id") Long id){
           User user=this.userService.queryUserInfo(id);
           return user;
    }


    @GetMapping("updatePwd")
    @ResponseBody
    public  ResponseEntity<Integer>  modifyPwd(
            @RequestParam("id") Long id,
            @RequestParam(value = "oldPwd",required =false) String oldPwd,
            @RequestParam(value = "newPwd",required =false) String newPwd
    ){
      Integer i=this.userService.modifyPwd(id,oldPwd,newPwd);
        return ResponseEntity.ok(i);
    }

    @GetMapping("modifyUser")
    public  ResponseEntity<Integer>  modifyUser(
            @RequestParam("id") Long id,
            @RequestParam(value = "username",required =false) String username,
            @RequestParam(value = "name",required =false) String name,
            @RequestParam(value = "idcard",required =false) String idcard,
            @RequestParam(value = "phone",required =false) String phone
    ){
       if(!StringUtils.isEmpty(username)){
         Integer flag= this.userService.modifyUser(id,username,"username");
           System.out.println(username);
         return ResponseEntity.ok(flag);
       }
        if(!StringUtils.isEmpty(name)){
            System.out.println(name);
            Integer flag= this.userService.modifyUser(id,name,"name");
            return ResponseEntity.ok(flag);
        }
        if(!StringUtils.isEmpty(idcard)){
            Integer flag= this.userService.modifyUser(id,idcard,"idcard");
            return ResponseEntity.ok(flag);
        }
        if(!StringUtils.isEmpty(phone)){
            Integer flag= this.userService.modifyUser(id,phone,"phone");
            return ResponseEntity.ok(flag);
        }
        return ResponseEntity.ok(0);
    }
}
