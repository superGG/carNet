package com.earl.carnet.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 黄祥谦.
 * @date:2016-1-10 下午11:33:17
 * @version :
 */
public class IpInterceptor implements HandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(IpInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
//		logger.info("aftercompletion ip---拦截器");
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp,
			Object arg2, ModelAndView arg3) throws Exception {
		logger.info("退出ip---拦截器");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse resp,
			Object handler) throws Exception {
		logger.info("进入ip---拦截器");
		// 获取参数
		String ip = request.getHeader("x-forwarded-for");
		logger.debug("x-forwarded-for=>" + ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		logger.info("ServletPath|ip|remote ip address|remote name|remote port|LocalAddress|LocalPort|char encodeing|ContentType|AuthType|PathTranslated|RemoteUser");
		logger.info( request.getServletPath()+"|"+ip+"|" + request.getRemoteAddr()+"|"
				+ request.getRemoteHost()+"|" + request.getRemotePort()+"|"
				+ request.getLocalAddr()+"|" + request.getLocalPort()+"|"
				+ request.getCharacterEncoding()+"|" + request.getContentType()+"|"
				+ request.getAuthType()+"|" + request.getPathTranslated()+"|"
				+ request.getRemoteUser()) ;
		return true;
	}
}
