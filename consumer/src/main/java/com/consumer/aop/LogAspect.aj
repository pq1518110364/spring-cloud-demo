package com.consumer.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author xiangwei
 * @date 2020-08-14 4:35 下午
 */
@Slf4j
@Aspect
@Component
public class LogAspect {


    /**
     * 切点：对service包中所有方法进行织入
     */
    @Pointcut("execution(* com.consumer.user.*.*(..))")
    private void allServiceMethodPointCut() {}

    @Before("allServiceMethodPointCut()")
    public void before() { log.info(" spring aop before log begin ");}

    @AfterReturning("allServiceMethodPointCut()")
    public void after() { log.info(" spring aop before log end ");}

    /**
     * 环绕通知,需要返回调用结果
     */
    @Around("allServiceMethodPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info(" spring aop around log begin ");
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            log.info(" spring aop around log end ");
        }
    }
}
