package com.health.mapper;

import com.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupMapper {
    //分页查询检查组
    List<CheckGroup> findPage(@Param("queryString") String queryString);

    //添加检查组
    void add(CheckGroup checkGroup);

    //设置中间表
    void setCheckGroupAndCheckItem(@Param("checkItemIds") Integer[] checkItemIds, @Param("id") Integer id);

    //通过检查组id查询对应的检查项id
    List<Integer> findCheckItemIdsByCheckGroupId(@Param("id") Integer id);

    //编辑检查组
    void edit(CheckGroup checkGroup);

    //根据检查组id删除中间表数据
    void deleteCheckItemAndCheckGroupById(@Param("id") Integer id);

    //查询检查组有没有被套餐引用
    Integer findCountById(@Param("id") Integer id);

    //删除检查组
    void deleteById(@Param("id") Integer id);

    //查询所有检查组
    List<CheckGroup> findAll();
}
