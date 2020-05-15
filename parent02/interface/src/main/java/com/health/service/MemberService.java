package com.health.service;

import com.health.pojo.Member;

import java.util.Map;

public interface MemberService {
    //获取会员数据
    Map getMemberReport();

    Map getSetmealReport();


    Map getMember2Report(Map<String, String> a) throws Exception;

    Map getMemberSexReport();

    Map getMemberYearsReport();

    Member findByTelephone(String telephone);

    void add(Member member);


}
