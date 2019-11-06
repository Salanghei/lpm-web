package com.hit.lpm.recommend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.potrait.service.StudentService;
import com.hit.lpm.recommend.model.RecResource;
import com.hit.lpm.recommend.service.RecResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-4 21:57
 **/
@Api(value = "资源相关功能", tags = "resource")
@RestController
@RequestMapping("${api.version}/resource")
public class ResourceController {
    @Autowired
    private RecResourceService recResourceService;

    @Autowired
    private StudentService studentService;

    private BaseController baseController = new BaseController();

    @ApiOperation(value = "获取全部资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getAllResource")
    @ResponseBody
    public JSONArray getAllResource(){
        List<RecResource> resourceList =  recResourceService.selectList(
                new EntityWrapper<RecResource>().eq("state", "pass"));
        JSONArray result = new JSONArray();
        for(RecResource resource : resourceList){
            JSONObject resultCell = new JSONObject();
            resultCell.put("name", resource.getName());
            resultCell.put("tags", resource.getTag().split(","));
            resultCell.put("time", resource.getTime());
            resultCell.put("type", resource.getType() + ".svg");
            resultCell.put("resourceId", resource.getResourceId());
            resultCell.put("auth", resource.getAuth());
            Integer uploaderId = resource.getStudentId();
            resultCell.put("uploader", studentService.selectById(uploaderId).getNickname());
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "获取某用户的已通过资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getUserPassResource")
    @ResponseBody
    public JSONArray getUserPassResource(HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        List<RecResource> resourceList = recResourceService.selectList(
                new EntityWrapper<RecResource>().eq("student_id", userId).eq("state", "pass"));
        JSONArray result = new JSONArray();
        for(RecResource resource : resourceList){
            JSONObject resultCell = new JSONObject();
            resultCell.put("name", resource.getName());
            if(!resource.getTag().equals("")) {
                resultCell.put("tags", resource.getTag().split(","));
            }else{
                resultCell.put("tags", new String[0]);
            }
            resultCell.put("time", resource.getTime());
            resultCell.put("type", resource.getType() + ".svg");
            resultCell.put("resourceId", resource.getResourceId());
            resultCell.put("auth", resource.getAuth());
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "获取某用户的待审核资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getUserTopassResource")
    @ResponseBody
    public JSONArray getUserTopassResource(HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        List<RecResource> resourceList = recResourceService.selectList(
                new EntityWrapper<RecResource>().eq("student_id", userId).eq("state", "topass"));
        JSONArray result = new JSONArray();
        for(RecResource resource : resourceList){
            JSONObject resultCell = new JSONObject();
            resultCell.put("name", resource.getName());
            if(!resource.getTag().equals("")) {
                resultCell.put("tags", resource.getTag().split(","));
            }else{
                resultCell.put("tags", new String[0]);
            }
            resultCell.put("time", resource.getTime());
            resultCell.put("type", resource.getType() + ".svg");
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "获取某用户的未通过资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getUserFailResource")
    @ResponseBody
    public JSONArray getUserFailResource(HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        List<RecResource> resourceList = recResourceService.selectList(
                new EntityWrapper<RecResource>().eq("student_id", userId).eq("state", "fail"));
        JSONArray result = new JSONArray();
        for(RecResource resource : resourceList){
            JSONObject resultCell = new JSONObject();
            resultCell.put("name", resource.getName());
            resultCell.put("time", resource.getTime());
            resultCell.put("type", resource.getType() + ".svg");
            if(!resource.getTag().equals("")) {
                resultCell.put("tags", resource.getTag().split(","));
            }else{
                resultCell.put("tags", new String[0]);
            }
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "上传资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "auth", value = "权限", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tag", value = "标签", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "details", value = "描述", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/uploadResource")
    @ResponseBody
    public Map<String, String> uploadResource(MultipartFile file, String auth, String tag, String details, HttpServletRequest request){
        Map<String, String> message = new HashMap<>();
        message.put("msg", "empty");
        if(!file.isEmpty()) {
            Integer userId = baseController.getLoginUserId(request);
            String uploadPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploads/" + userId;
            System.out.println(uploadPath);
            String fileName = file.getOriginalFilename();
            File newFile = new File(uploadPath + "/" + fileName);
            newFile.getParentFile().mkdirs();
            try {
                if(!newFile.exists()) {
                    newFile.createNewFile();
                }
                file.transferTo(newFile);
                message.put("msg", "success");
            } catch (Exception e) {
                e.printStackTrace();
                message.put("msg", "error");
            }
            List<String> typeLst = Arrays.asList("doc", "docx", "pdf", "ppt", "pptx", "rar", "txt", "zip", "xls", "xlsx");
            RecResource resource = new RecResource();
            resource.setName(fileName);
            String newFileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            if(typeLst.contains(newFileType)){  // 设置资源类型
                resource.setType(newFileType);
            }else{
                resource.setType("other");
            }
            resource.setSize(String.valueOf(newFile.length()));  // 资源大小
            Date date = new Date();
            resource.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            resource.setStudentId(userId);
            resource.setTag(tag);
            resource.setAuth(auth);
            resource.setDetails(details);
            resource.setResourceId(Integer.valueOf(new SimpleDateFormat("MMddHHmmss").format(date)));
            resource.setState("pass");
            recResourceService.insertAllColumn(resource);
        }
        message.put("code", "0");
        System.out.println(JSON.toJSONString(message));
        return message;
    }
}
