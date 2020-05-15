package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.constant.RedisMessageConstant;
import com.health.entity.Result;
import com.health.pojo.Member;
import com.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("login")
public class MobileLoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping("check")
    public Result check(@RequestBody Map map){

        try {
            //获取手机号
            String telephone = (String) map.get("telephone");
            //获取验证码
            String validateCode = (String) map.get("validateCode");

            String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);

            //比较用户提交的验证码和redis中的验证码是否相同
            if (code ==null || !code.equals(validateCode)){
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }

            //通过手机号查询是否是会员
            Member member = memberService.findByTelephone(telephone);
            if (member==null){
                //如果不是会员，注册为会员
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);

            }
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.LOGIN_ERROR);
        }

    }
}
