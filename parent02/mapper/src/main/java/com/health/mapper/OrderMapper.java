package com.health.mapper;

import com.health.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    //条件查询用户是否已经预约
    Order findByCondition(Order order);

    //添加预约
    void add(Order order);

    //根据id查询预约信息
    Map findById(@Param("id") Integer id);

    //今日预约数
    Integer findTodayOrderNumber(@Param("today") String today);

    //今日到诊数
    Integer findTodayVisitsNumber(@Param("today") String today);

    //本周和本月预约数
    Integer findOrderNumberAfterDate(@Param("date") String date);

    //本周到诊数
    Integer findVisitsNumberAfterDate(@Param("date") String date);

    //热门套餐
    List<Map<String, Object>> findHotSetmeal();



}
