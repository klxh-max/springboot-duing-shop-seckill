package com.duing.service;

import java.text.ParseException;

public interface RedisService {
    void initData(String goodsId, int stockNum);

    void initData();

    String seckill(String userId, String goodsId) throws ParseException;

    String seckillByLua(String userId, String goodsId);
}
