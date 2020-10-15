package com.hit.lpm.portrait.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.portrait.model.Course;

import java.util.List;

public interface CourseService extends IService<Course> {
    List<String> getCourseType();
}
