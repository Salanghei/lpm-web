package com.hit.lpm.management.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.hit.lpm.common.utils.StringUtil;

import java.io.Serializable;
import java.util.Date;


@TableName("stu_friend")
public class StuFriend implements Serializable {

    @TableId(type = IdType.INPUT)

    private Integer sfId;
    private Integer studentId;
    private Integer friendId;

    public Integer getSfId() {
        return sfId;
    }

    public void setSfId(Integer sfId) {
        this.sfId = sfId;
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
