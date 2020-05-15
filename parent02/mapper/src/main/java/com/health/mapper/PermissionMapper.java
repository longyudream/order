package com.health.mapper;

import com.health.pojo.Menu;
import com.health.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    //获取所有权限信息
    List<Menu> findAll();

    //权限分页数据
    List<Permission> findPage(@Param("queryString") String queryString);

    //添加权限信息
    void add(Permission permission);

    void edit(Permission permission);

    //根据权限id查询权限有没有被角色引用
    Integer queryRoleAndPermissionById(Integer id);

    void deleteById(Integer id);
}
