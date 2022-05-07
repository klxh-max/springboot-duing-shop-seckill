package com.duing.service.impl;

import com.duing.mapper.SeckillGoodsMapper;
import com.duing.model.SeckillGoods;
import com.duing.service.RedisService;
import com.duing.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SeckillGoodsMapper mapper;

    @Override
    public void initData(String goodsId, int stockNum) {
        /**
         * 存储数据时需要设计好key的规则，例如根据商品id找到唯一库存
         * 秒杀活动开始前工作人员先通过后台管理系统将数据导入数据库在。
         * 再确保redis中存储一份数据用于秒杀时刻（额外同步数据功能）
         * 从指定mysql表中取出需要的数据初始化到redis中
         */
        redisUtil.set(goodsId + "_stockNum", stockNum);
    }

    @Override
    public void initData() {
        List<SeckillGoods> list = mapper.getAllSeckillGoods();
        for (SeckillGoods goods : list) {
            String goodsId = goods.getGoodsId();
            redisUtil.set(goodsId + "_stockNum", goods.getStockNum());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            redisUtil.set(goodsId + "_startTime", format.format(goods.getStartTime()));
            redisUtil.set(goodsId + "_endTime", format.format(goods.getEndTime()));
        }
    }

    @Override
    public String seckill(String userId, String goodsId) {
        String start = (String) redisUtil.get(goodsId + "_startTime");
        String end = (String) redisUtil.get(goodsId + "_endTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null, endTime = null;
        try {
            startTime = format.parse(start);
            endTime = format.parse(end);
        } catch (Exception e) {
        }
        //首先判断秒杀状态
        if (startTime == null || new Date().before(startTime)) {
            return "秒杀还未开始";
        }
        if (new Date().after(endTime)) {
            return "秒杀已结束";
        }
        //判断用户是否曾经秒杀成功过
        if (redisUtil.get(goodsId + "_" + userId) != null) {
            return userId + "用户，每人限购一件";
        }
        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                //编写事务的逻辑
                redisOperations.watch(goodsId + "_stockNum");
                //判断秒杀商品库存是否充足
                int stockNum = (int) redisUtil.get(goodsId + "_stockNum");
                if (stockNum < 1) {
                    return "商品已被秒杀一空";
                }
                redisOperations.multi();
                redisUtil.decr(goodsId + "_stockNum");
                redisUtil.set(goodsId + "_" + userId, 1);
                return redisOperations.exec();
            }
        };
        redisUtil.execute(sessionCallback);
        if (redisUtil.hasKey(goodsId + "_" + userId)) {
            return userId + "用户，秒杀成功！";
        }
        return userId + "用户，秒杀失败！";
    }

    @Override
    public String seckillByLua(String userId, String goodsId) {
        String start = (String) redisUtil.get(goodsId + "_startTime");
        String end = (String) redisUtil.get(goodsId + "_endTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null, endTime = null;
        try {
            startTime = format.parse(start);
            endTime = format.parse(end);
        } catch (Exception e) {
        }
        //首先判断秒杀状态
        if (startTime == null || new Date().before(startTime)) {
            return "秒杀还未开始";
        }
        if (new Date().after(endTime)) {
            return "秒杀已结束";
        }
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        //获取lua脚本资源
        script.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("lua/seckill.lua")
        ));
        List<String> keyList = new ArrayList<>();
        keyList.add(userId);
        keyList.add(goodsId);
        Object result = redisUtil.execute(script, keyList);
        String reStr=String.valueOf(result);
        if ("0".equals(reStr)){
            return "商品已被秒杀一空";
        }else if ("2".equals(reStr)){
            return userId + "用户，每人限购一件";
        }else if ("1".equals(reStr)){
            return userId + "用户，秒杀成功！";
        }else {
            return userId + "用户，秒杀失败！";
        }
    }

}

