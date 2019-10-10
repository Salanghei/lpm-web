package com.hit.lpm.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.system.dao.MenuMapper;
import com.hit.lpm.system.model.Menu;
import com.hit.lpm.system.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
