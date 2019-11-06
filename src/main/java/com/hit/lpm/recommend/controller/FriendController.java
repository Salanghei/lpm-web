package com.hit.lpm.recommend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.potrait.model.Student;
import com.hit.lpm.potrait.service.StudentService;
import com.hit.lpm.recommend.model.RecFriend;
import com.hit.lpm.recommend.service.RecFriendService;
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

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-14 21:57
 **/
@Api(value = "学习伙伴相关功能", tags = "friend")
@RestController
@RequestMapping("${api.version}/friend")
public class FriendController {
    @Autowired
    private RecFriendService recFriendService;

    @Autowired
    private StudentService studentService;

    private BaseController baseController = new BaseController();

    @ApiOperation(value = "获取某用户的全部好友")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getFriend")
    @ResponseBody
    public JSONArray getFriend(HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        List<RecFriend> friendList = recFriendService.selectList(
                new EntityWrapper<RecFriend>().eq("user_id", userId));
        JSONArray result = new JSONArray();
        for(RecFriend friend : friendList){
            JSONObject resultCell = new JSONObject();
            Student friendInfo = studentService.selectById(friend.getFriendId());
            resultCell.put("friendId", friend.getFriendId());
            resultCell.put("trust", friend.getTrust());
            resultCell.put("name", friendInfo.getStudentName());
            resultCell.put("education", friendInfo.getEducation());
            resultCell.put("position", friendInfo.getCountry() + " | " + friendInfo.getProvince() + " | " + friendInfo.getCity());
            result.add(resultCell);
        }
        return result;
    }
}
