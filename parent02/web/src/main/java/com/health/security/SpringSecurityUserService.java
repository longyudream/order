package com.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.pojo.Permission;
import com.health.pojo.Role;
import com.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import com.health.pojo.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名查询信息
        User user = userService.findByUsername(username);
        //定义集合封装权限
        ArrayList<GrantedAuthority> list = new ArrayList<>();

        //获取所有角色
        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            //通过角色获取所有权限
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                //添加权限
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), list);
    }
}
