package osgitut.movies.impl;
 
import java.util.*;
import osgitut.movies.*;
import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;
 
public class MovieListerImpl implements MovieLister {
    private final ServiceTracker finderTrack;
 
    public MovieListerImpl(ServiceTracker finderTrack) {
        this.finderTrack = finderTrack;
    }
 
    public List listByDirector(String name) {
        MovieFinder finder = (MovieFinder) finderTrack.getService();
        if(finder == null) {
            return null;
        } else {
            return doSearch(name, finder);
        }
    }
 
    private List doSearch(String name, MovieFinder finder) {
        Movie[] movies = finder.findAll();
        List result = new LinkedList();
        for (int i = 0; i < movies.length; i++) {
            if(movies[i].getDirector().indexOf(name) > -1) {
                result.add(movies[i]);
            }
        }
 
        return result;
    }
}
