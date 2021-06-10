package com.fh.shop.api.cart.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class cartVo implements Serializable {
    List<cartItemVo> cartItemVoList = new ArrayList<>();

    private Long totalCount;

    private String totalPrice;



}
