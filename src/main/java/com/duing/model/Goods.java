package com.duing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    private long id;
    private String goodsId;
    private String goodsName;
    private String goodsType;
    private Double price;
    private String imgPath;

}
