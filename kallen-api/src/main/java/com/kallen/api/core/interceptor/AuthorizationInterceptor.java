package com.kallen.api.core.interceptor;


import com.kallen.api.core.annotation.Login;
import com.kallen.api.core.token.service.TokenService;
import com.kallen.common.exception.ErrorCode;
import com.kallen.common.exception.KallenException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 * 有@Login注解的方法，注入当前登录用户,及TOKEN信息
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenService tokenService;

    public static final String USER_KEY = "userId";
    public static final String TOKEN_KEY = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }

        if(annotation == null){
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
//        if(StringUtils.isBlank(token)){
//            token = request.getParameter("token");
//        }

        //token为空
        if(StringUtils.isBlank(token)){
            throw new KallenException("token不能为空", ErrorCode.TOKEN_NOT_EMPTY);
        }

        //查询token信息
        if(!tokenService.isTokenExist(token)){
            throw new KallenException("token失效，请重新登录！", ErrorCode.TOKEN_INVALID);
        }

        Long userIdByToken = tokenService.getUserIdByToken(token);
        if (userIdByToken == null) throw new KallenException(ErrorCode.LOGIN_STATUS_HAS_EXPIRED);

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, userIdByToken);
        request.setAttribute(TOKEN_KEY, token);

        return true;
    }
}
