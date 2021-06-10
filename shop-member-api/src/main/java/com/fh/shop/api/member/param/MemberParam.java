package com.fh.shop.api.member.param;

import com.fh.shop.api.member.po.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemberParam extends Member implements Serializable {

    @ApiModelProperty(value = "会员验证码")
    private String code;

    @ApiModelProperty(value = "第二次密码")
    private String confirmPassword;

    private Integer status;


}
