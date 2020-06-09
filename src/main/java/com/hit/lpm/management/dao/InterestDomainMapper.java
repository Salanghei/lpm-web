package com.hit.lpm.management.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.hit.lpm.management.model.InterestDomain;

import java.util.List;

public interface InterestDomainMapper extends BaseMapper<InterestDomain> {

    List selectDomainOfId(Integer stuId);
}
