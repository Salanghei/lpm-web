package com.hit.lpm.recommend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.potrait.model.Student;
import com.hit.lpm.potrait.service.StudentService;
import com.hit.lpm.recommend.model.RecFriend;
import com.hit.lpm.recommend.model.RecFriendApply;
import com.hit.lpm.recommend.model.RecResource;
import com.hit.lpm.recommend.service.RecFriendApplyService;
import com.hit.lpm.recommend.service.RecFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-4 21:57
 **/
@Api(value = "学习伙伴相关功能", tags = "friend")
@RestController
@RequestMapping("${api.version}/friend")
public class FriendController {
    @Autowired
    private RecFriendService recFriendService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RecFriendApplyService recFriendApplyService;

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

    @ApiOperation(value = "结伴申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "toApplyUserId", value = "被申请结伴的用户ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "details", value = "结伴申请理由", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "trust", value = "信任值", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/applyFriend")
    @ResponseBody
    public Map<String, String> applyFriend(String toApplyUserId, String details, String trust, HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        RecFriendApply newFriendApply = new RecFriendApply();
        newFriendApply.setApplyUserId(userId);
        newFriendApply.setUserId(Integer.valueOf(toApplyUserId));
        newFriendApply.setState("toPass");
        Date date = new Date();
        newFriendApply.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        newFriendApply.setTrust(Double.valueOf(trust));
        newFriendApply.setDetails(details);
        recFriendApplyService.insertAllColumn(newFriendApply);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "success");
        return map;
    }

    @ApiOperation(value = "获取某用户收到的结伴申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getFriendApply")
    @ResponseBody
    public JSONArray getFriendApply(HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        List<RecFriendApply> applyList = recFriendApplyService.selectList(
                new EntityWrapper<RecFriendApply>().eq("user_id", userId).eq("state", "toPass"));
        JSONArray result = new JSONArray();
        for(RecFriendApply apply : applyList){
            JSONObject resultCell = new JSONObject();
            Student applyUser = studentService.selectById(apply.getApplyUserId());
            resultCell.put("details", apply.getDetails());
            resultCell.put("name", applyUser.getNickname());
            resultCell.put("trust", apply.getTrust());
            resultCell.put("time", apply.getTime());
            resultCell.put("icon", "../../assets/images/head.png");
            result.add(resultCell);
        }
        return result;
    }
}
