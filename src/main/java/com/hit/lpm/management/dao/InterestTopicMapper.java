package com.hit.lpm.management.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.management.model.InterestTopic;
import com.hit.lpm.management.model.LearnStyle;

import java.util.List;

public interface InterestTopicMapper extends BaseMapper<InterestTopic> {

    List selectTopicOfId(Integer stuId);
}
