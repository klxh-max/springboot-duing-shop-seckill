package com.duing.service;

import com.duing.vo.GoodsDetailVo;
import com.duing.vo.GoodsVo;

import java.util.List;

public interface GoodsService {

    List<GoodsVo> getGoods();
    GoodsDetailVo goodsDetail(String goodsId);
    GoodsDetailVo getGoodsDetail(String goodsId);
    void reduceStockNum(String goodsId);
}
