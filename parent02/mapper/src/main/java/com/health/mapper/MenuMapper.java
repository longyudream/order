package com.health.mapper;

import com.health.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    List<Menu> findAll();

    List<Menu> getMenus(@Param("username") String username);
}
