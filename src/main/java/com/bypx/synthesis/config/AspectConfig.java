package com.bypx.synthesis.config;

import com.bypx.synthesis.bean.User;
import com.bypx.synthesis.service.LogSerive;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
public class AspectConfig {
    @Resource
    LogSerive logSerive;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    //第一个*表示方法的返回类型为任意
    //因为要对所有的controller进行处理，所以要定义到具体的类，要先有包名
    //包名后面的两点：该包以及子包
    //第二个*任意类名
    //第三个*任意方法，可以改成具体的方法
    //（..）任意输入参数
    @Pointcut("execution(* com.bypx.synthesis.controller..*.*(..))")
    public void control(){ }

    @Before("control()")
    //连接后要做哪些增强的功能
    public void logadd(JoinPoint jp){
        String methodName = jp.getSignature().getName();
        Object [] args = jp.getArgs();

        for(Object arg : args){
            System.out.println(arg);
        }
        logSerive.add(methodName,"d");
    }
    @Around("control()")//特殊： 可以限制已有功能 执行或不执行
    public Object around(ProceedingJoinPoint pjp){
        Object result = null;
        try{
            String methodName = pjp.getSignature().getName();
            result = pjp.proceed();
        }catch(Throwable throwable){
            throwable.printStackTrace();
        }
        return result;
    }



}
