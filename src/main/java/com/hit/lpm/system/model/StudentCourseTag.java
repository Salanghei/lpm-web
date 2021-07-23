package com.hit.lpm.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("lpm_student_course_tag")
public class StudentCourseTag {
    @TableId(type=IdType.INPUT)
    private Integer sctId;
    private Integer studentId;
    private String courseId;
    private Integer tag;

    public Integer getSctId() {
        return sctId;
    }

    public void setSctId(Integer sctId) {
        this.sctId = sctId;
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

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
