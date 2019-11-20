package com.hit.lpm.recommend.service.impl;

import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.recommend.dao.RecStudentMapper;
import com.hit.lpm.recommend.service.RecStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-4 22:13
 **/
@Service
public class RecStudentServiceImpl implements RecStudentService {
    @Resource
    private RecStudentMapper recStudentMapper;

    @Override
    public List<Integer> getRandomStudentId(int n){
        return recStudentMapper.selectRandomStuId(n);
    }

    @Override
    public List<Student> getRandomStudentInfo(){
        return recStudentMapper.selectRandomStudentInfo();
    }
}
