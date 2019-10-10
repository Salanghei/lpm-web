package com.hit.lpm.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.system.dao.RoleMapper;
import com.hit.lpm.system.model.Role;
import com.hit.lpm.system.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
