package com.chutianlong.controller;

import com.chutianlong.pojo.Picture_worker;
import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Teacher;
import com.chutianlong.pojo.admin;
import com.chutianlong.service.LoginService;
import com.chutianlong.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/tologin")
    public  String goLogin(HttpServletResponse response){
       response.setStatus(200);
        response.setHeader("Content-Type","text/html;charset=UTF-8");
        return "login";
    }

    @RequestMapping("/")
    public  String toLogin(HttpServletResponse response){
       response.setStatus(200);
        response.setHeader("Content-Type","text/html;charset=UTF-8");
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Object login(String idcard, String inputString, HttpServletRequest request, Model model){
        if(idcard==null||"".equals(idcard)){
            return "10";
        }
        if(inputString==null||"".equals(inputString)){
            return "11";
        }
        //根据身份证号查询
        Teacher userTeacher = loginService.teacherLogin(idcard);
        if(userTeacher==null||"".equals(userTeacher)){
            admin admins = loginService.adminLogin(idcard);
            if(admins==null||"".equals(admins)){
                Picture_worker pictureWorker = loginService.pictureWorker(idcard);
                if(pictureWorker!=null&&!"".equals(pictureWorker)){
                    String password="";
                    if(pictureWorker!=null){
                        password = pictureWorker.getPassword();
                    }else{
                        return "3";
                    }
                    if(MD5Utils.validatePassword(password,inputString)){
                        request.getSession().setAttribute("pictureWorker",pictureWorker);
                        return "1";
                    }
                }
            }else{
                String password="";
                if(admins!=null){
                    password = admins.getPassword();
                }else{
                    return "3";
                }
                if(MD5Utils.validatePassword(password,inputString)){
                    request.getSession().setAttribute("admins",admins);
                    return "2";
                }
            }
        }
        String password="";
        if(userTeacher!=null){
             password = userTeacher.getPassword();
        }else{
            return "3";
        }
        //查询完毕之后对比
        if(MD5Utils.validatePassword(password,inputString)&&userTeacher.getIsmaster()==1){
            request.getSession().setAttribute("user",userTeacher);
            //返回参数
            return userTeacher;
        }
        return "3";
    }
    @RequestMapping("/off")
    public String off(HttpServletRequest request, HttpServletResponse response){
        System.out.println("a");
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("pictureWorker");
        request.getSession().removeAttribute("admin");
        Cookie[] cookies = request.getCookies();
        for(int i = 0,len = cookies.length; i < len; i++) {
            Cookie cookie = new Cookie(cookies[i].getName(),null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "login";
    }



}
