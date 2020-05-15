package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.service.ReportService;
import com.health.mapper.MemberMapper;
import com.health.mapper.OrderMapper;
import com.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Map getBusinessReport() throws Exception {
        /**
         * reportDate,//今日日期
         * todayNewMember,//今日新增会员数
         * totalMember,//总会员数
         * thisWeekNewMember,//本周新增会员数
         * thisMonthNewMember,//本月新增会员数
         * todayOrderNumber,//今日预约数
         * todayVisitsNumber,//今日到诊数
         * thisWeekOrderNumber,//本周预约数
         * thisWeekVisitsNumber,//本周到诊数
         * thisMonthOrderNumber,//本月预约数
         * thisMonthVisitsNumber,//本月到诊数
         * hotSetmeal:热门套餐4个
         */
        //获取今日日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());

        //获取本周第一天
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());

        //获取本月第一天
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        //获取今日新增会员数
        Integer todayNewMember = memberMapper.findTodayNewMember(today);
        //获取总会员数
        Integer totalMember = memberMapper.findTotalMember();

        //本周新增会员数
        Integer thisWeekNewMember = memberMapper.findNewMemberCountAfterDate(thisWeekMonday);
        //本月新增会员数
        Integer thisMonthNewMember = memberMapper.findNewMemberCountAfterDate(firstDay4ThisMonth);

        //今日预约数
        Integer todayOrderNumber = orderMapper.findTodayOrderNumber(today);
        //今日到诊数
        Integer todayVisitsNumber = orderMapper.findTodayVisitsNumber(today);
        //本周预约数
        Integer thisWeekOrderNumber = orderMapper.findOrderNumberAfterDate(thisWeekMonday);
        //本月预约数
        Integer thisMonthOrderNumber = orderMapper.findOrderNumberAfterDate(firstDay4ThisMonth);
        //本周到诊数
        Integer thisWeekVisitsNumber = orderMapper.findVisitsNumberAfterDate(thisWeekMonday);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderMapper.findVisitsNumberAfterDate(firstDay4ThisMonth);

        //热门套餐
        List<Map<String, Object>> hotSetmeal = orderMapper.findHotSetmeal();

        Map<String, Object> result = new HashMap<>();

        result.put("reportDate", today);
        result.put("todayNewMember", todayNewMember);
        result.put("totalMember", totalMember);
        result.put("thisWeekNewMember", thisWeekNewMember);
        result.put("thisMonthNewMember", thisMonthNewMember);
        result.put("todayOrderNumber", todayOrderNumber);
        result.put("thisWeekOrderNumber", thisWeekOrderNumber);
        result.put("thisMonthOrderNumber", thisMonthOrderNumber);
        result.put("todayVisitsNumber", todayVisitsNumber);
        result.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        result.put("hotSetmeal", hotSetmeal);
        return result;
    }
}
