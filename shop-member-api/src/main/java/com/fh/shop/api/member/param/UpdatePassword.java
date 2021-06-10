package com.fh.shop.api.member.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePassword implements Serializable {

    private Long id;
    private String oldPassword;
    private String password;
    private String confirmPassword;
}
