package com.hit.lpm.management.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hit.lpm.common.PageResult;
import com.hit.lpm.management.model.*;
import com.hit.lpm.management.service.*;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.model.StudentCourseRelation;
import com.hit.lpm.portrait.service.StudentCourseRelationService;
import com.hit.lpm.portrait.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Api(value = "学习者管理相关功能", tags = "stuMgt")
@RestController
@RequestMapping("${api.version}/stuMgt")

public class OneStudentController {




    @Autowired
    private LearnStyleService learnStyleService;
    @Autowired
    private InterestTopicService interestTopicService;
    @Autowired
    private InterestDomainService interestDomainService;
    @Autowired
    private Course2Service course2Service;
    @Autowired
    private StudentCourseRelationService studentCourseRelationService;
    @Autowired
    private CourProgressService courProgressService;
    @Autowired
    private StuFriendService stuFriendService;
    @Autowired
    private Student2Service student2Service;
    //获取某一学生的标签
    @ApiOperation(value = "获取某一学生的标签")

    @GetMapping("/getStuStyle")
    @ResponseBody
    public JSONObject getStyle(Integer stuId){

        List<Map> m=learnStyleService.selectStyleOfId(stuId);

        JSONObject result=new JSONObject();

        for(Object key :m.get(0).keySet()){
            result.put((String) key,m.get(0).get(key));
        }



        return result;
    }

    //获取学生的感兴趣话题
    @ApiOperation(value = "获取某一学习者标签和感兴趣话题")

    @GetMapping("/getStuTopic")
    @ResponseBody
    public JSONArray getTopic(Integer stuId){

        List<Map> listGot=interestTopicService.selectTopicOfId(stuId);

        JSONArray result=new JSONArray();

        for(Map m:listGot){

            JSONObject temp=new JSONObject();
            temp.put("topic",m.get("topic"));
            temp.put("score",m.get("score"));
            result.add(temp);
        }

        return result;
    }
    //获取学生的感兴趣领域
    @ApiOperation(value = "获取学生的感兴趣领域")

    @GetMapping("/getStuDomain")
    @ResponseBody
    public JSONArray getStuDomain(Integer stuId){

        List<Map> listGot=interestDomainService.selectDomainOfId(stuId);

        JSONArray result=new JSONArray();

        for(Map m:listGot){

            JSONObject temp=new JSONObject();
            temp.put("domain",m.get("domain"));
            temp.put("score",m.get("score"));
            result.add(temp);
        }

        return result;
    }

    //某一学习者感兴趣课程
    @ApiOperation(value = "某一学习者感兴趣课程")
    @GetMapping("/getStuIntCour")

    @ResponseBody
    public PageResult<Course2> getStuIntCourse(Integer page, Integer limit, Integer stuId){//        List<HashMap> stuList=student2Service.selectSomeAttributeOfAllStudent();

        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<Course2> courPage = new Page<>(page, limit);
        EntityWrapper<Course2> wrapper = new EntityWrapper<>();
        if (stuId != null) {
            List courList=studentCourseRelationService.selectList(new EntityWrapper<StudentCourseRelation>().eq("student_id", stuId+"").and().gt("score","50"));
            List<String> courIdList=new ArrayList<>();
            for(int i=0;i<=courList.size()-1;i++){
                StudentCourseRelation temp=(StudentCourseRelation) (courList.get(i));
                courIdList.add(temp.getCourseId());
            }


            if(courIdList.size()>0){
                wrapper.in("course_id",courIdList);
            }else{
                wrapper.eq("course_id","NULL COURSE");
            }
        }

        course2Service.selectPage(courPage, wrapper);
        List<Course2> userList = courPage.getRecords();

        return new PageResult<Course2>(userList, courPage.getTotal());
    }


    //某一学习者选择的课程
    @ApiOperation(value = "某一学习者感兴趣课程")
    @GetMapping("/getStuCour")

    @ResponseBody
    public PageResult<Course2> getStuCourse(Integer page, Integer limit, Integer stuId){//        List<HashMap> stuList=student2Service.selectSomeAttributeOfAllStudent();

        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<Course2> courPage = new Page<>(page, limit);
        EntityWrapper<Course2> wrapper = new EntityWrapper<>();
        if (stuId != null) {
            List courList=studentCourseRelationService.selectList(new EntityWrapper<StudentCourseRelation>().eq("student_id", stuId+""));
            List<String> courIdList=new ArrayList<>();
            for(int i=0;i<=courList.size()-1;i++){
                StudentCourseRelation temp=(StudentCourseRelation) (courList.get(i));
                courIdList.add(temp.getCourseId());
            }

            if(courIdList.size()>0){
                wrapper.in("course_id",courIdList);
            }else{
                wrapper.eq("course_id","NULL COURSE");
            }
        }


        course2Service.selectPage(courPage, wrapper);
        List<Course2> userList = courPage.getRecords();

        return new PageResult<Course2>(userList, courPage.getTotal());
    }

    //学生删除已选课程
    @PostMapping("/delStuCour")
    public JSONObject delCourse(String courseId,Integer stuId){

        StudentCourseRelation scr=studentCourseRelationService.selectOne(
                new EntityWrapper<StudentCourseRelation>()
                        .eq("student_id", stuId+"").and()
                        .eq("course_id",courseId+""));
        JSONObject result=new JSONObject();
        if(scr==null){
            result.put("ifDone",false);
            return result;
        }
        result.put("ifDone",true);
        studentCourseRelationService.deleteById(scr.getScId());
        return result;
    }
    //学生选新课
    @PostMapping("/addStuCour")
    public JSONObject addStuCour(String courseId,Integer stuId){

        StudentCourseRelation scr2=studentCourseRelationService.selectOne(
                new EntityWrapper<StudentCourseRelation>().eq("course_id",courseId)
                        .and().eq("student_id",""+stuId));
        JSONObject result=new JSONObject();
        if(scr2!=null){
            result.put("ifDone",false);
            return result;
        }
        result.put("ifDone",true);
        StudentCourseRelation scrNew=new StudentCourseRelation();


        //查询最大id是哪个
        List tempList=studentCourseRelationService.selectList(

                new EntityWrapper<StudentCourseRelation>().orderDesc(Arrays.asList(new String[] {"sc_id"})));

        StudentCourseRelation tempSCR=(StudentCourseRelation)(tempList.get(0));

        Integer newId=tempSCR.getScId()+1;
        //scrNew.setScId(newId);
        scrNew.setCourseId(courseId);
        scrNew.setStudentId(stuId);
        scrNew.setScore(0);


        studentCourseRelationService.insert(scrNew);

        return result;
    }


    //获取一学习者学习进度所有信息
    @ApiOperation(value = "获取一学习者学习进度所有信息")
    @GetMapping("/getStuPro")

    @ResponseBody
    public JSONObject getStuPro(Integer stuId){//        List<HashMap> stuList=student2Service.selectSomeAttributeOfAllStudent();

        JSONObject result=new JSONObject();
        List courList=studentCourseRelationService.selectList(new EntityWrapper<StudentCourseRelation>().eq("student_id", stuId+""));
        List<String> courIdList=new ArrayList<>();
        String[] courNameArr=new String[courList.size()];
        for(int i=0;i<=courList.size()-1;i++){
            StudentCourseRelation temp=(StudentCourseRelation) (courList.get(i));
            courIdList.add(temp.getCourseId());
            Course2 tempCour=course2Service.selectOne(new EntityWrapper<Course2>().eq("course_id", temp.getCourseId()));
            courNameArr[i]=tempCour.getCourseName();
        }
        result.put("name",courNameArr);
        //准备成绩分布图参数
        JSONArray jsa1=new JSONArray();
        for(int i=0;i<=courIdList.size()-1;i++){
            JSONObject tempJso=new JSONObject();
            tempJso.put("name",courNameArr[i]);
            String courIdNow=courIdList.get(i);
            List<CourProgress> cpList=courProgressService.selectList(
                    new EntityWrapper<CourProgress>().eq("course_id", ""+courIdNow)
                            .and().eq("student_id", ""+stuId).orderBy("unit"));
            int[] score=new int[7];
            System.out.println(courIdList.get(i));
            for(int j=0;j<=cpList.size()-1;j++){
                score[j]=cpList.get(j).getScore();
            }
            tempJso.put("data",score);
            tempJso.put("type","line");
            jsa1.add(tempJso);
        }
       result.put("chart1",jsa1);

        //准备课堂参与讨论度图参数
        JSONArray jsa2=new JSONArray();
        for(int i=0;i<=courIdList.size()-1;i++){
            JSONObject tempJso=new JSONObject();
            tempJso.put("name",courNameArr[i]);
            String courIdNow=courIdList.get(i);
            List<CourProgress> cpList=courProgressService.selectList(
                    new EntityWrapper<CourProgress>().eq("course_id", ""+courIdNow)
                            .and().eq("student_id", ""+stuId).orderBy("unit"));
            int[] score=new int[7];
            System.out.println(courIdList.get(i));
            for(int j=0;j<=cpList.size()-1;j++){
                score[j]=cpList.get(j).getParticipation();
            }
            tempJso.put("data",score);
            tempJso.put("type","line");
            jsa2.add(tempJso);
        }
        result.put("chart2",jsa2);


        //准学习进度图参数
        JSONArray jsa3=new JSONArray();
        int[] courseP=new int[courIdList.size()];
        int[] disP=new int[courIdList.size()];
        int[] examP=new int[courIdList.size()];
        for(int i=0;i<=courIdList.size()-1;i++){
            String courIdNow=courIdList.get(i);
            List<CourProgress> cpList=courProgressService.selectList(
                    new EntityWrapper<CourProgress>().eq("course_id", ""+courIdNow)
                            .and().eq("student_id", ""+stuId).orderBy("unit"));
            if(cpList.size()>0){
                courseP[i]=cpList.get(0).getCourseP();
                disP[i]=cpList.get(0).getDiscussionP();
                examP[i]=cpList.get(0).getExamP();
            }else{
                courseP[i]=0;
                disP[i]=0;
                examP[i]=0;
            }

        }
        JSONObject PTempJsp1=new JSONObject();
        PTempJsp1.put("name","课件观看进度");
        PTempJsp1.put("type","bar");
        PTempJsp1.put("data",courseP);
        jsa3.add(PTempJsp1);

        JSONObject PTempJsp2=new JSONObject();
        PTempJsp2.put("name","课堂讨论进度");
        PTempJsp2.put("type","bar");
        PTempJsp2.put("data",disP);
        jsa3.add(PTempJsp2);

        JSONObject PTempJsp3=new JSONObject();
        PTempJsp3.put("name","测试测验进度");
        PTempJsp3.put("type","bar");
        PTempJsp3.put("data",examP);
        jsa3.add(PTempJsp3);

        result.put("chart3",jsa3);


        return result;
    }
    //某一学习者的好友
    @ApiOperation(value = "某一学习者的好友")
    @GetMapping("/getStuFri")

    @ResponseBody
    public PageResult<Student2> getStuFri(Integer page, Integer limit, Integer stuId){//        List<HashMap> stuList=student2Service.selectSomeAttributeOfAllStudent();

        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<Student2> stuPage = new Page<>(page, limit);
        EntityWrapper<Student2> wrapper = new EntityWrapper<>();
        if (stuId != null) {
            List stuList=stuFriendService.selectList(new EntityWrapper<StuFriend>().eq("student_id", ""+stuId));
            List<Integer> stuIdList=new ArrayList<>();
            for(int i=0;i<=stuList.size()-1;i++){
                StuFriend temp=(StuFriend) (stuList.get(i));
                stuIdList.add(temp.getFriendId());
            }

            if(stuIdList.size()>0){
                wrapper.in("student_id",stuIdList);
            }else{
                wrapper.eq("student_id","NULL STUDENT");
            }
        }


        student2Service.selectPage(stuPage, wrapper);
        List<Student2> userList = stuPage.getRecords();

        return new PageResult<Student2>(userList, stuPage.getTotal());
    }
    @ApiOperation(value = "删除某一学习者某位好友", notes = "")

    @PostMapping("/delStuFri")
    public JSONObject delStuFri(int stuId,int friID){
//        Student2 temp=student2Service.selectById(studentId);
//        JSONObject result=new JSONObject();
//        if(temp==null){
//            result.put("ifDone",false);
//            return result;
//        }
//        result.put("ifDone",true);
//        student2Service.deleteById(studentId);
        System.out.println(stuId);
        System.out.println(friID);
        StuFriend temp=stuFriendService.selectOne(
                new EntityWrapper<StuFriend>().eq("student_id", ""+stuId)
                        .and().eq("friend_id",""+friID));
        JSONObject result=new JSONObject();
        if(temp==null){
            result.put("ifDone",false);
            return result;
        }
        result.put("ifDone",true);
        stuFriendService.deleteById(temp.getSfId());
        return result;
    }
    //学生添加新好友
    @PostMapping("/addStuFri")
    public JSONObject addStuFri(Integer stuId, Integer friId){

        StuFriend sf2=stuFriendService.selectOne(
                new EntityWrapper<StuFriend>().eq("student_id",""+stuId).
                        and().eq("friend_id",""+friId));
        JSONObject result=new JSONObject();
        if(sf2!=null){
            result.put("ifDone",false);
            return result;
        }
        result.put("ifDone",true);
        StuFriend sfNew=new StuFriend();

        //查询最大id是哪个
        List tempList=stuFriendService.selectList(

                new EntityWrapper<StuFriend>().orderDesc(Arrays.asList(new String[] {"sf_id"})));

        StuFriend tempSf=(StuFriend) (tempList.get(0));

        Integer newId=tempSf.getSfId()+1;
        //scrNew.setScId(newId);
        sfNew.setSfId(newId);
        sfNew.setStudentId(stuId);
        sfNew.setFriendId(friId);


        stuFriendService.insert(sfNew);

        return result;
    }

    //获取某一学生的基本信息
    @ApiOperation(value = "获取某一学生的基本信息")

    @GetMapping("/getStuOneInf")
    @ResponseBody
    public JSONObject getStuOneInf(Integer stuId){

        Student2 stu=student2Service.selectOne(new EntityWrapper<Student2>().eq("student_id",""+stuId));
        JSONObject result=new JSONObject();
        result.put("1", "姓名: "+stu.getStudentName());
        result.put("2", "id: "+stu.getStudentId());
        result.put("3", "昵称: "+stu.getNickname());
        result.put("4", "性别: "+stu.getGender());
        result.put("5","生日: "+stu.getBirthday());
        result.put("6", "学历: "+stu.getEducation());
        result.put("7", "省份: "+stu.getCity());


        return result;
    }
}
