package example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientSimpsonService implements ISimpsonService {

	private ISimpsonService remoteSimpsonService;

	private Map<Integer, IEpisode> episodeCache = new HashMap<Integer, IEpisode>();

	public ClientSimpsonService(ISimpsonService remoteSimpsonService) {
		if (remoteSimpsonService == null) {
			throw new IllegalArgumentException(
					"Parameter 'remoteSimpsonService' must not be null");
		}
		this.remoteSimpsonService = remoteSimpsonService;
	}

	public IEpisode getEpisode(int episodeNumber)
			throws EpisodeNotFoundException {
		IEpisode episode = episodeCache.get(episodeNumber);
		if (episode == null) {
			episode = remoteSimpsonService.getEpisode(episodeNumber);
			episodeCache.put(episodeNumber, episode);
		}
		return episode;
	}

	public List<IEpisode> getEpisodes(int[] episodeNumbers) {
		// caching is left as a test-first exercise
		return remoteSimpsonService.getEpisodes(episodeNumbers);
	}
}
