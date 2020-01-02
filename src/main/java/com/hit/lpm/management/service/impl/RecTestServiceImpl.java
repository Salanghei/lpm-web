package com.hit.lpm.management.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.management.dao.RecTestMapper;
import com.hit.lpm.management.model.RecTest;
import com.hit.lpm.management.service.RecTestService;
import org.springframework.stereotype.Service;


@Service
public class RecTestServiceImpl extends ServiceImpl<RecTestMapper, RecTest> implements RecTestService {
}
