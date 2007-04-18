import org.osgi.framework.*;
 
public class HelloWorldKiller implements BundleActivator {
    public void start(BundleContext context) {
        System.out.println("HelloWorldKiller searching...");
        Bundle[] bundles = context.getBundles();
        for(int i=0; i<bundles.length; i++) {
            if("HelloWorld".equals(bundles[i].getSymbolicName())) {
                try {
                    System.out.println("Hello World found, "
                                     + "destroying!");
                    bundles[i].uninstall();
                    return;
                } catch (BundleException e) {
                    System.err.println("Failed: " + e.getMessage());
                }
            }
        }
        System.out.println("Hello World bundle not found");
    }
 
    public void stop(BundleContext context) {
        System.out.println("HelloWorldKiller shutting down");
    }
}
