package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.entity.Result;
import com.health.pojo.Order;
import com.health.service.OrderService;
import com.health.constant.MessageConstant;
import com.health.constant.RedisMessageConstant;
import com.health.utils.SMSUtils;
import com.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderMobileController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("add")
    public Result add(@RequestBody Map map) {
        try {
            //获取手机号
            String telephone = (String) map.get("telephone");
            //设置redis中的key
            String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;

            //从缓存中获取验证码
            String code = jedisPool.getResource().get(key);
            System.out.println("code==================== " + code);
            //获取用户输入的验证码
            String validateCode = (String) map.get("validateCode");
            System.out.println("validateCode ============= " + validateCode);
            //判断验证码是的过期，以及输入的验证码是否正确
            if (code == null || !code.equals(validateCode)) {
                //验证码过期或者验证码不正确
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            } else {
                //验证码输入正确
                //开始预约
                Order order = orderService.add(map);

                //预约成功后发送短信通知
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, ValidateCodeUtils.generateValidateCode(4).toString());
                return new Result(true, MessageConstant.ADD_ORDER_SUCCESS, order);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ORDER_FAIL);
        }
    }

    /**
     * 根据id查询预约信息
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Result findById(@RequestParam Integer id) {
        try {
            Map map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
