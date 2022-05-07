package com.duing.service.impl;

import com.duing.mapper.GoodsMapper;
import com.duing.mapper.SeckillGoodsMapper;
import com.duing.model.Goods;
import com.duing.model.SeckillGoods;
import com.duing.service.GoodsService;
import com.duing.vo.GoodsDetailVo;
import com.duing.vo.GoodsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public List<GoodsVo> getGoods() {
        List<Goods> goodsList=goodsMapper.getGoods();
        List<GoodsVo> voList=new ArrayList<>();
        for (Goods goods:goodsList){
            GoodsVo vo=new GoodsVo();
            BeanUtils.copyProperties(goods,vo);
            System.out.println(vo.getGoodsId());
            SeckillGoods seckillGoods= seckillGoodsMapper.getSeckillGoodsById(vo.getGoodsId());
            BeanUtils.copyProperties(seckillGoods,vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public GoodsDetailVo goodsDetail(String goodsId) {
        Goods goods= goodsMapper.getGoodsById(goodsId);
        SeckillGoods seckillGoods= seckillGoodsMapper.getSeckillGoodsById(goodsId);
        GoodsDetailVo vo=new GoodsDetailVo();
        BeanUtils.copyProperties(goods,vo);
        BeanUtils.copyProperties(seckillGoods,vo);
        return vo;
    }

    @Override
    public GoodsDetailVo getGoodsDetail(String goodsId) {
        Goods goods = goodsMapper.getGoodsById(goodsId);
        SeckillGoods seckillGoods = seckillGoodsMapper.getSeckillGoodsById(goodsId);

        GoodsDetailVo detailVo = new GoodsDetailVo();
        detailVo.setName(goods.getGoodsName());
        detailVo.setGoodsId(goods.getGoodsId());
        detailVo.setImgPath(goods.getImgPath());
        detailVo.setPrice(goods.getPrice());

        detailVo.setSeckillPrice(seckillGoods.getSeckillPrice());
        detailVo.setStockNum(seckillGoods.getStockNum());
        detailVo.setStartTime(seckillGoods.getStartTime());
        detailVo.setEndTime(seckillGoods.getEndTime());

        return detailVo;
    }



    @Override
    public void reduceStockNum(String goodsId) {
        SeckillGoods seckillGoods = seckillGoodsMapper.getSeckillGoodsById(goodsId);
        seckillGoods.setStockNum(seckillGoods.getStockNum() - 1);
        seckillGoodsMapper.reduceStockNum(seckillGoods);
    }

}
