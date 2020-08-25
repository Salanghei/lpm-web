package com.hit.lpm.recommend.service.impl;

import com.hit.lpm.recommend.dao.RecFriendMapper;
import com.hit.lpm.recommend.dao.StudentIndexMapper;
import com.hit.lpm.recommend.model.Graph;
import com.hit.lpm.recommend.model.PlatformScore;
import com.hit.lpm.recommend.model.StudentIndex;
import com.hit.lpm.recommend.service.RelationNetworkService;
import org.springframework.stereotype.Service;

import java.util.*;

import com.hit.lpm.recommend.model.Graph.Vertex;
import com.hit.lpm.recommend.model.Graph.Edge;

import javax.annotation.Resource;

@Service
public class RelationNetworkServiceImpl implements RelationNetworkService {
    @Resource
    private StudentIndexMapper studentIndexMapper;

    @Override
    public Graph initGrapgh(List<PlatformScore> scores) {
        //结点集
        List<Vertex> verList = new LinkedList<Vertex>();
        //边集
        Map<Vertex, List<Edge>> vertex_edgeList_map = new HashMap<Vertex, List<Edge>>();

        //将有初始可靠度的学生加入图的结点集中
        for (PlatformScore score : scores) {
            if (score.getAvg() != null){
                StudentIndex student = new StudentIndex();
                student.setLearnerIndex(score.getLearnerIndex());

                student = studentIndexMapper.selectOne(student);
                Integer studentId = student.getStudentId();

                Vertex vertex = new Vertex(studentId + "", score.getAvg());
                verList.add(vertex);
            }
        }


        //图
        Graph g = new Graph(verList, vertex_edgeList_map);
        return g;
    }
}
