package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.entity.Result;
import com.health.pojo.Menu;
import com.health.service.MenuService;
import com.health.constant.MessageConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Reference
    private MenuService menuService;
    @RequestMapping("findAll")
    public Result findAll(){
        try {
            List<Menu> list = menuService.findAll();
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_MENU_FAIL);
        }
    }

}
