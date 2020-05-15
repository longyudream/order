package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.entity.Result;
import com.health.service.SetmealService;
import com.health.constant.MessageConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("setmeal")
public class SetmealMobileController {
    @Reference
    private SetmealService setmealService;

    /**
     * 查询所有套餐
     *
     * @return
     */
    @RequestMapping("findSetmealAll")
    public Result findSetmealAll() {
        try {
            String setmealList = setmealService.findSetmealAll();
            return new Result(true, MessageConstant.QUERY_ORDERSETTING_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDERSETTING_FAIL);
        }
    }

    /**
     * 通过id查询套餐和检查组以及检查项
     *
     * @return
     */
    @RequestMapping("findById")
    public Result findById(@RequestParam("id") Integer id) {
        try {
            String setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }
}
