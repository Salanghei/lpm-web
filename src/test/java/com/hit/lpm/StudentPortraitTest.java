package com.hit.lpm;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hit.lpm.portrait.model.Course;
import com.hit.lpm.portrait.model.StudentCourseRelation;
import com.hit.lpm.portrait.model.StudentPortrait;
import com.hit.lpm.portrait.service.CourseService;
import com.hit.lpm.portrait.service.StudentCourseRelationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CloseableIterator;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-12-04 15:10
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentPortraitTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentCourseRelationService studentCourseRelationService;

    //生成学习者感兴趣课程
    @Test
    public void generateStudentCourses() throws FileNotFoundException {
        Map<String, Course> courseMap = getCourseMap();
        System.out.println("courseMap" + courseMap.size());
//        List<StudentPortrait> studentPortraitList = mongoTemplate.findAll(StudentPortrait.class);
//        System.out.println("studentPortraitList"+studentPortraitList.size());
//        for (StudentPortrait studentPortrait : studentPortraitList) {}
        int line = 0;
        CloseableIterator<StudentPortrait> closeableIterator = mongoTemplate.stream(Query.query(Criteria.where("")).noCursorTimeout(), StudentPortrait.class);
        while (closeableIterator.hasNext()){
            StudentPortrait studentPortrait = closeableIterator.next();
            List<StudentCourseRelation> scs = studentCourseRelationService
                    .selectList(new EntityWrapper<StudentCourseRelation>().eq("student_id", studentPortrait.getStudentId()));
            List<Course> courses = new LinkedList<>();
            for (StudentCourseRelation sc : scs) {
                Course course = courseMap.get(sc.getCourseId());
                courses.add(course);
            }
            studentPortrait.setCourses(courses);
            mongoTemplate.save(studentPortrait);
            if (line % 10000 == 0) System.out.println(line);
            line++;
        }
        //StudentPortrait studentPortrait = mongoTemplate.findOne(Query.query(Criteria.where("studentId").is(2501145)), StudentPortrait.class);
    }

    private Map<String, Course> getCourseMap() {
        Map<String, Course> courseMap = new HashMap<>();
        int page = 0;
        int limit = 10000;
        EntityWrapper<Course> wrapper = new EntityWrapper<>();
        Page<Course> coursePage;
        do {
            coursePage = new Page<>(page, limit);
            System.out.println("当前查询到第" + (page * limit) + "行");
            courseService.selectPage(coursePage, wrapper);
            List<Course> courseList = coursePage.getRecords();
            for (Course course : courseList) {
                course.setCourseInfo(null);
                courseMap.put(course.getCourseId(), course);
            }
            page++;
        } while (coursePage.hasNext());
        return courseMap;
    }


}
