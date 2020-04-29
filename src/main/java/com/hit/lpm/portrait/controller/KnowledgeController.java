package com.hit.lpm.portrait.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.portrait.model.*;
import com.hit.lpm.portrait.service.*;
import com.hit.lpm.system.model.User;
import com.hit.lpm.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-10-13 19:57
 **/
@Api(value = "兴趣意图", tags = "knowledge")
@RestController
@RequestMapping("${api.version}/knowledge")
public class KnowledgeController extends BaseController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private  KnowledgeNodeService knowledgeNodeService;
    @Autowired
    private StudentKnowledgeRelationService studentKnowledgeRelationService;
    @Autowired
    private UserService userService;




    @ApiOperation(value = "查询知识点及掌握程度")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping
    public PageResult<Map<String, Object>> listKnowledge(HttpServletRequest request) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Integer userId = getLoginUserId(request);
        User user = userService.selectById(userId);
        //Integer stuId = studentService.selectOne(new EntityWrapper<Student>().eq("user_id", user.getUsername())).getStudentId();
        Integer stuId = 1;
       if (user.getUsername().matches("^[0-9]*$")) stuId = Integer.valueOf(user.getUsername());
        List<StudentKnowledgeRelation> sks = studentKnowledgeRelationService
                .selectList(new EntityWrapper<StudentKnowledgeRelation>().eq("student_id", stuId));
        for (StudentKnowledgeRelation sk : sks) {
            Map<String, Object> map = new HashMap<>();
            KnowledgeNode node = knowledgeNodeService.selectById(sk.getNodeId());
            KnowledgeNode pnode = knowledgeNodeService.selectById(node.getParentId());
            map.put("knowledge_node", node.getName());
            if(pnode!=null)map.put("parent_knowledge_node", pnode.getName());
            map.put("score", sk.getScore());
            maps.add(map);
        }
        return new PageResult<>(maps);
    }


}
