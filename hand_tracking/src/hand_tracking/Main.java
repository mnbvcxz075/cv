package hand_tracking;

import java.awt.AWTException;

import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;


public class Main {
	public static void main(String args[]){
		ControlMouse robot = null;

		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
			grabber.start();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        try {
			System.out.println(grabber.grab().width());
			System.out.println(grabber.grab().height());
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		try {
			robot = new ControlMouse();
		} catch (AWTException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(robot ==null){
			System.exit(-1);
		}






	}
}
