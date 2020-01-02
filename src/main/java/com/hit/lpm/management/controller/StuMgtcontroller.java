package com.hit.lpm.management.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.service.StudentService;
import com.hit.lpm.management.model.RecTest;
import com.hit.lpm.management.service.RecTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Api(value = "学习者管理相关功能", tags = "stuMgt")
@RestController
@RequestMapping("${api.version}/stuMgt")

public class StuMgtcontroller {
    @Autowired
    private RecTestService recTestService;

    @Autowired
    private StudentService studentService;



    private BaseController baseController = new BaseController();

    //获取某用户全部好友
    @ApiOperation(value = "获取某用户的全部好友")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getFriend")
    @ResponseBody
    public JSONArray getFriend(HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        List<RecTest> friendList = recTestService.selectList(
                new EntityWrapper<RecTest>().eq("user_id", userId));
        JSONArray result = new JSONArray();
        for(RecTest friend : friendList){
            JSONObject resultCell = new JSONObject();
            Student friendInfo = studentService.selectById(friend.getFriendId());
            resultCell.put("friendId", friend.getFriendId());
            resultCell.put("trust", friend.getTrust());
            resultCell.put("name", friendInfo.getNickname());
            resultCell.put("education", friendInfo.getEducation());
            resultCell.put("position", friendInfo.getCountry() + " | " + friendInfo.getProvince() + " | " + friendInfo.getCity());
            result.add(resultCell);
        }
        return result;
    }

    //获取所有学生性别
    @ApiOperation(value = "获取所有学生性别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getGender")
    @ResponseBody
    public JSONObject getGender(HttpServletRequest request){
        int count1,count2,count3;
        count1 = studentService.selectCount(new EntityWrapper<Student>().eq("gender", "男"));
        count2 = studentService.selectCount(new EntityWrapper<Student>().eq("gender", "女"));
        count3 = studentService.selectCount(new EntityWrapper<Student>().eq("gender", "其他"));

        JSONObject result=new JSONObject();

        result.put("男",count1);
        result.put("女",count2);
        result.put("其他",count3);

        return result;
    }

    //获取所有学生性别
    @ApiOperation(value = "获取所有学生教育程度统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getEdu")
    @ResponseBody
    public JSONObject getEdu(HttpServletRequest request){
        int count[]=new int[8];

        count[0] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "小学"));
        count[1] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "初中"));
        count[2]= studentService.selectCount(new EntityWrapper<Student>().eq("education",  "高中"));
        count[3] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "本科"));
        count[4] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "专科"));
        count[5] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "硕士"));
        count[6] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "博士"));
        count[7] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "其他"));

        JSONObject result=new JSONObject();

        result.put("小学",count[0]);
        result.put("初中",count[1]);
        result.put("高中",count[2]);
        result.put("本科",count[3]);
        result.put("专科",count[4]);
        result.put("硕士",count[5]);
        result.put("博士",count[6]);
        result.put("其他",count[7]);

        return result;
    }
}
