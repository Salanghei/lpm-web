package com.hit.lpm.recommend.service;

import com.hit.lpm.portrait.model.Course;

import java.util.List;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-4 22:13
 **/
public interface RecCourseService {
    // 随机选取3个课程的信息
    List<Course> getRandomCourseInfo();
}
