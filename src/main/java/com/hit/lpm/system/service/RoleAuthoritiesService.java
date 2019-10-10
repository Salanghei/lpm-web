package com.hit.lpm.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.system.model.RoleAuthorities;

/**
 * Created by Administrator on 2018-12-19 下午 4:38.
 */
public interface RoleAuthoritiesService extends IService<RoleAuthorities> {

    void deleteTrash();
}
