package hand_tracking;

import java.awt.AWTException;

import org.bytedeco.javacv.FrameGrabber.Exception;


public class Main {
	public static void main(String args[]){
		//変数の初期化
		HandMouse robot = null;
		HandRecognition hand = null;

		//手のひら認識、マウスの乗っ取りの用意
		try {
			hand = new HandRecognition();
			System.out.println(hand.img);
			robot = new HandMouse(hand,false);
		} catch (Exception | AWTException e) {
			e.printStackTrace();
		}
		if(hand.img==null||robot ==null){
			System.exit(1);
		}

		//フレームの初期化
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
