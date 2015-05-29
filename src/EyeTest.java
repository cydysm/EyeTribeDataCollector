import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JTextArea[] eyeData = new JTextArea[2];
	
	// Establish connection to local eye tribe server
	private boolean isConnect = gm.activate(ApiVersion.VERSION_1_0,
			ClientMode.PUSH);


	private EyeTest() {
		super("Eye Tribe Data Collector");
		initiateUI();
	}

	private void initiateUI() {
		this.setResizable(false);
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

		if (!isConnect) {
			eyeData[0].setText("Server Not Set Up");
		} else {
			eyeData[0].setText("Eye Tribe Not Calibrated");
		}

		// Add widget to screen
		this.setLayout(null);
		this.add(eyeData[0]);
		this.add(eyeData[1]);
		this.add(pause);

		gazeListener = new GazeListener(eyeData);
		gm.addGazeListener(gazeListener);

		setVisible(true);
	}

	public static void main(String[] args) {
		new EyeTest();
	}
}
