package com.hit.lpm.recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.model.Course;
import com.hit.lpm.recommend.dao.RecClusterMapper;
import com.hit.lpm.recommend.dao.RecCourseMapper;
import com.hit.lpm.recommend.model.RecCluster;
import com.hit.lpm.recommend.model.RecCourse;
import com.hit.lpm.recommend.service.RecClusterService;
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
public class RecCourseServiceImpl extends ServiceImpl<RecCourseMapper, RecCourse> implements RecCourseService {
//    @Resource
//    private RecCourseMapper recCourseMapper;
//
//    @Override
//    public List<Course> getRandomCourseInfo(){
//        return recCourseMapper.selectRandomCourseInfo();
//    }
}
