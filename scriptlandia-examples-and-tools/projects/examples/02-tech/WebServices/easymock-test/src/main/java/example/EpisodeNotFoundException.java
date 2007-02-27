package example;

/**
 * This Exception is thrown if an {@link IEpisode IEpisode} was not found by the
 * {@link ISimpsonService ISimpsonService}.
 * 
 * @author Ralf Stuckert
 */
public class EpisodeNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an EpisodeNotFoundException.
	 */
	public EpisodeNotFoundException() {
	}

}