package com.health.mapper;

import com.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    //套餐分页数据
    List<Setmeal> findByCondition(@Param("queryString") String queryString);


    //新增套餐
    void add(Setmeal setmeal);

    //设置中间表关系
    void setSetmealAndCheckGroup(@Param("checkGroupIds") Integer[] checkGroupIds, @Param("id") Integer id);

    //查询所有套餐
    List<Setmeal> findSetmealAll();

    //通过套餐id查询套餐、检查项和检查组
    Setmeal findById(Integer id);

    List<Map> getSetmealReport();

}
