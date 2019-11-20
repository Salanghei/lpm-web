package com.hit.lpm.recommend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.service.StudentService;
import com.hit.lpm.recommend.model.RecFriend;
import com.hit.lpm.recommend.model.RecFriendApply;
import com.hit.lpm.recommend.service.RecFriendApplyService;
import com.hit.lpm.recommend.service.RecFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            resultCell.put("name", friendInfo.getNickname());
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
            resultCell.put("apply", apply);
            resultCell.put("applyUser", applyUser.getNickname());
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "通过好友申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applyId", value = "申请ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/passFriendApply")
    @ResponseBody
    public JSONObject passFriendApply(String applyId, HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        JSONObject result = new JSONObject();
        RecFriendApply recFriendApply = recFriendApplyService.selectById(applyId);
        recFriendApply.setState("pass");
        recFriendApplyService.updateById(recFriendApply);  // 更新申请状态
        RecFriend newFriend = new RecFriend();
        newFriend.setUserId(userId);
        newFriend.setFriendId(recFriendApply.getApplyUserId());
        newFriend.setTrust(recFriendApply.getTrust());
        recFriendService.insertAllColumn(newFriend);
        newFriend.setUserId(recFriendApply.getApplyUserId());
        newFriend.setFriendId(userId);
        newFriend.setTrust(recFriendApply.getTrust());
        recFriendService.insertAllColumn(newFriend);
        result.put("msg", "success");
        return result;
    }

    @ApiOperation(value = "拒绝好友申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applyId", value = "申请ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/refuseFriendApply")
    @ResponseBody
    public JSONObject refuseFriendApply(String applyId, HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        JSONObject result = new JSONObject();
        RecFriendApply recFriendApply = recFriendApplyService.selectById(applyId);
        recFriendApply.setState("fail");
        recFriendApplyService.updateById(recFriendApply);  // 更新申请状态
        result.put("msg", "success");
        return result;
    }

    @ApiOperation(value = "解除学习伙伴关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "friendId", value = "学习伙伴ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/deleteFriend")
    @ResponseBody
    public JSONObject deleteFriend(String friendId, HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        recFriendService.delete(
                new EntityWrapper<RecFriend>().eq("user_id", userId).eq("friend_id", friendId));
        recFriendService.delete(
                new EntityWrapper<RecFriend>().eq("user_id", friendId).eq("friend_id", userId));
        JSONObject result = new JSONObject();
        result.put("msg", "success");
        return result;
    }
}
