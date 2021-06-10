package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.Constants;
import com.fh.common.KeyUtil;
import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.util.Md5Util;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;


    @Override
    public ServerResponse login(String memberName, String password) {
        //判断用户名和密码是否为空
        if (StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_IS_NULL);
        }
        //判断用户名是否正确
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("memberName",memberName);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        if (member==null){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_MEMBER_NAME_NOT_EXIST);
        }
        //判断密码是否正确
        String md5 = Md5Util.md5(password);
        if (!md5.equals(member.getPassword())){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_PASSWORD_IS_ERROR);
        }
        Integer status = member.getStatus();
        if (status== Constants.INACTIVE){
            String mail = member.getMail();
            Map<String,String> map = new HashMap<>();
            map.put("mail",mail);
            map.put("id",member.getId()+"");
            return ServerResponse.error(ResponseEnum.MENBERPARAM_INFO_IS_NULL_STATUS,map);
        }
        //=================================生成签名
        //将用户信息转为json
        MemberVo memberVo = new MemberVo();
        Long id = member.getId();
        memberVo.setId(id);
        memberVo.setMemberName(member.getMemberName());
        memberVo.setNickName(member.getNickName());
        String memberVoJson = JSON.toJSONString(memberVo);
        //=================================生成签名
        String sign = Md5Util.sign(memberVoJson,Constants.SECRET);
        //将用户信息生成一个base64编码
        String memberVoJsonBase64 = Base64.getEncoder().encodeToString(memberVoJson.getBytes());
        String signVoJson = Base64.getEncoder().encodeToString(sign.getBytes());
        //将信息存入token
        RedisUtil.setEX(KeyUtil.buildMemberKey(id),"",Constants.TOKEN_EXPIRE);
        //将数据和签名响应到前台
        //将用户信息和签名通过，分割成一个字符串传递给前台x.y
        return ServerResponse.success(memberVoJsonBase64+"."+signVoJson);
    }






}
