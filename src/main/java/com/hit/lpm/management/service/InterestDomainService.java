package com.hit.lpm.management.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.management.model.InterestDomain;
import com.hit.lpm.management.model.InterestTopic;

import java.util.List;

public interface InterestDomainService extends IService<InterestDomain> {
    List selectDomainOfId(Integer stuId);
}
