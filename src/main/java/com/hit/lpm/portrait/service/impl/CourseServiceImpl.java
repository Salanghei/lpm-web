package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.CourseMapper;
import com.hit.lpm.portrait.model.Course;
import com.hit.lpm.portrait.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-10-13 21:41
 **/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    private CourseMapper courseMapper;

    @Override
    public List<String> getCourseType(){
        //System.out.println(courseMapper.hashCode());
        return baseMapper.selectCourseType();
    }

}
