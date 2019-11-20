package com.hit.lpm.portrait.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.index.IndexDirection.ASCENDING;

/**
 * @program: lmp-web
 * @description: 学习者画像
 * @author: guoyang
 * @create: 2019-11-13 20:00
 **/
@Document(collection = "portrait")
public class StudentPortrait implements Serializable {
    //标记id字段
    @Id
    private ObjectId id;
    //创建单字段索引（默认ASCENDING 升序、DESCENDING 降序）
    @Indexed(direction = ASCENDING)
    private Integer studentId;
    private String studentName;
    private String nickname;
    private String gender;
    private Date birthday;
    private String education;
    private String country;
    private String province;
    private String city;
    private List<Topic> topics;

    public StudentPortrait() {
    }

    public StudentPortrait(Student student) {
        this.birthday = student.getBirthday();
        this.city = student.getCity();
        this.country = student.getCountry();
        this.education = student.getEducation();
        this.gender = student.getGender();
        this.studentId = student.getStudentId();
        this.nickname = student.getNickname();
        this.province = student.getProvince();
        this.studentName = student.getStudentName();

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
