package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("lpm_student_intention")
public class StudentIntention {
    private String courseId;
    private Integer studentId;
    private Double videoScore;
    private Double testScore;
    private Integer tag;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Double getVideoScore() {
        return videoScore;
    }

    public void setVideoScore(Double videoScore) {
        this.videoScore = videoScore;
    }

    public Double getTestScore() {
        return testScore;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
