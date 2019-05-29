package com.blog.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author wang
 */
@Controller
@Slf4j
public class Test {

    @RequestMapping("/")
    public String hello(Map<String, Object> map) {
        log.info("hello is invoke!");
        map.put("hello", "Hello Java Config!");
        return "hello";
    }
}
