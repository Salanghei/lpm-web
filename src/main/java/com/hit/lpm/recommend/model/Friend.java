package com.hit.lpm.recommend.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-5 18:05
 **/
@TableName("lpm_friend")
public class Friend {
    @TableId
    private Integer id;
    private Integer studentId;
    private Integer friendId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }
}
