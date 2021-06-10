package com.fh.shop.api.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SkuVo implements Serializable {
    @ApiModelProperty(value = "Id",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    @ApiModelProperty(value = "价格")
    private String price;
    @ApiModelProperty(value = "图片")
    private String image;

}
