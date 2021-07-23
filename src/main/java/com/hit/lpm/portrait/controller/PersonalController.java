package com.hit.lpm.portrait.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
        if(studentVideoRecords != null && studentVideoRecords.size() > 0) {
            Map<Double, Integer> speedMap = new HashMap<>();
            int c0 = 0, c1 = 0, c2 = 0, c3 = 0, c4 = 0;
            for (StudentVideoRecord studentVideoRecord : studentVideoRecords) {
                Double speed = studentVideoRecord.getSpeed();
                if (speedMap.containsKey(speed)) {
                    speedMap.put(speed, speedMap.get(speed) + 1);
                } else {
                    speedMap.put(speed, 0);
                }
                Double videoLen = studentVideoRecord.getVideoLen();
                if (videoLen < 5 * 60) {
                    c0++;
                } else if (videoLen < 10 * 60) {
                    c1++;
                } else if (videoLen < 15 * 60) {
                    c2++;
                } else if (videoLen < 20 * 60) {
                    c3++;
                } else {
                    c4++;
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
        return null;
    }

    @Autowired
    private StudentBehaviorService studentBehaviorService;

    @Autowired
    private StudentProblemService studentProblemService;

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
        int courseCount = studentCourseService.selectCount(
                new EntityWrapper<StudentCourse>().eq("student_id", stuId));
        courseCount = courseCount == 0? 1: courseCount;
        JSONObject result = new JSONObject();
        if(behavior != null) {
            result.put("logCount", String.valueOf(behavior.getVideoFrequency()/ courseCount));
            result.put("postCount", String.valueOf((behavior.getPostCount() + behavior.getReplyCount())/ courseCount));
            result.put("learnLength", String.format("%.2f", behavior.getLearnHours()/ courseCount));
            result.put("testCount", String.valueOf(behavior.getTestCount()/ courseCount));
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
        }else{
            int logCount = studentVideoRecordService.selectCount(
                    new EntityWrapper<StudentVideoRecord>().eq("student_id", stuId));
            Map<String, Object> learnLength = studentVideoRecordService.selectMap(
                    new EntityWrapper<StudentVideoRecord>().setSqlSelect("ifnull(sum(video_len), 0) as sum").eq("student_id", stuId));
            int postCount = studentPostService.selectCount(
                    new EntityWrapper<StudentPost>().eq("student_id", stuId));
            int testCount = studentProblemService.selectCount(
                    new EntityWrapper<StudentProblem>().eq("student_id", stuId));
            result.put("logCount", String.valueOf(logCount / courseCount));
            result.put("postCount", String.valueOf(postCount / courseCount));
            result.put("learnLength", String.format("%.2f", (Double)learnLength.get("sum") / (courseCount * 60)));
            result.put("testCount", String.valueOf(testCount / courseCount));
            result.put("kind", "未知");
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
        List<StudentPost> studentPosts = studentPostService.selectList(
                new EntityWrapper<StudentPost>().eq("student_id", stuId));
        List<StudentVideoRecord> studentVideoRecords = studentVideoRecordService.selectList(
                new EntityWrapper<StudentVideoRecord>().eq("student_id", stuId));
        List<String> activeTimeList = new ArrayList<>();
        for(StudentLog studentLog: studentLogs){
            activeTimeList.add(studentLog.getLogTime());
        }
        for(StudentPost studentPost: studentPosts){
            activeTimeList.add(studentPost.getTime());
        }
        for(StudentVideoRecord studentVideoRecord: studentVideoRecords){
            activeTimeList.add(studentVideoRecord.getStartTime());
        }
        if(activeTimeList.size() != 0) {
            int[] workDay = new int[24];
            int[] weekend = new int[24];
            for (String activeTime : activeTimeList) {
                String dateStr = activeTime.substring(0, 10);
                String time = activeTime.substring(11, activeTime.length());
                int hour = Integer.valueOf(time.split(":")[0]);
                Calendar cal = Calendar.getInstance();
                Date date;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                    cal.setTime(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int w = cal.get(Calendar.DAY_OF_WEEK);  // 获取周几
                if (w == 1 || w == 7) {
                    weekend[hour]++;
                } else {
                    workDay[hour]++;
                }
            }
            JSONObject result = new JSONObject();
            result.put("workday", Ints.asList(workDay));
            result.put("weekend", Ints.asList(weekend));
            return result;
        }
        return null;
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

    @Autowired
    private StudentHabitService studentHabitService;

    @ApiOperation(value = "学习习惯")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/habit")
    @ResponseBody
    public JSONObject getLearnHabit(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        StudentHabit studentHabit = studentHabitService.selectById(stuId);
        if(studentHabit == null){
            return null;
        }
        JSONObject json = new JSONObject();
        json.put("activeTime", studentHabit.getTimeslot());
        json.put("activeDomain", studentHabit.getDiscipline());
        json.put("activeArea", studentHabit.getArea());
        json.put("activeSystem", studentHabit.getSystem());
        return json;
    }

    @Autowired
    private StudentAbilityService studentAbilityService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseStructureService courseStructureService;

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "有成绩的课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/courses")
    @ResponseBody
    public JSONObject getCourses(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<String> courseIds = studentService.selectStudentCourses(stuId);
        JSONObject res = new JSONObject();
        if(courseIds != null){
            JSONArray courses = new JSONArray();
            JSONArray ids = new JSONArray();
            for(String id: courseIds){
                Course course = courseService.selectById(id);
                if(course != null) {
                    ids.add(id);
                    courses.add(course.getCourseName());
                }
            }
            res.put("ids", ids);
            res.put("courses", courses);
        }
        return res;
    }

    @ApiOperation(value = "习题成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/score")
    @ResponseBody
    public JSONObject getProblemScore(HttpServletRequest request, String courseId) {
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        if(courseId == null){
            List<String> courseIds = studentService.selectStudentCourses(stuId);
            if(courseIds != null && courseIds.size() > 0) {
                courseId = courseIds.get(0);
            }
        }else {
            courseId = String.join("+", courseId.split("\\s+"));
        }
        System.out.println(courseId);
        JSONObject res = new JSONObject();
        if(courseId != null) {
            List<CourseStructure> courseStructures = courseStructureService.selectList(
                    new EntityWrapper<CourseStructure>().eq("course_id", courseId));
            if (courseStructures != null) {
                JSONArray problems = new JSONArray();
                JSONArray scores = new JSONArray();
                JSONArray fullScores = new JSONArray();
                for (CourseStructure cs : courseStructures) {
                    problems.add(cs.getProblemId());
                    fullScores.add(cs.getFullScore());
                    Double score = studentService.selectStudentProblemScore(stuId, cs.getProblemId());
                    if (score != null) {
                        scores.add(score);
                    } else {
                        scores.add(0);
                    }
                }
                res.put("problems", problems);
                res.put("scores", scores);
                res.put("fullScores", fullScores);
                return res;
            }
        }
        return null;
    }

    @ApiOperation(value = "学习能力")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/ability")
    @ResponseBody
    public JSONObject getStudentAbility(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentAbility> studentAbilities = studentAbilityService.selectList(
                new EntityWrapper<StudentAbility>().eq("student_id", stuId));
        JSONObject res = new JSONObject();
        if(studentAbilities != null){
            JSONArray courses = new JSONArray();
            JSONArray ability = new JSONArray();
            for(StudentAbility sa: studentAbilities){
                Course course = courseService.selectById(sa.getCourseId());
                if(course != null){
                    courses.add(course.getCourseName());
                    Double a = new BigDecimal(sa.getAbility()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    ability.add(a);
                }
            }
            res.put("courses", courses);
            res.put("ability", ability);
        }
        return res;
    }
}
