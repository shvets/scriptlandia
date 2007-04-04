package aspect.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class BeforeAdvice1 implements MethodBeforeAdvice {

  public void before(Method method, Object[] args, Object target) throws Throwable {
    System.out.println("Calling " + getClass().getName() + ".before()");
  }

}
