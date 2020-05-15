package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.mapper.MenuMapper;
import com.health.pojo.Menu;
import com.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> findAll() {

        return menuMapper.findAll();
    }
}
