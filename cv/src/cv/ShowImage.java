package cv;
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
import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.helper.opencv_core.CvArr;
import org.bytedeco.javacv.CanvasFrame;

public class ShowImage {

	public static CanvasFrame initCanvas(){
        return initCanvas(600,200,200);
	}

	public static CanvasFrame initCanvas(int width,int height,int size){
		double aspect = 1.0*width/height;
		if(width>size){
			width = size;
			height = (int) (width/aspect);
		}
		if(height>size){
			height=size;
			width=(int) (height*aspect);
		}

		CanvasFrame canvas;
		canvas = new CanvasFrame("Canvas",1);
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        canvas.setCanvasSize(width, height);

        return canvas;
	}
	public static CanvasFrame initCanvas(int width,int height,int size,int x,int y){
		double aspect = 1.0*width/height;
		if(width>size){
			width = size;
			height = (int) (width/aspect);
		}
		if(height>size){
			height=size;
			width=(int) (height*aspect);
		}

		CanvasFrame canvas;
		canvas = new CanvasFrame("Canvas",1);
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        canvas.setCanvasSize(width, height);
        canvas.setLocation(x, y);
        return canvas;
	}

	public static IplImage loadImage(String fileName) throws IOException{
		return IplImage.createFrom(ImageIO.read(new File(fileName)));
	}

	public static void HSVBinarization(IplImage read,IplImage write,int hUpper,int hLower,int sUpper,int sLower){
		if(hUpper<hLower){
			IplImage hsvimg =  cvCreateImage(read.cvSize(), IPL_DEPTH_8U, 3);
			IplImage mask2 = write.clone();
			CvScalar hsv_min = cvScalar(0,sLower,0,0);
	        CvScalar hsv_max = cvScalar(hUpper,sUpper,255,255);
			CvScalar hsv_min2 = cvScalar(hLower,sLower,0,0);
	        CvScalar hsv_max2 = cvScalar(180,sUpper,255,255);

			cvCvtColor(read,hsvimg,CV_BGR2HSV);
			cvInRangeS(hsvimg, hsv_min, hsv_max,write);
			cvInRangeS(hsvimg, hsv_min2, hsv_max2,mask2);
			cvCopy(mask2,write,mask2);


	        cvErode(write,write,null,4);
	        cvDilate(write,write,null,4);

		}
		else{
			IplImage hsvimg =  cvCreateImage(read.cvSize(), IPL_DEPTH_8U, 3);;
			CvScalar hsv_min = cvScalar(hLower,sLower,0,0);
	        CvScalar hsv_max = cvScalar(hUpper,sUpper,255,255);

			cvCvtColor(read,hsvimg,CV_BGR2HSV);
			cvInRangeS(hsvimg, hsv_min, hsv_max,write);

	        cvErode(write,write,null,4);
	        cvDilate(write,write,null,5);
	        cvErode(write,write,null,1);

		}
	}

	public static void Binarization(IplImage read,IplImage write,int max1,int min1,int max2,int min2,int max3,int min3,int type){
			IplImage hsvimg =  cvCreateImage(read.cvSize(), IPL_DEPTH_8U, 3);;
			CvScalar hsv_min = cvScalar(min1,min2,min3,0);
	        CvScalar hsv_max = cvScalar(max1,max2,max3,255);

			cvCvtColor(read,hsvimg,type);
			cvInRangeS(hsvimg, hsv_min, hsv_max,write);

	        cvErode(write,write,null,4);
	        cvDilate(write,write,null,5);
	        cvErode(write,write,null,1);
	        cvSmooth(write,write);


	}

	public static CvSeq drawContour(CvArr img,IplImage binImg){
		CvContour contours=new CvContour();
		CvMemStorage mem=CvMemStorage.create();

		cvFindContours(binImg.clone(),mem,contours,Loader.sizeof(CvContour.class),CV_RETR_EXTERNAL,CV_CHAIN_CODE );
		cvDrawContours (img, contours, CV_RGB (255, 0, 0), CV_RGB (0, 255, 0), 1, 2, CV_AA, cvPoint (0, 0));


		return contours;
	}

	public static CvSeq drawLargestContour(CvArr img,IplImage binImg){
		CvContour contours=new CvContour();
		CvMemStorage mem=CvMemStorage.create();


		cvFindContours(binImg.clone(),mem,contours,Loader.sizeof(CvContour.class),CV_RETR_EXTERNAL,CV_CHAIN_APPROX_NONE  );
		double max=0;
		CvSeq largestContour = contours;
		for(CvSeq ptr=contours;ptr!=null;ptr = ptr.h_next()){
			double size = cvContourArea(ptr);
			if(max<size){
				max=size;
				largestContour=ptr;
			}
		}
		CvSeq approxPoly = cvApproxPoly(largestContour,Loader.sizeof(CvContour.class),mem, CV_POLY_APPROX_DP, 60,0);
		CvMemStorage hullStrage=CvMemStorage.create();

		CvSeq hull = cvConvexHull2(approxPoly,hullStrage,CV_CLOCKWISE,1);

		cvDrawContours (img, approxPoly, CV_RGB (0, 255, 0), CV_RGB (0, 255, 0), 0, 2, CV_AA, cvPoint (0, 0));
		cvDrawContours (img, hull, CV_RGB (255, 0, 0), CV_RGB (0, 255, 0), 0, 2, CV_AA, cvPoint (0, 0));
		 for(int i = 0; i < hull.total(); i++){
	            CvPoint v=new CvPoint(cvGetSeqElem(hull, i));

	            System.out.println(" X value = "+v.x()+" ; Y value ="+v.y());
	    }

		return largestContour;
	}

	public static void main(String args[]){
		IplImage img = null;
		try {
			img = loadImage("D:\\desktop\\img5.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
        IplImage binImg = cvCreateImage(img.cvSize(), IPL_DEPTH_8U, 1);

//		for(int y =400;y<800;y++){
//			for(int x = 400;x<800;x++){
//				System.out.println(cvGet2D(hsvimg,x,y));
//			}
//		}

		HSVBinarization(img,binImg,30,170,180,20);

/////////////////////////////////////////////////////////////////////////////
		drawLargestContour(img,binImg);
//		CvMemStorage mem=CvMemStorage.create();
//		CvSeq contours=CvContour.create(0, Loader.sizeof(CvContour.class),  Loader.sizeof(CvSeq.class), mem);
//
//		cvFindContours(binImg.clone(),mem,contours,Loader.sizeof(CvContour.class),CV_RETR_EXTERNAL,CV_CHAIN_APPROX_NONE  );
//		cvApproxPoly(contours, Loader.sizeof(CvContour.class),mem, CV_POLY_APPROX_DP, 10,0);
//		cvDrawContours (img, contours, CV_RGB (255, 0, 0), CV_RGB (0, 255, 0), 0, 2, CV_AA, cvPoint (0, 0));
////////////////////////////////////////////////////////////////////////

//		IplImage temp = cvCreateImage(img.cvSize(),IPL_DEPTH_32F,1);
//		IplImage distance = cvCreateImage(img.cvSize(),IPL_DEPTH_8U,1);
//		cvDistTransform(binImg,temp);//,1,3,null,null,0);
//		cvNormalize(temp,distance,0.0,255.0,CV_MINMAX,null);
//
//		double max=0;
//		int x=0,y=0;
//		for(int i =0;i<binImg.width();i++){
//			for(int j = 0;j<distance.height();j++){
//				if(cvGet2D(distance,j,i).getVal(0)>max){
//					x=i;y=j;max=cvGet2D(distance,j,i).getVal(0);
//				}
//			}
//		}
//		System.out.print(x+","+y);
//		int rad=0;
//		for(int i =0;i<img.width();i++){
//			if(x>=i&&cvGet2D(distance,y,x+i).getVal(0)==0){
//				rad=i;
//				break;
//			}if(x>=i&&cvGet2D(distance,y,x-i).getVal(0)==0){
//				rad=i;
//				break;
//			}
//		}
//		cvCircle(binImg,cvPoint(x,y),5, cvScalar(0,0,255,0),-1,4,0);
//		cvRectangle(binImg,cvPoint(x-rad,y-rad),cvPoint(x+rad,y+rad), cvScalar(0,0,255,0),-1,4,0);
//
//
//		cvDistTransform(binImg,temp);//,1,3,null,null,0);
//		cvNormalize(temp,distance,0.0,255.0,CV_MINMAX,null);
//////////////////////////////////////////////////////////////////////////////

		CanvasFrame canvas = initCanvas(img.width(),img.height(),600);
		canvas.showImage(img);
		CanvasFrame canvas2 = initCanvas(img.width(),img.height(),600);
		canvas2.showImage(binImg);
	}


}
