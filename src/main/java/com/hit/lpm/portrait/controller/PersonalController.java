package com.hit.lpm.portrait.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.primitives.Ints;
import com.hit.lpm.common.BaseController;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2020-6-12 12:21
 **/
@Api(value = "个性化分析", tags = "personal")
@RestController
@RequestMapping("${api.version}/personal")
public class PersonalController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private StudentVideoRecordService studentVideoRecordService;
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private StudentLogService studentLogService;
    @Autowired
    private StudentPostService studentPostService;

    @ApiOperation(value = "视频观看情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/videoWatch")
    @ResponseBody
    public JSONObject getVideoWatchState(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentVideoRecord> studentVideoRecords = studentVideoRecordService.selectList(
                new EntityWrapper<StudentVideoRecord>().eq("student_id", stuId));
        Map<Double, Integer> speedMap = new HashMap<>();
        int c0 = 0, c1 = 0, c2 = 0, c3 = 0, c4 = 0;
        for(StudentVideoRecord studentVideoRecord: studentVideoRecords){
            Double speed = studentVideoRecord.getSpeed();
            if(speedMap.containsKey(speed)){
                speedMap.put(speed, speedMap.get(speed) + 1);
            }else{
                speedMap.put(speed, 0);
            }
            Double videoLen = studentVideoRecord.getVideoLen();
            if(videoLen < 5 * 60){
                c0 ++;
            }else if(videoLen < 10 * 60){
                c1 ++;
            }else if(videoLen < 15 * 60){
                c2 ++;
            }else if(videoLen < 20 * 60){
                c3 ++;
            }else{
                c4 ++;
            }
        }
        Map<String, Integer> videoLenMap = new HashMap<>();
        videoLenMap.put("< 5min", c0);
        videoLenMap.put("5min ~ 10min", c1);
        videoLenMap.put("10min ~ 15min", c2);
        videoLenMap.put("15min ~ 20min", c3);
        videoLenMap.put(">= 20min", c4);
        JSONObject result = new JSONObject();
        result.put("speed", speedMap);
        result.put("videoLen", videoLenMap);
        return result;
    }

    @Autowired
    private StudentBehaviorService studentBehaviorService;

    @ApiOperation(value = "总体学习状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/total")
    @ResponseBody
    public JSONObject getTotalState(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        Behavior behavior = studentBehaviorService.selectById(stuId);
        JSONObject result = new JSONObject();
        result.put("logCount", String.valueOf(behavior.getFrequency()));
        result.put("postCount", String.valueOf(behavior.getPostCount() + behavior.getReplyCount()));
        result.put("learnLength", String.valueOf(behavior.getLearnHours()));
        result.put("testCount", String.valueOf(behavior.getTestCount()));
        int kind = behavior.getKind();
        if(kind == 0){
            result.put("kind", "拖沓懒散型");
        }else if(kind == 1){
            result.put("kind", "敷衍了事型");
        }else if(kind == 2){
            result.put("kind", "积极主动型");
        }else{
            result.put("kind", "半途而废型");
        }
        return result;
    }

    @ApiOperation(value = "活跃情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/active")
    @ResponseBody
    public JSONObject getActiveState(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentLog> studentLogs = studentLogService.selectList(
                new EntityWrapper<StudentLog>().eq("student_id", stuId));
        int[] workDay = new int[24];
        int[] weekend = new int[24];
        for(StudentLog studentLog: studentLogs){
            String[] time = studentLog.getLogTime().split(" ");
            int hour = Integer.valueOf(time[1].split(":")[0]);
            String dateStr = time[0];
            Calendar cal = Calendar.getInstance();
            Date date;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                cal.setTime(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int w = cal.get(Calendar.DAY_OF_WEEK);  // 获取周几
            if(w == 1 || w == 7){
                weekend[hour] ++;
            }else {
                workDay[hour] ++;
            }
        }
        JSONObject result = new JSONObject();
        result.put("workday", Ints.asList(workDay));
        result.put("weekend", Ints.asList(weekend));
        return result;
    }

    @Autowired
    private PersonalityService personalityService;

    @ApiOperation(value = "学习风格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/personality")
    @ResponseBody
    public JSONArray getPersonality(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        Personality personality = personalityService.selectById(stuId);
        JSONArray result = new JSONArray();
        if(personality == null){
            return result;
        }
        Double agr = new BigDecimal(personality.getAgr()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        JSONObject json = new JSONObject();
        json.put("key", "宜人型");
        json.put("value", agr);
        result.add(json);
        Double con = new BigDecimal(personality.getCon()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        json = new JSONObject();
        json.put("key", "严谨型");
        json.put("value", con);
        result.add(json);
        Double ext = new BigDecimal(personality.getExt()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        json = new JSONObject();
        json.put("key", "外倾型");
        json.put("value", ext);
        result.add(json);
        Double neu = new BigDecimal(personality.getNeu()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        json = new JSONObject();
        json.put("key", "神经质");
        json.put("value", neu);
        result.add(json);
        Double opn = new BigDecimal(personality.getOpn()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        json = new JSONObject();
        json.put("key", "经验开放型");
        json.put("value", opn);
        result.add(json);
        return result;
    }
}
