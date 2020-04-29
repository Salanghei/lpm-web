package com.hit.lpm.situation.service;

import com.hit.lpm.portrait.model.KnowledgeNode;
import com.hit.lpm.situation.model.CourseEdge;
import com.hit.lpm.situation.model.CourseNode;
import com.hit.lpm.situation.model.KnowledgeNodes;

import java.util.List;

public interface LearningPathService {
    List<CourseEdge> getCourseEdge(String courseId);
    List<CourseNode> getCourseNode(int userId, String courseId);
    String getCourseName(String courseId);
    KnowledgeNodes getKnowledgeNodeById(int knowledgeNodeId, String courseId);
    void learnKnowledge(int stuId, int nodeId, String courseId);
    int getStudentIdByUserId(String userId);
}
