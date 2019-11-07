package com.hit.lpm.recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.recommend.dao.RecFriendApplyMapper;
import com.hit.lpm.recommend.dao.RecResourceApplyMapper;
import com.hit.lpm.recommend.model.RecResourceApply;
import com.hit.lpm.recommend.service.RecResourceApplyService;
import org.springframework.stereotype.Service;

@Service
public class RecResourceApplyServiceImpl extends ServiceImpl<RecResourceApplyMapper, RecResourceApply> implements RecResourceApplyService {
}
