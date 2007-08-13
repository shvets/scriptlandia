package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
//import javax.swing.SwingWorker;
import org.jdesktop.swingworker.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import org.gudy.azureus2.core3.security.SESecurityManager;

/**
 * This is the main frame of the client torrent downloader 
 * application
 * 
 * @author J Steenkamp
 */
public class ClientFrame extends JFrame implements ActionListener, TorrentFileListener{

	/**
	 * The <code>SwingWorker</code> class that handles the long 
	 * running task of resetting the <code>ClientTorrentManager</code>
	 */
	private class ClientTorrentManagerResetWorker extends SwingWorker{

		@Override
		protected Object doInBackground() throws Exception {
			if(manager != null){
				manager.reset();
			}
			return null;
		}

		@Override
		protected void done() {
			stopScreenTimer();
			start.setEnabled(true);
			stop.setEnabled(false);
			reset.setEnabled(true);
			progress.setValue(0);
			progress.setIndeterminate(false);
			progress.setStringPainted(true);
			messages.setText("");
			currentStatus.setText("");
			totalDownloaded.setText("");
			totalUploaded.setText("");
			dataSendRate.setText("");
			dataReceiveRate.setText("");
		}
		
	}
	
	/**
	 * The <code>SwingWorker</code> class that handles the long 
	 * running task of starting a new torrent download on 
	 * the <code>ClientTorrentManager.</code>
	 */
	private class ClientTorrentManagerStartWorker extends SwingWorker{

		@Override
		protected Object doInBackground() throws Exception {
		
			if(manager == null){
				manager = ClientTorrentManager.getInstance();
				manager.addTorrentChangeListener(topFrame);
			}
		
			//Stop any previous running processes.
			manager.stopTorrentProcess();
			manager.setDestination(destinationFolder);
			manager.setServer( server.getText().trim());

			boolean started = manager.startTorrentProcess();
			if(started == false){
				return false;
			}else{
				return true;
			}
		}

		@Override
		protected void done() {
			
			try {
				
				boolean result = (Boolean)get();
				if(result == false){
					throw new ExecutionException("internal error - please refer to messages.", new Exception());
				}
				
				start.setEnabled(false);
				stop.setEnabled(true);
				currentStatus.setText("Started....");
				startScreenTimer();
				
			} catch (InterruptedException e) {
				//Ignore.
			} catch (ExecutionException e) {
				JOptionPane.showMessageDialog(topFrame,"Could not start the torrent manager - " + e,
											  "Error starting torrent manager ",
											  JOptionPane.ERROR_MESSAGE);
				start.setEnabled(true);
				stop.setEnabled(false);
				currentStatus.setText("Failed to Start...");
				
			} finally{
				updateMessagePane();
				progress.setIndeterminate(false);
				progress.setStringPainted(true);
			}
		}
		
	}
	
	/**
	 * The <code>SwingWorker</code> class that handles the long 
	 * running task of stopping all torrent downloads on the 
	 * the <code>ClientTorrentManager</code>.
	 */
	private class ClientTorrentManagerStopWorker extends SwingWorker{

		@Override
		protected Object doInBackground() throws Exception {
			manager.stopTorrentProcess();
			return null;
		}

		@Override
		protected void done() {
			start.setEnabled(true);
			stop.setEnabled(false);
			currentStatus.setText("Stopped");
			progress.setIndeterminate(false);
			progress.setStringPainted(true);
			stopScreenTimer();
		}
	}
	
	/**
	 * This <code>ActionListener</code> class is called periodically by the 
	 * <code>screenUpdator<code> timer. It gets the latest statistics from the 
	 * <code>ClientTorrentManager</code> and updates the labels on the screen 
	 */
	private class ManagerStatusUpdator implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			if(manager == null){
				return;
			}
 
			if (manager.isRunning()) {
				if (manager.isDownloading()) {
					currentStatus.setText("Currently Downloading...");
				} else {
					if (manager.isSeeding()) {
						currentStatus.setText("Currently Seeding...");
						progress.setStringPainted(false);
						progress.setIndeterminate(true);
					}else{
						currentStatus.setText("Currently Running...");
					}
				}
			}
			
			if(textFormatter == null) textFormatter = new DecimalFormat("###,###,###,###");
			progress.setValue((int)manager.getPercentDone());
			
			totalDownloaded.setText(textFormatter.format(manager.getTotalBytesDownloaded() / 1000) + " KB");
			totalUploaded.setText(textFormatter.format(manager.getTotalBytesUploaded() / 1000) + " KB");
			dataSendRate.setText(textFormatter.format(manager.getDataSendRate() / 1000) + " KB/s");
			dataReceiveRate.setText(textFormatter.format(manager.getDataReceiveRate() / 1000) + " KB/s");
			updateMessagePane();
		}
	}
	
	/**
	 * The interval that the screen updator will update the values on the screen
	 */
	private static final int SCREEN_UPDATE_INTERVAL = 4 * 1000; //4 Seconds Update interval
	
	
	/**
	 * The method that start this application. It also sets the look and feel to be that of 
	 * the operation system.
	 */
	public static void main(String[] args) throws Exception{
		
		Runnable bootstrapper = new Runnable(){
			public void run(){
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) { /* Could not set the look and feel - not a serious problem though */} 
				ClientFrame frame = new ClientFrame("localhost");
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(bootstrapper);
	}
	
	
	private JTextField server;
	private JTextField destination;
	private JTextField fullTorrentURL;
	
	/**
	 * The destination folder to which to download the data file.
	 */
	private File destinationFolder;
	private JButton browse;
	private JButton start;
	private JButton stop;
	private JButton reset;
	private JFileChooser chooser;
	private JLabel currentStatus;
	
	private JProgressBar progress;
	private JLabel totalDownloaded;
	private JLabel totalUploaded;
	private JLabel dataSendRate;
	private JLabel dataReceiveRate;
	private JTextArea messages;
	
	/**
	 * A reference to be used by the inner classes.
	 */
	private ClientFrame topFrame = this;
	
	/**
	 * This timer will update the on-screen statistics periodically 
	 * with the latest values from the <code>ClientTorrentManager</code> class
	 */
	private Timer screenUpdator;
	
	private DecimalFormat textFormatter;
	
	/**
	 * The main client torrent manager we will use for this client application.
	 */
	private ClientTorrentManager manager;
 
	/**
	 * Creates the frame with the default tracker / torrent hosting
	 * server.
	 * 
	 * @param defaultInitialServer - the default server
	 */
	public ClientFrame(String defaultInitialServer){
		super("BitTorrent Data File Retriever and Distributor");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				int res = JOptionPane.showConfirmDialog(topFrame, "<html>Are you sure you wish to exit ? <br><br> Please note " +
																   "that it might take a while for the application to perform some " +
																   "clean-up whilst exiting.</html>","Confirm Exit",JOptionPane.OK_CANCEL_OPTION);
				if(res == JOptionPane.OK_OPTION){
					
					JPanel glassPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
					glassPane.setOpaque(true);
					glassPane.setBackground(new Color(255,255,255,182));
					JLabel exiting = new JLabel("Exiting....");
					exiting.setFont(new Font("Arial",Font.BOLD,17));
					glassPane.add(exiting);
					topFrame.setGlassPane(glassPane);
					glassPane.setVisible(true);
					
					if(manager != null) manager.close();
					
					SESecurityManager.exitVM(0);
				}
			}
		});
		
		GridBagLayout gdLayout = new GridBagLayout();
		Container pane = getContentPane();
		pane.setLayout(gdLayout);
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.insets = new Insets(2,2,2,2);
		
		pane.add(new JLabel("Server : "),gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.HORIZONTAL;
		
		server = new JTextField(defaultInitialServer,35);
		server.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {}
			public void focusLost(FocusEvent e) {
				setFullURL();
			}
		});
		
		pane.add(server,gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		
		pane.add(new JLabel("Full URL : "),gc);
		
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 5;
		
		fullTorrentURL = new JTextField(35);
		fullTorrentURL.setEnabled(false);
		pane.add(fullTorrentURL,gc);
		setFullURL();
		
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.weightx = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		pane.add(new JLabel("Destination : "),gc);
	
		gc.gridx = 1;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.weightx = 5;
		gc.fill = GridBagConstraints.HORIZONTAL;
		
		destination = new JTextField(25);
		pane.add(destination,gc);
		destination.setEditable(false);
		
		gc.gridx = 2;
		gc.gridy = 2;
		gc.fill = GridBagConstraints.NONE;
		gc.gridwidth = 1;
		gc.weightx = 1;
		gc.anchor = GridBagConstraints.CENTER;
		
		browse = new JButton("Browse...");
		browse.addActionListener(this);
		pane.add(browse,gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.CENTER;
		
		JPanel buttonPanel = new JPanel();
		start = new JButton("Start");
		stop = new JButton("Stop");
		stop.setEnabled(false);
		start.addActionListener(this);
		stop.addActionListener(this);
		start.setEnabled(true);
		reset = new JButton("Reset / Clear");
		reset.addActionListener(this);
		
		buttonPanel.add(start);
		buttonPanel.add(stop);
		buttonPanel.add(reset);
		
		pane.add(buttonPanel,gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.CENTER;
		
		JPanel statusPanel = new JPanel();
		statusPanel.add(new JLabel("<html><b><u>Current Status :</u></b></html>"));
		currentStatus = new JLabel(" Stopped ");
		statusPanel.add(currentStatus);
		pane.add(statusPanel,gc);
		
		gc.gridx = 0;
		gc.gridy = 5;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.CENTER;
		
		progress = new JProgressBar();
		progress.setStringPainted(true);
		progress.setMinimum(0);
		progress.setMaximum(100);
		
		pane.add(progress,gc);
		
		gc.gridx = 0;
		gc.gridy = 6;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.CENTER;

		pane.add(new JLabel(),gc);
		
		gc.gridx = 1;
		gc.gridy = 6;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.CENTER;
		
		JPanel statsPanel = new JPanel(new GridLayout(2,2));
		pane.add(statsPanel,gc);
		
		statsPanel.add(new JLabel("Total Downloaded : "),gc);
		totalDownloaded = new JLabel();
		statsPanel.add(totalDownloaded,gc);
		statsPanel.add(new JLabel("Total Uploaded : "),gc);
		totalUploaded = new JLabel();
		statsPanel.add(totalUploaded,gc);
		statsPanel.add(new JLabel("Data Send Rate : "),gc);
		dataSendRate = new JLabel();
		statsPanel.add(dataSendRate,gc);
		statsPanel.add(new JLabel("Data Receive Rate : "),gc);
		dataReceiveRate = new JLabel();
		statsPanel.add(dataReceiveRate,gc);
		
		JPanel messagesPanel = new JPanel(new GridLayout(1,1));
		messages = new JTextArea();
		messages.setEditable(false);
		messages.setRows(8);
		messages.setPreferredSize(new Dimension(300,300));
		messages.setLineWrap(true);
		messages.setWrapStyleWord(false);
		messages.setFont(new Font("Arial",Font.PLAIN,11));
		JScrollPane scroller = new JScrollPane(messages);
		messagesPanel.add(scroller);
		messagesPanel.setBorder(BorderFactory.createTitledBorder("Messages"));
		
		gc.gridx = 0;
		gc.gridy = 7;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.CENTER;
		pane.add(messagesPanel,gc);
		
		if(pane instanceof JPanel){
			((JPanel)(pane)).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		pack();
		setResizable(false);
	}
	
	/**
	 * Handler method for the buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == browse){
			selectDestinationDirectory();
		}
		
		if(source == start){
			startTorrentManager();
		}
		
		if(source == stop){
			stopTorrentManager();
		}
		
		if(source == reset){
			resetTorrentManager();
		}
		
	}

	private void resetTorrentManager(){
		start.setEnabled(false);
		reset.setEnabled(false);
		stop.setEnabled(false);
		progress.setIndeterminate(true);
		progress.setStringPainted(false);
		(new ClientTorrentManagerResetWorker()).execute();
	}
	
	private void selectDestinationDirectory(){
		browse.setEnabled(false);
		if(chooser == null){
			chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		int result = chooser.showSaveDialog(this);
		if(result == JFileChooser.APPROVE_OPTION){
			File selectedFile = chooser.getSelectedFile();
			destinationFolder = selectedFile;
			destination.setText(destinationFolder.getAbsolutePath());
		}
		browse.setEnabled(true);
	}

	private void setFullURL(){
		fullTorrentURL.setText("http://" + server.getText() + "/torrent-server/download.torrent");
	}
	
	private void startScreenTimer(){
		if(screenUpdator != null){
			screenUpdator.stop();
		}
		screenUpdator = new Timer(SCREEN_UPDATE_INTERVAL,new ManagerStatusUpdator());
		screenUpdator.start();
	}
	
	private void startTorrentManager(){
		
		if(destinationFolder == null || server.getText().trim().equals("")){
			JOptionPane.showMessageDialog(topFrame,"Please ensure that you specify both a destination folder and server. ",
										  "Invalid Server / Destination Folder",JOptionPane.ERROR_MESSAGE);
			return;
		}
		start.setEnabled(false);
		stop.setEnabled(true);
		progress.setIndeterminate(true);
		progress.setStringPainted(false);
		currentStatus.setText("Starting....");
		(new ClientTorrentManagerStartWorker()).execute();
	}
	
	private void stopScreenTimer(){
		if(screenUpdator != null){
			screenUpdator.stop();
		}
	}
	
	
	private void stopTorrentManager(){
		start.setEnabled(false);
		stop.setEnabled(false);
		currentStatus.setText("Stopping...");
		progress.setIndeterminate(true);
		progress.setStringPainted(false);
		(new ClientTorrentManagerStopWorker()).execute();
	}
	
	/**
	 * This method gets invoked when the <code>ClientTorrentManager</code> finds out 
	 * that the torrent on the tracker server has changed - i.e. someone uploaded 
	 * an updated file to the server.
	 * As a result, we need to reset our UI and start downloading the
	 * new torrent.
	 */
	public void trackerTorrentFileChanged(TorrentFileEvent event) {
		
		stopScreenTimer();
		manager.stopTorrentProcess();
		
		Runnable runner = new Runnable(){
			public void run() {
				stopScreenTimer();
				start.setEnabled(false);
				stop.setEnabled(false);
				progress.setIndeterminate(false);
				progress.setStringPainted(false);
				messages.setText("UPDATED FILE - The torrent file on the server has been updated !! \n RESTARTING....");
				messages.repaint();
				manager.reset();
				//Start the process again.
				startTorrentManager();
			}
		};
		SwingUtilities.invokeLater(runner);
	}

	/**
	 * Updates the message box with the latest of what have been 
	 * set by the <code>ClientTorrentManager</code>
	 */
	private void updateMessagePane(){
		if(manager != null){
			Collection<String> manMessages = manager.getMessages();
			if(messages.getText().length() > 8000){
				messages.setText(messages.getText().substring(0,6000));
			}
			
			for(String str : manMessages){
				messages.append(str);
				messages.append("\n");
			}
		}
	}

}




