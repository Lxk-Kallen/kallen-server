package com.kallen.api.core.interceptor;

import com.kallen.api.core.annotation.UserId;
import com.kallen.api.core.token.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token获取userId
 *
 * @author Scott
 */
@Component
public class UserIdInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    public static final String USER_KEY = "userId";
    public static final String TOKEN_KEY = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        UserId annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(UserId.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
//        if (StringUtils.isBlank(token)) {
//            token = request.getParameter("token");
//        }

        //token为空
        if (StringUtils.isBlank(token)) {
            return true;
        }

        //查询token信息
        if(tokenService.isTokenExist(token)){
            //设置userId到request里，后续根据userId，获取用户信息
            request.setAttribute(USER_KEY, tokenService.getUserIdByToken(token));
            request.setAttribute(TOKEN_KEY, token);
            return true;
        }

        return true;
    }

}
