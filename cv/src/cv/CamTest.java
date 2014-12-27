package cv;

import static org.bytedeco.javacpp.opencv_core.*;

import javax.swing.JFrame;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class CamTest {
    public static void main(String[] args) {
    	IplImage binImg = null;

        new CamTest().loop(binImg);
    }

    private void loop(IplImage binImg) {
        CanvasFrame canvas = new CanvasFrame("Webcam");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // デフォルト(0)のWebカメラを使う
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
            grabber.start();

            // フレームレートを取得
            double frameRate = grabber.getFrameRate();
            long wait = (long) (1000 / (frameRate == 0 ? 10 : frameRate));

            // 画像を取りつづける
            while (true) {
                Thread.sleep(wait);
                IplImage image = grabber.grab();

                // 取ってきた画像を画面に表示
                if (image != null) {
                    canvas.showImage(image);
            		binImg = cvCreateImage(image.cvSize(), IPL_DEPTH_8U, 1);

                }
            }

        // 何かあったらエラーを吐いて終わる
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
