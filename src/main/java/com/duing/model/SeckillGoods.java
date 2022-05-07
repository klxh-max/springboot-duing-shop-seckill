package com.duing.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillGoods {

    private long id;
    private String goodsId;
    private Double seckillPrice;
    private int stockNum;
    private Date startTime;
    private Date endTime;

}
