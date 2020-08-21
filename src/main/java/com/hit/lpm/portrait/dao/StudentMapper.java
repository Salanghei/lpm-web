package com.hit.lpm.portrait.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.portrait.model.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper extends BaseMapper<Student> {

    List<String> selectKeywordsByStuId(Integer stuId);

    Double selectStudentProblemScore(@Param("stuId") Integer stuId,
                                     @Param("problemId") String problemId);

    List<String> selectStudentCourses(Integer stuId);
}
