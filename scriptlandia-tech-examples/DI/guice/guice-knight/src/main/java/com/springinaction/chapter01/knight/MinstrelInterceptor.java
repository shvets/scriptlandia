package com.springinaction.chapter01.knight;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MinstrelInterceptor implements MethodInterceptor {
  
  public Object invoke(MethodInvocation mi) throws Throwable {

    Knight knight = (Knight) mi.getThis();

    System.out.println(
        "La la la, Sir " + knight.getName() + " is so brave!");
    Object rtn = mi.proceed();
    System.out.println("" +
        "Tee-he-he, he did " + mi.getMethod().getName());
    
    return rtn;
  }
}