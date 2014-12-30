package hand_tracking;

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
			hand = new HandRecognition();
			System.out.println(hand.img);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		if(hand.img==null){
			System.exit(1);
		}




		MakeCanvas canvas = new MakeCanvas(hand.binImg);
		MakeCanvas canvas2 = new MakeCanvas(hand.binImg);
//		while(true){
		for(int i=0;i<10000000;i++){
			try{
				hand.getCameraImage();
				hand.binarization();
				canvas.canvas.showImage(hand.binImg);
				canvas2.canvas.showImage(hand.img);
				System.out.println(hand.getCentroid());
			}catch(Exception e){

			}
		}

	}
}
