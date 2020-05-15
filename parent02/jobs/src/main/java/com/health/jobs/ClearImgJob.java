package com.health.jobs;

import com.health.mapper.OrderSettingMapper;
import com.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Date;

@Component
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    public void clearImg(){
       /* //对resdis中存储的图片的两个set集合进行比较，获取垃圾图片名称
        Set<String> imgSet = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        //遍历差值，获取其中图片名称
        for (String s : imgSet) {
            //删除七牛云中的图片
            QiniuUtils.deleteFileFromQiniu(s);
            //删除redis中多余的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
        }*/
        //每月最后一天凌晨2点 删除预约数据
        try {
            Date today = DateUtils.getToday();
            String date = DateUtils.parseDate2String(today, "yyyy-MM-dd");
            orderSettingMapper.deleteOrderSettingByToday(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
