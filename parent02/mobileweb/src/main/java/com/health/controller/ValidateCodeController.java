package com.health.controller;

import com.health.entity.Result;
import com.health.constant.MessageConstant;
import com.health.constant.RedisMessageConstant;
import com.health.utils.SMSUtils;
import com.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("send1telephone")
    public Result send1telephone(@RequestParam("telephone")String telephone){
        try {
            //通过工具类随机获取一个验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            //调用工具类，发送验证码到手机
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
            String key = telephone+ RedisMessageConstant.SENDTYPE_ORDER;
            //将验证码保存到redis中保存10分钟
            jedisPool.getResource().setex(key,60*10,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("send2telephone")
    public Result send2telephone(@RequestParam("telephone")String telephone){
        try {
            //通过工具类随机获取一个验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            //调用工具类，发送验证码到手机
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
            String key = telephone+ RedisMessageConstant.SENDTYPE_LOGIN;
            //将验证码保存到redis中保存10分钟
            jedisPool.getResource().setex(key,60*10,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
