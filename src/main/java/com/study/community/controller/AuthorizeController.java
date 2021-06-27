package com.study.community.controller;

import com.study.community.dto.AccessTokenDTO;
import com.study.community.dto.GithubUser;
import com.study.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("github.client.id")
    private String clientID;

    @Value("github.client.secret")
    private String clientSecret;

    @Value("github.Redirect.uri")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken= githubProvider.getAccessToken(accessTokenDTO);
        if (accessToken!=null) {
            GithubUser user = githubProvider.getUser(accessToken);
            System.out.println(user.getId());
        }else {
            System.out.println("accessToken is null");
        }
        return "index";

    }
}
