package client;

import java.util.EventListener;

/**
 * The listener interface for getting notified when the torrent file that
 * gets distributed by the tracker server changes - i.e when an
 * updated file is distributed around the network.
 * 
 * @author J Steenkamp
 */
public interface TorrentFileListener extends EventListener{
	
	/**
	 * This method is invoked when it is detected that the 
	 * local torrent is no longer the same as the one on the 
	 * tracker server
	 * 
	 * @param event - event object.
	 */
	public void trackerTorrentFileChanged(TorrentFileEvent event);
}
