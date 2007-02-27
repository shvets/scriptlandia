import java.util.Date;

/**
 * Contains the name and description of a release of a project.
 * 
 * @author Howard M. Lewis Ship
 */
public class ProjectRelease
{
    private String _name;

    private String _releaseId;

    private String _shortDescription;

    private String _longDescription;

    private String _category;

    private String _tapestryVersion;

    private Date _releaseDate;

    private boolean _public;

    /**
     * A user-specified category, used to group similar projects.
     */
    public String getCategory()
    {
        return _category;
    }

    public void setCategory(String category)
    {
        _category = category;
    }

    /**
     * A longer description used on a detail page.
     */
    public String getLongDescription()
    {
        return _longDescription;
    }

    public void setLongDescription(String longDescription)
    {
        _longDescription = longDescription;
    }

    /**
     * The name of the project.
     */
    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    /**
     * If true, the project is visible to other users. If false, then the project is not visible.
     * This is used as a "draft" mode, when information about the project is not complete.
     */
    public boolean isPublic()
    {
        return _public;
    }

    public void setPublic(boolean public1)
    {
        _public = public1;
    }

    /**
     * The date when the project was released. Used to generate a chronological listing.
     */
    public Date getReleaseDate()
    {
        return _releaseDate;
    }

    public void setReleaseDate(Date releaseDate)
    {
        _releaseDate = releaseDate;
    }

    /**
     * The version number of the project that was released.
     */
    public String getReleaseId()
    {
        return _releaseId;
    }

    public void setReleaseId(String releaseId)
    {
        _releaseId = releaseId;
    }

    /**
     * A single-line description used in an overview listing.
     */
    public String getShortDescription()
    {
        return _shortDescription;
    }

    public void setShortDescription(String shortDescription)
    {
        _shortDescription = shortDescription;
    }

    /**
     * The version of Tapestry required for the project.
     */
    public String getTapestryVersion()
    {
        return _tapestryVersion;
    }

    public void setTapestryVersion(String tapestryVersion)
    {
        _tapestryVersion = tapestryVersion;
    }
}