package soundground;

import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PImage;

public class SoundGround extends PApplet {

	private static final long serialVersionUID = 1L;
	
	public Minim minim;
	public Wave wave;
	public BallMaker ballMaker;
	public ParamBox paramBox;
	public PImage gobstopperBox;
	
	// params
	public int numBalls;
	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "soundground.SoundGround" });
	  }
	
	public void setup() {
		//size(512, 700, P3D);
		//lights();
		//translate(58, 48, 0);
		Global.screenHeight = this.screenHeight;
		Global.screenWidth = this.screenWidth;
		size(Global.screenWidth, Global.screenHeight, P2D);
		//size(512, 700, P2D);
		frameRate(60);
		//smooth();
		  
		gobstopperBox = loadImage("gobstopperBox.jpg");
		gobstopperBox.resize(800, 800);
		paramBox = new ParamBox(this);

		minim = new Minim(this);
		//minim.debugOn();
		wave = new Wave(minim.getLineIn(Minim.STEREO, Global.screenWidth), this);
		  
		refresh();
	}
	
	public void refresh() {
		wave.sensitivity = paramBox.sensitivity;
		ballMaker = new BallMaker(paramBox.numBalls, paramBox.bounce, this);  
	}

	public void draw() {
		background(0);
		stroke(255);
		fill(100);
		
		image(gobstopperBox, Global.screenWidth / 2 - 400, 0);
		  
		wave.draw();
		ballMaker.update(wave.getMaxY()); 
		ballMaker.draw();
		  
		paramBox.draw();
	}
	
	public void stop()
	{
	  wave.stop();
	  minim.stop(); 
	  super.stop();
	}
	
	public void keyPressed() 
	{
	  if (key == 'p') {
		  paramBox.showHide();
	  }
	  
	  if (key == 'r') {
		  refresh();
	  }
	}
	
}
