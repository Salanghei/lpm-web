package com.hit.lpm.recommend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hit.lpm.common.BaseController;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.service.StudentService;
import com.hit.lpm.recommend.model.RecResource;
import com.hit.lpm.recommend.model.RecResourceApply;
import com.hit.lpm.recommend.model.ResourceStudentAuth;
import com.hit.lpm.recommend.service.RecResourceApplyService;
import com.hit.lpm.recommend.service.RecResourceService;
import com.hit.lpm.recommend.service.ResourceStudentAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
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

    @Autowired
    private ResourceStudentAuthService resourceStudentAuthService;

    @Autowired
    private RecResourceApplyService recResourceApplyService;

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
            if(!resource.getTag().equals("")) {
                resultCell.put("tags", resource.getTag().split(","));
            }else{
                resultCell.put("tags", new String[0]);
            }
            resultCell.put("time", resource.getTime());
            resultCell.put("type", resource.getType() + ".svg");
            resultCell.put("resourceId", resource.getResourceId());
            Integer uploaderId = resource.getStudentId();
            resultCell.put("uploader", studentService.selectById(uploaderId).getNickname());
            resultCell.put("uploaderId", uploaderId);
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "获取资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "resourceid", value = "资源ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploaderid", value = "资源上传者的ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getResource")
    @ResponseBody
    public JSONObject getResource(String resourceid, String uploaderid, HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        JSONObject result = new JSONObject();
        RecResource resource = recResourceService.selectOne(
                new EntityWrapper<RecResource>().eq("resource_id", Integer.valueOf(resourceid)).eq("student_id", Integer.valueOf(uploaderid)));
        result.put("resource", resource);
        if (!resource.getTag().equals("")) {
            result.put("tags", resource.getTag().split(","));
        } else {
            result.put("tags", new String[0]);
        }
        result.put("uploader", studentService.selectById(Integer.valueOf(uploaderid)).getNickname());

        if(Integer.valueOf(uploaderid).equals(userId)) { // 当前登录用户上传的资源
            JSONArray authStudentList = new JSONArray();
            List<ResourceStudentAuth> authes = resourceStudentAuthService.selectList(
                    new EntityWrapper<ResourceStudentAuth>().eq("resource_id", resourceid));
            for(ResourceStudentAuth auth : authes){
                JSONObject authStudent = new JSONObject();
                authStudent.put("authName", studentService.selectById(auth.getStudentId()).getNickname());
                authStudent.put("authTime", auth.getTime());
                authStudent.put("authId", auth.getRsaId());
                authStudentList.add(authStudent);
            }
            result.put("authStudentList", authStudentList);
            result.put("auth", "all");
        }else{
            result.put("auth", resource.getAuth());
            ResourceStudentAuth rsa = resourceStudentAuthService.selectOne(
                    new EntityWrapper<ResourceStudentAuth>().eq("resource_id", resourceid).eq("student_id", userId));
            if(rsa != null){
                result.put("auth", "可下载");
            }
            result.put("authStudentList", new Student[0]);
        }
        return result;
    }

    @ApiOperation(value = "获取某用户的资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "审核状态", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getUserResource")
    @ResponseBody
    public JSONArray getUserTopassResource(String state, HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        List<RecResource> resourceList = recResourceService.selectList(
                new EntityWrapper<RecResource>().eq("student_id", userId).eq("state", state));
        JSONArray result = new JSONArray();
        for(RecResource resource : resourceList){
            JSONObject resultCell = new JSONObject();
            resultCell.put("resource", resource);
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

    @ApiOperation(value = "有下载权限的资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getAuthResource")
    @ResponseBody
    public JSONArray getAuthResource(HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        JSONArray result = new JSONArray();
        List<ResourceStudentAuth> rsaList = resourceStudentAuthService.selectList(
                new EntityWrapper<ResourceStudentAuth>().eq("student_id", userId));
        for(ResourceStudentAuth rsa : rsaList){
            JSONObject resultCell = new JSONObject();
            RecResource resource = recResourceService.selectOne(
                    new EntityWrapper<RecResource>().eq("resource_id", rsa.getResourceId()));
            resultCell.put("resourceName", resource.getName());
            resultCell.put("resourceId", resource.getResourceId());
            resultCell.put("uploadTime", resource.getTime());
            resultCell.put("getAuthTime", rsa.getTime());
            resultCell.put("uploader", studentService.selectById(resource.getStudentId()).getNickname());
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "资源权限申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "toApplyResourceId", value = "申请的资源ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "toApplyResourceName", value = "申请的资源名字", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "toApplyResourceUserId", value = "资源上传者ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "details", value = "申请理由", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/applyResource")
    @ResponseBody
    public Map<String, String> applyResource(String toApplyResourceId, String toApplyResourceName,
                                             String toApplyResourceUserId, String details, HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        RecResourceApply recResourceApply = new RecResourceApply();
        recResourceApply.setApplyUserId(userId);
        recResourceApply.setResourceId(Integer.valueOf(toApplyResourceId));
        recResourceApply.setResourceName(toApplyResourceName);
        recResourceApply.setUserId(Integer.valueOf(toApplyResourceUserId));
        Date date = new Date();
        recResourceApply.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        recResourceApply.setDetails(details);
        recResourceApply.setState("toPass");
        recResourceApplyService.insertAllColumn(recResourceApply);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "success");
        return map;
    }

    @ApiOperation(value = "获取某用户收到的资源申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/getResourceApply")
    @ResponseBody
    public JSONArray getResourceApply(HttpServletRequest request){
        Integer userId = baseController.getLoginUserId(request);
        List<RecResourceApply> recResourceApplyList = recResourceApplyService.selectList(
                new EntityWrapper<RecResourceApply>().eq("user_id", userId).eq("state", "toPass"));
        JSONArray result = new JSONArray();
        for(RecResourceApply  recResourceApply : recResourceApplyList){
            JSONObject resultCell = new JSONObject();
            resultCell.put("resourceApply", recResourceApply);
            resultCell.put("applyUser", studentService.selectById(recResourceApply.getApplyUserId()).getNickname());
            result.add(resultCell);
        }
        return result;
    }

    @ApiOperation(value = "通过资源申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applyId", value = "申请ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/passResourceApply")
    @ResponseBody
    public JSONObject passResourceApply(String applyId){
        JSONObject result = new JSONObject();
        RecResourceApply recResourceApply = recResourceApplyService.selectById(applyId);
        recResourceApply.setState("pass");
        recResourceApplyService.updateById(recResourceApply);  // 更新申请状态
        ResourceStudentAuth newAuth = new ResourceStudentAuth();
        newAuth.setResourceId(recResourceApply.getResourceId());
        newAuth.setStudentId(recResourceApply.getApplyUserId());
        Date date = new Date();
        newAuth.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        resourceStudentAuthService.insertAllColumn(newAuth);
        result.put("msg", "success");
        return result;
    }

    @ApiOperation(value = "拒绝资源申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applyId", value = "申请ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/refuseResourceApply")
    @ResponseBody
    public JSONObject refuseResourceApply(String applyId){
        JSONObject result = new JSONObject();
        RecResourceApply recResourceApply = recResourceApplyService.selectById(applyId);
        recResourceApply.setState("fail");
        recResourceApplyService.updateById(recResourceApply);  // 更新申请状态
        result.put("msg", "success");
        return result;
    }

    @ApiOperation(value = "删除资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "resourceId", value = "资源ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/deleteResource")
    @ResponseBody
    public JSONObject deleteResource(String resourceId){
        // 删除资源
        recResourceService.delete(
                new EntityWrapper<RecResource>().eq("resource_id", resourceId));
        // 删除授权信息
        resourceStudentAuthService.delete(
                new EntityWrapper<ResourceStudentAuth>().eq("resource_id", resourceId));
        // 删除资源申请信息
        recResourceApplyService.delete(
                new EntityWrapper<RecResourceApply>().eq("resource_id", resourceId));
        JSONObject result = new JSONObject();
        result.put("msg", "success");
        return result;
    }

    @ApiOperation(value = "撤销下载权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "authId", value = "资源ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/deleteResourceAuth")
    @ResponseBody
    public JSONObject deleteResourceAuth(String authId){
        resourceStudentAuthService.deleteById(Integer.valueOf(authId));
        JSONObject result = new JSONObject();
        result.put("msg", "success");
        return result;
    }
}
