package com.health.mapper;

import com.health.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    //获取角色信息
    List<Role> findAll();

    //设置角色-权限表
    void setRoleAndPermission(@Param("roleid") Integer roleid, @Param("permissions") Integer[] permissions);

    //添加角色
    void add(Role role);

    //根据角色获取权限信息
    List<Integer> findPermissionsByRoleId(@Param("roleId") Integer roleId);

    List<Role> findPage(@Param("queryString") String queryString);

    //查询角色对应的权限
    List<Integer> findPermissionIdsByRoleId(@Param("id") Integer id);

    //设置角色、页面中间表
    void setRoleAndMenu(@Param("id") Integer id, @Param("menuIds") Integer[] menuIds);

    //获取角色对应的页面
    List<Integer> findMenuIdsByRoleId(@Param("id") Integer id);

    //编辑角色
    void edit(Role role);

    //删除角色对应的权限信息
    void deletePermissionIdByRoleId(@Param("id") Integer id);

    //删除角色对应的页面信息
    void deleteMenuIdByRoleId(Integer id);

    //删除角色信息
    void deleteById(@Param("id") Integer id);

    Integer queryRoleAndUserById(@Param("id") Integer id);

    void setRole(@Param("id") Integer id, @Param("permissions") Integer permissionId);

    void setMenuAndRole(@Param("id") Integer id, @Param("menuId") Integer menuId);
}
