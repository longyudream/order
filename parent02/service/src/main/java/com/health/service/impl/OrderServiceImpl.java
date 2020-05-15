package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.mapper.OrderMapper;
import com.health.mapper.OrderSettingMapper;
import com.health.mapper.MemberMapper;
import com.health.pojo.Member;
import com.health.pojo.Order;
import com.health.pojo.OrderSetting;
import com.health.service.OrderService;
import com.health.constant.MessageConstant;
import com.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * * 1、检查用户所选择的预约日期是否已经提供了预约设置，如果没有设置则无法进行预约
     * 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
     * 3、检查当前用户是否为会员
     * 不是会员：自动完成注册，
     * 是会员：检查用户是否重复预约（通过member_id、orderDate、setmeal_id，查询同一个会员在当天是不是预约了同一个套餐），如果是重复预约则无法完成再次预约
     * 4、添加预约
     * 需要手动设置预约状态、预约类型、会员编号、预约日期、套餐编号
     * 5、预约成功，更新当日的已预约人数
     * <p>
     * 添加预约信息
     *
     * @param map
     * @return
     */
    @Override
    public Order add(Map map) throws Exception {
        //获取日期
        String orderDate = (String) map.get("orderDate");

        //将字符串日期转换成日期对象
        Date date = DateUtils.parseString2Date(orderDate);

        //通过日期查询是否可以预约
        OrderSetting orderSetting = orderSettingMapper.findByOrderDate(date);

        if (orderSetting == null) {
            //如果无法预约就抛出异常
            throw new RuntimeException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //判断预约是否已满
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            //抛出异常
            throw new RuntimeException(MessageConstant.ORDER_FULL);
        }
        //通过手机号查询会员
        String telephone = (String) map.get("telephone");
        Member member = memberMapper.findByTelephone(telephone);

        //判断是否是会员
        if (member == null) {
            //如果不是会员，注册为会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(new Date());
            member.setSex((String) map.get("sex"));
            member.setName((String) map.get("name"));

            //执行注册
            memberMapper.add(member);
        } else {
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(date);
            order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));

            //如果是会员，查询是否重复预约
            Order queryOrder = orderMapper.findByCondition(order);
            if (queryOrder != null) {
                throw new RuntimeException(MessageConstant.HAS_ORDERED);
            }
        }
        //添加预约
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType((String) map.get("orderType"));

        orderMapper.add(order);

        //预约成功后更新预约数量
        orderSettingMapper.updateReservationsByOrderDate(date);
        return order;
    }

    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderMapper.findById(id);
        Date date = (Date) map.get("orderDate");
        map.put("orderDate",DateUtils.parseDate2String(date));
        return map;
    }
}
