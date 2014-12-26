package cv;

import static org.bytedeco.javacpp.opencv_core.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;

public class UseImage implements ActionListener{

	CanvasFrame canvas,canvas2;
	IplImage img = null;
	IplImage conImg;
	IplImage binImg;
	JFrame frame;
	JLabel label,label2;
	int[] max,min;

	UseImage(String str){
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


	}


	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public  JFrame Controler() {
		return null;
	}
}