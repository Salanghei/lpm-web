package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("lpm_student_problem")
public class StudentProblem {
    private Integer studentId;
    private String courseId;
    private String problemId;
    private Double fullScore;
    private Double score;
    private String isTest;
    private String type;
    private String submitTime;

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

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public Double getFullScore() {
        return fullScore;
    }

    public void setFullScore(Double fullScore) {
        this.fullScore = fullScore;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getIsTest() {
        return isTest;
    }

    public void setIsTest(String isTest) {
        this.isTest = isTest;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
}
