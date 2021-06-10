package com.fh.shop.api.cate.biz;

import com.alibaba.fastjson.JSON;
import com.fh.common.ServerResponse;
import com.fh.shop.api.cate.mapper.ICateMapper;
import com.fh.shop.api.cate.po.Cate;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author lenovo
 */
@Service("cateService")
@Transactional(rollbackFor = Exception.class)
public class ICateServiceImpl implements ICateService {

    @Autowired
    private ICateMapper iCateMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse query() {
        String cateListInfo = RedisUtil.get("cateList");
        if (cateListInfo==null){
            List<Cate> cateList = iCateMapper.selectList(null);
            String cateListIfo = JSON.toJSONString(cateList);
            RedisUtil.set("cateList",cateListIfo);
            return ServerResponse.success(cateList);
        }else {
            List<Cate> cateListArr = JSON.parseArray(cateListInfo,Cate.class);
            return ServerResponse.success(cateListArr);
        }
    }
}
