package com.health.mapper;

import com.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemMapper {
    //分页查询检查项
    List<CheckItem> findPage(@Param("queryString") String queryString);

    //新增检查项
    void add(CheckItem checkItem);

    //更新检查项
    void edit(CheckItem checkItem);

    //删除检查项
    void deleteById(@Param("id") Integer id);

    /**
     * 查询检查项
     * @return
     */
    List<CheckItem> findAll();

    //查询检查项有没有被检查组引用
    Integer queryCheckitemAndCheckGroupById(@Param("id") Integer id);
}
