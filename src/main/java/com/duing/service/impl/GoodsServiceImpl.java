package com.duing.service.impl;

import com.duing.mapper.GoodsMapper;
import com.duing.model.Goods;
import com.duing.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getGoods() {
        return goodsMapper.getGoods();
    }
}
