package com.hit.lpm.management.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.management.dao.InterestTopicMapper;
import com.hit.lpm.management.dao.LearnStyleMapper;
import com.hit.lpm.management.model.InterestTopic;
import com.hit.lpm.management.model.LearnStyle;
import com.hit.lpm.management.service.InterestTopicService;
import com.hit.lpm.management.service.LearnStyleService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InterestTopicServiceImpl extends ServiceImpl<InterestTopicMapper, InterestTopic> implements InterestTopicService {

    @Override
    public List selectTopicOfId(Integer stuId) {
        return baseMapper.selectTopicOfId(stuId);
    }



}
