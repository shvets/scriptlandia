//

import java.io.File;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

public aspect TestCaseGeneratorAspect {

    pointcut testPoint() :
            execution(/* test-point method pattern */
                      public static void main(String[])
            );

    pointcut mockPoints() :
        call (/* mock-points method pattern */
              public void test1()
             )
        && withincode(/* test-point method pattern*/
                      public void test2()
                     );

    Document document;

    // POJO for encapsulating method call data
    private MethodCall methodCall;

    Object around() : testPoint() {

        /* instantiate new XML document here */
        document = new Document();

        // encapsulate invoked method data into POJO
        MethodCall methodCall = new MethodCall();
        methodCall.setMethod(
            thisJoinPoint.getSignature().toString());
        methodCall.setArgs(thisJoinPoint.getArgs());
        Object retValue = proceed();
        methodCall.setRetValue(retValue);

        /* marshal POJO into test-point XML element here */

        /* persist XML to file here */

        // pass back return value
        return retValue;
    }

    Object around() : mockPoints() {

        // encapsulate invoked method data into POJO
        MethodCall methodCall = new MethodCall();
        methodCall.setMethod(
            thisJoinPoint.getSignature().toString());
        methodCall.setArgs(thisJoinPoint.getArgs());
        Object retValue = proceed();
        methodCall.setRetValue(retValue);

        /* marshal POJO into mock-point XML element here */

        // pass back return value
        return retValue;
    }

}
