package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //pointcut signature

    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}


