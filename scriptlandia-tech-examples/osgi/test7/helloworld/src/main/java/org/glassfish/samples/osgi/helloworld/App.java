package org.glassfish.samples.osgi.helloworld;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Hello world!
 *
 */
public class App implements BundleActivator {
    public void start(BundleContext context) throws Exception {
        System.out.println("Hey!");
    }
    public void stop(BundleContext context) throws Exception {
        System.out.println("Bye!");
    }
}
