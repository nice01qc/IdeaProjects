package com.wanda.ffanad.crm.interceptors;

import io.jsonwebtoken.Jwts;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wanda.ffanad.core.domains.Crmaccount;
import com.wanda.ffanad.core.domains.CrmaccountDto;
import com.wanda.ffanad.core.services.AccountService;

/**
 * Jwt验证拦截器.
 */
public class JwtInterceptor implements HandlerInterceptor {
    /**
     * 日志.
     */
    private Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    AccountService accountService;

    /**
     * 因为1.7不支持 switch字符串改为枚举
     */
    private enum COOKIENAME  {
    	accountId, jwtToken
    }
    /**
     * 当收到请求，在Controller之前调用.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  Controller
     * @return 是否继续执行
     * @throws Exception 异常
     */
    @Override
    public final boolean preHandle(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   final Object handler) throws Exception {
        String accountId = null, jwtToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                switch (COOKIENAME.valueOf(cookie.getName())) {
                    case accountId:
                        accountId = cookie.getValue();
                        break;
                    case jwtToken:
                        jwtToken = cookie.getName();
                        break;
                }
            }
        }
        if (!(StringUtils.isEmpty(accountId) || StringUtils.isEmpty(jwtToken))) {
            Crmaccount selectTemplate = new Crmaccount();
        	//CrmaccountDto selectTemplate = new CrmaccountDto();
            selectTemplate.setAccountId(Integer.parseInt(accountId));
            //Crmaccount account = accountService.selectByAccount(selectTemplate).get(0);
            CrmaccountDto account = accountService.selectByAccount(selectTemplate).get(0);
            String key = account.getAccountKey();
            if (handler instanceof HandlerMethod) {
                if (StringUtils.isEmpty(account.getPhone()) || StringUtils.isEmpty(account.getUsername())) {
//                    request.getRequestDispatcher("/accountInfo.html");    //TODO:跳转到新增信息页
                    return false;
                }
                try {
                    String subject = Jwts.parser().setSigningKey(key).
                            parseClaimsJws(jwtToken).getBody().getSubject();
                    if (!subject.equals(accountId)) {
                        request.getRequestDispatcher("/backcrmlogin.html");
                        return false;
                    }
                    //TODO:判断权限，失败跳转404页面
                    request.setAttribute("currentAccount", account);
                } catch (Exception e) {
                    request.getRequestDispatcher("/backcrmlogin.html");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Controller执行之后，数据发到前台之前.
     *
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @param handler      Controller
     * @param modelAndView ModelAndView
     * @throws Exception 异常
     */
    @Override
    public final void postHandle(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final Object handler,
                                 final ModelAndView modelAndView)
            throws Exception {
    }

    /**
     * 一切结束之后，主要用于回收资源.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  Controller
     * @param ex       Exception
     * @throws Exception 异常
     */
    @Override
    public final void afterCompletion(final HttpServletRequest request,
                                      final HttpServletResponse response,
                                      final Object handler,
                                      final Exception ex) throws Exception {
    }

    /**
     * getLogger.
     *
     * @return Logger
     */
    public final Logger getLogger() {
        return logger;
    }

    /**
     * setLogger.
     *
     * @param loggerParam loggerParam
     */
    public final void setLogger(final Logger loggerParam) {
        this.logger = loggerParam;
    }
}
