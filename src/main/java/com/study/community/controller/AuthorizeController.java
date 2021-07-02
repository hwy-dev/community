package com.study.community.controller;

import com.study.community.dto.AccessTokenDTO;
import com.study.community.dto.GithubUser;
import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import com.study.community.provider.GithubProvider;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        //accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken= githubProvider.getAccessToken(accessTokenDTO);
        //String accessToken ="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";
        if (accessToken!=null) {
            GithubUser githubUser = githubProvider.getUser(accessToken);
            if (githubUser!=null){
                //登录成功，写cookie 和session
                User user=new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userMapper.addUser(user);
                response.addCookie(new Cookie("token",token));

                return "redirect:/";
            }
            else
            {
                //登录失败，重新登录
                return "redirect:/";
            }
        }else {
            System.out.println("accessToken is null");
        }
        return "redirect:/";

    }
}
