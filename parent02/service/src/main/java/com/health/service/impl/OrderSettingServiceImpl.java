package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.mapper.OrderSettingMapper;
import com.health.pojo.OrderSetting;
import com.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingMapper orderSettingMapper;

    /**
     * 新增预约数据
     *
     * @param orderSettings
     */
    @Override
    public void importOrderSettings(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            //检查当前预约日期是否存在，存在就修改，不存在就新建
            Integer count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
            if (count > 0) {
                //已经存在
                orderSettingMapper.updateNumberByOrderDate(orderSetting);
            } else {
                //不存在
                orderSettingMapper.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map> findOrderSettingByMonth(String date) {
        //定义月份第一天
        String dateBegin = date + "-1";
        //定义月份最后一天
        String dateEnd = date + "-31";

        //同当前月份的第一天和最后一天查询当前月的所有的预约数据
        List<OrderSetting> orderSettings = orderSettingMapper.findOrderSettingByMonth(dateBegin, dateEnd);

        List list = new ArrayList();

        //遍历每一个Order Setting，将数据封装到map中
        for (OrderSetting orderSetting : orderSettings) {
            Map<String, Object> map = new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());//获取几号
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());

            //将所有数据封装到list中
            list.add(map);
        }
        return list;
    }

    /**
     * 根据日期设置预约数量
     * @param orderSetting
     */
    @Override
    public void updateNumberByOrderDate(OrderSetting orderSetting) {
        //根据日期统计之前是否设置过预约数量
        Integer count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if (count>0){
            //如果设置过就执行更新
            orderSettingMapper.updateNumberByOrderDate(orderSetting);
        }else{
            //如果之前设置过就执行新增
            orderSettingMapper.add(orderSetting);
        }
    }
}
