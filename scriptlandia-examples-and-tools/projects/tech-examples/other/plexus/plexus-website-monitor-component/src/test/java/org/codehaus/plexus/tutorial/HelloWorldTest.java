package org.codehaus.plexus.tutorial;

import org.codehaus.plexus.PlexusTestCase;

/**
 * @author
 * @version $$Id: HelloWorldTest.java 3353 2006-05-31 14:17:11Z evenisse $$
 */
public class HelloWorldTest
    extends PlexusTestCase
{
    public void testMessage()
        throws Exception
    {
        HelloWorld component = (HelloWorld) lookup( HelloWorld.ROLE );

        assertEquals( "The hello message wasn't as expected.",
                      "Hello World!", component.sayHello() );
    }
}
