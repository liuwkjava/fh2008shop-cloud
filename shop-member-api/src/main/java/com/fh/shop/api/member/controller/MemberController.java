package com.fh.shop.api.member.controller;

import com.alibaba.fastjson.JSON;
import com.fh.common.Constants;
import com.fh.common.KeyUtil;
import com.fh.common.ServerResponse;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**，
 * @author lenovo
 */
@RestController
@RequestMapping("/api")
@Api(tags = "会员接口")
public class MemberController {

    @Autowired
    private IMemberService memberService;
    @Autowired
    private HttpServletRequest request;


    @GetMapping("member/findMember")
    @ApiOperation("获取会员信息")
    @ApiImplicitParam(name = "x-auht",value = "头信息",dataType = "java.lang.String",required = true,paramType = "header")
    public ServerResponse findMember() throws UnsupportedEncodingException {
        String memberVoJson = URLDecoder.decode(request.getHeader(Constants.CURR_MEMBER),"utf-8");
        MemberVo memberVo = JSON.parseObject(memberVoJson, MemberVo.class);
        return ServerResponse.success(memberVo);
    }


    @GetMapping("member/logout")
    @ApiOperation("会员注销")
    @ApiImplicitParam(name = "x-auht",value = "头信息",dataType = "java.lang.String",required = true,paramType = "header")
    public ServerResponse logout() throws UnsupportedEncodingException {
        String memberVoJson = URLDecoder.decode(request.getHeader(Constants.CURR_MEMBER),"utf-8");
        MemberVo memberVo = JSON.parseObject(memberVoJson, MemberVo.class);
        RedisUtil.delete(KeyUtil.buildMemberKey(memberVo.getId()));
        return ServerResponse.success();
    }

    @PostMapping("member/login")
    @ApiOperation("会员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberName",value = "会员名",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name="password",value = "密码",dataType = "java.lang.String",required = true)
    })
    public ServerResponse login(String memberName, String password){
        return memberService.login(memberName,password);
    }



}
