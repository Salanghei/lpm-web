package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("lpm_student_post")
public class StudentPost implements Serializable {
    private Integer spId;
    private String courseId;
    private String courseName;
    private Integer studentId;
    private String type;
    private String mainPostId;
    private String postId;
    private String title;
    private String content;
    private String time;

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMainPostId() {
        return mainPostId;
    }

    public void setMainPostId(String mainPostId) {
        this.mainPostId = mainPostId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return content;
    }

    public void setContext(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
