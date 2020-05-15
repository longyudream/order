package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.health.entity.PageResult;
import com.health.mapper.CheckGroupMapper;
import com.health.pojo.CheckGroup;
import com.health.service.CheckGroupService;
import com.health.constant.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupMapper checkGroupMapper;

    /**
     * 分页检查组
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<CheckGroup> checkGroupList = checkGroupMapper.findPage(queryString);
        PageInfo<CheckGroup> pageInfo = new PageInfo<>(checkGroupList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加检查组
     *
     * @param checkItemIds
     * @param checkGroup
     */
    @Override
    public void add(Integer[] checkItemIds, CheckGroup checkGroup) {
        //添加检查组
        checkGroupMapper.add(checkGroup);
        //设置中间表关系
        setCheckGroupAndCheckItem(checkItemIds, checkGroup.getId());
    }

    //设置中间表
    private void setCheckGroupAndCheckItem(Integer[] checkItemIds, Integer id) {
        checkGroupMapper.setCheckGroupAndCheckItem(checkItemIds, id);
    }

    //查询检查组对应的检查项
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupMapper.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(Integer[] checkItemIds, CheckGroup checkGroup) {
        //编辑检查组
        checkGroupMapper.edit(checkGroup);

        //根据检查组id删除中间表数据
        checkGroupMapper.deleteCheckItemAndCheckGroupById(checkGroup.getId());
        //重新设置中间表数据
        setCheckGroupAndCheckItem(checkItemIds, checkGroup.getId());
    }

    //删除检查组
    @Override
    public void deleteById(Integer id) {
        //查询检查组有没有被套餐引用
        Integer count = checkGroupMapper.findCountById(id);
        if (count > 0){
            throw new RuntimeException(MessageConstant.CHECKGROUP_IS_QUOTED);
        }else{
            //删除中间表
            checkGroupMapper.deleteCheckItemAndCheckGroupById(id);
            //删除检查组
            checkGroupMapper.deleteById(id);
        }
    }

    @Override
    public List<CheckGroup> findAll() {
       return checkGroupMapper.findAll();
    }
}
