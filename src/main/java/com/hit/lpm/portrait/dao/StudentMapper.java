package com.hit.lpm.portrait.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.portrait.model.Student;

import java.util.List;

public interface StudentMapper extends BaseMapper<Student> {

    List<String> selectKeywordsByStuId(Integer stuId);


}
