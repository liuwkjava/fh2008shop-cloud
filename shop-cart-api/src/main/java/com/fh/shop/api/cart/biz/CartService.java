package com.fh.shop.api.cart.biz;

import com.fh.common.ServerResponse;

public interface CartService {

    ServerResponse cartAdd(Long memberId,Long skuId,Long count);

    ServerResponse findCart(Long memberId);

    ServerResponse findCartCount(Long memberId);

    ServerResponse deleteCartItem(Long memberId, Long skuId);

    ServerResponse deleteBath(Long memberId, String ids);
}

