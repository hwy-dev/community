package com.study.community.advice;

import com.alibaba.fastjson.JSON;
import com.study.community.dto.ResultDTO;
import com.study.community.exception.CustomizeErrorCode;
import com.study.community.exception.CustomizeException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    //HttpServletRequest request,
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response){
        //HttpStatus status=getStatujs(request);
        String contentTyhpe =request.getContentType();
        if (contentTyhpe.equals("application/json")){
            ResultDTO resultDTO=null;
            //返回JSON
            if (e instanceof CustomizeException){
                resultDTO= ResultDTO.errorOf((CustomizeException) e);
            }else{
                resultDTO= ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JSON.toJSONString(resultDTO));
                printWriter.close();
            }catch (IOException ioe){

            }
            return  null;
        }else{
            //错误页面跳转
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

    /*private HttpStatus getStatujs(HttpServletRequest request){
        Integer statusCode=(Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode==null){
            return  HttpStatus.INTERNAL_SERVER_ERROR;
        }else{
            return HttpStatus.valueOf(statusCode);
        }
    }*/
}
