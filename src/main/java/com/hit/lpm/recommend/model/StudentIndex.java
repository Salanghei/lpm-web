package com.hit.lpm.recommend.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.models.auth.In;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-5 18:05
 **/
@TableName("lpm_student_index")
public class StudentIndex {
    @TableId
    private Integer id;
    private Integer studentId;
    private String learnerIndex;

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

    public String getLearnerIndex() {
        return learnerIndex;
    }

    public void setLearnerIndex(String learnerIndex) {
        this.learnerIndex = learnerIndex;
    }
}
