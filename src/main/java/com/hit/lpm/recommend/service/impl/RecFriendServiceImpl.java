package com.hit.lpm.recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.recommend.dao.RecFriendMapper;
import com.hit.lpm.recommend.model.RecFriend;
import com.hit.lpm.recommend.service.RecFriendService;
import org.springframework.stereotype.Service;

@Service
public class RecFriendServiceImpl extends ServiceImpl<RecFriendMapper, RecFriend> implements RecFriendService {
}
