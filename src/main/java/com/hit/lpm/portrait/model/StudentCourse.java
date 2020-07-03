package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2020-6-11 21:06
 **/
@TableName("lpm_student_course")
public class StudentCourse {
    @TableId
    private Integer scId;
    private Integer studentId;
    private String courseId;
    private String isChoose;
    private String chooseTime;
    private String quitTime;
    private Double videoWatchRatio;
    private String getCertificate;
    private Double score = 0.0;
    private String scoreLevel;

    public Integer getScId() {
        return scId;
    }

    public void setScId(Integer scId) {
        this.scId = scId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(String isChoose) {
        this.isChoose = isChoose;
    }

    public String getChooseTime() {
        return chooseTime;
    }

    public void setChooseTime(String chooseTime) {
        this.chooseTime = chooseTime;
    }

    public String getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(String quitTime) {
        this.quitTime = quitTime;
    }

    public Double getVideoWatchRatio() {
        return videoWatchRatio;
    }

    public void setVideoWatchRatio(Double videoWatchRatio) {
        this.videoWatchRatio = videoWatchRatio;
    }

    public String getGetCertificate() {
        return getCertificate;
    }

    public void setGetCertificate(String getCertificate) {
        this.getCertificate = getCertificate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getScoreLevel() {
        return scoreLevel;
    }

    public void setScoreLevel(String scoreLevel) {
        this.scoreLevel = scoreLevel;
    }
}
