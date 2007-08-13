package example;

import java.io.InputStream;

public interface IEpisode {

	int getNumber();
	
	String getTitle();
	
	InputStream getDataAsStream();
}
