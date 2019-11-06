package com.hit.lpm.recommend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.potrait.model.Student;
import com.hit.lpm.potrait.service.StudentService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-14 19:07
 **/
@Api(value = "个人信息", tags = "message")
@RestController
@RequestMapping("${api.version}/message")
public class StudentController {
    @Autowired
    private StudentService studentService;

    private BaseController baseController = new BaseController();

    @ApiOperation(value = "查询个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping
    @ResponseBody
    public Student studentMessage(HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        return studentService.selectById(userId);
    }
}
