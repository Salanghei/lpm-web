package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2020-6-11 21:31
 **/
@TableName("lpm_student_video_record")
public class StudentVideoRecord {
    @TableId
    private Integer svId;
    private Integer studentId;
    private String videoId;
    private Double videoLen;
    private String system;
    private Double speed;
    private String startTime;
    private String endTime;
    private Double startPoint;
    private Double endPoint;

    public Integer getSvId() {
        return svId;
    }

    public void setSvId(Integer svId) {
        this.svId = svId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Double getVideoLen() {
        return videoLen;
    }

    public void setVideoLen(Double videoLen) {
        this.videoLen = videoLen;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Double startPoint) {
        this.startPoint = startPoint;
    }

    public Double getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Double endPoint) {
        this.endPoint = endPoint;
    }
}
