package robot_test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Main {

	public static void main(String[] args) {
//		JFrame frame =  new JFrame();
//		JPanel panel = new JPanel();

		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		if(robot == null){
			System.exit(-1);
		}
		robot.mouseMove(50, 50);
		robot.mousePress(InputEvent.BUTTON1_MASK);
	}

}
