package com.hit.lpm.situation.service;

import com.hit.lpm.situation.model.CourseEdge;
import com.hit.lpm.situation.model.CourseNode;

import java.util.List;

public interface LearningPathService {
    List<CourseEdge> getCourseEdge(String courseId);
    List<CourseNode> getCourseNode(int userId, String courseId);
    String getCourseName(String courseId);
}
