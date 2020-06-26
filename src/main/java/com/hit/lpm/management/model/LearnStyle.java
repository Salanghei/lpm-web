package com.hit.lpm.management.model;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("learn_style")
public class LearnStyle {
    @TableId(type = IdType.INPUT)

    private Integer stId;
    private Integer studentId;
    private String topic;
    private int score;

}
