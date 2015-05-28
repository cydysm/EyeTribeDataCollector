import javax.swing.*;

import com.theeyetribe.client.IGazeListener;
import com.theeyetribe.client.data.GazeData;

public class GazeListener implements IGazeListener {

	private JTextArea[] eyeData;

	public GazeListener(JTextArea[] eyeData) {
		this.eyeData = eyeData;
	}

	@Override
	public void onGazeUpdate(GazeData gazeData) {
		getEyeData(gazeData);
	}

	private void getEyeData(GazeData gazeData) {
		for (int i = 0; i < 2; i++) {
			switch (i) {
			case 0:
				eyeData[i].setText(String.format(
						"Left Eye Coordinates in Pixels: (%.0f, %.0f)\nLeft Eye Pupil Size: %.0f mm",
						gazeData.leftEye.smoothedCoordinates.x,
						gazeData.leftEye.smoothedCoordinates.y,
						gazeData.leftEye.pupilSize));
				break;
			case 1:
				eyeData[i].setText(String.format(
						"Right Eye Coordinates in Pixels: (%.0f, %.0f)\nRight Eye Pupil Size: : %.0f mm",
						gazeData.rightEye.smoothedCoordinates.x,
						gazeData.rightEye.smoothedCoordinates.y,
						gazeData.rightEye.pupilSize));
				break;
			}
		}
	}
}
