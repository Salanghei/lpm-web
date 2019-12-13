package com.hit.lpm.portrait.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.portrait.model.Course;
import com.hit.lpm.portrait.model.StudentCourseRelation;
import com.hit.lpm.portrait.model.StudentPortrait;
import com.hit.lpm.portrait.model.Topic;
import com.hit.lpm.portrait.service.StudentCourseRelationService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-10-13 19:57
 **/
@Api(value = "兴趣意图", tags = "interest")
@RestController
@RequestMapping("${api.version}/interest")
public class InterestController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StudentCourseRelationService studentCourseRelationService;


    @ApiOperation(value = "查询感兴趣的话题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/topic")
    public PageResult<Topic> listTopic(HttpServletRequest request) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        StudentPortrait studentPortrait = mongoTemplate.findOne(Query.query(Criteria.where("studentId").is(stuId)), StudentPortrait.class);
        return new PageResult<>(studentPortrait.getTopics());
    }

    @ApiOperation(value = "查询感兴趣的课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/course")
    public List<Course> listCourse(HttpServletRequest request) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        StudentPortrait studentPortrait = mongoTemplate.findOne(Query.query(Criteria.where("studentId").is(stuId)), StudentPortrait.class);
        return studentPortrait.getCourses();
    }

    @ApiOperation(value = "查询感兴趣的领域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stuId", value = "ID", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping("/domain")
    public PageResult<Map<String, Object>> listDomain(HttpServletRequest request, Integer stuId) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        if (stuId == null) {
            stuId = 1;
            if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        }
        List<StudentCourseRelation> scs = studentCourseRelationService
                .selectList(new EntityWrapper<StudentCourseRelation>().eq("student_id", stuId));
        StudentPortrait studentPortrait = mongoTemplate.findOne(Query.query(Criteria.where("studentId").is(stuId)), StudentPortrait.class);
        Map<String, Integer> domains = new HashMap<>();
        for (Course course : studentPortrait.getCourses()) {
            for (String courseDomain : course.getDomains()) {
                domains.put(courseDomain, domains.getOrDefault(courseDomain, 0) + 10);
            }
        }
        for (Topic topic : studentPortrait.getTopics()) {
            domains.put(topic.getDomain(), domains.getOrDefault(topic.getDomain(), 0) + topic.getCount());
        }
        for (String domain : domains.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("domain", domain);
            map.put("info", domain);
            map.put("score", domains.get(domain));
            maps.add(map);
        }
        return new PageResult<>(maps);
    }

}
