package com.hit.lpm.system.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "addTags")
public class StudentData {
    @Id
    private ObjectId id;
    private String course_id;
    private Integer student_id;
    private Double final_score;
    private String final_score_level;
    private Double video_percent;
    private List<Integer> video_watch_times;
    private List<Integer> video_watch_time;
    private List<Integer> video_watch_history;
    private List<Integer> test_history;
    private List<Double> test_score;
    private Double test_percent;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Double getFinal_score() {
        return final_score;
    }

    public void setFinal_score(Double final_score) {
        this.final_score = final_score;
    }

    public String getFinal_score_level() {
        return final_score_level;
    }

    public void setFinal_score_level(String final_score_level) {
        this.final_score_level = final_score_level;
    }

    public Double getVideo_percent() {
        return video_percent;
    }

    public void setVideo_percent(Double video_percent) {
        this.video_percent = video_percent;
    }

    public List<Integer> getVideo_watch_times() {
        return video_watch_times;
    }

    public void setVideo_watch_times(List<Integer> video_watch_times) {
        this.video_watch_times = video_watch_times;
    }

    public List<Integer> getVideo_watch_time() {
        return video_watch_time;
    }

    public void setVideo_watch_time(List<Integer> video_watch_time) {
        this.video_watch_time = video_watch_time;
    }

    public List<Integer> getVideo_watch_history() {
        return video_watch_history;
    }

    public void setVideo_watch_history(List<Integer> video_watch_history) {
        this.video_watch_history = video_watch_history;
    }

    public List<Integer> getTest_history() {
        return test_history;
    }

    public void setTest_history(List<Integer> test_history) {
        this.test_history = test_history;
    }

    public List<Double> getTest_score() {
        return test_score;
    }

    public void setTest_score(List<Double> test_score) {
        this.test_score = test_score;
    }

    public Double getTest_percent() {
        return test_percent;
    }

    public void setTest_percent(Double test_percent) {
        this.test_percent = test_percent;
    }
}
