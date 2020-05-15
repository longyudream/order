package com.health.service;

import com.health.pojo.Order;

import java.util.Map;

public interface OrderService {
    //添加预约信息
    Order add(Map map)throws Exception;

    //根据id查询预约信息
    Map findById(Integer id) throws Exception;
}
