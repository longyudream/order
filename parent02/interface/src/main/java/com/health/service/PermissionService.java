package com.health.service;

import com.health.entity.PageResult;
import com.health.pojo.Menu;
import com.health.pojo.Permission;

import java.util.List;

public interface PermissionService {
    //获取所有权限信息
    List<Menu> findAll();

    //查询权限分页数据
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void add(Permission permission);

    void edit(Permission permission);

    void deleteById(Integer id)throws Exception;
}
