package com.hit.lpm.management.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.management.model.LearnStyle;

import java.util.List;

public interface LearnStyleService extends IService<LearnStyle> {
    List selectStyleOfId(Integer stuId);
}
