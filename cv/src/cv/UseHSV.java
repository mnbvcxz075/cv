package cv;

import static org.bytedeco.javacpp.opencv_core.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;

public class UseHSV implements ActionListener{

	CanvasFrame canvas;
	IplImage img = null;
	IplImage hsv_mask;
	JFrame frame;
	JLabel label,label2;
	int[] max,min;

	UseHSV(String str){
		try {
			img = ShowImage.loadImage(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		hsv_mask = cvCreateImage(img.cvSize(), IPL_DEPTH_8U, 1);
		max = new int[3];
		min = new int[3];
		max[0] = 30;min[0] = 170;max[1] = 180;min[1] = 20;
//		ShowImage.HSVBinarization(img,hsv_mask,max[0],min[0],max[1],min[1]);

		canvas = ShowImage.initCanvas(img.width(),img.height(),600);
		canvas.showImage(hsv_mask);

		frame = Controler();
		frame.setVisible(true);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Hmax +")&&max[0]<=170){
			max[0]+=10;
		}
		if(e.getActionCommand().equals("Hmax -")&&max[0]>=10){
			max[0]-=10;
		}
		if(e.getActionCommand().equals("Smax +")&&max[1]<=240){
			max[1]+=10;
		}
		if(e.getActionCommand().equals("Smax -")&&max[1]>=10){
			max[1]-=10;
		}
		if(e.getActionCommand().equals("Hmin +")&&min[0]<=170){
			min[0]+=10;
		}
		if(e.getActionCommand().equals("Hmin -")&&min[0]>=10){
			min[0]-=10;
		}
		if(e.getActionCommand().equals("Smin +")&&min[1]<=240){
			min[1]+=10;
		}
		if(e.getActionCommand().equals("Smin -")&&min[1]>=10){
			min[1]-=10;
		}


		label.setText(max[0]+"~"+min[0]+" "+max[1]+"~"+min[1]+" "+max[2]+"~"+min[2]);
		ShowImage.HSVBinarization(img,hsv_mask,max[0],min[0],max[1],min[1]);
		canvas.showImage(hsv_mask);
	}

	public JFrame Controler(){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		Map<String,JButton> buttons = new HashMap<String,JButton>();
		buttons.put("Hmax +",new JButton("Hmax +"));
		buttons.put("Hmax -",new JButton("Hmax -"));
		buttons.put("Smax +",new JButton("Smax +"));
		buttons.put("Smax -",new JButton("Smax -"));
		buttons.put("Hmin +",new JButton("Hmin +"));
		buttons.put("Hmin -",new JButton("Hmin -"));
		buttons.put("Smin +",new JButton("Smin +"));
		buttons.put("Smin -",new JButton("Smin -"));

		label = new JLabel();
		label.setText(max[0]+"~"+min[0]+" "+max[1]+"~"+min[1]);
		for(Map.Entry<String,JButton> e:buttons.entrySet()){
			e.getValue().addActionListener(this);
		}

		panel.setLayout(new BorderLayout());
		panel.add(buttons.get("Hmax +"),BorderLayout.NORTH);
		panel.add(buttons.get("Hmax -"),BorderLayout.SOUTH);
		panel.add(buttons.get("Smax -"),BorderLayout.WEST);
		panel.add(buttons.get("Smax +"),BorderLayout.EAST);
		panel.add(buttons.get("Hmin +"),BorderLayout.NORTH);
		panel.add(buttons.get("Hmin -"),BorderLayout.SOUTH);
		panel.add(buttons.get("Smin -"),BorderLayout.WEST);
		panel.add(buttons.get("Smin +"),BorderLayout.EAST);
		panel.add(label,BorderLayout.CENTER);


		frame.add(panel);

		frame.setSize(800, 200);
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		return frame;
	}

}