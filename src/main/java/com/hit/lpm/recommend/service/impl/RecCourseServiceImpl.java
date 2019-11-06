package com.hit.lpm.recommend.service.impl;

import com.hit.lpm.potrait.model.Course;
import com.hit.lpm.recommend.dao.RecCourseMapper;
import com.hit.lpm.recommend.service.RecCourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-5 10:58
 **/
@Service
public class RecCourseServiceImpl implements RecCourseService {
    @Resource
    private RecCourseMapper recCourseMapper;

    @Override
    public List<Course> getRandomCourseInfo(){
        return recCourseMapper.selectRandomCourseInfo();
    }
}
