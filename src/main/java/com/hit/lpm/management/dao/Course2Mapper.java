package com.hit.lpm.management.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.management.model.Course2;
import java.util.List;

public interface Course2Mapper extends BaseMapper<Course2> {
    List selectAllTypeAndCount();
    List selectAllSchAndCount();
}
