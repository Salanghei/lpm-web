package com.hit.lpm.recommend.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.models.auth.In;

/**
 * @program: lmp-web
 * @description:
 * @author: liuwuying
 * @create: 2020-07-27 19:05
 **/
@TableName("platform_score")
public class PlatformScore {
    @TableId
    private String learnerIndex;
    private Integer studentId;
    private Double avg;

    public String getLearnerIndex() {
        return learnerIndex;
    }

    public void setLearnerIndex(String learnerIndex) {
        this.learnerIndex = learnerIndex;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
