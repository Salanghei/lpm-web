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
        JSONArray courses = new JSONArray();
        for(StudentCourse sc: scs){
            MyCourseStructure courseStructure = mongoTemplate.findOne(
                    Query.query(Criteria.where("course_id").is(sc.getCourseId())),
                    MyCourseStructure.class, "course");
            if(courseStructure == null)
                continue;
            JSONObject course = new JSONObject();
            JSONArray chapters = new JSONArray();
            for(Chapter ch: courseStructure.getChapters()){
                JSONObject chapter = new JSONObject();
                JSONArray sections = new JSONArray();
                for(Section se: ch.getSections()){
                    JSONObject section = new JSONObject();
                    section.put("name", se.getSection_name());
                    section.put("value", 0);
                    sections.add(section);
                }
                chapter.put("name", ch.getChapter_name());
                chapter.put("children", sections);
                chapters.add(chapter);
            }
            course.put("name", courseStructure.getCourse_name());
            course.put("children", chapters);
            courses.add(course);
        }
        json.put("children", courses);
        return json;
    }
    /*public JSONObject knowledgeBackground(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        JSONObject json = new JSONObject();
        json.put("name", "我");
        List<StudentCourse> scs = studentCourseService.selectList(
                new EntityWrapper<StudentCourse>().eq("student_id", stuId));
        JSONArray children = new JSONArray();
        for(StudentCourse sc: scs){
            List<CourseStructure> css = courseStructureService.selectList(
                    new EntityWrapper<CourseStructure>().eq("course_id", sc.getCourseId()));
            if(css == null || css.size() == 0)
                continue;
            Map<String, List<Integer>> map = new HashMap<>();
            for(int i = 0; i < css.size(); i ++){
                CourseStructure cs = css.get(i);
                String chapter = cs.getChapterName();
                if(map.containsKey(chapter)){
                    map.get(chapter).add(i);
                }else{
                    List<Integer> sections = new ArrayList<>();
                    sections.add(i);
                    map.put(chapter, sections);
                }
            }
            JSONObject child = new JSONObject();
            JSONArray childArray = new JSONArray();
            Double myChildWeight = 0.0;
            for(Map.Entry<String, List<Integer>> entry: map.entrySet()){
                JSONObject childCell = new JSONObject();
                JSONArray childCellArray = new JSONArray();
                Double childWeight = 0.0;
                for(Integer i: entry.getValue()){
                    JSONObject j = new JSONObject();
                    Double score = studentService.selectStudentProblemScore(stuId, css.get(i).getProblemId());
                    Double weight = 0.0;
                    if(score != null)  // 保留小数点后两位
                        weight = new BigDecimal(score / css.get(i).getFullScore()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    j.put("name", css.get(i).getSectionName() + " [" + weight + "]");
                    j.put("value", weight);
                    childWeight += weight;
                    childCellArray.add(j);
                }
                childCell.put("children", childCellArray);
                childWeight = new BigDecimal(childWeight / entry.getValue().size()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                childCell.put("name", entry.getKey() + " [" + childWeight + "]");
                childCell.put("value", childWeight);
                myChildWeight += childWeight;
                childArray.add(childCell);
            }
            child.put("children", childArray);
            myChildWeight = new BigDecimal(myChildWeight / map.entrySet().size()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            child.put("name", css.get(0).getCourseName() + " [" + myChildWeight + "]");
            child.put("value", myChildWeight);
            children.add(child);
        }
        json.put("children", children);
        return json;
    }*/
}
