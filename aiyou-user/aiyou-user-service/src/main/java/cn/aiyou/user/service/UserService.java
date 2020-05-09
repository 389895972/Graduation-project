package cn.aiyou.user.service;

import cn.aiyou.common.entity.Result;
import cn.aiyou.common.utils.NumberUtils;
import cn.aiyou.user.mapper.UserMapper;
import cn.aiyou.user.pojo.User;
import cn.aiyou.user.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final  String KEY_PREFIX="user:verify:";

    public Boolean checkUser(String data, Integer type) {
        User record = new User();
        if(type==1){
            record.setUsername(data);
        }else if(type==2){
            record.setPhone(data);
        }else {
            return null;
        }
        return this.userMapper.selectCount(record)==0;

    }

    public void sendVerifyCode(String phone) {
        if(StringUtils.isBlank(phone)){
            return;
        }
        //生成验证码
        System.out.println(333);
        String code = NumberUtils.generateCode(6);
        //发送消息到rabbitmq
        Map<String,String> msg=new HashMap<>();
        msg.put("phone",phone);
        msg.put("code",code);
        this.amqpTemplate.convertAndSend("aiyou.sms.exchange","verifycode.sms",msg);

        //把验证码保存到redis中
        this.redisTemplate.opsForValue().set(KEY_PREFIX+phone,code,5, TimeUnit.MINUTES);

    }

//    public void register(User user, String code) {
//        System.out.println("phone"+user.getPhone());
//        String redisCode= this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
//        //校验验证码
//        System.out.println(11111111);
//        System.out.println(user.getPhone());
//        System.out.println(redisCode);
//        if(!StringUtils.equals(code,redisCode))  {
//           System.out.println(222);
//          return;
//        }
//        //生成盐
//        String salt = CodecUtils.generateSalt();
//         user.setSalt(salt);
//        //加盐加密
//        user.setPassword(CodecUtils.md5Hex(user.getPassword(),salt));
//        //新增用户
//        user.setId(null);
//        user.setIdcard(user.getIdcard());
//        user.setName(user.getName());
//        user.setCreated(new Date());
//        System.out.println(user.getUsername());
//        this.userMapper.insertSelective(user);
//    }



     public Result register(User user, String code) {
        System.out.println("phone"+user.getPhone());
        String redisCode= this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        //校验验证码
//        System.out.println(11111111);
//        System.out.println(user.getPhone());
//        System.out.println(redisCode);
        if(!StringUtils.equals(code,redisCode))  {
           System.out.println(222);
          return new Result(false,201,"验证码错误",null);
        }
        //生成盐
        String salt = CodecUtils.generateSalt();
         user.setSalt(salt);
         System.out.println(user.getPassword());
        //加盐加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(),salt));
        //新增用户
        user.setId(null);
        user.setIdcard(user.getIdcard());
        user.setName(user.getName());
        user.setCreated(new Date());
         System.out.println(9999);
        System.out.println(user.getUsername());
        this.userMapper.insertSelective(user);
        return new Result(true,200,"注册成功",null);
    }

//    public User queryUser(String username, String password) {
//        User record=new User();
//        record.setUsername(username);
//        User user = this.userMapper.selectOne(record);
//        //判断user 是否为空
//
//        if(user==null){
//            return null;
//        }
//
//        //获取盐，对用户输入密码加盐加密
//        password=CodecUtils.md5Hex(password,user.getSalt());
//
//        //和数据库密码比较
//        if(StringUtils.equals(password,user.getPassword())){
//            return user;
//        }
//        return null;
//    }

    public User queryUser(String phone, String password) {
        User record=new User();
        record.setPhone(phone);
        User user = this.userMapper.selectOne(record);
        System.out.println(user);
        //判断user 是否为空
        if(user==null){
            System.out.println("3333333333333");
            return null;
        }

        //获取盐，对用户输入密码加盐加密
        password=CodecUtils.md5Hex(password,user.getSalt());

        //和数据库密码比较
        if(StringUtils.equals(password,user.getPassword())){
            return user;
        }

        return null;
    }

    public List<User> queryAllUser(int page) {
        PageHelper.startPage(page,10);
        List<User> users = this.userMapper.selectAll();
        return users;
    }
}
