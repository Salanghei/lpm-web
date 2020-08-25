package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("lpm_student_select_course")
public class StudentSelectCourse {
    @TableId(type = IdType.INPUT)

    private Integer id ;
    private Integer studentId;
    private String courseId;
    private Double videoWatchRate;
    private int getCertificate;
    private int score;
    private String scoreDegree;
    private String courseName;
    private String courseType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getVideoWatchRate() {
        return videoWatchRate;
    }

    public void setVideoWatchRate(Double videoWatchRate) {
        this.videoWatchRate = videoWatchRate;
    }

    public int getGetCertificate() {
        return getCertificate;
    }

    public void setGetCertificate(int getCertificate) {
        this.getCertificate = getCertificate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScoreDegree() {
        return scoreDegree;
    }

    public void setScoreDegree(String scoreDegree) {
        this.scoreDegree = scoreDegree;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
}
