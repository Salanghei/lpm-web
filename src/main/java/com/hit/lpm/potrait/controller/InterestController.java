package com.hit.lpm.potrait.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.potrait.model.*;
import com.hit.lpm.potrait.service.*;
import com.hit.lpm.system.model.User;
import com.hit.lpm.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * @create: 2019-10-13 19:57
 **/
@Api(value = "兴趣意图", tags = "interest")
@RestController
@RequestMapping("${api.version}/interest")
public class InterestController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private DomainService domainService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentTopicRelationService studentTopicRelationService;
    @Autowired
    private StudentCourseRelationService studentCourseRelationService;


    @ApiOperation(value = "查询感兴趣的话题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/topic")
    public PageResult<Map<String, Object>> listTopic(HttpServletRequest request) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        //Integer stuId = studentService.selectOne(new EntityWrapper<Student>().eq("user_id", user.getUsername())).getStudentId();
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentTopicRelation> sts = studentTopicRelationService
                .selectList(new EntityWrapper<StudentTopicRelation>().eq("student_id", stuId));
        for (StudentTopicRelation st : sts) {
            Map<String, Object> map = new HashMap<>();
            Topic topic = topicService.selectById(st.getTopicId());
            //Domain domain = domainService.selectById(topic.getDomainId());
            map.put("topic", topic.getTopicName());
            map.put("domain", topic.getDomain());
            map.put("score", st.getScore());
            maps.add(map);
        }

        return new PageResult<>(maps);
    }

    @ApiOperation(value = "查询感兴趣的课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/course")
    public PageResult<Map<String, Object>> listCourse(HttpServletRequest request) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        //Integer stuId = studentService.selectOne(new EntityWrapper<Student>().eq("user_id", user.getUsername())).getStudentId();
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentCourseRelation> scs = studentCourseRelationService
                .selectList(new EntityWrapper<StudentCourseRelation>().eq("student_id", stuId));
        for (StudentCourseRelation sc : scs) {
            Map<String, Object> map = new HashMap<>();
            Course course = courseService.selectById(sc.getCourseId());
            //Domain domain = domainService.selectById(course.getDomainId());
            //Teacher teacher = teacherService.selectById(course.getTeacherId());
            map.put("course", course.getCourseName());
            map.put("startTime", course.getStartTime());
            map.put("domain", course.getCourseType());
            map.put("teacher", course.getTeacher());
            map.put("score", sc.getScore());
            maps.add(map);
        }
        return new PageResult<>(maps);
    }

    @ApiOperation(value = "查询感兴趣的领域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/domain")
    public PageResult<Map<String, Object>> listDomain(HttpServletRequest request) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        //Integer stuId = studentService.selectOne(new EntityWrapper<Student>().eq("user_id", user.getUsername())).getStudentId();
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentCourseRelation> scs = studentCourseRelationService
                .selectList(new EntityWrapper<StudentCourseRelation>().eq("student_id", stuId));
        List<StudentTopicRelation> sts = studentTopicRelationService
                .selectList(new EntityWrapper<StudentTopicRelation>().eq("student_id", stuId));
        Map<String, Integer> domains = new HashMap<>();
        for (StudentCourseRelation sc : scs) {
            Course course = courseService.selectById(sc.getCourseId());
            //此处10是一个常数，用于缩小数字，今后可以考虑放到常数变量中
            String[] courseDomains = course.getCourseType().split("[^\\dA-Za-z\\u3007\\u4E00-\\u9FCB\\uE815-\\uE864]");
            for (String courseDomain:courseDomains) {
                domains.put(courseDomain, domains.getOrDefault(course.getDomainId(), 0) + sc.getScore() / 10);
            }
        }
        for (StudentTopicRelation st : sts) {
            Topic topic = topicService.selectById(st.getTopicId());
            //此处10是一个常数，用于缩小数字，今后可以考虑放到常数变量中
            domains.put(topic.getDomain(), domains.getOrDefault(topic.getDomain(), 0) + st.getScore() / 10);
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
