package hand_tracking;

import java.awt.AWTException;

import org.bytedeco.javacv.FrameGrabber.Exception;


public class Main {
	public static void main(String args[]){
		HandMouse robot = null;

		HandRecognition hand = null;
		try {
			hand = new HandRecognition();
			System.out.println(hand.img);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		if(hand.img==null){
			System.exit(1);
		}

		try {
			robot = new HandMouse(hand);
		} catch (AWTException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(robot ==null){
			System.exit(-1);
		}


		MakeFrames frames = new MakeFrames(hand);


//		while(true){
		for(int i=0;i<10000000;i++){
			try{
				hand.getCameraImage();
				hand.binarization();
				frames.update();
				robot.update();
				System.out.println(hand.getCentroid());
			}catch(Exception e){

			}
		}

	}
}
