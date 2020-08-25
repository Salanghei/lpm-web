package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.DomainMapper;
import com.hit.lpm.portrait.dao.StudentSelectCourseMapper;
import com.hit.lpm.portrait.model.Domain;
import com.hit.lpm.portrait.model.StudentSelectCourse;
import com.hit.lpm.portrait.service.DomainService;
import com.hit.lpm.portrait.service.StudentSelectCourseService;
import org.springframework.stereotype.Service;

@Service
public class StudentSelectCourseImpl extends ServiceImpl<StudentSelectCourseMapper, StudentSelectCourse> implements StudentSelectCourseService {
}
