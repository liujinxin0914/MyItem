package com.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.codehaus.jackson.map.ObjectMapper;

import com.web.common.AuthToken;
import com.web.common.WxConstant;


public class WebChatUtil {
    /**
     * 扩展xstream,使其支持name带有"_"的节点
     */
    // public static XStream xStream = new XStream(new DomDriver("UTF-8", new
    // XmlFriendlyNameCoder("-_", "_")));
    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    /**
     * 根据code获取微信授权access_token
     */
    public static AuthToken getTokenByAuthCode(String code) {
        // public static AuthToken getTokenByAuthCode() {
        AuthToken authToken = null;
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(WxConstant.Authtoken_URL(code));
            URLConnection uc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
                System.out.println("遍历json" + json.toString());
            }
            in.close();
            // 将json字符串转成javaBean
            authToken = jsonToEntity(json.toString(), AuthToken.class);
        } catch (IOException ex) {
            System.out.println("获取access_token异常");
        }
        return authToken;
    }

    /**
     * Json字符转Java实体
     *
     * @param jsonString Json字符串
     * @param entityType 实体类型
     * @return default null，表示转换失败
     */
    public static <T> T jsonToEntity(String jsonString, Class<T> entityType) {
        T entity = null;

        try {
            entity = jsonObjectMapper.readValue(jsonString, entityType);
        } catch (Exception e) {
            System.out.println("Json转换异常");
        }

        return entity;
    }

}
