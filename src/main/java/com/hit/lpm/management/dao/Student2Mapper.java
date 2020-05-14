package com.hit.lpm.management.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.management.model.Student2;

import java.util.List;


public interface Student2Mapper extends BaseMapper<Student2> {
    List selectAllProvinceAndCount();
    Integer selectAllCount();
    List selectSomeAttributeOfAllStudent();
}
