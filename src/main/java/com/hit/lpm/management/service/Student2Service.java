package com.hit.lpm.management.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.management.model.Student2;

import java.util.List;
import java.util.Map;


public interface Student2Service extends IService<Student2> {
    List selectAllProvinceAndCount();

    Integer selectAllCount();

    List selectSomeAttributeOfAllStudent();
}
