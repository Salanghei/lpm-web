package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

@TableName("lpm_student_personality")
public class Personality implements Serializable {
    @TableId(type = IdType.INPUT)
    private Integer studentId;
    private Double ext;
    private Double neu;
    private Double agr;
    private Double con;
    private Double opn;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Double getExt() {
        return ext;
    }

    public void setExt(Double ext) {
        this.ext = ext;
    }

    public Double getNeu() {
        return neu;
    }

    public void setNeu(Double neu) {
        this.neu = neu;
    }

    public Double getAgr() {
        return agr;
    }

    public void setAgr(Double agr) {
        this.agr = agr;
    }

    public Double getCon() {
        return con;
    }

    public void setCon(Double con) {
        this.con = con;
    }

    public Double getOpn() {
        return opn;
    }

    public void setOpn(Double opn) {
        this.opn = opn;
    }
}
