package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.entity.Result;
import com.health.pojo.Menu;
import com.health.pojo.Permission;
import com.health.service.PermissionService;
import com.health.constant.MessageConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    /**
     * 获取所有权限信息
     *
     * @return
     */
    @RequestMapping("findAll")
    public Result findAll() {
        try {
            List<Menu> menuList = permissionService.findAll();
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = permissionService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true,MessageConstant.QUERY_PERMISSION_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    /**
     * 添加权限信息
     * @param permission
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
            return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_PERMISSION_FAIL);
        }
    }
    @RequestMapping("edit")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
            return new Result(true,MessageConstant.EDIT_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }
    @RequestMapping("deleteById")
    public Result deleteById(@RequestParam("id") Integer id){
        try {
            permissionService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
        }catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }
}
