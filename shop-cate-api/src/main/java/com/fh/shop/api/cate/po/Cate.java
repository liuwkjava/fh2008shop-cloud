package com.fh.shop.api.cate.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class Cate implements Serializable {

    @ApiModelProperty(value = "分类id",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty(value = "分类名称")
    private String cateName;
    @ApiModelProperty(value = "分类父类",example = "0")
    private Long fatherId;
    @ApiModelProperty(value = "类型id",example = "0")
    private Long typeId;
    @ApiModelProperty(value = "类型名称")
    private String typeName;

}
