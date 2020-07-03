package com.hit.lpm.management.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.management.dao.InterestDomainMapper;

import com.hit.lpm.management.model.InterestDomain;

import com.hit.lpm.management.service.InterestDomainService;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InterestDomainServiceImpl extends ServiceImpl<InterestDomainMapper, InterestDomain> implements InterestDomainService {

    @Override
    public List selectDomainOfId(Integer stuId) {
        return baseMapper.selectDomainOfId(stuId);
    }



}
