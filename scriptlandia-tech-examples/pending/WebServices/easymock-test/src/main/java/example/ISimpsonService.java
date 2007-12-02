package example;

import java.util.List;

public interface ISimpsonService {

	IEpisode getEpisode(int number) throws EpisodeNotFoundException;
	
	List<IEpisode> getEpisodes(int[] numbers);
}
