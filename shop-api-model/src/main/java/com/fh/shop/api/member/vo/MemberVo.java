package com.fh.shop.api.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemberVo implements Serializable {

    @ApiModelProperty(value = "会员id",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty(value = "会员名称")
    private String memberName;
    @ApiModelProperty(value = "会员真实名称")
    private String nickName;


}
