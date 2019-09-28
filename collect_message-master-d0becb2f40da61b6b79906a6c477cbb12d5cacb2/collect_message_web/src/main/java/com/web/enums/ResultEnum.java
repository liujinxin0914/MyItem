package com.web.enums;


public enum ResultEnum {
    //全局
    UNKONW_ERROR("-1", "服务器异常"),
    UNKONW_ACTION("9999", "未知的交易类型"),
    SUCCESS("0000", "处理成功"),
    XXZY_ERROR("1001", "消息摘要验证失败"),
    YWCL_ERROR("1002", "业务处理失败，重试"),
    TOKEN_ERROR("1003", "Token验证失败"),
    USER_NONEXIST("1006", "无此用户"),
    USER_ERROR("1007", "用户受限制"),
    ZFMM_ERROR("1008", "支付密码错误"),
    ZFDD_ERROR("1009", "未知订单"),
    ZFDD_ZZYC("1010", "支付订单异常"),
    /**
     * 暂无数据
     */
    ZFDD_ZWSJ("1011", "暂无数据"), //暂无数据不属于逻辑错误、

    //接受数据
    ERROR_RSA_TRANSITION("2001", "RSA解析失败"),
    /**
     * 参数为空
     */
    ERROR_DATA_NULL("2002", "参数为空"),
    ERROR_TOKEN_PAST("2004", "请签到"),
    ERROR_USER_NOT_AVAILABLE("2005", "用户未开通"),
    ERROR_INTERFACE_ID("2006", "接口标识不正确"),
    ERROR_PASSWORD("2007", "密码错误"),

    ;

    private String code;

    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
