import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.theeyetribe.client.*;
import com.theeyetribe.client.GazeManager.*;

import javax.swing.*;

public class EyeTest extends JFrame {

	private static final long serialVersionUID = 1L;

	// Declare variables
	private GazeManager gm = GazeManager.getInstance();
	private GazeListener gazeListener;
	private JButton pause;
	private JButton resume;
	private JButton startStreaming;
	private JButton stopStreaming;
	private JTextArea[] eyeData = new JTextArea[2];
	private ArrayList<String> streamData = new ArrayList<String>();
	private FileWriter fileWriter = null;

	// Establish connection to local eye tribe server
	private boolean isConnect = gm.activate(ApiVersion.VERSION_1_0,
			ClientMode.PUSH);

	private EyeTest() {
		super("Eye Tribe Data Collector");
		initiateUI();
	}

	/**
	 * A method for initializing UI
	 */
	private void initiateUI() {
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300, 300);

		// eyeData[0] = left eye
		// eyeData[1] = right eye
		for (int i = 0; i < 2; i++) {
			eyeData[i] = new JTextArea();
			eyeData[i].setEditable(false);
			eyeData[i].setOpaque(false);
		}
		eyeData[0].setBounds(10, 10, 600, 60);
		eyeData[1].setBounds(10, 50, 600, 60);

		pause = new JButton("Pause Tracking");
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gazeListener.pause();
			}
		});
		pause.setBounds(10, 90, 140, 50);

		resume = new JButton("Resume Tracking");
		resume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gazeListener.resume();
			}
		});
		resume.setBounds(150, 90, 140, 50);

		startStreaming = new JButton("Start Streaming");
		startStreaming.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gazeListener.startStreaming();
				startStreaming.setText("Streaming...");
			}
		});
		startStreaming.setBounds(10, 150, 140, 50);

		stopStreaming = new JButton("Stop Streaming");
		stopStreaming.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				streamData = gazeListener.stopStreaming();
				startStreaming.setText("Start Streaming");
				try {
					// Check if there is data in the stream data ArrayList
					if (streamData.size() == 0) {
						return;
					}

					// Get current system time
					String timeStamp = new SimpleDateFormat(
							"yyyy-MM-dd_HH-mm-ss").format(Calendar
							.getInstance().getTime());
					
					// Create a text file
					File file = new File("StreamData_" + timeStamp + ".txt");
					fileWriter = new FileWriter(file);

					// Algorithm for producing meaningful streaming data
					// and write them into text files
					String temp = "";
					temp += "Left Eye Coordinates in Pixels		Right Eye Coordinates in Pixels		Timestamp\n";
					for (int i = 0; i < streamData.size() - 4; i = i + 3) {
						temp += String.format("%-25s%19s%41sms\n",
								streamData.get(i), streamData.get(i + 1),
								streamData.get(i + 2));
					}
					fileWriter.write(temp);
					gazeListener.stopStreaming().clear();
				} catch (IOException exception) {
				} finally {
					try {
						fileWriter.close();
					} catch (IOException exception) {
					}
				}
			}
		});
		stopStreaming.setBounds(150, 150, 140, 50);

		if (!isConnect) {
			eyeData[0].setText("Server Not Set Up");
		}

		// Add widget to screen
		this.setLayout(null);
		this.add(eyeData[0]);
		this.add(eyeData[1]);
		this.add(pause);
		this.add(resume);
		this.add(startStreaming);
		this.add(stopStreaming);

		// Initialize GazeListener and add listener to Gaze Manager
		gazeListener = new GazeListener(eyeData);
		gm.addGazeListener(gazeListener);

		// Set everything visible
		setVisible(true);
	}

	public static void main(String[] args) {
		new EyeTest();
	}
}
