package com.hit.lpm.recommend.dao;

import com.hit.lpm.portrait.model.Course;

import java.util.List;

public interface RecCourseMapper {
    // 随机选取3个课程的信息
    List<Course> selectRandomCourseInfo();
}
