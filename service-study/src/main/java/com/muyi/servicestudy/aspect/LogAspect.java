package com.muyi.servicestudy.aspect;

import com.muyi.servicestudy.annotation.LogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志切点，以注解为切点，进行日志记录
 * @author muyi
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    /**
     * 定义切点常量
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.boss.springboot.service.impl.*.*(..))")'
     * 切点表达式中..两个点表明多个，*代表一个
     * 上诉表达式代表切入com.boss.springboot.service.impl包下所有类所有方法，方法参数不限，返回类型不限。
     * 第一个*代表返回类型不限，第二个*表示所有类，第三个*表示所有方法，..两个点表示方法里的参数不限。
     */
    private final String POINT_CUT = "@annotation(com.muyi.servicestudy.annotation.LogAnnotation)";

    @Pointcut(POINT_CUT)
    public void operationLog() {
    }

    @Around("@annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, LogAnnotation logAnnotation) throws Throwable {
        log.info("@Around:LogAnnotation.description=" + logAnnotation.description());
        log.info("@Around:LogAnnotation.name=" + logAnnotation.name());
        Object thing = joinPoint.proceed();
        try {
            // 执行切入方法内容
            this.dealWithThing(thing, joinPoint);
            return thing;
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void dealWithThing(Object thing, ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class[] types = signature.getParameterTypes();
        log.info("@Around:方法参数类型为：{}", types);
        Object[] args = joinPoint.getArgs();
        // 遍历参数
        for (Object arg : args) {
            // 如果参数类型为Sting  则打出日志
            if (arg.getClass() == String.class) {
                log.info("@Around:参数内容为:{}", arg);
            }
        }
        log.info("@Around:方法返回值为：{}", thing);

    }


    @Before("operationLog()")
    public void permissionCheck(JoinPoint point) {
        log.info("@Before：模拟权限检查...");
        log.info("@Before：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.info("@Before：参数为：" + Arrays.toString(point.getArgs()));
        log.info("@Before：被织入的目标对象为：" + point.getTarget());
    }

    @AfterReturning(pointcut = POINT_CUT, returning = "returnValue")
    public void log(JoinPoint point, Object returnValue) {
        log.info("@AfterReturning：模拟日志记录功能...");
        log.info("@AfterReturning：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.info("@AfterReturning：参数为：" + Arrays.toString(point.getArgs()));
        log.info("@AfterReturning：返回值为：" + returnValue);
        log.info("@AfterReturning：被织入的目标对象为：" + point.getTarget());

    }

    @After("operationLog()")
    public void releaseResource(JoinPoint point) {
        log.info("@After：模拟释放资源...");
        log.info("@After：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.info("@After：参数为：" + Arrays.toString(point.getArgs()));
        log.info("@After：被织入的目标对象为：" + point.getTarget());
    }

    /**
     * 在抛出异常时使用
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "operationLog()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.info("@AfterThrowing:异常信息为：", ex);
    }
}
