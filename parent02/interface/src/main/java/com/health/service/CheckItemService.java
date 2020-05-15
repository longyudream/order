package com.health.service;

import com.health.entity.PageResult;
import com.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    //查询检查项
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    //新增检查项
    void add(CheckItem checkItem);

    //更新检查项
    void edit(CheckItem checkItem);

    //删除检查项
    void deleteById(Integer id);

    //查询所有检查项
    List<CheckItem> findAll();
}
