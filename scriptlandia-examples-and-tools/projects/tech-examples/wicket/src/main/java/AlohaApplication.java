
import wicket.ApplicationSettings;
import wicket.protocol.http.WebApplication;
import wicket.util.time.Duration;

public class AlohaApplication extends WebApplication {

  protected void init() {
    // Set the 'Home Page' of the application (the 'index')
    getPages().setHomePage(AlohaPage.class);

    // Note that many of the settings below are 'defaults', but are being
    // set explicitly in this example to clarify.

    // Can be enabled/disabled to leave 'wicket' XHTML extensions in
    // rendered result to browser (a development assistance setting)
    getSettings().setStripWicketTags(false);

    // Tells Wicket to explicitly report if a component is specified
    // in the component model of a Particular page, but isn't used.
    // Useful in development to ensure all components are being rendered.
    // (But potentially slower)
    getSettings().setComponentUseCheck(true);

    // Tells wicket to check the timestamp of certain resources (such as HTML markup)
    // and update its local cached representation if they have changed
    // Useful in development, but more efficient to leave unset (null) in a production
    // environment where you don't need/want polling.
    getSettings().setResourcePollFrequency(Duration.ONE_SECOND);

    // Tells wicket to use a helpful 'debug' exception page with a snapshot of the
    // component model, and the exception stack trace.
    // Other settings are available to show pages that say there has been an internal error
    // (a production-friendly page)
    // In addition, more advanced usage allows you to override this behavior completely.
    getSettings().setUnexpectedExceptionDisplay(ApplicationSettings.SHOW_EXCEPTION_PAGE);

    // Alternative configuration - common 'configuration types'
    // that set some of the settings above based on what you would
    // want for production vs. development
    //getSettings().configure("development");
    // -- or --
    //getSettings().configure("deployment");

    // Potentially get custom settings from web.xml
    System.out.println(getWicketServlet().getInitParameter("aloha-app-setting"));
  }
}

