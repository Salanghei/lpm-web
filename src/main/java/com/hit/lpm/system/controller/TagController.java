package com.hit.lpm.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.portrait.model.Knowledge;
import com.hit.lpm.portrait.model.StudentIntention;
import com.hit.lpm.portrait.service.StudentIntentionService;
import com.hit.lpm.system.model.IntentionNode;
import com.hit.lpm.system.model.StudentCourseTag;
import com.hit.lpm.system.model.StudentData;
import com.hit.lpm.system.model.StudentVideoBehavior;
import com.hit.lpm.system.service.StudentCourseTagService;
import com.hit.lpm.system.service.StudentVideoBehaviorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import java.util.*;

@Api(value = "标注", tags = "tag")
@RestController
@RequestMapping("${api.version}/tag")
public class TagController extends BaseController {
    @Autowired
    private StudentCourseTagService studentCourseTagService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StudentIntentionService studentIntentionService;

    @ApiOperation(value = "获取选课学生Id列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/getStudentList")
    @ResponseBody
    public List<StudentCourseTag> getStudentList(String courseId){
        List<StudentCourseTag> result = studentCourseTagService.selectList(
                new EntityWrapper<StudentCourseTag>().eq("course_id", courseId));
        return result == null? new ArrayList<StudentCourseTag>(): result;
    }

    @ApiOperation(value = "获取学生数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "studentId", value = "学生ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/studentData")
    @ResponseBody
    public StudentData getStudentData(String courseId, String studentId){
        StudentData result = mongoTemplate.findOne(
                Query.query(Criteria.where("course_id").is(courseId).and("student_id").is(Integer.valueOf(studentId))),
                StudentData.class, "addTags");
        return result;
    }

    @ApiOperation(value = "添加标注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "studentId", value = "学生ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tag", value = "标签", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/addTag")
    @ResponseBody
    public String addTag(String courseId, String studentId, String tag){
        StudentCourseTag studentCourseTag = new StudentCourseTag();
        studentCourseTag.setTag(Integer.valueOf(tag));
        studentCourseTagService.update(studentCourseTag,
                new EntityWrapper<StudentCourseTag>()
                        .eq("student_id", Integer.valueOf(studentId))
                        .eq("course_id", courseId));
        return "success";
    }

    @Autowired
    private StudentVideoBehaviorService studentVideoBehaviorService;

    @ApiOperation(value = "获取学生行为节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "studentId", value = "学生ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/studentVideoBehavior")
    @ResponseBody
    public JSONObject getStudentVideoBehavior(String studentId){
        List<StudentVideoBehavior> studentVideoBehaviors = studentVideoBehaviorService.selectList(
                new EntityWrapper<StudentVideoBehavior>().eq("student_id", Integer.valueOf(studentId)).orderBy("timestamp"));
        List<IntentionNode> nodes = new ArrayList<>();
        JSONArray edges = new JSONArray();
        List<String> categories = new ArrayList<>();
        if(studentVideoBehaviors != null){
            List<Integer> xArray = new ArrayList<>();
            List<String> chapterLst = new ArrayList<>();
            for(StudentVideoBehavior studentVideoBehavior: studentVideoBehaviors){
                String courseName = studentVideoBehavior.getCourseName();
                int cindex = courseName.indexOf('（');
                if(cindex > 0) {
                    courseName = courseName.substring(0, cindex);
                }
                cindex = courseName.indexOf('(');
                if(cindex > 0) {
                    courseName = courseName.substring(0, cindex);
                }
                //courseName = courseName.replaceAll("[\\(（][^\\)）]+[\\)）]$", "");
                //System.out.println(courseName);
                if(!categories.contains(courseName)){
                    categories.add(courseName);
                    xArray.add(0);
                }
                // 去掉课程名称中的括号
                String chapterName = studentVideoBehavior.getChapterName().replaceAll("\\s*", "");
                if(!chapterLst.contains(chapterName)) {
                    chapterLst.add(chapterName);
                    int index = categories.indexOf(courseName);
                    int x = xArray.get(categories.indexOf(courseName));
                    IntentionNode node = new IntentionNode();
                    node.setName(String.valueOf(chapterLst.indexOf(chapterName)));
                    node.setCategory(index);
                    node.setX(x);
                    node.setY(index * 65);
                    node.setChapterName(chapterName);
                    xArray.set(index, x + 65);
                    nodes.add(node);
                }
            }
            for(int i = 1; i < studentVideoBehaviors.size(); i ++){
                String chapterName0 = studentVideoBehaviors.get(i - 1).getChapterName().replaceAll("\\s*", "");
                String chapterName1 = studentVideoBehaviors.get(i).getChapterName().replaceAll("\\s*", "");
                JSONObject edge = new JSONObject();
                edge.put("source", String.valueOf(chapterLst.indexOf(chapterName0)));
                edge.put("target", String.valueOf(chapterLst.indexOf(chapterName1)));
                edges.add(edge);
            }
        }
        JSONObject result = new JSONObject();
        result.put("nodes", nodes);
        result.put("edges", edges);
        result.put("categories", categories);
        return result;
    }
}
