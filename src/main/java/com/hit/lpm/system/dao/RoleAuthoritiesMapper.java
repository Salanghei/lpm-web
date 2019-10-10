package com.hit.lpm.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.system.model.RoleAuthorities;

public interface RoleAuthoritiesMapper extends BaseMapper<RoleAuthorities> {

    int deleteTrash();
}
