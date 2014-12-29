package hand_tracking;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class ControlMouse {
	private int x,y;
	private Robot robot;

	ControlMouse(int x,int y) throws AWTException{
		this.x = x;
		this.y = y;
		robot = new Robot();
	}
	ControlMouse() throws AWTException{
		this.x = 200;
		this.y = 200;
		robot = new Robot();
	}

	void setPosition(int x,int y){

		this.x = x;
		this.y = y;
	}

	void mouseMove(){
		robot.mouseMove(x, y);
	}

	void mouseLeftClick(){
		robot.mousePress(InputEvent.BUTTON1_MASK);
	}


}
