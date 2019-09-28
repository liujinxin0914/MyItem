package com.web.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;

public class ImageBinary {

    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\wu\\Desktop\\图片\\视频\\2.jpg";
        File f = new File(fileName);
        byte[] bytes = null;
        FileInputStream fis;
        try {
            fis = new FileInputStream(f);
            bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(bytes);
        // 生成字符串    
        String imgStr = byte2hex(bytes);
        System.out.println(imgStr);
    }

    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    public static String toBinary(String str) {
        //把字符串转成字符数组
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            //toBinaryString(int i)返回变量的二进制表示的字符串
            //toHexString(int i) 八进制
            //toOctalString(int i) 十六进制
            result += Integer.toBinaryString(strChar[i]) + " ";
        }
        return result;
    }


}
