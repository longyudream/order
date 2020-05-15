package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.entity.Result;
import com.health.pojo.Setmeal;
import com.health.service.SetmealService;
import com.health.constant.MessageConstant;
import com.health.constant.RedisConstant;
import com.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("setmeal")
public class SetmealController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private SetmealService setmealService;

    /**
     * 查询套餐分页数据
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @RequestMapping("upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file) {
        try {
            //获取上传文件的文件名
            String originalFilename = file.getOriginalFilename();
            //获取后缀名
            int i = originalFilename.indexOf(".");
            String suffix = originalFilename.substring(i);
            //拼接一个唯一的文件名称
            String fileName = UUID.randomUUID() + suffix;
            //上传图片
            QiniuUtils.upload2Qiniu(file.getBytes(), fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 新增套餐
     *
     * @param checkGroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestParam("checkgroupIds") Integer[] checkGroupIds, @RequestBody Setmeal setmeal) {
        try {
            setmealService.add(checkGroupIds, setmeal);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

}
