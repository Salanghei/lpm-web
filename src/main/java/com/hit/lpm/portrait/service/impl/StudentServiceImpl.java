package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.CourseMapper;
import com.hit.lpm.portrait.dao.StudentCourseRelationMapper;
import com.hit.lpm.portrait.dao.StudentMapper;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.model.StudentWithCourse;
import com.hit.lpm.portrait.service.StudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private CourseMapper courseMapper;
    private StudentCourseRelationMapper relationMapper;

    @Override
    public List<String> selectKeywordsByStuId(Integer stuId) {
        return baseMapper.selectKeywordsByStuId(stuId);
    }

    @Override
    public Double selectStudentProblemScore(Integer stuId, String problemId){
        return  baseMapper.selectStudentProblemScore(stuId, problemId);
    }

    @Override
    public List<String> selectStudentCourses(Integer stuId){
        return baseMapper.selectStudentCourses(stuId);
    }

    @Override
    public StudentWithCourse selectSC(Integer stuId) {
        StudentWithCourse result = new StudentWithCourse();
//        EntityWrapper<StudentCourseRelation> wrapper = new EntityWrapper();
//        wrapper.eq("student_id",stuId);
//        List<StudentCourseRelation> scList = relationMapper.selectList(wrapper);
//        String course = "";
//        for (StudentCourseRelation c : scList) {
//            course += courseMapper.selectById(c.getCourseId()).getCourseName() + " ";
//        }
//        result.setCourse(course);
        return result;
    }
}
