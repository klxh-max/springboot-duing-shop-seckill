package com.duing.vo;

// 商品详情的展示数据

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVo {

    private String name;
    private String goodsId;
    private String imgPath;
    private Double price;
    private Double seckillPrice;
    private int stockNum;
    private Date startTime;
    private Date endTime;
}
