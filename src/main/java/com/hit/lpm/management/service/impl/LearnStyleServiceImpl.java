package com.hit.lpm.management.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.management.dao.Course2Mapper;
import com.hit.lpm.management.dao.LearnStyleMapper;
import com.hit.lpm.management.model.Course2;
import com.hit.lpm.management.model.LearnStyle;
import com.hit.lpm.management.service.Course2Service;
import com.hit.lpm.management.service.LearnStyleService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LearnStyleServiceImpl extends ServiceImpl<LearnStyleMapper, LearnStyle> implements LearnStyleService {

    @Override
    public List selectStyleOfId(Integer stuId) {
        return baseMapper.selectStyleOfId(stuId);
    }



}
