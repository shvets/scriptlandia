package osgitut.movies.impl;
 
import org.osgi.framework.*;
 
import osgitut.movies.*;
import java.util.Properties;
import java.util.Dictionary;
 
public class BasicMovieFinderActivator implements BundleActivator {
    private ServiceRegistration registration;
 
    public void start(BundleContext context) {
        MovieFinder finder = new BasicMovieFinderImpl();
 
        Dictionary props = new Properties();
        props.put("category", "misc");
 
        registration = context.registerService(
                               MovieFinder.class.getName(),
                               finder, props);
    }
 
    public void stop(BundleContext context) {
        registration.unregister();
    }
}
