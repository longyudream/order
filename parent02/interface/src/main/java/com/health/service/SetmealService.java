package com.health.service;

import com.health.entity.PageResult;
import com.health.pojo.Setmeal;

public interface SetmealService {
    //查询套餐分页数据
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    //新增套餐
    void add(Integer[] checkGroupIds, Setmeal setmeal);

    //查询所有套餐
   String findSetmealAll();

    //通过id查询套餐、检查组和检查项
    String findById(Integer id);
}
