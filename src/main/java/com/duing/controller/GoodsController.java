package com.duing.controller;

import com.duing.model.Goods;
import com.duing.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/")
    public List<Goods> list(){
        System.out.println("asd");
        return goodsService.getGoods();
    }
}
