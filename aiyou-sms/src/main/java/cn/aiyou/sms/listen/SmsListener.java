package cn.aiyou.sms.listen;

import cn.aiyou.sms.config.SmsProperties;
import cn.aiyou.sms.utils.SmsUtils;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import java.util.Map;

@Component
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties smsProperties;

    @RabbitListener(bindings=@QueueBinding(
            value = @Queue(value = "aiyou.sms.queue",durable = "true"),
            exchange = @Exchange(value ="aiyou.sms.exchange",ignoreDeclarationExceptions ="true",type = ExchangeTypes.TOPIC),
            key = {"verifycode.sms"}
    ))
    public void sendSms(Map<String,String> msg) throws ClientException {
           if(CollectionUtils.isEmpty(msg)){
               return;
           }
        String phone = msg.get("phone");
        String code = msg.get("code");
        if(StringUtils.isNoneBlank(phone)&&StringUtils.isNoneBlank(code)){
           this.smsUtils.sendSms(phone,code,this.smsProperties.getSignName(),this.smsProperties.getVerifyCodeTemplate());
        }
    }
}
