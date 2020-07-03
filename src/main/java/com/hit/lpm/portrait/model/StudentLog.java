package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("lpm_student_log")
public class StudentLog {
    private Integer studentId;
    private String system;
    private String logTime;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
}
