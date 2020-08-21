package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("lpm_student_ability")
public class StudentAbility implements Serializable {
    private Integer studentId;
    private String courseId;
    private Double ability;

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

    public Double getAbility() {
        return ability;
    }

    public void setAbility(Double ability) {
        this.ability = ability;
    }
}
