package com.fh.shop.api.goods.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class Sku implements Serializable {
    @ApiModelProperty(value = "skuId",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty(value = "spuId",example = "0")
    private Long spuId;
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    @ApiModelProperty(value = "价格",example = "0")
    private BigDecimal price;
    @ApiModelProperty(value = "库存",example = "0")
    private Integer stock;
    @ApiModelProperty(value = "配置信息")
    private String specInfo;
    @ApiModelProperty(value = "颜色id",example = "0")
    private Long colorId;
    @ApiModelProperty(value = "图片")
    private String Image;
    private String status; //0下1上
    private String recommend;//0不推荐1推荐
    private String newProduct;//0不新品1新品
    private Long sale;
}
