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
import com.hit.lpm.recommend.model.RecCluster;
import com.hit.lpm.recommend.model.RecCourse;
import com.hit.lpm.recommend.model.RecFriend;
import com.hit.lpm.recommend.service.RecClusterService;
import com.hit.lpm.recommend.service.RecCourseService;
import com.hit.lpm.recommend.service.RecFriendService;
import com.hit.lpm.recommend.service.RecStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
        // // 随机获取40个学生的id
        List<Integer> studentLst = recStudentService.getRandomStudentId(40);
        //studentLst.set(0, 71361);
        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();
        int level1 = 5;   // 第一层节点
        Random r = new Random();
        int level2 = r.nextInt(5) + 15;  // 第二层节点

        JSONObject firstNode = new JSONObject();  // 中心节点为已登录的用户
        firstNode.put("category", 0);
        firstNode.put("name", String.valueOf(userId));
        firstNode.put("value", 40);
        nodes.add(firstNode);    // 将中心节点加入

        List<Integer> recommendIds = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        for (int i = 0; i < level1; i++) {  // 第一层节点
            JSONObject node = new JSONObject();  // 创建新节点
            node.put("category", 1);
            node.put("name", String.valueOf(studentLst.get(i)));
            node.put("value", 30);
            nodes.add(node);
            recommendIds.add(studentLst.get(i));
            JSONObject edge = new JSONObject();  // 创建新边
            edge.put("source", String.valueOf(userId));   // 第一层每个节点都与中心节点相连
            edge.put("target", String.valueOf(studentLst.get(i)));
            Double weight = r.nextDouble() * 0.3 + 0.7;
            edge.put("weight", String.format("%.2f", weight));
            edges.add(edge);
            weights.add(weight);
        }

        for (int i = level1; i < level2; i++) {  // 第二层节点
            JSONObject node = new JSONObject();  // 创建新节点
            node.put("category", 2);
            node.put("name", String.valueOf(studentLst.get(i)));
            node.put("value", 20);
            nodes.add(node);
            for (int j = 0; j < r.nextInt(2) + 1; j++) {  // 第二层每个节点至少与一个第一层节点相连
                JSONObject edge = new JSONObject();  // 创建新边
                edge.put("source", String.valueOf(studentLst.get(r.nextInt(4))));
                edge.put("target", String.valueOf(studentLst.get(i)));
                edge.put("weight", String.format("%.2f", r.nextDouble() * 0.3 + 0.7));
                edges.add(edge);
            }
        }

        for (int i = level2; i < 40; i++) {  // 第三层节点
            JSONObject node = new JSONObject();  // 创建新节点
            node.put("category", 3);
            node.put("name", String.valueOf(studentLst.get(i)));
            node.put("value", 10);
            nodes.add(node);
            for (int j = 0; j < r.nextInt(2) + 1; j++) {  // 第三层每个节点至少与一个第二层节点相连
                JSONObject edge = new JSONObject();
                edge.put("source", String.valueOf(studentLst.get(r.nextInt(level2 - level1 - 1) + 5)));
                edge.put("target", String.valueOf(studentLst.get(i)));
                edge.put("weight", String.format("%.2f", r.nextDouble() * 0.3 + 0.7));
                edges.add(edge);
            }
        }

        // 排序
        for (int i = 0; i < level1; i++) {
            for (int j = 0; j < level1 - i - 1; j++) {
                if (weights.get(j) < weights.get(j + 1)) {
                    // 交换weights数组中数据位置
                    Double tempWeight = weights.get(j);
                    weights.set(j, weights.get(j + 1));
                    weights.set(j + 1, tempWeight);
                    // 交换recommendIds数组中数据中位置
                    Integer tempId = recommendIds.get(j);
                    recommendIds.set(j, recommendIds.get(j + 1));
                    recommendIds.set(j + 1, tempId);
                }
            }
        }
        result.put("nodes", nodes);
        result.put("edges", edges);
        result.put("userId", String.valueOf(userId));
        return result;
    }

//    @ApiOperation(value = "推荐学习伙伴")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
//    })
//    @GetMapping("/recommendStudent")
//    @ResponseBody
//    public JSONArray recommendStudent(){
//        List<Student> studentLst = recStudentService.getRandomStudentInfo();
//        JSONArray result = new JSONArray();
//        Random r = new Random();
//        for(Student student : studentLst){
//            JSONObject resultCell = new JSONObject();
//            resultCell.put("studentName", student.getNickname());
//            resultCell.put("studentId", student.getStudentId());
//            double trust = r.nextDouble() / 5 + 0.8;
//            resultCell.put("trust", String.format("%.2f", trust));
//            resultCell.put("course", "计算机网络");
//            result.add(resultCell);
//        }
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
        wrapper.eq("student_id",studentId).last("limit 3");
//        List<RecCourse> recCourseList = recCourseService.selectByMap(map);
        List<RecCourse> recCourseList = recCourseService.selectList(wrapper);
        List<Course> result = new ArrayList<>();
        for(RecCourse one : recCourseList){
            Course course = courseService.selectById(one.getCourseId());
            result.add(course);
        }
        return result;
    }
}
