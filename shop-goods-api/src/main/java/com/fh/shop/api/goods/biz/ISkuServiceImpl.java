package com.fh.shop.api.goods.biz;

import com.alibaba.fastjson.JSON;
import com.fh.common.ServerResponse;
import com.fh.mq.MailMessage;
import com.fh.shop.api.goods.mapper.ISkuMapper;
import com.fh.shop.api.goods.po.Sku;
import com.fh.shop.api.goods.vo.SkuVo;
import com.fh.shop.api.goods.vo.SkuVoMail;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ISkuServiceImpl implements ISkuService{
    @Autowired
    private ISkuMapper skuMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse findRecommendNewProductList() {
        String skuListInfo = RedisUtil.get("skuList");
        if (skuListInfo==null){
            //查询是上架是新品是推荐的sku数据
            List<Sku> skuList =  skuMapper.findRecommendNewProductList();
            //将需要的数据给放进去
            List<SkuVo> skuVoList = skuList.stream().map(x -> {
                SkuVo skuVo = new SkuVo();
                skuVo.setId(x.getId());
                skuVo.setImage(x.getImage());
                skuVo.setPrice(x.getPrice().toString());
                skuVo.setSkuName(x.getSkuName());
                return skuVo;
            }).collect(Collectors.toList());
            //将java对象转为json字符串
            String skuVoListIfo = JSON.toJSONString(skuVoList);
            //使用setEX设置过期时间
            RedisUtil.setEX("skuList",skuVoListIfo,1000000);
            return ServerResponse.success(skuVoList);
        }else {
            //将json字符串转为java对象
            List<SkuVo> skuVos = JSON.parseArray(skuListInfo, SkuVo.class);
            return ServerResponse.success(skuVos);
        }
    }

    @Override
    public ServerResponse finMailSku() {
        List<SkuVoMail> skuList = skuMapper.finMailSku();
        StringBuilder content = new StringBuilder("<html><head></head><body>");
        content.append("<table border=\"1\" style=\"width:1000px; height:150px;border:solid 1px #E8F2F9;font-size=14px;font-size:18px;\">");
        content.append("<tr>" +
                "<td style=\\\"background-color: #428BCA; color:#ffffff\\\">skuName</td>" +
                "<td>skuPrice</td>" +
                "<td>skuStock</td>" +
                "<td>品牌名</td>" +
                "<td>分类名</td>" +
                "</tr>");
        skuList.stream().forEach(x -> {
            content.append("<tr>" +
                    "<td><span>" + x.getSkuName() + "</span></td>" +
                    "<td><span>" + x.getPrice() + "</span></td>" +
                    "<td><span>" + x.getStock() + "</span></td>" +
                    "<td><span>" + x.getBrandName() + "</span></td>" +
                    "<td><span>" + x.getCateName() + "</span></td>" +
                    "</tr>");
        });
        content.append("</table>");
        content.append("</body></html>");
       // mailUtil.sendMailHtml("1454340247@qq.com","库存警告-刘维凯" , content.toString());
        MailMessage mailMessage = new MailMessage();
        mailMessage.setTo("1454340247@qq.com");
        mailMessage.setConent(content.toString());
        mailMessage.setTitle("库存警告-刘维凯");
      //  mailProducer.sendMail(mailMessage);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findSku(Long id) {
        Sku sku = skuMapper.selectById(id);
        return ServerResponse.success(sku);
    }


}
