package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.StudentVideoRecordMapper;
import com.hit.lpm.portrait.model.StudentVideoRecord;
import com.hit.lpm.portrait.service.StudentVideoRecordService;
import org.springframework.stereotype.Service;

@Service
public class StudentVideoRecordServiceImpl extends ServiceImpl<StudentVideoRecordMapper, StudentVideoRecord> implements StudentVideoRecordService {
}
