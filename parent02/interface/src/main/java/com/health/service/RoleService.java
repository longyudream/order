package com.health.service;

import com.health.entity.PageResult;
import com.health.pojo.Role;

import java.util.List;

public interface RoleService {
    //获取所有角色信息
    List<Role> findAll();

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void add(Role role, Integer[] MenuIds, Integer[] permissionIds);

    //查询角色对应的权限
    List<Integer> findPermissionIdsByRoleId(Integer id);

    //编辑角色
    void edit(Integer[] MenuIds, Integer[] permissionIds, Role role);

    //获取角色对应的页面
    List<Integer> findMenuIdsByRoleId(Integer id);

    //删除角色
    void deleteById(Integer id)throws Exception;
}
