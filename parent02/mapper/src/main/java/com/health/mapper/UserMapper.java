package com.health.mapper;

import com.health.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //通过用户名查询用户信息
    User findByUsername(@Param("username") String username);

    //查询用户信息
    List<User> findPage(@Param("queryString") String queryString);

    //新增会员
    void add(User user);

    //设置用户-角色表
    void setUserAndRole(@Param("id") Integer id, @Param("role") Integer role);

    //根据用户id查询对应角色
    List<Integer> findRolesByUserId(@Param("id") Integer id);

    //根据用户名查询用户信息
    Integer[] queryUserByUsername(@Param("username") String username);

    void edit(User user);

    void deleteById(@Param("id") Integer id);

    void deleteUserAndRole(@Param("id") Integer id);

    void addUserAndRole(@Param("id") Integer id, @Param("roles") Integer[] roles);

    List<Integer> findRoleAndRoles(@Param("id") Integer id);

    void addUserAndMenu(@Param("id") Integer id, @Param("menus") Integer[] menus);
}
