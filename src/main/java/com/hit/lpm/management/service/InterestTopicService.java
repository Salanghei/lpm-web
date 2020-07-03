package com.hit.lpm.management.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.management.model.InterestTopic;
import com.hit.lpm.management.model.LearnStyle;

import java.util.List;

public interface InterestTopicService extends IService<InterestTopic> {
    List selectTopicOfId(Integer stuId);
}
