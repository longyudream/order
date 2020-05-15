package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.entity.Result;
import com.health.pojo.Menu;
import com.health.pojo.User;
import com.health.service.UserService;
import com.health.constant.MessageConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;

    /**
     * 在Principal中存储了当前登录用户的相关认证信息，将其配置在方法的参数位置，spring会自动将其注入进来
     *
     * @param principal
     * @return
     */
    @RequestMapping("getUsername")
    public Result getUsername(Principal principal) {
        try {
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * 查询用户信息
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = userService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }

    /**
     * 新增用户
     *
     * @param
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestParam("roles") Integer [] roles , @RequestBody User user) {
        try {
            userService.add(roles,user);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    //根据用户id查询对应角色
    @RequestMapping("findRolesAndPermissionsByUserId")
    public Result findRolesAndPermissionsByUserId(@RequestParam("id") Integer id) {
        try {

            Map<String,List<Integer>> map = userService.findRolesAndPermissionsByUserId(id);
            return new Result(true,MessageConstant.QUERY_ROLE_FAIL,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }
    @RequestMapping("queryUserByUsername")
    public Result queryUserByUsername(@RequestParam("username") String username){
        try {
            boolean b = userService.queryUserByUsername(username);
            return new Result(true,MessageConstant.QUERY_USER_SUCCESS,b);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_USER_FAIL);
        }
    }
    @RequestMapping("edit")
    public Result edit(@RequestParam("roles") Integer roles, @RequestBody User user){
        try {
            userService.edit(user,roles);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MEMBER_FAIL);
        }
    }
    @RequestMapping("deleteById")
    public Result deleteById(@RequestParam("id") Integer id){
        try {
            userService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_MEMBER_FAIL);
        }
    }
    @RequestMapping("getMenus")
    public Result getMenus(@RequestParam("username") String username){
        try {
            List<Menu> list = userService.getMenus(username);
            return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_MENU_FAIL);
        }
    }
    @RequestMapping("findRoleAndRoles")
    public Result findRoleAndRoles(@RequestParam("id") Integer id){

        try {
            List<Integer> list = userService.findRoleAndRoles(id);
            return new Result(true,MessageConstant.QUERY_USER_FAIL,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_USER_FAIL);
        }

    }
}
