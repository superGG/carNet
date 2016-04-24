package com.earl.carnet.interceptor;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.earl.carnet.commons.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author 黄祥谦.
 * @date:2016-1-10 下午11:33:17
 * @version :
 */
public class Ip2Interceptor implements HandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(Ip2Interceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		Map<String, String[]> parameterMap = request.getParameterMap();

		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			logger.info(entry.getKey() + " : " + entry.getValue()[0]);
		}




		logger.info("aftercompletion ip2---拦截器");

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		logger.info("退出ip2拦截器");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse resp,
			Object arg2) throws Exception {
		logger.info("进入ip2拦截器");
		// 获取参数
		return true;
	}
}
