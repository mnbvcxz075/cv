package hand_tracking;

import java.io.IOException;

import org.bytedeco.javacv.FrameGrabber.Exception;


public class Main {
	public static void main(String args[]){
//		ControlMouse robot = null;
//
//
//		try {
//			robot = new ControlMouse();
//		} catch (AWTException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		if(robot ==null){
//			System.exit(-1);
//		}


		HandRecognition hand = null;
		try {
			hand = new HandRecognition("D:\\desktop\\img2.jpg");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		if(hand.img==null){
			System.exit(1);
		}




//		while(true){
		for(int i=0;i<100;i++){
			try{
				hand.getCameraImage();
				hand.binarization();
				//MakeCanvas canvas = new MakeCanvas(hand.binImg);
				System.out.println(hand.getCentroid());
			}catch(Exception e){

			}
		}

	}
}
