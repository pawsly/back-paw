package com.example.pawsly.OAuth.Naver;

import com.example.pawsly.OAuth.Naver.NaverInfoResponse;
import com.example.pawsly.OAuth.Naver.NaverTokens;
import com.example.pawsly.OAuth.OAuthApiClient;
import com.example.pawsly.OAuth.OAuthInfoResponse;
import com.example.pawsly.OAuth.OAuthLoginParams;
import com.example.pawsly.OAuth.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NaverApiClient implements OAuthApiClient {
    //RestTemplate 을 활용해서 외부 요청 후 미리 정의해둔 NaverTokens, NaverInfoResponse 로 응답값을 받습니다.

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}") // 수정된 부분
    private String authUrl;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}") // 수정된 부분
    private String apiUrl;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}") // 수정된 부분
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}") // 수정된 부분
    private String clientSecret;

    private final RestTemplate restTemplate;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        String url = authUrl + "/oauth2.0/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        NaverTokens response = restTemplate.postForObject(url, request, NaverTokens.class);

        assert response != null;
        return response.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v1/nid/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, NaverInfoResponse.class);
    }
}
