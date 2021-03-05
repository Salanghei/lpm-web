package com.hit.lpm.portrait.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.portrait.model.StudentIntention;
import com.hit.lpm.portrait.model.StudentVideoRecord;
import com.hit.lpm.portrait.service.StudentIntentionService;
import com.hit.lpm.system.model.User;
import com.hit.lpm.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2021-1-6 11:07
 **/
@Api(value = "学习意图", tags = "intention")
@RestController
@RequestMapping("${api.version}/intention")
public class IntentionController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private StudentIntentionService studentIntentionService;

    @ApiOperation(value = "得到用户学习意图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/intention")
    @ResponseBody
    public JSONArray getStudentIntention(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentIntention> studentIntentions = studentIntentionService.selectList(
                new EntityWrapper<StudentIntention>().eq("student_id", stuId));
        JSONArray result = new JSONArray();
        if(studentIntentions != null) {
            result = JSONArray.parseArray(JSONObject.toJSONString(studentIntentions));
        }
        return result;
    }

}
