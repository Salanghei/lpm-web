package com.hit.lpm.management.controller;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.management.model.Course2;
import com.hit.lpm.management.model.Student2;
import com.hit.lpm.management.service.Course2Service;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Api(value = "课程管理相关功能", tags = "courMgt")
@RestController
@RequestMapping("${api.version}/courMgt")
public class CozMgtController {

    @Autowired
    private Course2Service course2Service;

    //获取所有课程领域和数量
    @ApiOperation(value = "获取所有课程领域和数量")
    @GetMapping("/getCourType")
    @ResponseBody
    public JSONObject getCourType(){
        int aimCount=20;// 只统计课程数量前多少名的课程领域

        JSONObject result=new JSONObject();
        List<Map<String,Object>> courTypeMaps=course2Service.selectAllTypeAndCount();

        for(int i=0;i<=aimCount-1&& i<=courTypeMaps.size()-1;i++){
            String typeName= (String) courTypeMaps.get(i).get("course_type");
            Long typeCount= (Long) courTypeMaps.get(i).get("count(*)");
            if(typeName!=null && typeName.equals("其他")==false  ){
                result.put(typeName,typeCount);
            }
            else{
                aimCount++;
            }
        }


        return result;
    }


    //获取所有课程领域和数量
    @ApiOperation(value = "获取所有课程领域和数量")
    @GetMapping("/getCourSch")
    @ResponseBody
    public JSONObject getCourSch(){
        int aimCount=20;// 只统计课程数量前多少名的开课机构

        JSONObject result=new JSONObject();
        List<Map<String,Object>> courSchMaps=course2Service.selectAllSchAndCount();

        for(int i=0;i<=aimCount-1&& i<=courSchMaps.size()-1;i++){
            String schName= (String) courSchMaps.get(i).get("course_school");
            Long schCount= (Long) courSchMaps.get(i).get("count(*)");
            if(schName!=null && schName.equals("其他")==false  ){
                result.put(schName,schCount);
            }
            else{
                aimCount++;
            }
        }
        return result;
    }


    //-------------------------------------------------------------

    //获取所有课程多种信息,用于课程管理表格
    @ApiOperation(value = "获取所有课程多种信息")
    @GetMapping("/getInfAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "searchKey", value = "筛选条件字段", dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "searchValue", value = "筛选条件关键字", dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @ResponseBody
    public PageResult<Course2> getAllCourse(Integer page, Integer limit, String searchKey, String searchValue){//        List<HashMap> stuList=student2Service.selectSomeAttributeOfAllStudent();

        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<Course2> courPage = new Page<>(page, limit);
        EntityWrapper<Course2> wrapper = new EntityWrapper<>();
        if (searchKey != null && !searchKey.trim().isEmpty() && searchValue != null && !searchValue.trim().isEmpty()) {
            wrapper.eq(searchKey, searchValue);
        }

        course2Service.selectPage(courPage, wrapper);
        List<Course2> userList = courPage.getRecords();

        return new PageResult<Course2>(userList, courPage.getTotal());
    }
    @ApiOperation(value = "删除某一课程", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "String", paramType = "form"),
    })

    @PostMapping("/delCour")
    public JSONObject delCourse(String courseId){
        Course2 temp=course2Service.selectById(courseId);
        JSONObject result=new JSONObject();
        if(temp==null){
            result.put("ifDone",false);
            return result;
        }
        result.put("ifDone",true);
        course2Service.deleteById(courseId);

        return result;
    }

    @ApiOperation(value = "添加某一学课程", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "courseName", value = "课程名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "domainId", value = "领域id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "teacher", value = "教师", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "courseInfo", value = "课程介绍", required = true, dataType = "String", paramType = "form"),

            @ApiImplicitParam(name = "courseUrl", value = "课程地址", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "courseType", value = "课程类型", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "prerequisites", value = "前置知识", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "courseSchool", value = "开课机构", required = true, dataType = "String", paramType = "form"),
    })
    @PostMapping("/addCour")
    public JSONObject addStudent(String courseId,String courseName,int domainId,
                                 String teacher,String courseInfo,String courseUrl,
                                 String courseType, String prerequisites,String courseSchool){
        Course2 temp=course2Service.selectById(courseId);
        JSONObject result=new JSONObject();
        if(temp!=null){
            result.put("ifDone",false);
            return result;
        }
        result.put("ifDone",true);
        Course2 c2=new Course2();
        c2.setCourseId(courseId);
        c2.setCourseName(courseName);
        c2.setDomainId(domainId);
        c2.setTeacher(teacher);
        c2.setCourseInfo(courseInfo);
        c2.setCourseUrl(courseUrl);
        c2.setCourseType(courseType);
        c2.setPrerequisites(prerequisites);
        c2.setCourseSchool(courseSchool);
        course2Service.insert(c2);

        return result;
    }


}
