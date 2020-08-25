package com.hit.lpm.recommend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.StudentMapper;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.recommend.dao.RecClusterMapper;
import com.hit.lpm.recommend.dao.RecFriendMapper;
import com.hit.lpm.recommend.model.RecCluster;
import com.hit.lpm.recommend.model.RecFriend;
import com.hit.lpm.recommend.service.RecClusterService;
import com.hit.lpm.recommend.service.RecFriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-5 10:58
 **/
@Service
public class RecClusterServiceImpl extends ServiceImpl<RecClusterMapper, RecCluster> implements RecClusterService {

}
