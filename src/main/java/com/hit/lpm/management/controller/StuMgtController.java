package com.hit.lpm.management.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.service.StudentService;
import com.hit.lpm.management.model.Student2;
import com.hit.lpm.management.service.Student2Service;
import com.hit.lpm.system.model.Role;
import com.hit.lpm.system.model.User;
import com.hit.lpm.system.model.UserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "学习者管理相关功能", tags = "stuMgt")
@RestController
@RequestMapping("${api.version}/stuMgt")
public class StuMgtController {


    @Autowired
    private StudentService studentService;

    @Autowired
    private Student2Service student2Service;


    //获取所有学生性别
    @ApiOperation(value = "获取所有学生性别")

    @GetMapping("/getGender")
    @ResponseBody
    public JSONObject getGender(){
        int count1,count2,count3;
        count1 = studentService.selectCount(new EntityWrapper<Student>().eq("gender", "男"));
        count2 = studentService.selectCount(new EntityWrapper<Student>().eq("gender", "女"));
        count3 = studentService.selectCount(new EntityWrapper<Student>().eq("gender", "其他"));

        JSONObject result=new JSONObject();

        result.put("男",count1);
        result.put("女",count2);
        result.put("其他",count3);

        return result;
    }

    //获取所有学生教育程度
    @ApiOperation(value = "获取所有学生教育程度统计")

    @GetMapping("/getEdu")
    @ResponseBody
    public JSONObject getEdu(){
        int count[]=new int[8];

        count[0] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "小学"));
        count[1] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "初中"));
        count[2]= studentService.selectCount(new EntityWrapper<Student>().eq("education",  "高中"));
        count[3] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "本科"));
        count[4] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "专科"));
        count[5] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "硕士"));
        count[6] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "博士"));
        count[7] = studentService.selectCount(new EntityWrapper<Student>().eq("education", "其他"));

        JSONObject result=new JSONObject();

        result.put("小学",count[0]);
        result.put("初中",count[1]);
        result.put("高中",count[2]);
        result.put("本科",count[3]);
        result.put("专科",count[4]);
        result.put("硕士",count[5]);
        result.put("博士",count[6]);
        result.put("其他",count[7]);

        return result;
    }


    //获取所有学生省份和人数
    @ApiOperation(value = "获取所有学生省份和人数")
    @GetMapping("/getPro")
    @ResponseBody
    public JSONObject getPro(){
        int statisticsCount=16;// 只统计人数前多少名的省份
        JSONObject result=new JSONObject();
        List<Map<String,Object>> proMaps=student2Service.selectAllProvinceAndCount();
        int totalCount=student2Service.selectAllCount();
        for(int i=0;i<=statisticsCount-1&& i<=proMaps.size()-1;i++){
            String proName= (String) proMaps.get(i).get("province");
            Long proCount= (Long) proMaps.get(i).get("count(*)");

            if(proName.equals("其他")==false){
                result.put(proName,proCount);
            }
        }


        return result;
    }

    //获取所有学生多种信息,用于学生管理表格
    @ApiOperation(value = "获取所有学生学生多种信息")
    @GetMapping("/getInfAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "searchKey", value = "筛选条件字段", dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "searchValue", value = "筛选条件关键字", dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @ResponseBody
    public PageResult<Student2> getAllStudent(Integer page, Integer limit, String searchKey, String searchValue){//        List<HashMap> stuList=student2Service.selectSomeAttributeOfAllStudent();
//        JSONObject result=new JSONObject();
//        result.put("code",0);
//        result.put("msg","");
//        result.put("count",stuList.size());
//        JSONArray jsArr=new JSONArray();
//        for(HashMap tempMap:stuList){
//
//            JSONObject temp=new JSONObject();
//            //student_id,student_name,nickname,gender,birthday,education,province
//            temp.put("student_id",tempMap.get("student_id"));
//            temp.put("student_name",tempMap.get("student_name"));
//            temp.put("nickname",tempMap.get("nickname"));
//            temp.put("gender",tempMap.get("gender"));
//            temp.put("birthday",tempMap.get("birthday"));
//            temp.put("education",tempMap.get("education"));
//            temp.put("province",tempMap.get("province"));
//            jsArr.add(temp);
//        }
//        result.put("data",jsArr);
//        return result;
//        Student2 s2=new Student2();
//        s2.setStudentId(999999999);
//        student2Service.insert(s2);
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<Student2> stuPage = new Page<>(page, limit);
        EntityWrapper<Student2> wrapper = new EntityWrapper<>();
        if (searchKey != null && !searchKey.trim().isEmpty() && searchValue != null && !searchValue.trim().isEmpty()) {
            wrapper.eq(searchKey, searchValue);
        }

        student2Service.selectPage(stuPage, wrapper);
        List<Student2> userList = stuPage.getRecords();

        return new PageResult<Student2>(userList, stuPage.getTotal());
    }
    @ApiOperation(value = "删除某一学习者", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "用户id", required = true, dataType = "int", paramType = "form"),
    })
    @PostMapping("/delStu")
    public JSONObject delStudent(int studentId){
        Student2 temp=student2Service.selectById(studentId);
        JSONObject result=new JSONObject();
        if(temp==null){
            result.put("ifDone",false);
            return result;
        }
        result.put("ifDone",true);
        student2Service.deleteById(studentId);

        return result;
    }

    @ApiOperation(value = "添加某一学习者", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "studentName", value = "学生姓名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "nickname", value = "昵称", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别", required = true, dataType = "String", paramType = "form"),

            @ApiImplicitParam(name = "education", value = "教育水平", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "country", value = "国家", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "province", value = "省份", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "city", value = "城市", required = true, dataType = "String", paramType = "form"),
    })
    @PostMapping("/addStu")
    public JSONObject addStudent(Integer studentId,Integer userId,String studentName,
                           String nickname,String gender,String education,
                           String country, String province,String city){
        Student2 temp=student2Service.selectById(studentId);
        JSONObject result=new JSONObject();
        if(temp!=null){
            result.put("ifDone",false);
            return result;
        }
        result.put("ifDone",true);
        Student2 s2=new Student2();
        s2.setStudentId(studentId);
        s2.setUserId(userId);
        s2.setStudentName(studentName);
        s2.setNickname(nickname);
        s2.setGender(gender);
        s2.setEducation(education);
        s2.setCountry(country);
        s2.setProvince(province);
        s2.setCity(city);
        student2Service.insert(s2);

        return result;
    }

}
