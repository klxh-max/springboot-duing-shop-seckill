package com.duing.controller;

import com.duing.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/initData")
    public String initData() {
        redisService.initData();
        return "success";
    }

    @GetMapping("/seckillAPI")
    public String seckill(String userId,String goodsId) throws ParseException {
        if(userId==null||goodsId==null){
            return "参数出错";
        }
        String result=redisService.seckillByLua(userId, goodsId);
        System.out.println(result);
        return result;
    }

}
