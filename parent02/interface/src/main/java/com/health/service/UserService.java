package com.health.service;


import com.health.entity.PageResult;
import com.health.pojo.Menu;
import com.health.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User findByUsername(String username);

    //查询用户信息
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    //新增用户
    void add(Integer[] roles, User user)throws Exception;

    //根据用户id查询对应角色
    Map<String,List<Integer>> findRolesAndPermissionsByUserId(Integer id);

    //根据用户名查询用户信息
    boolean queryUserByUsername(String username);

    void edit(User user, Integer roles);

    //删除用户
    void deleteById(Integer id);

    List<Menu> getMenus(String username);

    List<Integer> findRoleAndRoles(Integer id);
}
