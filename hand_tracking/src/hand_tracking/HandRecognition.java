package hand_tracking;

import static org.bytedeco.javacpp.helper.opencv_core.*;
import static org.bytedeco.javacpp.helper.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.CvContour;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgproc.CvMoments;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class HandRecognition {
	IplImage img;
	IplImage binImg;
	IplImage tempImg;
	IplImage dist;

	private IplImage handImg;
	private CvSeq contours;
	private CvMemStorage mem ;
	private CvMoments moment;
	private OpenCVFrameGrabber grabber;
	private  CvScalar maxThreshold = cvScalar(135,145,175,0)
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
        moment =new CvMoments();
        tempImg = cvCreateImage(img.cvSize(),IPL_DEPTH_8U, 3);
        dist =cvCreateImage(img.cvSize(),IPL_DEPTH_32F, 1);


	}
	HandRecognition(String url) throws IOException{
		img = IplImage.createFrom(ImageIO.read(new File(url)));

        binImg = cvCreateImage(img.cvSize(),IPL_DEPTH_8U, 1);
        contours = new CvContour();
        mem = CvMemStorage.create();
        moment =new CvMoments();
        tempImg = cvCreateImage(img.cvSize(),IPL_DEPTH_8U, 3);
        dist =cvCreateImage(img.cvSize(),IPL_DEPTH_32F, 1);

	}

	void getCameraImage() throws Exception{
		img = grabber.grab();
	}

	java.awt.Point getCentroid(){
		return new java.awt.Point((int)(moment.m01()/moment.m00())
						,(int)(moment.m10()/moment.m00()));
	}

	void binarization(){

		//2値化
		cvCvtColor(img,tempImg,BIN_TYPE);
		cvInRangeS(tempImg, minThreshold, maxThreshold,binImg);

		//ノイズ除去
		cvErode(binImg,binImg,null,4);
		cvDilate(binImg,binImg,null,5);
		cvErode(binImg,binImg,null,1);

		//輪郭抽出
		cvFindContours(binImg.clone(),mem,contours,Loader.sizeof(CvContour.class),CV_RETR_EXTERNAL,CV_CHAIN_APPROX_NONE  );

		//輪郭ポインタの先頭を最大の物へ変更
		double max=0;
		for(CvSeq ptr=contours;ptr!=null;ptr = ptr.h_next()){
			double size = cvContourArea(ptr);
			if(max<size){
				max=size;
				contours=ptr;
			}
		}

		//2値画像を最大の輪郭へ変更
		cvRectangle(binImg,cvPoint(0,0),cvPoint(img.width(),img.height()), cvScalar(0,0,0,0),-1,4,0);
        cvDrawContours( binImg, contours, CV_RGB(255,255,255), CV_RGB(255,255,255), -1, CV_FILLED, 8 );


//        cvDistTransform( binImg, dist,CV_DIST_L2,3,new float[]{},null,0);
//        int max1,max2,max3;
//         max1=max2=max3=0;
//        java.awt.Point p = new java.awt.Point();
//        for(int i=0;i<img.height();i++){
//        	for(int j=0;j<img.width();j++){
//        		//System.out.println(
//        				if(isLarge(dist.imageData().get((dist.widthStep()*i+j*4+1))
//        						  ,dist.imageData().get((dist.widthStep()*i+j*4+2))
//        						  ,dist.imageData().get((dist.widthStep()*i+j*4+3))
//        						  ,max1,max2,max3)
//        				){
//        					max1=dist.imageData().get((dist.widthStep()*i+j*4+1));
//        					max2 = dist.imageData().get((dist.widthStep()*i+j*4+2));
//        					max3 = dist.imageData().get((dist.widthStep()*i+j*4+3));
//        					p.x=j;p.y=i;
//         				};
//        		if(cvGet2D(dist,i,j).getVal(0)>maxDist){
//        			p.x=j;p.y=i;
//        		}
//        	}
//        }


        //モーメントを用いた重心の導出
		cvMoments(contours,moment);
//		cvCircle(binImg,cvPoint(p.x,p.y),25, cvScalar(0,0,255,0),-1,4,0);
		cvCircle(binImg,cvPoint((int)(moment.m10()/moment.m00()),(int)(moment.m01()/moment.m00())),25, cvScalar(0,0,255,0),-1,4,0);
//
	}
	boolean isLarge(byte b1,byte b2,byte b3,int i1,int i2,int i3){
		if(b3>i3){
			return true;
		}
		if(b3==i3&&b2>i2){
			return true;
		}
		if(b3==i3&&b2==i2){
			if(i1>=0&&b1<0){
				return true;
			}
			else{
				return i1<b1;
			}
		}
		return false;
	}
}
