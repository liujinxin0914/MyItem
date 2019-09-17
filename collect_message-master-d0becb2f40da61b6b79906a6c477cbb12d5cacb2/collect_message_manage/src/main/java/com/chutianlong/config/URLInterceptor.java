package com.chutianlong.config;

import com.chutianlong.util.MD5Utils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        if(request.getSession().getAttribute("user")==null){
            if(request.getSession().getAttribute("pictureWorker")!=null){
               return true;
            }else if(request.getSession().getAttribute("admins")!=null){
                return true;
            }else{
                request.getRequestDispatcher("/Login/tologin").forward(request,response);
                return false;
            }
        }

        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}
