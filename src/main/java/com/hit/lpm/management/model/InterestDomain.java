package com.hit.lpm.management.model;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("interest_domain")
public class InterestDomain {
    @TableId(type = IdType.INPUT)
    private Integer sdId;
    private Integer student_id;
    private String domain;
    private Integer score;

    public Integer getSdId() {
        return sdId;
    }

    public void setSdId(Integer sdId) {
        this.sdId = sdId;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
