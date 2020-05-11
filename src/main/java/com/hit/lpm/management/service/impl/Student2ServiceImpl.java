package com.hit.lpm.management.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.management.dao.Student2Mapper;
import com.hit.lpm.management.model.Student2;
import com.hit.lpm.management.service.Student2Service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class Student2ServiceImpl extends ServiceImpl<Student2Mapper, Student2> implements Student2Service {
    @Override
    public List selectAllProvinceAndCount() {
        return baseMapper.selectAllProvinceAndCount();
    }
    @Override

    public Integer selectAllCount(){return baseMapper.selectAllCount();}

    @Override
    public List selectSomeAttributeOfAllStudent(){return baseMapper.selectSomeAttributeOfAllStudent();}

}
