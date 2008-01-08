package actions;

import java.util.Map;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.*;
import org.apache.struts2.interceptor.SessionAware;
import org.texturemedia.smarturls.Result;

@Validation()
@Result(name="success", type="redirect-action", location="hello-world3-view")
public class HelloWorld3Action extends ActionSupport implements SessionAware {

    private Map<String, String> session;

    public void setSession(Map value) {
        session = value;
    }

    protected Map<String, String> getSession() {
        Map<String, String> value = session;
        return value;
    }

    public static String GREETING_KEY = "greeting";

    @RequiredStringValidator(message="Please enter a greeting!")
    public String getGreeting() {
        return (String) getSession().get(GREETING_KEY);
    }

    public void setGreeting(String value) {
        getSession().put(GREETING_KEY,value);
    }

}
