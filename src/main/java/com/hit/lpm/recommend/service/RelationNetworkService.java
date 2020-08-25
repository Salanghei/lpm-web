package com.hit.lpm.recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.recommend.model.Graph;
import com.hit.lpm.recommend.model.PlatformScore;

import java.util.List;

/**
 * @program: lmp-web
 * @description:
 * @author: liuwuying
 * @create: 2020-07-27 19:08
 **/
public interface RelationNetworkService{
    Graph initGrapgh(List<PlatformScore> scores);

}

