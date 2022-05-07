package com.duing.mapper;

import com.duing.model.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeckillGoodsMapper {
    SeckillGoods getSeckillGoodsById(String goodsId);
    List<SeckillGoods> getAllSeckillGoods();
    void reduceStockNum(SeckillGoods seckillGoods);
}
