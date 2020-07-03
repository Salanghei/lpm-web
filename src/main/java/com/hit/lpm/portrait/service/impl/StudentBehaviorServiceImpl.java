package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.StudentBehaviorMapper;
import com.hit.lpm.portrait.model.Behavior;
import com.hit.lpm.portrait.service.StudentBehaviorService;
import org.springframework.stereotype.Service;

@Service
public class StudentBehaviorServiceImpl extends ServiceImpl<StudentBehaviorMapper, Behavior> implements StudentBehaviorService {
}
