package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.StudentIntentionMapper;
import com.hit.lpm.portrait.model.StudentIntention;
import com.hit.lpm.portrait.service.StudentIntentionService;
import org.springframework.stereotype.Service;

@Service
public class StudentIntentionServiceImpl extends ServiceImpl<StudentIntentionMapper, StudentIntention> implements StudentIntentionService {
}
