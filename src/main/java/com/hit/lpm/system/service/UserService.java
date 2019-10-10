package com.hit.lpm.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.system.model.User;

public interface UserService extends IService<User> {

    User getByUsername(String username);

}
