package com.fh.shop.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CrossFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        //处理跨域
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        //处理自定义的请求头
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth,content-type,x-token");
        //处理特殊请求
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"DELETE,POST,PUT,GET");
        HttpServletRequest request = currentContext.getRequest();
        //判断是否是预请求
        String methodAction = request.getMethod();
        if(methodAction.equalsIgnoreCase("options")){
            currentContext.setSendZuulResponse(false);
        }
        return null;
    }
}
