package com.hit.lpm.recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hit.lpm.recommend.model.RecResource;

import java.util.List;

public interface RecResourceMapper extends BaseMapper<RecResource> {
    List<RecResource> selectAll();
}
