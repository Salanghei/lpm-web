package com.hit.lpm.portrait.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.portrait.model.*;
import com.hit.lpm.portrait.service.CourseService;
import com.hit.lpm.portrait.service.StudentSelectCourseService;
import com.hit.lpm.portrait.service.StudentService;
import com.hit.lpm.system.model.User;
import com.hit.lpm.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-11-13 15:09
 **/
@Api(value = "学生信息", tags = "student")
@RestController
@RequestMapping("${api.version}/student")
public class StudentController extends BaseController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StudentSelectCourseService studentSelectCourseService;

    //@RequiresPermissions("get:/v1/student/portrait")
    @ApiOperation(value = "查询画像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stuId", value = "ID", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping("/portrait")
    @ResponseBody
    public StudentPortrait studentPortrait(Integer stuId, HttpServletRequest request) {
        StudentPortrait studentPortrait = mongoTemplate.findOne(Query.query(Criteria.where("studentId").is(stuId)), StudentPortrait.class);
        return studentPortrait;
    }

    @ApiOperation(value = "查询个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/message")
    @ResponseBody
    public Student studentMessage(String id, HttpServletRequest request) {
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        if (!id.equals("")) stuId = Integer.valueOf(id);
        return studentService.selectById(stuId);
    }

    @ApiOperation(value = "查询个人信息（带有所选课程）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/messageWithCourse")
    @ResponseBody
    public StudentWithCourse stuMsgWithCourse(String id, HttpServletRequest request) {
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        if (!id.equals("")) stuId = Integer.valueOf(id);
        Student student =  studentService.selectById(stuId);
        Map<String,Object> map = new HashMap<>();
        map.put("student_id", student.getStudentId());
        List<StudentSelectCourse> courseList = studentSelectCourseService.selectByMap(map);
        String courses = "";
        for(StudentSelectCourse course: courseList){
            courses += course.getCourseName() + "；";
        }
        StudentWithCourse result = new StudentWithCourse();
        result.setCourse(courses);
        result.setProvince(student.getProvince());
        result.setNickname(student.getNickname());
        result.setGender(student.getGender());
        result.setCountry(student.getCountry());
        result.setCity(student.getCity());
        result.setBirthday(student.getBirthday());
        result.setEducation(student.getEducation());
        result.setStudentName(student.getStudentName());
        return result;
    }

    @RequiresPermissions("get:/v1/student")
    @ApiOperation(value = "查询所有学习者", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "searchKey", value = "筛选条件字段", dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "searchValue", value = "筛选条件关键字", dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @GetMapping()
    public PageResult<Student> list(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<Student> studentPage = new Page<>(page, limit);
        EntityWrapper<Student> wrapper = new EntityWrapper<>();
        if (searchKey != null && !searchKey.trim().isEmpty() && searchValue != null && !searchValue.trim().isEmpty()) {
            wrapper.eq(searchKey, searchValue);
        }
        //wrapper.orderBy("create_time", true);
        studentService.selectPage(studentPage, wrapper);
        List<Student> studentList = studentPage.getRecords();
        return new PageResult<>(studentList, studentPage.getTotal());
    }

}
