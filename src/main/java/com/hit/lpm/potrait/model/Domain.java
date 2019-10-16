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

@TableName("lpm_domain")
public class Domain implements Serializable {

    @TableId
    private Integer domainId;

    private String domainName;

    private String domainInfo;

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainInfo() {
        return domainInfo;
    }

    public void setDomainInfo(String domainInfo) {
        this.domainInfo = domainInfo;
    }
}
