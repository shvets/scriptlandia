
import java.util.*;
import java.io.*;
import java.net.*;

import org.junit.*;
import static org.junit.Assert.*;

import org.glassfish.embed.*;


/**
 * Unit test for simple App.
 */
public class AppTest {
  private GlassFish glassFish;

  private final String NAME = "AppTest";
  private int port = 9999;

  @Before
  public void bootGlassfish() throws Exception{
    glassFish = newGlassFish(port);
    assertNotNull(glassFish);
  }

  @After
  public void shutdown(){
    this.glassFish.stop();
  }

  @Test
  public void testServlet() throws Exception {
    URL url = new URL("http://localhost:" + port + "/" + NAME + "/SimpleServlet");

    BufferedReader br = new BufferedReader(
            new InputStreamReader(
            url.openConnection().getInputStream()));
    assertEquals("Wow, I'm embedded!", br.readLine());
  }

  private GlassFish newGlassFish(int port) throws Exception {
    GlassFish glassfish = new GlassFish(port);
    ScatteredWar war = new ScatteredWar(NAME,
            new File("src/main/resources"),
            new File("src/main/resources/WEB-INF/web.xml"),
            Collections.singleton(new File("target/classes").toURI().toURL()));
    glassfish.deploy(war);
    System.out.println("Ready ...");

    return glassfish;
  }

}
