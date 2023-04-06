package com.bread.config.auth;

import com.bread.config.auth.dto.OAuthAttributes;
import com.bread.config.auth.dto.SessionUser;
import com.bread.domain.user.User;
import com.bread.domain.user.UserRepository;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserRepository userRepository;
  private final HttpSession httpSession;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();


    OAuth2User oAuth2User = delegate.loadUser(userRequest);


    // 간편 로그인 서비스 지원하는 서버 ex) 구글, 네이버, 페이스북..
    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    // oauth2 로그인 진행 시 사용되는 키
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
        .getUserInfoEndpoint().getUserNameAttributeName();

    log.info("registrationId : "+registrationId+", userNameAttributeName : "+userNameAttributeName+", ");

    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
        oAuth2User.getAttributes());


    User user = saveOrUpdate(attributes);


    httpSession.setAttribute("user", new SessionUser(user));

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
        attributes.getAttributes(),
        attributes.getNameAttributeKey());
  }

  private User saveOrUpdate(OAuthAttributes attributes) {
    User user = userRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(
            attributes.getName(), attributes.getPicture()))
        .orElse(attributes.toEntity());

    return userRepository.save(user);
  }
}
