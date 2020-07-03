package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.StudentCourseMapper;
import com.hit.lpm.portrait.model.StudentCourse;
import com.hit.lpm.portrait.service.StudentCourseService;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {
}
