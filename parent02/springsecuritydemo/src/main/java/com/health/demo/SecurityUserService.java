package com.health.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class SecurityUserService implements UserDetailsService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private HashMap map = new HashMap();

    //将map作为数据库，使用initData()方法
    public void initData(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));

        User user1 = new User();
        user1.setUsername("lisi");
        user1.setPassword(bCryptPasswordEncoder.encode("1234"));

        map.put(user.getUsername(),user);
        map.put(user1.getUsername(),user1);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        initData();

        User user = (User) map.get(username);

        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        list.add(new SimpleGrantedAuthority("add"));

        //声明使用明文密码
        //String password = "{noop}"+user.getPassword();

        org.springframework.security.core.userdetails.User datailUser = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),list);
        return datailUser;
    }

}

