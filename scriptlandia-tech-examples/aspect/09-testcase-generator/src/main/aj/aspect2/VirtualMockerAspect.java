import java.util.Map;

public aspect VirtualMockerAspect {

    pointcut mockPoints() :
        call (/* mock-points method pattern */)
        && withincode(/*test-point method pattern*/);

    Object around() : mockPoints() {

        // retrieve mock data
        Map<String,MethodCall> mocks =
            MockedTestCase.MOCKS.get();
        MethodCall mock = mocks.get(
            thisJoinPoint.getSignature().toString());

        // assert input and return output
        MockedTestCase.assertValues(
            "Unexpected input arguments passed to "
                + mock.getMethod(),
            mock.getArgs(),
            thisJoinPoint.getArgs());
        return mock.getRetValue();
    }
}