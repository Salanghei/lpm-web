package com.hit.lpm.recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.recommend.model.RecResource;

import java.util.List;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-5 12:28
 **/
public interface RecResourceService extends IService<RecResource> {
    List<RecResource> getAllResource();
}
