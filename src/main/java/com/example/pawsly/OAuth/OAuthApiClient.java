package com.example.pawsly.OAuth;

public interface OAuthApiClient {
    //OAuth 요청 을 위한 Client 클래스
    OAuthProvider oAuthProvider(); //Client 의 타입 반환
    String requestAccessToken(OAuthLoginParams params); //Authorization Code 를 기반으로 인증 API 를 요청해서 Access Token 을 획득
    OAuthInfoResponse requestOauthInfo(String accessToken); //Access Token 을 기반으로 Email, Nickname 이 포함된 프로필 정보를 획득
}
