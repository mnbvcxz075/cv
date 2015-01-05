package skin_color_segmentation;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class MakeSomeImage {
	CanvasFrame canvas[] ;
	OpenCVFrameGrabber grabber;
	IplImage img ;
	IplImage imgs[];


	MakeSomeImage() throws Exception{
		grabber = new OpenCVFrameGrabber(0);
		grabber.start();
		img = grabber.grab();


//		img = cvCreateImage(cvSize(4,4), IPL_DEPTH_8U, 3);
//		cvRectangle(img,cvPoint(0,0),cvPoint(4,4), cvScalar(255,100,0,0),-1,1,0);
//		cvRectangle(img,cvPoint(0,2),cvPoint(2,3), cvScalar(0,0,255,0),-1,1,0);

		imgs= new IplImage[3];

		for(int i=0;i<3;i++){
			imgs[i] = cvCreateImage(img.cvSize(), IPL_DEPTH_8U, 1);
		}

		canvas = new CanvasFrame[4];
		canvas[0]=new CanvasFrame("");
		canvas[1]=new CanvasFrame("b");
		canvas[2]=new CanvasFrame("g");
		canvas[3]=new CanvasFrame("r");
		for(int i=0;i<4;i++){
	        canvas[i].setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
			canvas[i].setCanvasSize(img.width()/2,img.height()/2);
			canvas[i].setVisible(true);
		}


	}

	public static void main(String args[]){
		MakeSomeImage obj=null;
		try {
			obj = new MakeSomeImage();
		} catch (Exception e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}

		if(obj==null){
			System.exit(0);
		}

		while(true){
			try {
				obj.update();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	void update() throws Exception{

		makeImages();
		img=grabber.grab();
		canvas[0].showImage(img);
		cvCvtColor(img,img,CV_BGR2Lab);

		for(int i=0;i<1;i++){
			canvas[i+1].showImage(imgs[i]);
		}

	}



	void makeImages(){
//			for(int s=0;s<img.height();s++){
//				for(int t=0;t<img.widthStep();t++){
//						imgs[t%3].imageData().put(t/3+s*imgs[t%3].widthStep(),(img.imageData().get(t+s*img.widthStep())));
//				}
//			}
		IplImage mask = cvCreateImage(img.cvSize(), IPL_DEPTH_8U, 1);
		cvCopy(img,mask,img);
		byte l = 0,b=0;
		for(int s=0;s<img.height();s++){
			for(int t=0;t<img.widthStep();t+=3){
				l=img.imageData().get(t+s*img.widthStep());
				if(l<0){
					b=0;
				}else{
					b=-128;
				}
				imgs[0].imageData().put(t/3+s*imgs[0].widthStep(),b);
			}
		}

	}

}
