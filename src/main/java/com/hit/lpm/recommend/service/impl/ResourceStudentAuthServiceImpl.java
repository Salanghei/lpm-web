package com.hit.lpm.recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.recommend.dao.ResourceStudentAuthMapper;
import com.hit.lpm.recommend.model.ResourceStudentAuth;
import com.hit.lpm.recommend.service.ResourceStudentAuthService;
import org.springframework.stereotype.Service;

@Service
public class ResourceStudentAuthServiceImpl extends ServiceImpl<ResourceStudentAuthMapper, ResourceStudentAuth>
        implements ResourceStudentAuthService {
}
