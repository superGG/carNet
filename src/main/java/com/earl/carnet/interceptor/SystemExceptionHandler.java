package com.earl.carnet.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.exception.ApplicationException;
import com.earl.carnet.exception.DomainSecurityException;

@ControllerAdvice
public class SystemExceptionHandler implements HandlerExceptionResolver {

    private static Log logger = LogFactory.getLog(SystemExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", ex);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        ResultMessage resultMessage = new ResultMessage();

        if (ex instanceof ApplicationException) {
            resultMessage.setServiceResult(false);
            resultMessage.setResultInfo(ex.getMessage());
        } else if (ex instanceof DomainSecurityException) {
            resultMessage.setServiceResult(false);
            resultMessage.setResultInfo(ex.getMessage());
        } else if (ex instanceof ConstraintViolationException) {
            StringBuilder builder = new StringBuilder();
//			resultMessage.setResultInfo("数据验证出错");
            resultMessage.setServiceResult(true);
            ConstraintViolationException cv = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> constraintViolations = cv.getConstraintViolations();

            constraintViolations.stream().forEach((constraintViolation) -> {
                logger.debug(constraintViolation.getExecutableReturnValue());
                logger.debug(constraintViolation.getPropertyPath());// 属性路径，el表达式
                logger.debug(constraintViolation.getMessage());// 消息

                builder.append(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());

                logger.debug(constraintViolation.getInvalidValue());// 传入参数
                logger.debug(constraintViolation.getLeafBean()); // 作用的bean对象
            });
            resultMessage.setResultInfo(builder.toString());
        } else if (ex instanceof IncorrectCredentialsException) {
            resultMessage.setResultInfo("用户名密码不正确");
            resultMessage.setServiceResult(false);
            logger.info("resultMessage =>" + resultMessage.toJson());
        } else if (ex instanceof UnknownAccountException) {
            resultMessage.setResultInfo("用户名密码不正确");
            resultMessage.setServiceResult(false);
            logger.info("resultMessage => " + resultMessage.toJson());
        } else if (ex instanceof SecurityException) {
            resultMessage.setServiceResult(false);
            resultMessage.setResultInfo(ex.getMessage());
            logger.info("resultMessage =>" + resultMessage.toJson());
        } else {
            resultMessage.setServiceResult(false);
            resultMessage.setResultInfo("系统出错");
            logger.info("resultMessage =>" + resultMessage.toJson());
        }
        try {
            out = response.getWriter();
            out.write(resultMessage.toJson());
//			out.println();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ex.printStackTrace();
        //注掉这个，后台不会报out.getWriter is ...异常
//		out.flush();
//		out.close();
        return null;
    }
}