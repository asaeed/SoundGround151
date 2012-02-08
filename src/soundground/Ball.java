package soundground;

import processing.core.PApplet;
//import processing.core.PConstants;
import processing.core.PVector;
import processing.core.PImage;

public class Ball {
	private PApplet app;
	
	public PVector pos;
	public PVector vel;
	public PVector acc;
	public float size;
	public int color;
	public float bounce;
	
	public float ground;
	
	public PImage ballImage;
	
	public Ball(int col, String ballImageName, float xPos, float yPos, float xVel, float yVel, PApplet appRef){
		color = col;
		pos = new PVector(xPos, yPos);
		vel = new PVector(xVel, yVel);
		acc = new PVector(0f,.3f);
		size = 20;
		app = appRef;
		//bounce = 1;
		
		ballImage = app.loadImage(ballImageName);
		int ballSize = (int)(size * 1.2);
		ballImage.resize(ballSize, ballSize);
	}

	public void update() { 
		
		float deadWave = Wave.center - 5;
		
		// if vel.y is too small, do nothing
		if (Math.abs(vel.y) < .05 && pos.y > deadWave && ground > deadWave)
			return;
				
		pos = PVector.add(pos, vel);
		vel = PVector.add(vel, acc);		
		
		// ground bounce
		if (pos.y >= ground){

			// always bounce up
			pos.y = ground;
			if (vel.y > 0)
				vel.y = vel.y * -1 * bounce;
			else 
				vel.y = vel.y * 2 * bounce;

			// some random horizontal and vertical movement
			if (pos.y < deadWave){
				vel.y = vel.y * app.random(.75f, 1.25f) + app.random(-10, 10);
				vel.x = vel.x + app.random(-1, 1) * 2;
			}
			// dampen
			vel = PVector.mult(vel, app.random(.5f, .7f));
		}

		// wall bounce
		if (pos.x <= size/2 || pos.x >= Global.screenWidth - size/2){
			vel.x = vel.x * -1; 
		}
	} 

	public void draw() {
		//app.smooth();
		//app.stroke(color);
		//app.fill(color);
		
		//app.noStroke();
		//app.sphere(size/2);
		
		app.image(ballImage, pos.x - size/2, pos.y - size/2);
		
		//app.ellipseMode(PConstants.CORNERS);
		//app.ellipse(pos.x - size/2, pos.y - size/2, pos.x + size/2, pos.y + size/2);
		//displayVector(vel, pos.x, pos.y, 4);
	}
	
	public void displayVector(PVector v, float x, float y, float scayl) {
	    app.pushMatrix();
	    float arrowsize = 4;
	    // Translate to location to render vector
	    app.translate(x,y);
	    app.stroke(255);
	    // Call vector heading function to get direction (note that pointing up is a heading of 0) and rotate
	    app.rotate(v.heading2D());
	    // Calculate length of vector & scale it to be bigger or smaller if necessary
	    float len = v.mag()*scayl;
	    // Draw three lines to make an arrow (draw pointing up since we've rotate to the proper direction)
	    app.line(0,0,len,0);
	    app.line(len,0,len-arrowsize,+arrowsize/2);
	    app.line(len,0,len-arrowsize,-arrowsize/2);
	    app.popMatrix();
	  } 
}
