package com.hit.lpm.portrait.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: lmp-web
 * @description:
 * @author: zhaoyang
 * @create: 2021-1-6 11:07
 **/
@Api(value = "学习意图", tags = "intention")
@RestController
@RequestMapping("${api.version}/intention")
public class IntentionController {
}
