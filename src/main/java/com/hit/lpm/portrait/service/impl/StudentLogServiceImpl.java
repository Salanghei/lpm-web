package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.StudentLogMapper;
import com.hit.lpm.portrait.model.StudentLog;
import com.hit.lpm.portrait.service.StudentLogService;
import org.springframework.stereotype.Service;

@Service
public class StudentLogServiceImpl extends ServiceImpl<StudentLogMapper, StudentLog> implements StudentLogService {
}
