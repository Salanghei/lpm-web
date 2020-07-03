package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2020-6-11 21:06
 **/
@TableName("lpm_video")
public class Video {
    private String courseId;
    private String courseName;
    private String videoId;
    private String videoName;

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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
