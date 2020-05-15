package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.health.entity.PageResult;
import com.health.mapper.PermissionMapper;
import com.health.pojo.Menu;
import com.health.pojo.Permission;
import com.health.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    //获取所有权限信息
    @Override
    public List<Menu> findAll() {

        return permissionMapper.findAll();
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<Permission> checkGroupList = permissionMapper.findPage(queryString);
        PageInfo<Permission> pageInfo = new PageInfo<>(checkGroupList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加权限信息
     * @param permission
     */
    @Override
    public void add(Permission permission) {
        permissionMapper.add(permission);
    }

    @Override
    public void edit(Permission permission) {
        permissionMapper.edit(permission);
    }

    @Override
    public void deleteById(Integer id) throws Exception{
        Integer count = permissionMapper.queryRoleAndPermissionById(id);
        if (count > 0){
            throw new RuntimeException("当前权限有角色使用，不能删除");
        }
        permissionMapper.deleteById(id);
    }
}
