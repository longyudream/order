package com.health.service;


import com.health.entity.PageResult;
import com.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    //分页查询检查组
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    //添加检查组
    void add(Integer[] checkItemIds, CheckGroup checkGroup);

    //查询检查组所对应的检查项
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    //编辑检查组
    void edit(Integer[] checkItemIds, CheckGroup checkGroup);

    //删除检查组
    void deleteById(Integer id);

    //查询所有检查组
    List<CheckGroup> findAll();
}
