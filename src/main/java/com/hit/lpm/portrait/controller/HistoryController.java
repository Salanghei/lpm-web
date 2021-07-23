package com.hit.lpm.portrait.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.portrait.model.Course;
import com.hit.lpm.portrait.model.StudentCourse;
import com.hit.lpm.portrait.model.StudentVideoRecord;
import com.hit.lpm.portrait.model.Video;
import com.hit.lpm.portrait.service.CourseService;
import com.hit.lpm.portrait.service.StudentCourseService;
import com.hit.lpm.portrait.service.StudentVideoRecordService;
import com.hit.lpm.portrait.service.VideoService;
import com.hit.lpm.system.model.User;
import com.hit.lpm.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2020-6-11 21:06
 **/
@Api(value = "学习历史", tags = "history")
@RestController
@RequestMapping("${api.version}/history")
public class HistoryController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private StudentVideoRecordService studentVideoRecordService;

    @ApiOperation(value = "查询总体学习情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/total")
    @ResponseBody
    public JSONObject getTotalData(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentCourse> studentCourses = studentCourseService.selectList(
                new EntityWrapper<StudentCourse>().eq("student_id", stuId));
        int courseCount = studentCourses.size();
        int passCount = 0;
        double videoWatchRatio = 0;
        double score = 0;
        for(StudentCourse studentCourse: studentCourses){
            String scoreLevel = studentCourse.getScoreLevel();
            if(scoreLevel != null && scoreLevel.equals("Pass")){
                passCount ++;
            }
            videoWatchRatio += studentCourse.getVideoWatchRatio();
            score += studentCourse.getScore();
        }
        JSONObject result = new JSONObject();
        result.put("courseCount", String.valueOf(courseCount));
        if(courseCount != 0) {
            result.put("passRatio", String.format("%.2f", ((double) passCount / courseCount) * 100));
            result.put("videoWatchRatio", String.format("%.2f", (videoWatchRatio / courseCount) * 100));
            result.put("score", String.format("%.2f", score / courseCount));
        }
        return result;
    }

    @ApiOperation(value = "查询已选课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/course")
    @ResponseBody
    public JSONArray getChooseCourseList(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentCourse> studentCourses = studentCourseService.selectList(
                new EntityWrapper<StudentCourse>().eq("student_id", stuId));
        JSONArray result = new JSONArray();
        for(StudentCourse studentCourse: studentCourses){
            Course course = courseService.selectById(studentCourse.getCourseId());
            JSONObject resultCell = new JSONObject();
            resultCell.put("courseName", course.getCourseName());
            resultCell.put("score", String.format("%.2f", studentCourse.getScore()));
            resultCell.put("scoreLevel", studentCourse.getScoreLevel());
            resultCell.put("getCertificate", studentCourse.getGetCertificate());
            resultCell.put("videoWatchRatio", String.format("%.2f", studentCourse.getVideoWatchRatio()*100));
            result.add(resultCell);
        }
        return result;
    }


    @ApiOperation(value = "查询学习时间线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/timeline")
    @ResponseBody
    public JSONArray getLearnTimeLine(HttpServletRequest request){
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        Integer stuId = 1;
        if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentVideoRecord> studentVideoRecords = studentVideoRecordService.selectList(
                new EntityWrapper<StudentVideoRecord>().eq("student_id", stuId).orderBy("start_time", false));
        JSONArray jsonArray = new JSONArray();
        if(studentVideoRecords != null) {
            for (StudentVideoRecord studentVideoRecord : studentVideoRecords) {
                JSONObject resultCell = new JSONObject();
                Video video = videoService.selectOne(
                        new EntityWrapper<Video>().eq("video_id", studentVideoRecord.getVideoId()));
                resultCell.put("time", studentVideoRecord.getStartTime());
                Double length = (studentVideoRecord.getEndPoint() - studentVideoRecord.getStartPoint()) / 60;
                resultCell.put("length", String.format("%.2f", length));
                resultCell.put("speed", String.format("%.1f", studentVideoRecord.getSpeed()));
                resultCell.put("system", studentVideoRecord.getSystem());
                if(video != null) {
                    resultCell.put("courseName", video.getCourseName());
                    resultCell.put("videoName", video.getVideoName());
                }else{
                    resultCell.put("courseName", "unknown");
                    resultCell.put("videoName", studentVideoRecord.getVideoId());
                }
                jsonArray.add(resultCell);
            }
        }
        return jsonArray;
    }
}
