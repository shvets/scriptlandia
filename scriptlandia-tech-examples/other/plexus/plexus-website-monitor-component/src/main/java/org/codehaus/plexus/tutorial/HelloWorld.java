package org.codehaus.plexus.tutorial;

/**
 * This component produces a greeting.
 *
 * @author
 * @version $$Id: HelloWorld.java 1355 2005-01-06 01:28:02Z trygvis $$
 */
public interface HelloWorld
{
    /** The role associated with the component. */
    String ROLE = HelloWorld.class.getName();

    /**
     * Says hello by returning a greeting to the caller.
     *
     * @return A greeting.
     */
    public String sayHello();
}

