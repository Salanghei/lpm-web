package com.hit.lpm.portrait.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.portrait.model.Student;

import java.util.List;

public interface StudentService extends IService<Student> {
    List<String> selectKeywordsByStuId(Integer stuId);
}
