package cv;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.CvMat;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSize;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;

public class LoadImage {
	public static void main(String args[]){
		IplImage bgrimg = null;

		try {
			bgrimg = IplImage.createFrom(ImageIO.read(new File("D:\\desktop\\img.jpg")));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		IplImage hsvimg = cvCreateImage(cvGetSize(bgrimg),IPL_DEPTH_8U,3);
		CvSize sz=cvGetSize(bgrimg);

//		PrintGrayImage(bgrimg,"S");
		ShowImage(bgrimg,"S");

		IplImage hsv_image = cvCreateImage(sz, 8, 3);
        IplImage hsv_mask = cvCreateImage(sz, 8, 1);
        IplImage hsv_edge = cvCreateImage(sz, 8, 1);

        CvScalar hsv_min = cvScalar(0,0,0,0);
        CvScalar hsv_max = cvScalar(0,0,0,0);

		cvCvtColor(bgrimg,hsvimg,CV_BGR2HSV);

//        cvInRangeS(hsv_image, hsv_min, hsv_max,hsv_mask);

        BufferedImage write = bgrimg.getBufferedImage();

        File f2 = new File("ret.jpg");
        try {
			ImageIO.write(write, "jpg", f2);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
    public static void PrintGrayImage(IplImage image, String caption)
    {
        int size = 512; // impractical to print anything larger
        CvMat mat = image.asCvMat();
        int cols = mat.cols(); if(cols < 1) cols = 1;
        int rows = mat.rows(); if(rows < 1) rows = 1;
        double aspect = 1.0 * cols / rows;
        if(rows > size) { rows = size; cols = (int) ( rows * aspect ); }
        if(cols > size) cols = size;
        rows = (int) ( cols / aspect );
        PrintGrayImage(image, caption, 0, cols, 0, rows);
    }

	   public static void PrintGrayImage(IplImage image, String caption, int MinX, int MaxX, int MinY, int MaxY)
	    {
	        int size = 512; // impractical to print anything larger
	        CvMat mat = image.asCvMat();
	        int cols = mat.cols(); if(cols < 1) cols = 1;
	        int rows = mat.rows(); if(rows < 1) rows = 1;

	        if(MinX < 0) MinX = 0; if(MinX > cols) MinX = cols;
	        if(MaxX < 0) MaxX = 0; if(MaxX > cols) MaxX = cols;
	        if(MinY < 0) MinY = 0; if(MinY > rows) MinY = rows;
	        if(MaxY < 0) MaxY = 0; if(MaxY > rows) MaxY = rows;

	        System.out.println("\n" + caption);
	        System.out.print("   +");
	        for(int icol = MinX; icol < MaxX; icol++) System.out.print("-");
	        System.out.println("+");

	        for(int irow = MinY; irow < MaxY; irow++)
	        {
	            if(irow<10) System.out.print(" ");
	            if(irow<100) System.out.print(" ");
	            System.out.print(irow);
	            System.out.print("|");
	            for(int icol = MinX; icol < MaxX; icol++)
	            {
	                int val = (int) mat.get(irow,icol);
	                String C = " ";
	                if(val == 0) C = "*";
	                System.out.print(C);
	            }
	            System.out.println("|");
	        }
	        System.out.print("   +");
	        for(int icol = MinX; icol < MaxX; icol++) System.out.print("-");
	        System.out.println("+");
	    }

	   public static void ShowImage(IplImage image, String caption)
	    {
	        CvMat mat = image.asCvMat();
	        int width = mat.cols(); if(width < 1) width = 1;
	        int height = mat.rows(); if(height < 1) height = 1;
	        double aspect = 1.0 * width / height;
	        if(height < 128) { height = 128; width = (int) ( height * aspect ); }
	        if(width < 128) width = 128;
	        height = (int) ( width / aspect );
	        ShowImage(image, caption, width, height);
	    }
	    public static void ShowImage(IplImage image, String caption, int size)
	    {
	        if(size < 128) size = 128;
	        CvMat mat = image.asCvMat();
	        int width = mat.cols(); if(width < 1) width = 1;
	        int height = mat.rows(); if(height < 1) height = 1;
	        double aspect = 1.0 * width / height;
	        if(height != size) { height = size; width = (int) ( height * aspect ); }
	        if(width != size) width = size;
	        height = (int) ( width / aspect );
	        ShowImage(image, caption, width, height);
	    }
	    public static void ShowImage(IplImage image, String caption, int width, int height)
	    {
	        CanvasFrame canvas = new CanvasFrame(caption, 1);   // gamma=1
	        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	        canvas.setCanvasSize(width, height);
	        canvas.showImage(image);
	    }

	    public  void update(){

	    }
}
