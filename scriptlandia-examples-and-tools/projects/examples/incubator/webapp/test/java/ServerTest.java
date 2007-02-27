import java.net.URL;
import java.io.File;

import junit.framework.TestCase;
import org.codehaus.cargo.container.configuration.LocalConfiguration;
import org.codehaus.cargo.container.configuration.ConfigurationType;
import org.codehaus.cargo.container.resin.Resin3xStandaloneLocalConfiguration;
import org.codehaus.cargo.container.resin.Resin3xInstalledLocalContainer;
import org.codehaus.cargo.container.deployable.Deployable;
import org.codehaus.cargo.container.deployable.WAR;
import org.codehaus.cargo.container.deployable.DeployableType;
import org.codehaus.cargo.container.InstalledLocalContainer;
import org.codehaus.cargo.container.ContainerType;
import org.codehaus.cargo.container.deployer.Deployer;
import org.codehaus.cargo.container.deployer.URLDeployableMonitor;
import org.codehaus.cargo.generic.configuration.ConfigurationFactory;
import org.codehaus.cargo.generic.configuration.DefaultConfigurationFactory;
import org.codehaus.cargo.generic.deployable.DefaultDeployableFactory;
import org.codehaus.cargo.generic.DefaultContainerFactory;

public class ServerTest extends TestCase {

  public void setUp() throws Exception {
    super.setUp();
  }

  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testRunServer1() throws Exception {
    Deployable war = new WAR("target/demo.war");

    LocalConfiguration configuration =
        new Resin3xStandaloneLocalConfiguration(new File("target/myresin3x"));
    configuration.addDeployable(war);

    InstalledLocalContainer container =
        new Resin3xInstalledLocalContainer(configuration);
    container.setHome(new File("c:/apps/resin-3.0.18"));

    container.start();
    // Here you are assured the container is started.

    container.stop();
    // Here you are assured the container is stopped.

//    assertEquals("name2", item2.getName());
  }

  public void testRunServer2() throws Exception {
    Deployable war = new DefaultDeployableFactory().createDeployable(
        "resin3x", "path/to/simple.war", DeployableType.WAR);

    ConfigurationFactory configurationFactory =
        new DefaultConfigurationFactory();
    LocalConfiguration configuration =
        (LocalConfiguration) configurationFactory.createConfiguration(
            "resin3x", ContainerType.INSTALLED, ConfigurationType.STANDALONE);
    configuration.addDeployable(war);

    InstalledLocalContainer container =
        (InstalledLocalContainer) new DefaultContainerFactory().createContainer(
            "resin3x", ContainerType.INSTALLED, configuration);
    container.setHome(new File("c:/apps/resin-3.0.18"));

    container.start();
    // Here you are assured the container is started.

    container.stop();
    // Here you are assured the container is stopped.
  }

  public void testRunServer3() throws Exception {
    InstalledLocalContainer container = new Resin3xInstalledLocalContainer(
        new Resin3xStandaloneLocalConfiguration(new File("target/myresin3x")));
    container.setHome(new File("c:/apps/resin-3.0.18"));

    container.start();

    // Here you are assured the container is started.

    Deployable war = new WAR("path/to/simple.war");
    Deployer deployer = new Resin3xInstalledLocalContainer(container);
    deployer.deploy(war);

    // Here you are NOT sure the WAR has finished deploying. To be sure you
    // need to use a DeployableMonitor to monitor the deployment. For example
    // the following code deploys the WAR and wait until it is available to
    // serve requests (the URL should point to a resource inside your WAR):
    deployer.deploy(war, new URLDeployableMonitor(
        new URL("http://server:port/some/url")));

    container.stop();

    // Here you are assured the container is stopped.
  }

}
