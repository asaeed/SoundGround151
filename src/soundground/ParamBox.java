package soundground;

import processing.core.PApplet;
import controlP5.*;

public class ParamBox implements ControlListener{
	
	private ControlP5 controlP5;
	private SoundGround app;
	private Textlabel frameRateBox;
	
	public int numBalls;
	public int sensitivity;
	public float bounce;
	
	public ParamBox(SoundGround appRef)
	{
		app = appRef;
		
		numBalls = 300;
		sensitivity = 500;
		bounce = 1;
		
		controlP5 = new ControlP5(app);
		controlP5.isMoveable();
		controlP5.addListener(this);
		//Numberbox nb = controlP5.addNumberbox("ChangeNumBalls", numBalls, 0, 0, 100, 14);
		//nb.setId(1);
		//nb.setMin(1);
		//nb.setMax(500);
		controlP5.addSlider("Balls", 1, 500).setId(1);
		setValue("Balls", numBalls);
		controlP5.addSlider("Sensitivity", 1, 1000).setId(2);
		setValue("Sensitivity", sensitivity);
		controlP5.addSlider("Bounce", 1, 70).setId(3);
		setValue("Bounce", (int)(bounce * 50));
		controlP5.addButton("Submit", 3).setId(4);
		frameRateBox = controlP5.addTextlabel("FrameRate", "init", 20, 20);
		controlP5.show();
		
	}
	
	public void draw()
	{
		frameRateBox.setValue(String.format("%.2f", app.frameRate));
		controlP5.draw();	
	}
	
	@Override
	public void controlEvent(ControlEvent theEvent) 
	{
		PApplet.println("got a control event from controller with id "+theEvent.controller().id());
		switch(theEvent.controller().id()) {
			case(1):
				numBalls = (int)(theEvent.controller().value());
				break;
			case(2):
				sensitivity = (int)(theEvent.controller().value());
				break;
			case(3):
				bounce = (theEvent.controller().value()) / 50;
				break;
			case(4):
				app.refresh();
				break;  
		}
	
	}
	
	public void setValue(String controllerName, int value){
		controlP5.controller(controllerName).setBroadcast(false);
		controlP5.controller(controllerName).setValue(value);
		controlP5.controller(controllerName).setBroadcast(true);
	}
	
	
	public void showHide()
	{
		if (controlP5.isVisible())
			  controlP5.hide();
		  else
			  controlP5.show();
	}
	
	// event doesn't fire
	public void ChangeNumBalls(int val) 
	{
		numBalls = val;
	}
	
	public void submit(Controller c)
	{
		app.setup();
	}
	
}
