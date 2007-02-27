package aspect.advice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class AfterReturningAdvice1 implements AfterReturningAdvice {

  public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
    System.out.println("Calling " + getClass().getName() + ".afterReturning()");
  }
}
