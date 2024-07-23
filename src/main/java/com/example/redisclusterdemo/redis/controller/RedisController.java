package com.example.redisclusterdemo.redis.controller;

import com.example.redisclusterdemo.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @GetMapping(path = "/save/{something}")
    public String saveSomething(@PathVariable("something") String something) {
        try {
            redisService.save(something);
            return "성공";
        } catch (Exception e) {
            log.info("RedisController saveSomething exception by = {}", e.getMessage());
            return "실패";
        }
    }

    @GetMapping(path = "/list")
    public List<String> getSomething() {
        try {
            return redisService.getList();
        } catch (Exception e) {
            log.info("RedisController getSomething exception by = {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}
