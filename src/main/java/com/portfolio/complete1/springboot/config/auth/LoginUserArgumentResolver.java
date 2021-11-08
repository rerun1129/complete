package com.portfolio.complete1.springboot.config.auth;

import com.portfolio.complete1.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession session;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {   //컨트롤러 메서드가 특정 파라미터를 지원하는지 판단.

        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;      //@LoginUser가 붙어있는지 판단
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());                   //파라미터 타입이 SessionUser인지 판단


        return isLoginUserAnnotation && isUserClass;
    }
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return session.getAttribute("user");        //파라미터에 전달할 객체 생성, 여기서는 세션에 객체를 가져옴
    }
}
