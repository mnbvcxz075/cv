package hand_tracking;

import java.awt.AWTException;


public class Main {
	public static void main(String args[]){
		ControlMouse robot = null;

		try {
			robot = new ControlMouse();
		} catch (AWTException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(robot ==null){
			System.exit(-1);
		}

		for(int i=0;i<400;i++){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			robot.setPosition(i, i);
			robot.mouseMove();
		}
	}
}
