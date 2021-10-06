package com.study.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.community.dto.FileDTO;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class FileController {
    @Value("${upload.imgs.path}")
    private String imgPath;
    //https://blog.csdn.net/qq_43925089/article/details/104928616
    /*@ResponseBody
    @RequestMapping("/upload")
    public FileDTO upload(){
        FileDTO fileDTO=new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/empty.jpeg");
        return fileDTO;
    */
    @ResponseBody
    @RequestMapping("/file/upload")
    public void upload(HttpServletRequest request,HttpServletResponse response,
                       @RequestParam("editormd-image-file")MultipartFile multipartFile
        )throws Exception {
        JSONObject jsonObject=new JSONObject();//可以理解为json格式的数据结构，可以使用put向对象中添加元素，可以很方便的在字符串和json格式之间转换。需要引入依赖fastjson
        //获取后缀名
        String originalName=multipartFile.getOriginalFilename();
        int index=originalName.lastIndexOf(".");
        String suffName=originalName.substring(index);
        //生成上传文件随机文件名
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = simpleDateFormat.format(date);
        //生成返回的URL
        //request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath()+"/images/"+
        String url=request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath()+"/upload/"+fileName+suffName;
        //将上传的文件存储到服务器磁盘
        try{
            //新的文件名
            String newName = fileName+suffName;

            File desFile=new File(imgPath+ newName);
            try {
                OutputStream outputStream=new FileOutputStream(desFile);
                IOUtils.copy(multipartFile.getInputStream(),outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            jsonObject.put("url",url);
            jsonObject.put("message","success");
            jsonObject.put("success",1);
        }catch(Exception e){
            e.printStackTrace();
            jsonObject.put("message","error");
            jsonObject.put("success",0);
        }
        response.getWriter().write(jsonObject.toJSONString());
    }
}
