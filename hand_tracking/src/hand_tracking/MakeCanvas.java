package hand_tracking;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;

public class MakeCanvas {
	CanvasFrame canvas;
	MakeCanvas(IplImage img){
		canvas = ShowImage.initCanvas(img.width(),img.height(),600);


		canvas.showImage(img);
	}
}
