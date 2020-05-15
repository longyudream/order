package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.health.entity.PageResult;
import com.health.mapper.CheckItemMapper;
import com.health.pojo.CheckItem;
import com.health.service.CheckItemService;
import com.health.constant.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * 分页查询检查项
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<CheckItem> checkItemList = checkItemMapper.findPage(queryString);
        PageInfo<CheckItem> pageInfo = new PageInfo<>(checkItemList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增检查项
     *
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    //更新检查项
    @Override
    public void edit(CheckItem checkItem) {
        checkItemMapper.edit(checkItem);
    }

    /**
     * 删除检查项
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //查询检查项有没有被检查组引用
        Integer count =  checkItemMapper.queryCheckitemAndCheckGroupById(id);
        if (count>0){
            throw new RuntimeException(MessageConstant.CHECKITEM_IS_QUOTED);
        }
        checkItemMapper.deleteById(id);
    }

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> list = checkItemMapper.findAll();
        return list;
    }
}
