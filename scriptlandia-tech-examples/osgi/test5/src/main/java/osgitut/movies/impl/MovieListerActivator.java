package osgitut.movies.impl;
 
import java.util.*;
import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;
import osgitut.movies.*;
 
public class MovieListerActivator implements BundleActivator {
 
    private ServiceTracker finderTracker;
    private ServiceRegistration listerReg;
 
    public void start(BundleContext context) throws Exception {
        // Create and open the MovieFinder ServiceTracker
        finderTracker = new ServiceTracker(context, MovieFinder.class.getName(), null);
        finderTracker.open();
 
        // Create the MovieLister and register as a service
        MovieLister lister = new MovieListerImpl(finderTracker);
        listerReg = context.registerService(MovieLister.class.getName(), lister, null);
 
        // Execute the sample search
        doSampleSearch(lister);
    }
 
    public void stop(BundleContext context) throws Exception {
        // Unregister the MovieLister service
        listerReg.unregister();
        
        // Close the MovieFinder ServiceTracker
        finderTracker.close();
    }
 
    private void doSampleSearch(MovieLister lister) {
        List movies = lister.listByDirector("Miyazaki");
        if(movies == null) {
            System.err.println("Could not retrieve movie list");
        } else {
            for (Iterator it = movies.iterator(); it.hasNext();) {
                Movie movie = (Movie) it.next();
                System.out.println("Title: " + movie.getTitle());
            }
        }
    }
}
