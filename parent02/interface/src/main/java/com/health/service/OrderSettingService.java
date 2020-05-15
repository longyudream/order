package com.health.service;

import com.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    //新增预约数据
    void importOrderSettings(List<OrderSetting> orderSettings);

    //查询当月预约数据
    List<Map> findOrderSettingByMonth(String date);

    //根据日期更新预约数量
    void updateNumberByOrderDate(OrderSetting orderSetting);

}
