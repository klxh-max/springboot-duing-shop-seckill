package com.duing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo {

    private String goodsId;
    private String goodsName;
    private String goodsType;
    private Double price;
    private String imgPath;
    private Double seckillPrice;
    private int stockNum;

}
