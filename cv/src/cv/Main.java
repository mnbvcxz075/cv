package cv;

import org.bytedeco.javacv.FrameGrabber.Exception;


public class Main{



	public static void main (String[] args) {
		UseImage uimg=null;
		try {
			uimg = new UseYCC();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		uimg.start();

	}



}

