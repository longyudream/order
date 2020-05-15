package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.health.service.SetmealService;
import com.health.entity.PageResult;
import com.health.mapper.SetmealMapper;
import com.health.pojo.Setmeal;
import com.health.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        //分页信息
        List<Setmeal> list = setmealMapper.findByCondition(queryString);
        PageInfo<Setmeal> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    //新增套餐
    @Override
    public void add(Integer[] checkGroupIds, Setmeal setmeal) {
        //新增套餐
        setmealMapper.add(setmeal);
        //设置中间表关系
        setSetmealAndCheckGroup(checkGroupIds, setmeal.getId());
        if (setmeal.getImg() != null) {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        }
    }

    //设置中间表关系
    private void setSetmealAndCheckGroup(Integer[] checkGroupIds, Integer id) {
        setmealMapper.setSetmealAndCheckGroup(checkGroupIds, id);
    }

    /**
     * 查询所有套餐
     * @return
     */
    @Override
    public String findSetmealAll() {
        //        优先从redis缓存获取数据
        Jedis jedis = jedisPool.getResource();
        String json = jedis.get("setmealJson");
//        如果获取不到调用方法从数据库获取,并且保存到redis中
        if (json==null){
            List<Setmeal> setmealList1 = setmealMapper.findSetmealAll();
            json = JSON.toJSONString(setmealList1);
            jedis.set("setmealJson",json);
        }
        jedis.close();
        return json;

    }

    /**
     * 通过套餐id查询套餐、检查组和检查项
     * @param id
     * @return
     */
    @Override
    public String findById(Integer id) {
//        优先从redis缓存获取数据
        Jedis jedis = jedisPool.getResource();
        String setmeal= jedis.get("setmealJson"+id);
//        如果获取不到调用方法从数据库获取,并且保存到redis中
        if (setmeal==null){
            Setmeal setmealList = setmealMapper.findById(id);
            setmeal = JSON.toJSONString(setmealList);
            jedis.set("setmealJson"+id,setmeal);
        }
        jedis.close();
        return setmeal;
    }


}
