package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.entity.Result;
import com.health.pojo.Role;
import com.health.service.RoleService;
import com.health.constant.MessageConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {
    @Reference
    private RoleService roleService;

    @RequestMapping("findAll")
    public Result findAll() {
        try {
            List<Role> roleList = roleService.findAll();
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    //获取角色分页信息
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = roleService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    @RequestMapping("add")
    public Result add(@RequestParam("PermissionIds") Integer[] PermissionIds, @RequestParam("MenuIds") Integer[] MenuIds, @RequestBody Role role) {
        try {
            roleService.add(role, PermissionIds, MenuIds);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
    }

    //查询角色对应的权限
    @RequestMapping("findPermissionIdsByRoleId")
    public Result findPermissionIdsByRoleId(@RequestParam("id") Integer id) {
        try {
            List<Integer> list = roleService.findPermissionIdsByRoleId(id);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    //编辑角色信息
    @RequestMapping("edit")
    public Result edit(@RequestParam("PermissionIds") Integer[] PermissionIds, @RequestParam("MenuIds") Integer [] MenuIds, @RequestBody Role role) {
        try {
            roleService.edit(PermissionIds,MenuIds, role);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
    }

    //获取角色对应的页面
    @RequestMapping("findMenuIdsByRoleId")
    public Result findMenuIdsByRoleId(@RequestParam("id") Integer id) {
        try {
            List<Integer> list = roleService.findMenuIdsByRoleId(id);
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }
    @RequestMapping("deleteById")
    public Result deleteById(@RequestParam("roleId") Integer id){
        try {
            roleService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ROLE_FAIL);
        }
    }
}
