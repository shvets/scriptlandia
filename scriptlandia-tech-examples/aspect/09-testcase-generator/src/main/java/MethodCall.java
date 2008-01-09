// MethodCall.java

public class MethodCall {
  private String method;
  private Object retValue;

  private Object[] args;

  public String getMethod() {
    return method;
  }  

  public void setMethod(String method) {
    this.method = method;
  }  

  public Object getRetValue() {
    return retValue;
  }  

  public void setRetValue(Object retValue) {
    this.retValue = retValue;
  }  

  public Object[] getArgs() {
    return args;
  }  

  public void setArgs(Object[] args) {
    this.args = args;
  }  

}