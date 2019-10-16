package com.hit.lpm.potrait.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-10-13 21:35
 **/

@TableName("lpm_topic")
public class Topic implements Serializable {

    @TableId
    private Integer topicId;

    private String topicName;

    private Integer domainId;

    private String topicInfo;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public String getTopicInfo() {
        return topicInfo;
    }

    public void setTopicInfo(String topicInfo) {
        this.topicInfo = topicInfo;
    }

}
