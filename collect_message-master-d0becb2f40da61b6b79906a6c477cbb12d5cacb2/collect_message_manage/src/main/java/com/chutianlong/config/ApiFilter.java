package com.chutianlong.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class ApiFilter
 */
public class ApiFilter implements Filter {
	private Logger log = LoggerFactory.getLogger(ApiFilter.class);

	/**
	 * @see Filter#Filter()
	 */
	public ApiFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// 跨域设置
		String contentType = request.getContentType();
		String remoteAddr = request.getRemoteAddr();
		HttpServletRequest httpRequest = (HttpServletRequest)request;
        String path=httpRequest.getRequestURI();
		
		log.info("请求IP地址：==>"+remoteAddr);
		log.info("请求url：==>"+path);
		log.info("请求方式：==>"+httpRequest.getMethod());
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "Accept,Accept-Ranges,Origin,XRequestedWith,Content-Type,LastModified");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
		httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
//		httpServletResponse.setHeader("simple response header", "Cache-Control,Content-Language,Content-Type,Expires,Last-Modified,Pragma");
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
