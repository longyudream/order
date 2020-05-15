package com.health.mapper;

import com.health.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberMapper {
    //根据手机号查询会员是否存在
    Member findByTelephone(@Param("telephone") String telephone);

    //会员注册
    void add(Member member);

    //查询每个月的会员数量
    int findMemberCountByMonth(@Param("monthBegin") String monthBegin,@Param("monthEnd") String monthEnd);

    //今日新增会员数数
    Integer findTodayNewMember(@Param("today") String today);

    //获取总会员数
    Integer findTotalMember();

    //本周和本月新增会员数
    Integer findNewMemberCountAfterDate(@Param("date") String Date);

    List<Map> getMemberSexReport();

    List<Map> getMemberYearsReport();

}
