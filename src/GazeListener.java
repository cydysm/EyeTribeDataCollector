import java.util.ArrayList;

import javax.swing.*;

import com.theeyetribe.client.IGazeListener;
import com.theeyetribe.client.data.GazeData;

public class GazeListener implements IGazeListener {

	// Declare variables
	private JTextArea[] eyeData;
	private String[] eyeInfo = new String[2];
	private ArrayList<String> streamData = new ArrayList<String>();
	private boolean isPause = false;
	private boolean isStream = false;;

	public GazeListener(JTextArea[] eyeData) {
		this.eyeData = eyeData;
	}

	/**
	 * Call this method whenever there is a new frame available
	 */
	@Override
	public void onGazeUpdate(GazeData gazeData) {
		if (!isPause) {
			getEyeData(gazeData);
		}
	}

	/**
	 * Generate eye data
	 * 
	 * @param gazeData
	 */
	private void getEyeData(GazeData gazeData) {
		for (int i = 0; i < 2; i++) {
			switch (i) {
			case 0:
				eyeInfo[i] = String
						.format("Left Eye Coordinates in Pixels: (%.0f, %.0f)\nLeft Eye Pupil Size: %.0f mm",
								gazeData.leftEye.smoothedCoordinates.x,
								gazeData.leftEye.smoothedCoordinates.y,
								gazeData.leftEye.pupilSize);
				break;
			case 1:
				eyeInfo[i] = String
						.format("Right Eye Coordinates in Pixels: (%.0f, %.0f)\nRight Eye Pupil Size: : %.0f mm",
								gazeData.rightEye.smoothedCoordinates.x,
								gazeData.rightEye.smoothedCoordinates.y,
								gazeData.rightEye.pupilSize);
				break;
			}
		}
		if (isStream) {
			streamData.add(String.format("%.0f, %.0f",
					gazeData.leftEye.smoothedCoordinates.x,
					gazeData.leftEye.smoothedCoordinates.y));
			streamData.add(String.format("%.0f, %.0f",
					gazeData.rightEye.smoothedCoordinates.x,
					gazeData.rightEye.smoothedCoordinates.y));
			streamData.add("" + gazeData.timeStamp);
		}
		eyeData[0].setText(eyeInfo[0]);
		eyeData[1].setText(eyeInfo[1]);
	}

	/**
	 * Pause data tracking
	 */
	public void pause() {
		isPause = true;
	}

	/**
	 * Resume data tracking
	 */
	public void resume() {
		isPause = false;
	}

	/**
	 * Start streaming data
	 */
	public void startStreaming() {
		isStream = true;
	}

	/**
	 * Stop streaming data
	 * @return	An ArrayList of eye data
	 */
	public ArrayList<String> stopStreaming() {
		isStream = false;
		return streamData;
	}
}
