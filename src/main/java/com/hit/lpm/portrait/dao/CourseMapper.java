package com.hit.lpm.portrait.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.portrait.model.Course;

import java.util.List;

public interface CourseMapper extends BaseMapper<Course> {
    List<String> selectCourseType();

}
