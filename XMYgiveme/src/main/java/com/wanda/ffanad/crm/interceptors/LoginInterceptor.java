package com.wanda.ffanad.crm.interceptors;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wanda.ffanad.base.annotation.PermitAll;
import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.core.common.constant.SystemConstant;

/**
 * @author huangzhiqiang19
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 登录页面，非ajax请求是跳转到此页面
	 */
	private final static String LOGIN_PAGE_URL = "/login.html";

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			PermitAll permitAllInClass = method.getBeanType().getAnnotation(PermitAll.class);
			PermitAll permitAllInMethod = method.getMethodAnnotation(PermitAll.class);
			RolePermission rolePermission = method.getMethodAnnotation(RolePermission.class);

			// 判断是否为所有都可访问
			if ((permitAllInClass != null && rolePermission == null) || permitAllInMethod != null)
				return true;

			// 优先校验是否登录
			HttpSession session = request.getSession();
			AccountEntity account = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
			if (account == null) {
				String xReqWith = request.getHeader("x-requested-with");
				if (StringUtils.isNotBlank(xReqWith) && xReqWith.equalsIgnoreCase("XMLHttpRequest")) {
					response.setStatus(HttpStatus.SC_UNAUTHORIZED);
				} else {
					response.sendRedirect(LOGIN_PAGE_URL);
				}
				return false;
			}
			// 校验权限
			if (null != rolePermission) {
				List<String> permissionList = Arrays.asList(rolePermission.roles());
				if (!permissionList.contains(account.getUserType() + "#" + account.getUserRoles())
						&& !permissionList.contains(SystemConstant.ROLE_SYSTEM_ALL)) {
					response.setStatus(403);
					return false;
				}
			}
		}
		return true;
	}

}
