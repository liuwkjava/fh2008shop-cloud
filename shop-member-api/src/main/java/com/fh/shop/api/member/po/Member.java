package com.fh.shop.api.member.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class Member implements Serializable {
    @ApiModelProperty(value = "会员id",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty(value = "会员名称")
    private String memberName;
    @ApiModelProperty(value = "会员密码")
    private String password;
    @ApiModelProperty(value = "会员真实名称")
    private String nickName;
    @ApiModelProperty(value = "会员手机号")
    private String phone;
    @ApiModelProperty(value = "会员邮箱")
    private String mail;
    @ApiModelProperty(value = "状态")
    private Integer status;
    private Long    score;
}
