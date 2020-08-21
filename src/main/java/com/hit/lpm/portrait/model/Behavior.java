package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

@TableName("lpm_student_behavior")
public class Behavior implements Serializable {
    @TableId(type = IdType.INPUT)
    private Integer studentId;
    private Double learnHours;
    private Integer frequency;
    private Integer videoFrequency;
    private Integer testCount;
    private Double avgtestFrequency;
    private Integer postCount;
    private Integer replyCount;
    private Double avgtestScore;
    private Double courseScore;
    private Integer kind;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Double getLearnHours() {
        return learnHours;
    }

    public void setLearnHours(Double learnHours) {
        this.learnHours = learnHours;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getVideoFrequency() {
        return videoFrequency;
    }

    public void setVideoFrequency(Integer videoFrequency) {
        this.videoFrequency = videoFrequency;
    }

    public Integer getTestCount() {
        return testCount;
    }

    public void setTestCount(Integer testCount) {
        this.testCount = testCount;
    }

    public Double getAvgtestFrequency() {
        return avgtestFrequency;
    }

    public void setAvgtestFrequency(Double avgtestFrequency) {
        this.avgtestFrequency = avgtestFrequency;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Double getAvgtestScore() {
        return avgtestScore;
    }

    public void setAvgtestScore(Double avgtestScore) {
        this.avgtestScore = avgtestScore;
    }

    public Double getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(Double courseScore) {
        this.courseScore = courseScore;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }
}
