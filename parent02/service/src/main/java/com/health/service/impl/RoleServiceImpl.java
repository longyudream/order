package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.health.service.RoleService;
import com.health.entity.PageResult;
import com.health.mapper.RoleMapper;
import com.health.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {

        return roleMapper.findAll();
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<Role> RoleList = roleMapper.findPage(queryString);
        PageInfo<Role> pageInfo = new PageInfo<>(RoleList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    //添加角色信息
    @Override
    public void add(Role role, Integer[] MenuIds, Integer[] permissionIds) {
        roleMapper.add(role);
        setRoleAndPermission(role.getId(), permissionIds);
        setRoleAndMenu(role.getId(), MenuIds);
    }

    //设置角色、页面中间表
    private void setRoleAndMenu(Integer id, Integer[] menuIds) {
        for (Integer menuId : menuIds) {
            roleMapper.setMenuAndRole(id,menuId);
        }
        //roleMapper.setRoleAndMenu(id, menuIds);
    }

    //设置角色-权限中间表
    private void setRoleAndPermission(Integer id, Integer[] permissionIds) {
        for (Integer permissionId : permissionIds) {
           // roleMapper.setRoleAndPermission(id,permissionIds);
            roleMapper.setRole(id,permissionId);
        }
        //roleMapper.setRoleAndPermission(id, permissionIds);
    }

    //查询角色对应的权限
    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer id) {
        List<Integer> list = roleMapper.findPermissionIdsByRoleId(id);
        return list;
    }

    //编辑角色信息
    @Override
    public void edit(Integer[] menuIds, Integer[] permissionIds, Role role) {
        //编辑角色信息
        roleMapper.edit(role);
        //删除角色对应的权限信息
        roleMapper.deletePermissionIdByRoleId(role.getId());
        //重新设置角色对应的权限信息
        //roleMapper.setRoleAndPermission(role.getId(), permissionIds);
        //删除角色对应的页面信息
        roleMapper.deleteMenuIdByRoleId(role.getId());
        //重新设置角色对应的页面信息
        roleMapper.setRoleAndMenu(role.getId(), menuIds);

    }

    //获取角色对应的页面
    @Override
    public List<Integer> findMenuIdsByRoleId(Integer id) {
        return roleMapper.findMenuIdsByRoleId(id);
    }

    //删除角色信息
    @Override
    public void deleteById(Integer id) throws Exception{
//        查询当前角色有没有没用户引用
        Integer count = roleMapper.queryRoleAndUserById(id);
        if (count > 0){
            throw new RuntimeException("当前角色被用户引用，不能删除！");
        }
        //删除中间表数据
        roleMapper.deleteMenuIdByRoleId(id);
        roleMapper.deletePermissionIdByRoleId(id);
        //删除角色信息
        roleMapper.deleteById(id);
    }
}
