package com.fh.shop.api.goods.biz;


import com.fh.common.ServerResponse;

public interface ISkuService {
    ServerResponse findRecommendNewProductList();

    ServerResponse finMailSku();

    ServerResponse findSku(Long id);

}
