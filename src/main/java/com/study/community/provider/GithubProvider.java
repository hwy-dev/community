package com.study.community.provider;

import com.alibaba.fastjson.JSON;
import com.study.community.dto.AccessTokenDTO;
import com.study.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Component加了后不需要实例化对象类
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code()==200) {
                String str = response.body().string();
                System.out.println(str);
                String token = str.split("&")[0].split("=")[1];
                System.out.println(token);
                return token;
            }else{
                System.out.println(response.message());
            }
            //String scop=split[1];
            //System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        //https://api.github.com/user?access_token=
        //https://api.github.com/users/用户名
        String url ="https://api.github.com/user";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str =response.body().string();
            GithubUser githubUser =JSON.parseObject(str,GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
