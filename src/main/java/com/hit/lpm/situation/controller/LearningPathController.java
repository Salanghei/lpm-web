package com.hit.lpm.situation.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.situation.model.CourseEdge;
import com.hit.lpm.situation.model.CourseNode;
import com.hit.lpm.situation.service.LearningPathService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        int userid = baseController.getLoginUserId(request);
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
        return jsonObject;
    }

}
