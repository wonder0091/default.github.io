package com.bypx.synthesis.config;

import com.bypx.synthesis.bean.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Writer;

@Component
@Aspect //表示aop的注解
public class LoginAspect {
    @Autowired
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @Pointcut("execution(* com.bypx.synthesis.controller.PageController.*(..))")
    public void pc(){ }

    @Around("pc()")//特殊： 可以限制已有功能 执行或不执行
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        String url = request.getRequestURI();
        if(url.equals("/login")||url.equals("/register")){
            return joinPoint.proceed();
        }
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(user!=null){
            return joinPoint.proceed();
        }else {
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Refresh","3;url=/login");
            Writer writer = response.getWriter();
            writer.write("<h1>请登录后访问</h1>");
            writer.write("<a href='/login'>立即跳转</a>");
            writer.flush();
            writer.close();
            return null;
        }
    }
}
