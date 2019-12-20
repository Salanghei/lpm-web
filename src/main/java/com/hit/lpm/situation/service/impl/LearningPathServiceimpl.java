package com.hit.lpm.situation.service.impl;

import com.hit.lpm.common.BaseController;
import com.hit.lpm.portrait.model.KnowledgeNode;
import com.hit.lpm.situation.dao.LearningPathMapper;
import com.hit.lpm.situation.model.*;
import com.hit.lpm.situation.service.LearningPathService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.RelationNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class LearningPathServiceimpl implements LearningPathService {
    public static final double size = 30.0;//结点大小基数
    @Resource
    private LearningPathMapper learningPathMapper;

    @Override
    public List<CourseEdge> getCourseEdge(String courseId) {
        List<KnowledgeEdges> edges = learningPathMapper.getPathEdge(courseId);
        List<CourseEdge> result = new ArrayList<>();
        for(KnowledgeEdges i : edges){
            CourseEdge temp = new CourseEdge();
            temp.setCourseName(i.getCourseId());
            temp.setSource(i.getSourceId() -1);
            temp.setTarget(i.getTargetId()-1);
            result.add(temp);
        }
        return result;
    }

    @Override
    public List<CourseNode> getCourseNode(int userId, String courseId) {
        List<KnowledgeNodes> nodes = learningPathMapper.getPathNode(courseId);
        List<KnowledgeEdges> edges = learningPathMapper.getPathEdge(courseId);
        int studentId = learningPathMapper.getStudentIdByUserId(userId);
        List<KnowledgeRelation> relations = learningPathMapper.getLearningSituation(studentId, courseId);
        HashMap<Integer, Double> nodeMap = new HashMap<>();
        HashMap<Integer, List<Integer>> preNode = new HashMap<>();
        for(KnowledgeRelation i : relations){
            nodeMap.put(i.getNodeId(), i.getScore());
        }
        for(KnowledgeNodes i : nodes){
            List<Integer> temp_list = new ArrayList<>();
            for(KnowledgeEdges j : edges){
                if(j.getTargetId() == i.getNodeId()){
                    temp_list.add(j.getSourceId());
                }
            }
            preNode.put(i.getNodeId(), temp_list);
        }

        List<CourseNode> result = new ArrayList<>();
        Random random = new Random();
        for(KnowledgeNodes i : nodes){
            CourseNode temp = new CourseNode();
            if(nodeMap.containsKey(i.getNodeId())){
                temp.setCategory(0);
            }
            else {
                boolean canLearn = true;
                for(int j : preNode.get(i.getNodeId())){
                    if(nodeMap.containsKey(j)) {
                        continue;
                    }
                    canLearn = false;
                    break;
                }
                if(canLearn){
                    temp.setCategory(1);
                }
                else {
                    temp.setCategory(2);
                }
            }
            double temp_s = size*i.getImportance();
            temp_s = temp_s < 10 ? 10 : temp_s;
            temp_s = temp_s > 100 ? 100 : temp_s;
            temp.setSymbolSize(temp_s);
            temp.setName(i.getName());
            temp.setCourseName(i.getName());
            temp.setId(i.getNodeId());
            temp.setValue(nodeMap.containsKey(i.getNodeId()) ? nodeMap.get(i.getNodeId()) : 0);
            int x = 0;
            if(temp.getCategory() == 1)
                x = 400;
            else if(temp.getCategory() == 2)
                x = 800;
            temp.setX(random.nextInt(200)+x);
            temp.setY(random.nextInt(700));
            result.add(temp);
        }
        return result;
    }

    @Override
    public String getCourseName(String courseId){
        return learningPathMapper.getCourseNameById(courseId);
    }
}
