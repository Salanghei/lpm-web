package com.hit.lpm.management.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.management.model.Course2;
import com.hit.lpm.portrait.model.Course;

import java.util.List;

public interface Course2Service extends IService<Course2> {
    List selectAllTypeAndCount();

    List selectAllSchAndCount();
}
