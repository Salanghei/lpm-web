package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.StudentHabitMapper;
import com.hit.lpm.portrait.model.StudentHabit;
import com.hit.lpm.portrait.service.StudentHabitService;
import org.springframework.stereotype.Service;

@Service
public class StudentHabitServiceImpl extends ServiceImpl<StudentHabitMapper, StudentHabit> implements StudentHabitService {
}
