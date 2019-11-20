package com.hit.lpm.recommend.dao;

import com.hit.lpm.portrait.model.Student;

import java.util.List;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-4 22:13
 **/
public interface RecStudentMapper {
    // 随机获取n个学生的id
    List<Integer> selectRandomStuId(int n);

    // 随机选取3个学生的信息
    List<Student> selectRandomStudentInfo();
}
