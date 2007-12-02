package client;

import java.util.EventObject;

/**
 * Instances of this class is passed to implementors of the <code>TorrentFileListener</code>
 * interface. There is not a lot of information to pass over, so this is a relatively simple class.
 */
public class TorrentFileEvent extends EventObject {
	public TorrentFileEvent(Object source) {
		super(source);
	}
}
