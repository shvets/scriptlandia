import org.osgi.framework.*;
 
public class HelloActivator implements BundleActivator {
  public void start(BundleContext context) {
    System.out.println("Hello EclipseZone Readers!");
  }
 
  public void stop(BundleContext context) {
    System.out.println("Goodbye EclipseZone Readers!");
  }
}
