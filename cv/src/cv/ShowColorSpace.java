package cv;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;

public class ShowColorSpace {
	ShowColorSpace(){

		IplImage img = cvCreateImage(cvSize(256,256),IPL_DEPTH_8U,3);
		for(int j=0;j<256;j++){
			for(int i=0;i<img.widthStep();i++){
				switch(i%3){
					case 0:img.imageData().put(i+j*img.widthStep(),(byte) 256);break;
					case 1:img.imageData().put(i+j*img.widthStep()+1,(byte)(i/3));break;
					case 2:img.imageData().put(i+j*img.widthStep()+2,(byte) j);break;
				}
			}
		}
		cvCvtColor(img,img,CV_YUV2BGR);
		CanvasFrame canvas = ShowImage.initCanvas(255,255,255);
		canvas.showImage(img);
	}

	public static void main(String args[]){
		new ShowColorSpace();
	}
}
