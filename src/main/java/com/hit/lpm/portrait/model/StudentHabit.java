package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

@TableName("lpm_student_habit")
public class StudentHabit implements Serializable {
    @TableId(type = IdType.INPUT)
    private Integer studentId;
    private String timeslot;
    private String discipline;
    private String area;
    private String system;

    public Integer getStudentid() {
        return studentId;
    }

    public void setStudentid(Integer studentid) {
        this.studentId = studentid;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}
