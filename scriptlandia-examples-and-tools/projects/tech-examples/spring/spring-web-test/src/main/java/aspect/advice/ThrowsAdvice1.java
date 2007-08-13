package aspect.advice;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

public class ThrowsAdvice1 implements ThrowsAdvice {

  public void afterThrowing(Exception exception) throws Throwable {
    System.out.println("Calling " + getClass().getName() + ".afterThrowing() with 1 parameter");
    System.out.println("Exception: " + exception.getMessage());
  }

  public void afterThrowing(Method method, Object[] args, Object target,
                            IllegalArgumentException exception) throws Throwable {
    System.out.println("Calling " + getClass().getName() + ".afterThrowing() with 4 parameters");
    System.out.println("Exception: " + exception.getMessage());
  }

}
