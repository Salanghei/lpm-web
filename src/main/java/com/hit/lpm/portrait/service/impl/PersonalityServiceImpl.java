package com.hit.lpm.portrait.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.PersonalityMapper;
import com.hit.lpm.portrait.model.Personality;
import com.hit.lpm.portrait.service.PersonalityService;
import org.springframework.stereotype.Service;

@Service
public class PersonalityServiceImpl extends ServiceImpl<PersonalityMapper, Personality> implements PersonalityService {
}
