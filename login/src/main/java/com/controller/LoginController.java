package com.chu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("login")
public class LoginController {
    @RequestMapping("/go")
    public String login(){
        System.out.println("你好啊");
        return "index";
    }
    @RequestMapping("/tologin")
    public String login(String name,String password){

        return "index";
    }
}
