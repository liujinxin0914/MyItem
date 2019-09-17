package com.chutianlong.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Value("${default.upload.image}")
    private String img;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 指定资源请求映射
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+img);
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + "/img/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("file:" + "/*/");
        super.addResourceHandlers(registry);
    }
/*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
*/
    /*@Bean
    public TomcatServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((Connector connector) -> {
            connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}");
            connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}");
        });
        return factory;
    }*/
   @Override
    public void addInterceptors(InterceptorRegistry registry) {
         //多个拦截器组成一个拦截器链
         //addPathPattern 用于添加拦截规则 路径，是带api接口的
         //用于排除用户的拦截
        registry.addInterceptor(new URLInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/Login/tologin","/Login/login","/static/**","/upload/**");
        super.addInterceptors(registry);
    }
}
