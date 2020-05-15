package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.mapper.MemberMapper;
import com.health.mapper.SetmealMapper;
import com.health.pojo.Member;
import com.health.service.MemberService;
import com.health.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public Map getMemberReport() {
        //获取日历对象
        Calendar calendar = Calendar.getInstance();
        //设置日历往前推12个月
        calendar.add(Calendar.MONTH, -12);

        //定义一个list集合，存放过去12个月每一个月的月份
        List<String> months = new ArrayList<>();

        //定义一个list集合，存放过去12个月的会员数量
        List<Integer> memberCounts = new ArrayList<>();

        //遍历过去12个月的每一个月
        for (int i = 0; i < 12; i++) {
            //获取每一个月的时间
            Date time = calendar.getTime();
            //获取每一个月的月份
            String month = new SimpleDateFormat("yyyy-MM").format(time);

            //定义每一个月开始日期
            String monthBegin = month + "-1";
            //定义每一个月结束时间
            String monthEnd = month + "-31";

            //统计每个月人数
            int count = memberMapper.findMemberCountByMonth(monthBegin, monthEnd);

            //添加每一个月
            months.add(month);

            //添加每一个月的会员数量
            memberCounts.add(count);

            //每次循环都在日历的月份上+1，
            calendar.add(Calendar.MONTH, +1);
        }
        //创建一个map，将月份集合以及每个月会员数量存放其中
        HashMap<String, List> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCounts", memberCounts);
        return map;
    }

    @Override
    public Map getSetmealReport() {
        List<Map> list = setmealMapper.getSetmealReport();

        List<String> names = new ArrayList<>();

        for (Map map : list) {
            String name = (String) map.get("name");
            names.add(name);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("setmealNames",names);
        map.put("setmealCounts",list);
        return map;
    }
    @Override
    public Map getMember2Report(Map<String,String> a) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String value1 =  a.get("value1");
        String value2 =  a.get("value2");
        Calendar starDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        starDate.setTime(sdf.parse(value1));
        endDate.setTime(sdf.parse(value2));

        int r = endDate.get(Calendar.MONTH) - starDate.get(Calendar.MONTH);
        int m = (endDate.get(Calendar.YEAR) - starDate.get(Calendar.YEAR)) * 12;
        //两个时间段之间隔了abs个月
        int abs = Math.abs(m + r)+1;

        ArrayList<String> months = new ArrayList<>();
        List<Integer> memberCounts = new ArrayList<>();

        for (int i = 0; i < abs; i++) {
            //获取每个月的时间
            Date time = starDate.getTime();
            //获取每个月的月份
            String month =sdf.format(time);
            //定义每个月开始日期
            String monthBegin = month+"-1";
            //定义每个月结束时间
            String monthEnd = month+"-31";
            //统计每个月人数
            int count = memberMapper.findMemberCountByMonth(monthBegin,monthEnd);
            //添加每一个月
            months.add(month);
            //添加每一个月的会员数量
            memberCounts.add(count);
            //每次循环都在日历的月份上+1，如-11月,-10月，-9月，依次类推
            starDate.add(Calendar.MONTH,+1);
        }
        Map<String, List> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCounts",memberCounts);
        return map;
    }


    @Override
    public Map getMemberSexReport() {

        List<Map> memberSexCounts= memberMapper.getMemberSexReport();

        List<String> memberSexs =new ArrayList<>();

        for (Map map : memberSexCounts) {
            String name = (String) map.get("name");

            if(name.equals("1")){
                map.put("name","男");
                name="男";
            }else {
                map.put("name","女");
                name="女";
            }
            memberSexs.add(name);
        }

        Map map = new HashMap<>();

        map.put("memberSexCounts",memberSexCounts);
        map.put("memberSexs",memberSexs);
        return map;

    }

    @Override
    public Map getMemberYearsReport() {

        List<Map> memberYearsCounts = memberMapper.getMemberYearsReport();

        List<String> memberYears=new ArrayList<>();
        for (Map memberYearsCount : memberYearsCounts) {

            String name= (String) memberYearsCount.get("name");


//            String name = null;
//            try {
//                name = DateUtils.parseDate2String(date, "yyyy-MM");
//                memberYearsCount.put("name",name);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            memberYears.add(name);

        }

        Map map=new HashMap();
        map.put("memberYearsCounts",memberYearsCounts);
        map.put("memberYears",memberYears);

        return map;
    }


    @Override
    public Member findByTelephone(String telephone) {
        return memberMapper.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {

        //当添加一个会员的时候，对会员的登陆密码进行加密。
        if (member.getPassword()!=null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberMapper.add(member);
    }



}
