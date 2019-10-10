package com.hit.lpm.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.system.model.User;

public interface UserMapper extends BaseMapper<User> {

    User getByUsername(String username);
}
