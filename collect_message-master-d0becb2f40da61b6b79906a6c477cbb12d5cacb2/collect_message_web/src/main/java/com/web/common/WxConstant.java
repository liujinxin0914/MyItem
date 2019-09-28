package com.web.common;

public class WxConstant {
    /**
     * 公众号AppId wx9dfff98fb16feb04
     */
    public static final String APP_ID = "wxabc670e3c404adf9";

    /**
     * 公众号AppSecret defc6832db583495c9101a961836fdfc
     */
    public static final String APP_SECRET = "6ea4288771aee716f483a040e7f83fc0";

    /**
     * 返回成功字符串
     */
    public static final String RETURN_SUCCESS = "SUCCESS";

    /**
     * 支付地址(包涵回调地址)
     */
    static String redirectUrl = "";
    public static final String PAY_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APP_ID + "&redirect_uri=" + redirectUrl
            + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

    /**
     * 通过code获取授权access_token的URL
     */
    public static String Authtoken_URL(String code) {
        StringBuffer url = new StringBuffer();
        // api.weixin.qq.com 10.10.208.11:8033
        url.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
        url.append(WxConstant.APP_ID);
        url.append("&secret=");
        url.append(WxConstant.APP_SECRET);
        url.append("&code=");
        url.append(code);
        url.append("&grant_type=authorization_code");
        return url.toString();
    }

}
