package com.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.common.AuthToken;
import com.web.utils.WebChatUtil;


@Controller
@RequestMapping(value = "/weChat")
public class WeChatController {
    @RequestMapping(value = "/getCode", produces = "text/html;charset=UTF-8")
    public String resultCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = null;
        code = request.getParameter("code");// 用户授权码
        String state = request.getParameter("state");// 前台传值
        System.out.println("用户授权码" + code);
        // 通过code获取网页授权access_token
        AuthToken authToken = WebChatUtil.getTokenByAuthCode(code);

        String openId = authToken.getOpenid();
        System.out.println("openid " + openId);

        //String url= "redirect:https://msrsj.cn/dist/index.html#/null?openId=" + openId+"&goto="+state;
        String url = "redirect:http://msshh.ihongwan.com:8082/photo-h5/" + state + "?openId=" + openId;
        return url;
    }


}
