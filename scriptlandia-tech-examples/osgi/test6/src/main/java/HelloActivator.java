import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;
 
public class HelloActivator implements BundleActivator {
    ServiceTracker httpServiceTracker;

    public void start(BundleContext context) throws Exception {
        System.out.println("Hello World!!");

        httpServiceTracker = new HttpServiceTracker(context);
        httpServiceTracker.open();
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Goodbye World!!");

        httpServiceTracker.close();
        httpServiceTracker = null;
    }
}
