package com.health.mapper;

import com.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {
    //检查当前预约日期在数据库中是否存在
    Integer findCountByOrderDate(@Param("orderDate") Date orderDate);
    //已经存在就更新
    void updateNumberByOrderDate(OrderSetting orderSetting);
    //不存在就新建
    void add(OrderSetting orderSetting);

    //获取预约数据
    List<OrderSetting> findOrderSettingByMonth(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd);

    //查询当前日期是否可以预约
    OrderSetting findByOrderDate(@Param("date") Date date);

    //预约成功后更新后台数据
    void updateReservationsByOrderDate(@Param("date") Date date);
    //清除之前的预约设置数据
    void deleteOrderSettingByToday(@Param("date") String date);

}
