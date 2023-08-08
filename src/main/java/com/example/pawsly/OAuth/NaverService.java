package com.example.pawsly.OAuth;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NaverService {
    public String getNaverAccessToken(String code) {
        String access_Token = "";
        String reqURL = "https://nid.naver.com/oauth2.0/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=lgklDmIp2xU8jyG57MEv"); // TODO Naver Client ID 입력
            sb.append("&client_secret=SEjLhWD6CK"); // TODO Naver Client Secret 입력
            sb.append("&redirect_uri=http://3.39.25.7:8080/user/naver"); // TODO 리다이렉트 URI 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result.toString());

                access_Token = element.getAsJsonObject().get("access_token").getAsString();

                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    public void createNaverUser(String token) throws Exception {
        String reqURL = "https://openapi.naver.com/v1/nid/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result.toString());

                String id = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
                String email = element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();

                System.out.println("Naver ID : " + id);
                System.out.println("Naver Email : " + email);

                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
