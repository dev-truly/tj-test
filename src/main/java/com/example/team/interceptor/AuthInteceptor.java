package com.example.team.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInteceptor implements HandlerInterceptor {

	/*@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//System.out.println("AuthInterceptor.preHandle");
		
		String url = request.getRequestURI();
		HttpSession session = request.getSession();
		MemberEntity member = (MemberEntity)session.getAttribute("loginuser");
		
		if (url.contains("/board/") && member == null) {
			response.sendRedirect("/account/login.action");
			return false;
		} 
		
		// do something
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//System.out.println("AuthInterceptor.postHandle");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		//System.out.println("AuthInterceptor.afterCompletion");
	}*/
	
}
