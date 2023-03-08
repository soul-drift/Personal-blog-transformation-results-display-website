package com.lr.blog.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component//spring boot组件扫描
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());//输出日志记录，拿到logger

    @Pointcut("execution(* com.hjt.blog.web.*.*(..))")   //通过这个注解声明是个切面，execution定义切面拦截哪些类（web下的所有web控制器）
    public void log() {}


    @Before("log()")        //在切面之前执行、("log()")传递切面
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//拿到ServletRequestAttributes
        HttpServletRequest request = attributes.getRequest();   //拿到request
        String url = request.getRequestURL().toString();        //传递参数
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();//获得方法的名字joinPoint.getSignature().getName()
        Object[] args = joinPoint.getArgs();        //拿到请求的对象
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);    //记录请求
    }

    @After("log()")         //请求方法之后来执行
    public void doAfter() {
//        logger.info("--------doAfter--------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")    //定义返回的是什么东西，作为里面的值
    public void doAfterRuturn(Object result) {                  //记录返回的内容
        logger.info("Result : {}", result);                     //获取返回的内容
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
