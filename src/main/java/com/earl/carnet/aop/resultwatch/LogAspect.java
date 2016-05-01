package com.earl.carnet.aop.resultwatch;

import com.earl.carnet.commons.vo.JsonEntry;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class LogAspect {

	public static final Logger logger = Logger.getLogger(LogAspect.class);
//	* com.earl.fishshop.domain.*.*DaoImpl.*(..)
	@Pointcut("execution(* com.earl.carnet.web.*Controller.*(..))")
	public void logManager() throws Throwable {
	}

	// @Pointcut("execution(* com.mysoft.manager.impl.TxtFileManager.*(..))")
	public void txtFileManager() {
	}

	// @Pointcut("execution(* com.mysoft.manager.KeywordsChecker.checkNormalKeywords(..))")
	public void checkNormalKeywords() {
	}

	// @Pointcut("execution(* com.mysoft.manager.KeywordsChecker.checkFixKeywords()) && args(text, ..)")
	public void checkFixKeywords(String text) {
	}

	// @Around("propertyManager() || txtFileManager()")
	//两个相同的@Around("logManager()")在两个方法上，
	//@Around
	//invoke()
	//@Around
	//invoke1()
	//执行顺序是，
	//invoke
//		invoke1
//		
//		invoke1
//	  invoke
	@Around("logManager()")
	public Object invoke(ProceedingJoinPoint join) throws Throwable {

		logger.info("start "+join.getSignature());
		long start = System.currentTimeMillis();
		Object result = join.proceed();

		String toShow = null;

		if(ResponseEntity.class.isInstance(result)){
			Object resultParams = ((ResponseEntity) result).getBody();
			if (resultParams instanceof JsonEntry){
				JsonEntry show = (JsonEntry)resultParams;
				toShow = show.toJson();
			}
		}else{
			if (result instanceof JsonEntry){
				JsonEntry show = (JsonEntry)result;
				toShow = show.toJson();
			}
		}

		Signature name = join.getSignature();
		// String name =
		// MethodSignature.class.cast(join.getSignature()).getMethod().getName();
		Object[] args = join.getArgs();
		long time = System.currentTimeMillis() - start;
		StringBuilder builder = new StringBuilder();
		builder.append("MethodSignature:").append(name).append("\n").append("Args:")
				.append(args).append("\n").append("毫秒:").append(time).append("秒:")
				.append(time / 1000).append("\n").append("result:").append(toShow);
		logger.info(builder.toString());
		return result;
	}
	
//	@Around("logManager()")
//	public Object invoke1(ProceedingJoinPoint join) throws Throwable {
//		logger.info("---->");
//		Object result = join.proceed();
//		logger.info("---->");
//		return result;
//	}

	// @Around("checkNormalKeywords()")
	public String invokeAndReturnString(ProceedingJoinPoint join)
			throws Throwable {
		return "";
	}

	/**
	 * @param text
	 * @return
	 * @throws Throwable
	 */
	// @Around(value = "checkFixKeywords(text)")
	public String invokeCheckFixKeywords(ProceedingJoinPoint join, String text)
			throws Throwable {
		if ("abcflg".equals(text)) {
			return "flg";
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		//启动Spring容器
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		//获取service组件
//		UserService service = (UserService) context.getBean("userService");
//		//以普通的方式调用UserService对象的三个方法
//		User user = service.get(1L);
//		service.save(user);
//		try {
//			service.delete(1L);
//		} catch (Exception e) {
//			if(log.isWarnEnabled()){
//				log.warn("Delete user : " + e.getMessage());
//			}
//		}
//	}
}