package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-10-13 21:54
 **/
@TableName("lpm_student_topic_relation")
public class StudentTopicRelation implements Serializable {
    @TableId
    private Integer stId;

    private Integer studentId;

    private Integer topicId;

    private Integer score;

    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
