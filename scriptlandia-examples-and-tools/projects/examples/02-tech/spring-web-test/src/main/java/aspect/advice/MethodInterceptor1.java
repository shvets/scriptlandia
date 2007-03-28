package aspect.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MethodInterceptor1 implements MethodInterceptor {

  public Object invoke(MethodInvocation invocation) throws Throwable {
    System.out.println("Calling " + getClass().getName() + ".invoke() - before proceed()");

    Object returnValue = invocation.proceed();

    System.out.println("returnValue " + returnValue);

    System.out.println("Calling " + getClass().getName() + ".invoke() - after proceed()");

    return returnValue;
  }
}
