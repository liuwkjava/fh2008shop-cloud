package com.fh.shop.api.goods.controller;

import com.fh.common.ServerResponse;
import com.fh.shop.api.goods.biz.ISkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(tags = "Sku接口")
public class SkuController {

    @Autowired
    private ISkuService skuService;
    @GetMapping("skus")
    @ApiOperation("Sku查询")
    public ServerResponse findRecommendNewProductList(){
        return skuService.findRecommendNewProductList();
    }

    @GetMapping("skus/mail")
    @ApiOperation("发送sku短信")
    public ServerResponse finMailSku(){
        return skuService.finMailSku();
    }

    @GetMapping("/skus/findSku")
    public ServerResponse findSku(@RequestParam("id") Long id){
        return skuService.findSku(id);
    };


}
