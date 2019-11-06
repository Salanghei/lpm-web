package com.hit.lpm.recommend.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.omg.CORBA.INTERNAL;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2019-11-5 18:05
 **/
@TableName("rec_friend")
public class RecFriend {
    @TableId
    private Integer ufId;
    private Integer userId;
    private Integer friendId;
    private Double trust;

    public Integer getUfId() {
        return ufId;
    }

    public void setUfId(Integer ufId) {
        this.ufId = ufId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Double getTrust() {
        return trust;
    }

    public void setTrust(Double trust) {
        this.trust = trust;
    }
}
