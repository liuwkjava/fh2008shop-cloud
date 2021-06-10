package com.fh.shop.api.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.goods.po.Sku;
import com.fh.shop.api.goods.vo.SkuVoMail;
import com.fh.shop.api.goods.vo.cartItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISkuMapper extends BaseMapper<Sku> {

    List<Sku> findRecommendNewProductList();

    List<SkuVoMail> finMailSku();

    int updateStock(cartItemVo itemVo);

    void updateSkuStock(@Param("skuId") Long skuId,@Param("count") Long count);

    void updateSale(@Param("skuId")Long skuId,@Param("skuCount") Long skuCount);

}
