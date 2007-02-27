import java.util.Date;

import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.IPage;

/**
 * Java class for the AddProject page; contains a form used to collect data for creating a new
 * {@link tutorial.forms.data.ProjectRelease}.
 * 
 * @author Howard M. Lewis Ship
 */
public abstract class AddProject extends BasePage implements PageBeginRenderListener
{
    public abstract ProjectRelease getProject();

    public abstract void setProject(ProjectRelease project);

    public void pageBeginRender(PageEvent event)
    {
        ProjectRelease project = new ProjectRelease();

        project.setReleaseDate(new Date());

        setProject(project);
    }

    @InjectPage("ShowProject")
      public abstract ShowProject getShowProject();  

  public IPage doSubmit()
  {
    ShowProject showProject = getShowProject();

    //showProject.setProject(getProject());

    return showProject;
  }  

}
