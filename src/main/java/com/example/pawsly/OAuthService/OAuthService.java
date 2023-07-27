
package com.example.pawsly.OAuthService;

import com.example.pawsly.user1.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuthRepository oAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); //OAuth 서비스에서 가져온 유저 정보를 담고 있다.
        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); // 카카오, 네이버, 구글 서비스(OAuth)이름
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //OAuth 로그인시 pk가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); //OAuth 서비스의 유저 정보들

        // registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌
        OAuthUserProfile oAuthUserProfile = OAuthAttributes.extract(registrationId, attributes);
        oAuthUserProfile.setProvider(registrationId); // 인스턴스 생성 후에 호출
        User user = saveOrUpdate(oAuthUserProfile);

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, oAuthUserProfile, registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttribute,
                userNameAttributeName);

    }

    private Map customAttribute(Map attributes, String userNameAttributeName, OAuthUserProfile oAuthUserProfile, String registrationId) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("name", oAuthUserProfile.toUserDto().getName());
        customAttribute.put("email", oAuthUserProfile.toUserDto().getEmail());
        return customAttribute;

    }
    private User saveOrUpdate(OAuthUserProfile oAuthUserProfile) {
        User user = oAuthRepository.findByEmailAndProvider(oAuthUserProfile.getUser_email(), oAuthUserProfile.toUserDto().getProvider())
                .map(u -> u.update(oAuthUserProfile.getUser_name(), oAuthUserProfile.getUser_email()))
                .orElse(oAuthUserProfile.toUserDto().toEntity());

        return oAuthRepository.save(user);
    }



}

