package cv;

import static org.bytedeco.javacpp.opencv_core.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class UseImage extends Thread implements ActionListener{

	CanvasFrame canvas,canvas2;
	IplImage img = null;
	IplImage conImg;
	IplImage binImg;
	JFrame frame;
	JLabel label,label2;
	int[] max,min;
	double frameRate;
	long wait;
	OpenCVFrameGrabber grabber;

	UseImage(String str) throws Exception{
		try {
			img = ShowImage.loadImage(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		binImg = cvCreateImage(img.cvSize(), IPL_DEPTH_8U, 1);
		max = new int[3];
		min = new int[3];
		max[0] = 30;min[0] = 170;max[1] = 180;min[1] = 20;
//		ShowImage.HSVBinarization(img,binImg,max[0],min[0],max[1],min[1]);

		canvas = ShowImage.initCanvas(img.width(),img.height(),600,0,200);

		canvas2 = ShowImage.initCanvas(img.width(),img.height(),600,canvas.getWidth(),200);

        grabber = new OpenCVFrameGrabber(1);
        frameRate = grabber.getFrameRate();
        wait = (long) (1000 / (frameRate == 0 ? 10 : frameRate));
        grabber.start();

	}
	UseImage() throws Exception{

        grabber = new OpenCVFrameGrabber(0);
        frameRate = grabber.getFrameRate();
        wait = (long) (1000 / (frameRate == 0 ? 10 : frameRate));
        grabber.start();

		img = grabber.grab();


		binImg = cvCreateImage(img.cvSize(), IPL_DEPTH_8U, 1);
		max = new int[3];
		min = new int[3];
		max[0] = 30;min[0] = 170;max[1] = 180;min[1] = 20;

		canvas = ShowImage.initCanvas(img.width(),img.height(),600,0,200);

		canvas2 = ShowImage.initCanvas(img.width(),img.height(),600,canvas.getWidth(),200);


	}

	@Override
	public void run(){
		try {
			sleep(wait);

			img = grabber.grab();

			update();

		} catch (InterruptedException | Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void update(){

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public  JFrame Controler() {
		return null;
	}
}