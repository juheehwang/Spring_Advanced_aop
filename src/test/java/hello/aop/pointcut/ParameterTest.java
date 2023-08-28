package hello.aop.pointcut;

import hello.aop.order.OrderService;
import hello.aop.order.aop.member.MemberService;
import hello.aop.order.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success(){
        log.info("memberService Proxy = {} ", memberService.getClass());
        memberService.hello("helloA");
    }

    @Aspect
    @Slf4j
    static class ParameterAspect{
        @Pointcut("execution(* hello.aop.order.aop.member..*.*(..))")
        private void allMember(){}

        @Around("allMember() && args(arg,..)")
        public Object logArgs1(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs1]{}, arg={}", joinPoint.getSignature(),arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg,..)")
        public void logArgs3(String arg){
            log.info("[logArgs3] arg={}",arg);
        }

        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj){
            log.info("[this]{}, obj={}", joinPoint.getSignature(),obj.getClass());
        }
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj){
            log.info("[target]{}, obj={}", joinPoint.getSignature(),obj.getClass());
        }
        @Before("allMember() && target(annotation)")
        public void targetArgs(JoinPoint joinPoint, ClassAop annotation){
            log.info("[target]{}, obj={}", joinPoint.getSignature(),annotation);
        }
        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation){
            log.info("[within]{}, obj={}", joinPoint.getSignature(),annotation);
        }
    }
}
