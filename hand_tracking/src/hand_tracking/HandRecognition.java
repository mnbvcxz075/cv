package hand_tracking;

import static org.bytedeco.javacpp.helper.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.CvContour;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_imgproc.CvMoments;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class HandRecognition {
	private IplImage img;
	private IplImage binImg;
	private IplImage handImg;
	private CvSeq contours;
	private CvMemStorage mem ;
	private CvMoments moment;
	private OpenCVFrameGrabber grabber;
	private  CvScalar maxThreshold = cvScalar(135,145,175,225)
			,minThreshold = cvScalar(70,120,60,0);
	private final int BIN_TYPE = CV_BGR2Lab;

	HandRecognition() throws Exception{
		//カメラ起動
        grabber = new OpenCVFrameGrabber(0);
        double frameRate = grabber.getFrameRate();
        grabber.start();
		img = grabber.grab();

        binImg = cvCreateImage(img.cvSize(), IPL_DEPTH_8U, 1);
        contours = new CvContour();
        mem = CvMemStorage.create();

	}

	void getCameraImage() throws Exception{
		img = grabber.grab();
	}

	Point getCentroid(){
		return new Point((int)(moment.m01()/moment.m00())
						,(int)(moment.m10()/moment.m00()));
	}

	void binarization(){
		//2値化
		cvCvtColor(img,img,BIN_TYPE);
		cvInRangeS(img, minThreshold, maxThreshold,binImg);

		//ノイズ除去
		cvSmooth(binImg,binImg);
		cvErode(binImg,binImg,null,4);
		cvDilate(binImg,binImg,null,5);
		cvErode(binImg,binImg,null,1);

		//輪郭抽出
		cvFindContours(binImg.clone(),mem,contours,Loader.sizeof(CvContour.class),CV_RETR_EXTERNAL,CV_CHAIN_CODE );

		//輪郭ポインタの先頭を最大の物へ変更
		double max=0;
		for(CvSeq ptr=contours;ptr!=null;ptr = ptr.h_next()){
			double size = cvContourArea(ptr);
			if(max<size){
				max=size;
				contours=ptr;
			}
		}

		//モーメントの導出
		cvMoments(contours,moment);
	}
}
