package com.duing.controller;

import com.duing.model.Goods;
import com.duing.service.GoodsService;
import com.duing.vo.GoodsDetailVo;
import com.duing.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/")
    public String list(Model model) {
        List<GoodsVo> voList = goodsService.getGoods();
        model.addAttribute("voList",voList);
        return "list";
    }

    @GetMapping("/goodsDetail/{goodsId}")
    public String goodsDetail(Model model, @PathVariable String goodsId){
        GoodsDetailVo goodsDetailVo= goodsService.goodsDetail(goodsId);
        Date startTime=goodsDetailVo.getStartTime();
        Date endTime=goodsDetailVo.getEndTime();
        Date nowTime=new Date();
        int status,remainSeconds=-1;
        if (nowTime.before(startTime)){
            status=0;
            remainSeconds=(int)((startTime.getTime()- nowTime.getTime())/1000);
        }else if (nowTime.after(endTime)){
            status=2;
            remainSeconds=(int)((endTime.getTime()- nowTime.getTime())/1000);
        }else {
            status=1;
        }
        model.addAttribute("detailVo",goodsDetailVo);
        model.addAttribute("status",status);
        model.addAttribute("remainSeconds",remainSeconds);
        return "detail";
    }

}
