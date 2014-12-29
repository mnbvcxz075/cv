package hand_tracking;

import java.awt.AWTException;

import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;


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





		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        double frameRate = grabber.getFrameRate();
        long wait = (long) (1000 / (frameRate == 0 ? 10 : frameRate));
        try {
			grabber.start();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        try {
			System.out.println(grabber.grab().width());
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
