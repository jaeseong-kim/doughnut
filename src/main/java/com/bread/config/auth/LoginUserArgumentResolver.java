package com.bread.config.auth;

import com.bread.config.auth.LoginUser;
import com.bread.config.auth.dto.SessionUser;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final HttpSession httpSession;

  // 컨트롤러에 있는 메소드 중 특정 파라미터를 지원하는지 판단하는 메소드
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    // 파라미터에 어노테이션이 @LoginUser인 경우 참
    boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

    // 파라미터 타입이 SessionUser인 경우 참
    boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
    return isLoginUserAnnotation && isUserClass;
  }

  //파라미터에 전달할 객체 생성
  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    // 세션에서 객체 가져옴
    return httpSession.getAttribute("user");
  }
}
