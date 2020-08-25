package com.hit.lpm.recommend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hit.lpm.portrait.dao.StudentMapper;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.recommend.dao.RecFriendMapper;
import com.hit.lpm.recommend.model.RecFriend;
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
public class RecFriendServiceImpl extends ServiceImpl<RecFriendMapper, RecFriend> implements RecFriendService {
    @Resource
    private RecFriendMapper recFriendMapper;
    @Resource
    private StudentMapper studentMapper;

    /**
     * 好友推荐 基于信任关系
     *
     * @param userId
     * @return 推荐好友列表 及 共同好友数
     */
    @Override
    public Map<Student, Integer> getFriendRecommend(Integer userId) {
        List<RecFriend> rFriendList = new ArrayList<>();
        Map<Student, Integer> resultMap = new LinkedHashMap<>();
        //传递关系:若A和B是朋友,B和C是朋友，那么C就可以推荐给A。推荐结果按照传递关系的个数进行排序。
        //先找出学生A的朋友B，再找出朋友B的朋友C，给每个推荐的朋友计数。
        EntityWrapper<RecFriend> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        //找出朋友列表
        List<RecFriend> friendList = recFriendMapper.selectList(wrapper);
        //存储已经成为朋友的用户id
        List<Integer> friendIds = new ArrayList<>();
        for (RecFriend i : friendList) {
            friendIds.add(i.getFriendId());
        }
        for (RecFriend i : friendList) {
            EntityWrapper<RecFriend> wrapper1 = new EntityWrapper<>();
            wrapper1.eq("user_id", i.getFriendId());
            //找出朋友的朋友
            List<RecFriend> cFriendList = recFriendMapper.selectList(wrapper1);
            //排除已经成为朋友的和本身
            for (int j = 0; j < cFriendList.size(); j++) {
                RecFriend friend=cFriendList.get(j);
                if (friendIds.contains(friend.getFriendId()) || friend.getFriendId().equals(userId)) {
                    cFriendList.remove(j);
                    j--;
                } else {
                    rFriendList.add(friend);
                }
            }
        }
        //计算推荐列表里重复推荐的学生，降序排序
        //key：学生id value：推荐次数
        Map<Integer, Integer> map = new HashMap<>();
        for (RecFriend one : rFriendList) {
            Integer friendId = one.getFriendId();
            if (map.containsKey(friendId)) {
                map.put(friendId, map.get(friendId) + 1);
            } else {
                map.put(friendId, 1);
            }
        }
        //排序,转换为list
        List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
        //使用list.sort()排序
        list.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() < 2) continue;
            Integer id = list.get(i).getKey();
            Student student = new Student();
            student.setUserId(id);
            student = studentMapper.selectOne(student);
            resultMap.put(student, list.get(i).getValue());
        }
        return resultMap;
    }
}
