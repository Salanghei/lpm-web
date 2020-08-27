package com.hit.lpm.recommend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.portrait.dao.StudentMapper;
import com.hit.lpm.portrait.model.Cluster;
import com.hit.lpm.portrait.model.Course;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.model.StudentCourseRelation;
import com.hit.lpm.portrait.service.ClusterService;
import com.hit.lpm.portrait.service.CourseService;
import com.hit.lpm.portrait.service.StudentCourseRelationService;
import com.hit.lpm.recommend.model.Friend;
import com.hit.lpm.recommend.model.RecCluster;
import com.hit.lpm.recommend.model.RecCourse;
import com.hit.lpm.recommend.model.RecFriend;
import com.hit.lpm.recommend.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apdplat.word.vector.F;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-4 21:57
 **/
@Api(value = "推荐相关功能", tags = "recommend")
@RestController
@RequestMapping("${api.version}/recommend")
public class RecommendController {
    @Autowired
    private RecStudentService recStudentService;

    @Autowired
    private RecCourseService recCourseService;

    @Autowired
    private RecFriendService recFriendService;
    @Resource
    private StudentMapper studentMapper;

    @Autowired
    private StudentCourseRelationService studentCourseRelationService;

    @Autowired
    private RecClusterService recClusterService;

    @Autowired
    private ClusterService clusterService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FriendService friendService;

    private BaseController baseController = new BaseController();

    @ApiOperation(value = "构建信任关系网络")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getRelationNetwork")
    @ResponseBody
    public JSONObject getRelationNetwork(HttpServletRequest request) {
        Integer userId = baseController.getLoginUserId(request);
        JSONObject result = new JSONObject();

        //查询一级信任伙伴
        Map<String, Object> map = new HashMap<>();
        Set<Integer> idSet = new HashSet<>();
        idSet.add(userId);
        map.put("student_id", userId);
        List<Friend> friendList = friendService.selectByMap(map);
        if (friendList.size() > 5) {
            friendList = friendList.subList(0, 5);
        }

        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();
        int level1 = friendList.size();   // 第一层节点

        JSONObject firstNode = new JSONObject();  // 中心节点为已登录的用户
        firstNode.put("category", 0);
        firstNode.put("name", String.valueOf(userId));
        firstNode.put("value", 40);
        nodes.add(firstNode);    // 将中心节点加入

        List<BigDecimal> weights = new ArrayList<>();

        for (int i = 0; i < level1; i++) {//第一层节点
            Friend friend = friendList.get(i);
            idSet.add(friend.getFriendId());
            JSONObject node = new JSONObject();  // 创建新节点
            node.put("category", 1);
            node.put("name", String.valueOf(friend.getFriendId()));
            node.put("value", 30);
            nodes.add(node);
            JSONObject edge = new JSONObject();  // 创建新边
            edge.put("source", String.valueOf(userId));   // 第一层每个节点都与中心节点相连
            edge.put("target", String.valueOf(friend.getFriendId()));
            BigDecimal weight = friend.getTrust();
            edge.put("weight", String.format("%.2f", weight));
            edges.add(edge);
            weights.add(weight);
        }

        for (int i = 0; i < level1; i++) {
            Friend friend = friendList.get(i);
            map.clear();
            map.put("student_id", friend.getFriendId());
            List<Friend> friendList1 = friendService.selectByMap(map);
            if (friendList1 != null && friendList1.size() > 5) {
                friendList1 = friendList1.subList(0, 5);
            }
            for (int j = 0; j < friendList1.size(); j++) {//第二层节点
                Friend friend1 = friendList1.get(j);

                JSONObject node1 = new JSONObject();  // 创建新节点
                node1.put("category", 2);
                node1.put("name", String.valueOf(friend1.getFriendId()));
                node1.put("value", 20);
                if (!idSet.contains(friend1.getFriendId())) {
                    nodes.add(node1);
                    idSet.add(friend1.getFriendId());
                }
                JSONObject edge1 = new JSONObject();  // 创建新边
                edge1.put("source", String.valueOf(friend1.getStudentId()));   // 第一层每个节点都与中心节点相连
                edge1.put("target", String.valueOf(friend1.getFriendId()));
                BigDecimal weight1 = friend1.getTrust();
                edge1.put("weight", String.format("%.2f", weight1));
                edges.add(edge1);
                weights.add(weight1);
            }
        }

        for (int i = 0; i < level1; i++) {
            Friend friend = friendList.get(i);
            map.clear();
            map.put("student_id", friend.getFriendId());
            List<Friend> friendList1 = friendService.selectByMap(map);
            if (friendList1 != null && friendList1.size() > 5) {
                friendList1 = friendList1.subList(0, 5);
            }
            for (int j = 0; j < friendList1.size(); j++) {
                Friend friend1 = friendList1.get(j);
                map.clear();
                map.put("student_id", friend1.getFriendId());
                List<Friend> friendList2 = friendService.selectByMap(map);
                if (friendList2 != null && friendList2.size() > 5) {
                    friendList2 = friendList2.subList(0, 5);
                }
                for (int k = 0; k < friendList2.size(); k++) {//第三层节点
                    Friend friend2 = friendList2.get(k);

                    JSONObject node2 = new JSONObject();  // 创建新节点
                    node2.put("category", 3);
                    node2.put("name", String.valueOf(friend2.getFriendId()));
                    node2.put("value", 10);
                    if (!idSet.contains(friend2.getFriendId())) {
                        nodes.add(node2);
                        idSet.add(friend2.getFriendId());
                    }
                    JSONObject edge2 = new JSONObject();  // 创建新边
                    edge2.put("source", String.valueOf(friend2.getStudentId()));   // 第一层每个节点都与中心节点相连
                    edge2.put("target", String.valueOf(friend2.getFriendId()));
                    BigDecimal weight2 = friend2.getTrust();
                    edge2.put("weight", String.format("%.2f", weight2));
                    edges.add(edge2);
                    weights.add(weight2);
                }
            }
        }
        result.put("nodes", nodes);
        result.put("edges", edges);
        result.put("userId", String.valueOf(userId));

        return result;
    }

//    @ApiOperation(value = "构建信任关系网络")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
//    })
//    @GetMapping("/getRelationNetwork")
//    @ResponseBody
//    public JSONObject getRelationNetwork(HttpServletRequest request) {
//        Integer userId = baseController.getLoginUserId(request);
//        JSONObject result = new JSONObject();
//
//        //一级信任伙伴
//        Map<String, Object> map = new HashMap<>();
//        Set<Integer> idSet = new HashSet<>();
//        idSet.add(userId);
//        map.put("student_id", userId);
//
//        List<Friend> friendList = friendService.selectByMap(map);
//        if (friendList.size() > 5) {
//            friendList = friendList.subList(0, 5);
//        }
//
//        JSONArray nodes = new JSONArray();
//        JSONArray edges = new JSONArray();
//        int level1 = friendList.size();   // 第一层节点
//        int level2 = 0;  // 第二层节点
//
//        JSONObject firstNode = new JSONObject();  // 中心节点为已登录的用户
//        firstNode.put("category", 0);
//        firstNode.put("name", String.valueOf(userId));
//        firstNode.put("value", 40);
//        nodes.add(firstNode);    // 将中心节点加入
//
//        List<BigDecimal> weights = new ArrayList<>();
//
//        for (int i = 0; i < level1; i++) {
//            idSet.add(friendList.get(i).getFriendId());
//        }
//
//        for (int i = 0; i < level1; i++) {  // 第一层节点
//            Friend friend = friendList.get(i);
//            JSONObject node = new JSONObject();  // 创建新节点
//            node.put("category", 1);
//            node.put("name", String.valueOf(friend.getFriendId()));
//            node.put("value", 30);
//            nodes.add(node);
////            recommendIds.add(friend.getFriendId());
//            JSONObject edge = new JSONObject();  // 创建新边
//            edge.put("source", String.valueOf(userId));   // 第一层每个节点都与中心节点相连
//            edge.put("target", String.valueOf(friend.getFriendId()));
//            BigDecimal weight = friend.getTrust();
//            edge.put("weight", String.format("%.2f", weight));
//            edges.add(edge);
//            weights.add(weight);
//
//            map.clear();
//            map.put("student_id", friend.getFriendId());
//            List<Friend> friendList1 = friendService.selectByMap(map);
//            if (friendList1 != null && friendList1.size() > 5) {
//                friendList1 = friendList1.subList(0, 5);
//            }
//            level2 = friendList1.size();
//
//            for (int i1 = 0; i1 < level2; i1++) {
//                int id = friendList1.get(i1).getFriendId();
//                if (!idSet.contains(id)) idSet.add(id);
//            }
//            for (int j = 0; j < level2; j++) {//第二层节点
//                Friend friend1 = friendList1.get(j);
//                if (idSet.contains(friend1.getFriendId())) continue;
//                idSet.add(friend1.getFriendId());
//                JSONObject node1 = new JSONObject();  // 创建新节点
//                node1.put("category", 2);
//                node1.put("name", String.valueOf(friend1.getFriendId()));
//                node1.put("value", 20);
//                nodes.add(node1);
//                JSONObject edge1 = new JSONObject();  // 创建新边
//                edge1.put("source", String.valueOf(friend1.getStudentId()));   // 第一层每个节点都与中心节点相连
//                edge1.put("target", String.valueOf(friend1.getFriendId()));
//                BigDecimal weight1 = friend1.getTrust();
//                edge1.put("weight", String.format("%.2f", weight1));
//                edges.add(edge1);
//                weights.add(weight1);
//
//                map.clear();
//                map.put("student_id", friend1.getFriendId());
//                List<Friend> friendList2 = friendService.selectByMap(map);
//                if (friendList2 != null && friendList2.size() > 5) {
//                    friendList2 = friendList2.subList(0, 5);
//                }
//                for (int k = 0; k < friendList2.size(); k++) {//第三层节点
//                    Friend friend2 = friendList2.get(k);
//                    if (idSet.contains(friend2.getFriendId())) continue;
//                    idSet.add(friend2.getFriendId());
//                    JSONObject node2 = new JSONObject();  // 创建新节点
//                    node2.put("category", 3);
//                    node2.put("name", String.valueOf(friend2.getFriendId()));
//                    node2.put("value", 10);
//                    nodes.add(node2);
//                    JSONObject edge2 = new JSONObject();  // 创建新边
//                    edge2.put("source", String.valueOf(friend2.getStudentId()));   // 第一层每个节点都与中心节点相连
//                    edge2.put("target", String.valueOf(friend2.getFriendId()));
//                    BigDecimal weight2 = friend2.getTrust();
//                    edge2.put("weight", String.format("%.2f", weight2));
//                    edges.add(edge2);
//                    weights.add(weight2);
//                }
//            }
//
//        }
//
//        result.put("nodes", nodes);
//        result.put("edges", edges);
//        result.put("userId", String.valueOf(userId));
//
//        return result;
//    }


    @ApiOperation(value = "推荐学习伙伴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getRecommendFriend")
    @ResponseBody
    public JSONArray getRecommendFriend(HttpServletRequest request) {
        Integer userId = baseController.getLoginUserId(request);
        EntityWrapper<RecFriend> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderBy("trust", false);//降序
        wrapper.gt("trust", 1);//共同好友数>1

        List<RecFriend> friendList = recFriendService.selectList(wrapper);

        JSONArray result = new JSONArray();
        for (RecFriend friend : friendList) {
            JSONObject resultCell = new JSONObject();
            Student student = studentMapper.selectById(friend.getFriendId());
            resultCell.put("studentId", student.getStudentId());
            resultCell.put("name", student.getNickname());
            resultCell.put("education", student.getEducation());
            resultCell.put("position", student.getCountry() + " | " + student.getProvince()
                    + " | " + student.getCity());
            resultCell.put("count", friend.getTrust());
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "推荐学习群组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/recommendCluster")
    @ResponseBody
    public JSONArray recommendCluster(HttpServletRequest request) {
        Integer studentId = baseController.getLoginUserId(request);
        Map<String, Object> map = new HashMap<>();
        map.put("student_id", studentId);
        List<RecCluster> list = recClusterService.selectByMap(map);

        JSONArray result = new JSONArray();
        for (RecCluster one : list) {
            JSONObject resultCell = new JSONObject();
            resultCell.put("studentId", one.getStudentId());
            resultCell.put("clusterId", one.getClusterId());
            resultCell.put("clusterName", one.getClusterName());

            Integer clusterId = one.getClusterId();
            EntityWrapper<Cluster> wrapper = new EntityWrapper<>();
            wrapper.eq("cluster_id", clusterId);
            Integer numsOfStu = clusterService.selectCount(wrapper);
            resultCell.put("numsOfStu", numsOfStu);
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "推荐课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/recommendCourse")
    @ResponseBody
    public List<Course> recommendCourse(HttpServletRequest request) {
        Integer studentId = baseController.getLoginUserId(request);
//        Map<String, Object> map = new HashMap<>();
//        map.put("student_id",studentId);

        EntityWrapper<RecCourse> wrapper = new EntityWrapper<>();
        wrapper.eq("student_id", studentId).last("limit 3");
//        List<RecCourse> recCourseList = recCourseService.selectByMap(map);
        List<RecCourse> recCourseList = recCourseService.selectList(wrapper);
        List<Course> result = new ArrayList<>();
        for (RecCourse one : recCourseList) {
            Course course = courseService.selectById(one.getCourseId());
            result.add(course);
        }
        return result;
    }
}
