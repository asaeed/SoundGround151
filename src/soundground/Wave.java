package soundground;

import ddf.minim.AudioInput;
import processing.core.PApplet;

public class Wave {
	
	private PApplet app;
	public AudioInput in;
	public static float center;
	public int sensitivity;
	
	public Wave (AudioInput audio, PApplet appRef) {
		center = Global.screenHeight - 200;
		in = audio;
		app = appRef;
	} 

	public void update() { 
	} 

	public void draw() {
		for(int i = 0; i < in.bufferSize() - 1; i++)
		{
			app.line(i, getY(i), i+1, getY(i+1));
		}
		app.line(0, getMaxY(), Global.screenWidth, getMaxY());
	}

	public void stop() {
		in.close();
	}

	public float getY(float x) {
		if (x >= in.bufferSize() || x <= 0)
			return center;
		return center + in.left.get((int)x) * sensitivity; 
	}

	public float getMaxY() {
		float maxY = center;
		for(int i = 0; i < in.bufferSize() - 1; i++)
		{
			if (getY(i) < maxY)
				maxY = getY(i);
		}
		return maxY;
	}  
}
