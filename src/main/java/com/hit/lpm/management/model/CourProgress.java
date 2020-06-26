package com.hit.lpm.management.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.hit.lpm.common.utils.StringUtil;

import java.io.Serializable;
import java.util.Date;


@TableName("progress")
public class CourProgress implements Serializable {

    @TableId(type = IdType.INPUT)

    private Integer cpId;
    private Integer studentId;
    private String courseId;
    private Integer unit;
    private Integer score;
    private Integer participation;
    private Integer courseP;
    private Integer discussionP;
    private Integer examP;

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
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

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getParticipation() {
        return participation;
    }

    public void setParticipation(Integer participation) {
        this.participation = participation;
    }

    public Integer getCourseP() {
        return courseP;
    }

    public void setCourseP(Integer courseP) {
        this.courseP = courseP;
    }

    public Integer getDiscussionP() {
        return discussionP;
    }

    public void setDiscussionP(Integer discussionP) {
        this.discussionP = discussionP;
    }

    public Integer getExamP() {
        return examP;
    }

    public void setExamP(Integer examP) {
        this.examP = examP;
    }
}
