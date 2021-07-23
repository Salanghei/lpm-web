package com.hit.lpm.portrait.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.portrait.model.*;
import com.hit.lpm.portrait.service.*;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-10-13 19:57
 **/
@Api(value = "知识背景", tags = "knowledge")
@RestController
@RequestMapping("${api.version}/knowledge")
public class KnowledgeController extends BaseController {

    @Autowired
    private  KnowledgeNodeService knowledgeNodeService;
    @Autowired
    private StudentKnowledgeRelationService studentKnowledgeRelationService;
    @Autowired
    private UserService userService;


    @ApiOperation(value = "查询知识点及掌握程度")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping
    public PageResult<Map<String, Object>> listKnowledge(HttpServletRequest request) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        //Integer stuId = studentService.selectOne(new EntityWrapper<Student>().eq("user_id", user.getUsername())).getStudentId();
        Integer stuId = 1;
       if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentKnowledgeRelation> sks = studentKnowledgeRelationService
                .selectList(new EntityWrapper<StudentKnowledgeRelation>().eq("student_id", stuId));
        for (StudentKnowledgeRelation sk : sks) {
            Map<String, Object> map = new HashMap<>();
            KnowledgeNode node = knowledgeNodeService.selectById(sk.getNodeId());
            KnowledgeNode pnode = knowledgeNodeService.selectById(node.getParentId());
            map.put("knowledge_node", node.getName());
            if(pnode!=null)map.put("parent_knowledge_node", pnode.getName());
            map.put("score", sk.getScore());
            maps.add(map);
        }
        return new PageResult<>(maps);
    }

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseStructureService courseStructureService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @ApiOperation(value = "知识背景")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/background")
    @ResponseBody
    public JSONObject knowledgeBackground(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentCourse> scs = studentCourseService.selectList(
                new EntityWrapper<StudentCourse>().eq("student_id", stuId));
        JSONObject json = new JSONObject();
        json.put("name", "我");
        Double max = 0.0;
        JSONArray courses = new JSONArray();
        for(StudentCourse sc: scs){
            MyCourseStructure courseStructure = mongoTemplate.findOne(
                    Query.query(Criteria.where("course_id").is(sc.getCourseId())),
                    MyCourseStructure.class, "course");
            if(courseStructure == null)
                continue;
            JSONObject course = new JSONObject();
            JSONArray chapters = new JSONArray();
            Double courseValue = 0.0;
            int chapterCount = 0;
            for(Chapter ch: courseStructure.getChapters()){
                JSONObject chapter = new JSONObject();
                JSONArray sections = new JSONArray();
                Double chapterValue = 0.0;
                int sectionCount = 0;
                for(Section se: ch.getSections()){
                    JSONObject section = new JSONObject();
                    List<CourseStructure> css = courseStructureService.selectList(
                            new EntityWrapper<CourseStructure>().eq("section_name", se.getSection_name()));
                    Double value = 0.0;
                    if(css != null){
                        for(CourseStructure cs: css) {
                            Double score = studentService.selectStudentProblemScore(stuId, cs.getProblemId());
                            if(score != null) {
                                value += score;
                            }
                        }
                    }
                    //System.out.println(se.getSection_name() + "\t" + value);
                    section.put("value", value / 10);
                    section.put("name", se.getSection_name());
                    sections.add(section);
                    max = Math.max(max, value / 10);
                    chapterValue += value / 10;
                    sectionCount ++;
                }
                chapterValue = new BigDecimal(sectionCount == 0? 0: chapterValue / sectionCount).setScale(2, RoundingMode.HALF_UP).doubleValue();
                chapter.put("name", ch.getChapter_name());
                chapter.put("children", sections);
                chapter.put("value", chapterValue);
                chapters.add(chapter);
                courseValue += chapterValue;
                chapterCount ++;
            }
            courseValue = new BigDecimal(chapterCount == 0? 0: courseValue / chapterCount).setScale(2, RoundingMode.HALF_UP).doubleValue();
            course.put("name", courseStructure.getCourse_name());
            course.put("children", chapters);
            course.put("value", courseValue);
            courses.add(course);
        }
        json.put("children", courses);
        json.put("max", max);
        return json;
    }

    @ApiOperation(value = "知识背景测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/backgroundtest")
    @ResponseBody
    public Knowledge knowledgeBackgroundTest(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        Knowledge knowledge = mongoTemplate.findOne(
                Query.query(Criteria.where("student_id").is(stuId)),
                Knowledge.class, "knowledge");
        return knowledge;
    }
}
