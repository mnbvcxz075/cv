package skin_color_segmentation;

import static org.bytedeco.javacpp.opencv_core.*;

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

		for(int i=0;i<3;i++){
			canvas[i+1].showImage(imgs[i]);
		}

	}



	void makeImages(){
		for(int i=0;i<3;i++){
			for(int s=0;s<1;s++){
				for(int t=0;t<1;t++){
				switch(byteNum(img.imageData().get(t*3+s*img.widthStep()+i*8))){
					case 0:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 0);break;
					case 1:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 19);break;
					case 2:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 38);break;
					case 3:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 57);break;
					case 4:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 76);break;
					case 5:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 95);break;
					case 6:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 114);break;
					case 7:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 133);break;
					case 8:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 152);break;
					case 9:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 171);break;
					case 10:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 190);break;
					case 11:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 209);break;
					case 12:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 228);break;
					case 13:imgs[i].imageData().put(t+s*imgs[i].widthStep(), (byte) 247);break;
					}
					System.out.println(img.imageData().get(t*4+s*img.widthStep())+","
							+img.imageData().get(t*4+s*img.widthStep()+1)+","
							+img.imageData().get(t*4+s*img.widthStep()+2)+","
							+img.imageData().get(t*4+s*img.widthStep()+3)+"\n"
							+img.imageData().get(t*4+s*img.widthStep()+4)+","
							+img.imageData().get(t*4+s*img.widthStep()+5)+","
							+img.imageData().get(t*4+s*img.widthStep()+6)+","
							+img.imageData().get(t*4+s*img.widthStep()+7)+"\n"
							+img.imageData().get(t*4+s*img.widthStep()+8)+","
							+img.imageData().get(t*4+s*img.widthStep()+9)+","
							+img.imageData().get(t*4+s*img.widthStep()+10)+","
							+cvGet2D(img,s,t));
				}
			}
		}
	}

	int byteNum(int i){
		i+=128;
		if(i==0){
			return 0;
		}else if(i<20){
			return 1;
		}else if(i<40){
			return 2;
		}else if(i<60){
			return 3;
		}else if(i<80){
			return 4;
		}else if(i<100){
			return 5;
		}else if(i<120){
			return 6;
		}else if(i<140){
			return 7;
		}else if(i<160){
			return 8;
		}else if(i<180){
			return 9;
		}else if(i<200){
			return 10;
		}else if(i<220){
			return 11;
		}else if(i<240){
			return 12;
		}else{
			return 13;
		}
	}
}
