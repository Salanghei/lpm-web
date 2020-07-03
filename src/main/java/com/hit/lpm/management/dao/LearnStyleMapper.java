package com.hit.lpm.management.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.management.model.Course2;
import com.hit.lpm.management.model.LearnStyle;

import java.util.List;

public interface LearnStyleMapper extends BaseMapper<LearnStyle> {

    List selectStyleOfId(Integer stuId);
}
