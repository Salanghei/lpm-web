package com.hit.lpm.recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.recommend.model.RecFriend;

import java.util.List;
import java.util.Map;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-5 10:58
 **/
public interface RecFriendService extends IService<RecFriend> {
    /**
     * 好友推荐算法
     * @param userId
     * @return 好友列表
     */
    Map<Student,Integer> getFriendRecommend (Integer userId);
}

