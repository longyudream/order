package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.health.service.UserService;
import com.health.entity.PageResult;
import com.health.mapper.MenuMapper;
import com.health.mapper.RoleMapper;
import com.health.mapper.UserMapper;
import com.health.pojo.Menu;
import com.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.health.pojo.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 通过用户名查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 查询用户信息
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<User> UserList = userMapper.findPage(queryString);
        PageInfo<User> pageInfo = new PageInfo<>(UserList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增用户
     * @param
     * @param roles
     * @param user
     * @param
     */
    @Override
    public void add(Integer[] roles, User user) throws Exception {
        String password = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);
        Date birthday = user.getBirthday();
        String date = DateUtils.parseDate2String(birthday, "yyyy-MM-dd");
        Date date1 = DateUtils.parseString2Date(date, "yyyy-MM-dd");
        user.setBirthday(date1);
        user.setPassword(encode);
        //添加用户
        userMapper.add(user);
        userMapper.addUserAndRole(user.getId(),roles);

    }


    //设置用户-角色表
    public void setRoleAndPermission(Integer roleid, Integer[] permissions) {
        roleMapper.setRoleAndPermission(roleid, permissions);
    }

    //设置用户-角色表
    public void setUserAndRole(Integer id, Integer role) {
        userMapper.setUserAndRole(id, role);
    }

    //根据用户id查询对应角色信息和权限信息
    @Override
    public Map<String, List<Integer>> findRolesAndPermissionsByUserId(Integer id) {
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> roleIds = userMapper.findRolesByUserId(id);
        ArrayList<Integer> Permissions = new ArrayList<>();
        for (Integer roleId : roleIds) {
            List<Integer> list = roleMapper.findPermissionsByRoleId(roleId);
            for (Integer integer : list) {
                Permissions.add(integer);
            }
        }
        map.put("Roles", roleIds);
        map.put("Permissions", Permissions);
        return map;
    }

    @Override
    public boolean queryUserByUsername(String username) {
        Integer[] count = userMapper.queryUserByUsername(username);
        if (count.length > 0) {
            return false;
        }
        return true;
    }

    @Override
    public void edit(User user, Integer roles) {
        userMapper.edit(user);
        //删除中间表数据
        userMapper.deleteUserAndRole(user.getId());
        //设置中间表
        userMapper.setUserAndRole(user.getId(),roles);
    }

    @Override
    public void deleteById(Integer id) {
        //删除中间表数据
        userMapper.deleteUserAndRole(id);
        userMapper.deleteById(id);

    }

    @Override
    public List<Menu> getMenus(String username) {
        List<Menu> list = menuMapper.getMenus(username);
        return list;
    }

    @Override
    public List<Integer> findRoleAndRoles(Integer id) {
        return userMapper.findRoleAndRoles(id);
    }
}
