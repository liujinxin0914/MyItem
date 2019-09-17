package com.web.utils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UrlUtils {

    public static void main(String[] args) {
        String encode = URLEncoder.encode("http://1864rg4803.imwork.net/weChat/getCode");
        //System.out.println(encode);
        List<String> arr = new ArrayList<String>();
        arr.add("河大大一 （1）班");
        arr.add("河大大一 ");
        arr.add("河南大学");
        String url = getUrl("D:\\upload/", arr);
        //MultipartFile file = BASE64DecodedMultipartFile.base64ToMultipart(url);
        //System.out.println(file.getOriginalFilename());
        System.out.println(url);

    }


    public static String getUrl(String url, List<String> arr) {
        StringBuffer string = new StringBuffer();
        Collections.reverse(arr);
        string.append(url);
        for (int i = 0; i < arr.size(); i++) {
            string.append(arr.get(i) + "/");
        }
        return string.toString();
    }

}
