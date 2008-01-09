
public class MockedTestCase extends TestCase {

    public static ThreadLocal<Map<String,MethodCall>>
        MOCKS =
           new ThreadLocal<Map<String,MethodCall>>();

    private File xmlFile;
    private MethodCall testPoint;

    public MockedTestCase(File xmlFile) {
        super("testIt");
        this.xmlFile = xmlFile;
    }

    // overrides super to return test case XML file name
    public String getName() {
        return xmlFile.getName();
    }

    protected void setUp() throws Exception {

        testPoint = new MethodCall();
        /* Unmarshal XML test-point into POJO */

        Map<String,MethodCall> mocks =
            new HashMap<String,MethodCall>();
        /* Unmarshal XML mock-points and load into Map */

        // store loaded mocks in thread local var
        MOCKS.set(mocks);
    }

    public void testIt() {

        // invoke the test
        // - pass in test-point args and capture the result
        Object args = testPoint.getArgs();
        Object result = /* invocation goes here */;

        // assert the result
        assertValues(
            "Unexpected result returned by "
                + testPoint.getMethod(),
            testPoint.getRetValue(),
            result);

    }
    public static void assertValues(
        String msg, Object expected, Object actual) {
        /* Compare the two values here and throw assertion
         * error if not the same. One generic way of doing
         * this might involve marshaling both objects to XML
         * and then comparing the two with an XML diff utility.
         */
    }
}
