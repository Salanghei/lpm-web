package com.hit.lpm.situation.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.situation.model.CourseEdge;
import com.hit.lpm.situation.model.CourseNode;
import com.hit.lpm.situation.model.KnowledgeDetail;
import com.hit.lpm.situation.model.KnowledgeNodes;
import com.hit.lpm.situation.service.LearningPathService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import io.swagger.models.auth.In;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "学习路径相关功能", tags = "learningpath")
@RestController
@RequestMapping("${api.version}/learningpath")
public class LearningPathController {
    @Autowired
    LearningPathService learningPathService;


    BaseController baseController = new BaseController();
    @ApiOperation(value = "获取学习路径")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getLearningPath")
    @ResponseBody
    public JSONObject getLearningPath(HttpServletRequest request){
        //int userid = baseController.getLoginUserId(request);
        int userid = 1;
        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();
        String courseId = "course-v1:SCUT+145033+2018_T1";
        List<CourseNode> list_node = learningPathService.getCourseNode(userid, courseId);
        List<CourseEdge> list_edge = learningPathService.getCourseEdge(courseId);
        for(CourseNode i : list_node){
            nodes.add(JSONObject.toJSON(i));
        }
        for(CourseEdge i : list_edge){
            edges.add(JSONObject.toJSON(i));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("node", nodes);
        jsonObject.put("edge", edges);
        jsonObject.put("courseName", learningPathService.getCourseName(courseId));
        jsonObject.put("courseId", courseId);
        return jsonObject;
    }

    @ApiOperation(value = "查看知识点详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "category", value = "所属类别", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "knowledgeId", value = "知识点ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "score", value = "掌握程度", required = true, dataType = "double", paramType = "query")
    })
    @GetMapping("/getKnowledgeDetail")
    @ResponseBody
    public KnowledgeDetail showKnowLedgeDetail(int category, int knowledgeId, String courseId, double score){
        KnowledgeDetail result = new KnowledgeDetail();
        courseId = courseId.replaceAll(" ", "+");
        String category_temp = "";
        if(category == 0){
            category_temp = "已完成学习";
        }
        else if(category == 1){
            category_temp = "可以开始学习";
        }
        else {
            category_temp = "不满足学习条件，不可学习";
        }
        result.setCategory(category_temp);
        result.setCourseId(courseId);
        result.setNodeId(knowledgeId);
        result.setCourseName(learningPathService.getCourseName(courseId)) ;
        KnowledgeNodes temp = learningPathService.getKnowledgeNodeById(knowledgeId, courseId);
        result.setImportance(temp.getImportance());
        result.setScore(score);
        result.setOffline(temp.getOffline() == 0 ? "无": "有");
        result.setVideoTime((int)(temp.getImportance()*60));
        result.setName(temp.getName());
        return result;
    }


    @ApiOperation(value = "完成对一个知识点的学习")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nodeId", value = "知识点ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, dataType = "String", paramType = "query"),

    })
    @PostMapping("/learnKnowledge")
    @ResponseBody
    public Map<String, String> learnKnowledgeNode(int nodeId, String courseId,HttpServletRequest request){
        int userid = baseController.getLoginUserId(request);
        courseId = courseId.replaceAll(" ", "+");
        int stuId = learningPathService.getStudentIdByUserId(String.valueOf(userid));
        learningPathService.learnKnowledge(stuId, nodeId, courseId);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "success");
        return map;
    }
}
