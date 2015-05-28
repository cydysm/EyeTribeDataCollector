import com.theeyetribe.client.*;
import com.theeyetribe.client.GazeManager.*;

import javax.swing.*;

public class EyeTest extends JFrame {

	private static final long serialVersionUID = 1L;

	private GazeManager gm = GazeManager.getInstance();
	private GazeListener gazeListener;
	private boolean isConnect = gm.activate(ApiVersion.VERSION_1_0,
			ClientMode.PUSH);

	private JTextArea[] eyeData = new JTextArea[2];

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
		eyeData[0].setBounds(10, 10, 600, 600);
		eyeData[1].setBounds(10, 50, 600, 600);

		if (!isConnect) {
			eyeData[0].setText("Server Not Set Up");
		} else { 
			eyeData[0].setText("Eye Tribe Not Calibrated");
		}
		
		this.setLayout(null);
		this.add(eyeData[0]);
		this.add(eyeData[1]);

		gazeListener = new GazeListener(eyeData);
		gm.addGazeListener(gazeListener);

		setVisible(true);
	}

	public static void main(String[] args) {
		new EyeTest();
	}
}
