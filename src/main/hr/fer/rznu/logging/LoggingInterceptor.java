package hr.fer.rznu.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoggingInterceptor implements HandlerInterceptor {

	@Autowired
	private ApplicationContext appContext;

	private String getBrowserName(HttpServletRequest request) {

		String userAgent = request.getHeader("User-Agent").toLowerCase();
		
		if (userAgent.contains("firefox")) {
			return "firefox";
		} else {
			if (userAgent.contains("chrome")) {
				return "chrome";
			} else {
				if (userAgent.contains(".net")) {
					return "ie";
				}
			}
		}
		return "unknown";
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ILogger logger = (ILogger) appContext.getBean("rznuLogger");
		System.out.println(request.getRequestURI() + " " + getBrowserName(request));
		logger.writeLog(request.getRequestURI() + " " + getBrowserName(request));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
