package com.fh.shop.api.filter;

import com.alibaba.fastjson.JSON;
import com.fh.common.Constants;
import com.fh.common.KeyUtil;
import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.util.Md5Util;
import com.fh.util.RedisUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

@Component
@Slf4j
public class JwtFilter extends ZuulFilter {
    @Value("${fh.shop.checkUrls}")
    private List<String> checkUrls;
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {
//         log.info("===========:{}",checkUrls);
         //当前访问的url
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        StringBuffer requestURL = request.getRequestURL();
        boolean isCheck =false;
        for (String checkUrl : checkUrls){
            if (requestURL.indexOf(checkUrl)>0){
                isCheck = true;
                break;
            }
        }
        if (!isCheck){
            //默认相当于放行
            return null;
        }
        //验证
        //判断是否有头部信息
        String header = request.getHeader("x-auth");
        if (StringUtils.isEmpty(header)){
            return buildResponse(ResponseEnum.TOKEN_IS_MISS);
        }
        //判断头部信息是否完整
        String[] headerArr = header.split("\\.");
        if (headerArr.length!=2){
            return buildResponse(ResponseEnum.TOKEN_IS_NOT_FULL);
        }
        //验签【核心】
        String memberJsonBase64 = headerArr[0];
        String signJsonBase64 = headerArr[1];
        //运行base64解码
        String memberVoJson = new String(Base64.getDecoder().decode(memberJsonBase64), "utf-8");
        String sign = new String(Base64.getDecoder().decode(signJsonBase64), "utf-8");
        String newSign = Md5Util.sign(memberVoJson, Constants.SECRET);
        if (!newSign.equals(sign)){
            return buildResponse(ResponseEnum.TOKEN_IS_FAIL);
        }
        //将json转为java对象
        MemberVo memberVo = JSON.parseObject(memberVoJson, MemberVo.class);
        Long id = memberVo.getId();
        //判断验证是否过期
        if (!RedisUtil.exist(KeyUtil.buildMemberKey(id))){
            return buildResponse(ResponseEnum.TOKEN_IS_TIME_OUT);
        }
        //续命
        RedisUtil.expire(KeyUtil.buildMemberKey(id), Constants.TOKEN_EXPIRE);
        //request.setAttribute(Constants.CURR_MEMBER,memberVo);
        currentContext.addZuulRequestHeader( Constants.CURR_MEMBER, URLEncoder.encode(memberVoJson,"utf-8"));
        return null;
    }

    private Object buildResponse(ResponseEnum responseEnum) {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType("application/json:charset=utf-8");
        currentContext.setSendZuulResponse(false);
        ServerResponse error = ServerResponse.error(ResponseEnum.TOKEN_IS_MISS);
        String res = JSON.toJSONString(error);
        currentContext.setResponseBody(res);
        return null;
    }
}
