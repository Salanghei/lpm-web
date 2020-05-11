package com.hit.lpm.management.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.management.dao.Course2Mapper;
import com.hit.lpm.management.model.Course2;

import com.hit.lpm.management.service.Course2Service;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Course2ServiceImpl extends ServiceImpl<Course2Mapper, Course2> implements Course2Service {

    @Override
    public List selectAllTypeAndCount() {
        return baseMapper.selectAllTypeAndCount();
    }
    @Override
    public List selectAllSchAndCount(){return baseMapper.selectAllSchAndCount();};
}
