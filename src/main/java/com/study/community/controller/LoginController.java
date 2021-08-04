package com.study.community.controller;

import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import com.study.community.model.UserExample;
import com.study.community.utils.ValidateImageCodeUtils;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
public class LoginController {
    @Value("${upload.img.path}")
    private String imgPath;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = {"/login"})
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        if (username != null) {
            UserExample userExample=new UserExample();
            UserExample.Criteria cri = userExample.createCriteria();
            cri.andNameEqualTo(username);
            cri.andPasswordEqualTo(password);

            List<User> users = userMapper.selectByExample(userExample);
            if (users != null && users.size()>0 )  {
                request.getSession().setAttribute("user", users.get(0));
                response.addCookie(new Cookie("token", users.get(0).getToken()));
                return "redirect:/";
            } else {
                return "login";
            }
            //登陆成功，防止表单重复提交，可以重定向到主页
            //session.setAttribute("loginUser",username);

            /*String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(username);
            user.setAccountId("999");
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl("");
            userMapper.addUser(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";*/
        } else {
            //登陆失败
            //map.put("msg","用户名密码错误");
            return "login";
        }
    }

    // 注册方法
    @RequestMapping(value = {"/reg"})
    public String reg(@RequestParam(value = "username",required = true) String username,
                           @RequestParam(value = "password",required = true) String password,
                      @RequestParam(value = "email",required = true) String email,
                      @RequestParam(value = "phone",required = true) String phone,
                      @RequestParam("fileWGPicture") MultipartFile file,
                           HttpSession session,
                           Model model,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        //String sessionCode = (String)session.getAttribute("code"); // 生成的验证码
        // 忽略大小写, 比较用户输入的验证码与生成的验证码
        if (username!=null && !username.equals("") && password!=null && !password.equals("")) {
            UserExample userExample=new UserExample();
            UserExample.Criteria cri = userExample.createCriteria();
            cri.andNameEqualTo(username);

            List<User> users = userMapper.selectByExample(userExample);
            if (users!=null && users.size()>0) {
                //System.out.println("注册失败");
                return "redirect:/register"; // 注册失败跳转到注册界面
            }
            else{
                User user = new User();
                //下面开始保存图片
                String fileName;
                String returnUrl="";
                String newName="empty.jpeg";
                if (file != null && file.getSize() > 0) {
                    if (!file.isEmpty()) {
                        fileName =file.getOriginalFilename();
                        //图片访问的URI
                        returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/image/";
                        //文件后缀
                        String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());

                        //新的文件名
                        newName = System.currentTimeMillis()+"_"+new Random().nextInt(1000) + fileSuffix;

                        File desFile=new File(imgPath+ newName);
                        try {
                            OutputStream outputStream=new FileOutputStream(desFile);
                            IOUtils.copy(file.getInputStream(),outputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //Thumbnails.of(file.getInputStream())
                        //        .size(Integer.parseInt(300), Integer.parseInt(300)).toFile(des);
                        //System.out.println(file.getName());
                    }
                }
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(username);
                user.setPassword(password);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                //returnUrl+
                user.setAvatarUrl(newName);
                userMapper.insert(user);
                //response.addCookie(new Cookie("token",token));
                System.out.println("注册成功");
            return "redirect:/loginHtml"; // 注册成功跳转到登录界面
            }
        }
        else{
            return "redirect:/register"; // 注册失败跳转到注册界面
        }

    }

    // 生成验证码
    @GetMapping("/code")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        // 生成验证码
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
        // 存入session作用域中
        session.setAttribute("code", securityCode);
        // 响应图片
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
