package com.hit.lpm.recommend.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-7 15:17
 **/
@TableName("rec_resource_student_auth")
public class ResourceStudentAuth {
    @TableId
    private Integer rsaId;
    private String time;
    private Integer studentId;
    private Integer resourceId;

    public Integer getRsaId() {
        return rsaId;
    }

    public void setRsaId(Integer rsaId) {
        this.rsaId = rsaId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}
