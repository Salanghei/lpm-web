package com.hit.lpm.portrait.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Map;

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

    private String domain;

    private Integer count;

    private String topicInfo;

    @TableField(exist=false)
    private Map<String, Integer> domainMap;

    public Map<String, Integer> getDomainMap() {
        return domainMap;
    }

    public void setDomainMap(Map<String, Integer> domainMap) {
        this.domainMap = domainMap;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


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

    public String getDomain() {
        if (this.domain != null && !this.domain.isEmpty()) return domain;
        else {
            String domain = "其他";
            int max = 0;
            for (String k : domainMap.keySet()) {
                if (domainMap.get(k) > max) {
                    max = domainMap.get(k);
                    domain = k;
                }
            }
            return domain;
        }
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTopicInfo() {
        return topicInfo;
    }

    public void setTopicInfo(String topicInfo) {
        this.topicInfo = topicInfo;
    }

}
