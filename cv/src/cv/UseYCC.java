package cv;

import static org.bytedeco.javacpp.opencv_imgproc.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacv.FrameGrabber.Exception;

public class UseYCC extends UseImage{

	UseYCC() throws Exception{
		super();

		max[0]=135;max[1]=185;max[2]=135;
		min[0]=70;min[1]=120;min[2]=110;
		frame = Controler();
		frame.setVisible(true);

		update();
	}

	public void update() throws Exception{
		img = grabber.grab();
		label.setText(max[0]+" "+max[1]+" "+max[2]);
		label2.setText(min[0]+" "+min[1]+" "+min[2]);
		ShowImage.Binarization(img,binImg,max[0],min[0],max[1],min[1],max[2],min[2],CV_BGR2HSV);
		conImg = img.clone();
		ShowImage.drawLargestContour(conImg, binImg);
		canvas.showImage(binImg);
		canvas2.showImage(conImg);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Ymax +")&&max[0]<=240){
			max[0]+=10;
		}
		if(e.getActionCommand().equals("Ymax -")&&max[0]>=10){
			max[0]-=10;
		}
		if(e.getActionCommand().equals("Crmax +")&&max[1]<=240){
			max[1]+=10;
		}
		if(e.getActionCommand().equals("Crmax -")&&max[1]>=10){
			max[1]-=10;
		}
		if(e.getActionCommand().equals("Cbmax +")&&max[2]<=240){
			max[2]+=10;
		}
		if(e.getActionCommand().equals("Cbmax -")&&max[2]>=10){
			max[2]-=10;
		}
		if(e.getActionCommand().equals("Ymin +")&&min[0]<=170){
			min[0]+=10;
		}
		if(e.getActionCommand().equals("Ymin -")&&min[0]>=10){
			min[0]-=10;
		}
		if(e.getActionCommand().equals("Crmin +")&&min[1]<=240){
			min[1]+=10;
		}
		if(e.getActionCommand().equals("Crmin -")&&min[1]>=10){
			min[1]-=10;
		}
		if(e.getActionCommand().equals("Cbmin +")&&min[2]<=240){
			min[2]+=10;
		}
		if(e.getActionCommand().equals("Cbmin -")&&min[2]>=10){
			min[2]-=10;
		}

//		update();
	}

	@Override
	public JFrame Controler(){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		Map<String,JButton> buttons = new HashMap<String,JButton>();
		buttons.put("Ymax +",new JButton("Ymax +"));
		buttons.put("Ymax -",new JButton("Ymax -"));
		buttons.put("Ymin +",new JButton("Ymin +"));
		buttons.put("Ymin -",new JButton("Ymin -"));
		buttons.put("Crmax +",new JButton("Crmax +"));
		buttons.put("Crmax -",new JButton("Crmax -"));
		buttons.put("Crmin +",new JButton("Crmin +"));
		buttons.put("Crmin -",new JButton("Crmin -"));
		buttons.put("Cbmax +",new JButton("Cbmax +"));
		buttons.put("Cbmax -",new JButton("Cbmax -"));
		buttons.put("Cbmin +",new JButton("Cbmin +"));
		buttons.put("Cbmin -",new JButton("Cbmin -"));

		label = new JLabel();
		label.setText(max[0]+" "+max[1]+" "+max[2]);
		label2 = new JLabel();
		label2.setText(min[0]+" "+min[1]+" "+min[2]);

		for(Map.Entry<String,JButton> e:buttons.entrySet()){
			e.getValue().addActionListener(this);
		}

		panel.setLayout(new GridLayout(2,7));
		panel.add(buttons.get("Ymax +"));
		panel.add(buttons.get("Ymin +"));
		panel.add(buttons.get("Crmax +"));
		panel.add(buttons.get("Crmin +"));
		panel.add(buttons.get("Cbmax +"));
		panel.add(buttons.get("Cbmin +"));
		panel.add(label);
		panel.add(buttons.get("Ymax -"));
		panel.add(buttons.get("Ymin -"));
		panel.add(buttons.get("Crmax -"));
		panel.add(buttons.get("Crmin -"));
		panel.add(buttons.get("Cbmax -"));
		panel.add(buttons.get("Cbmin -"));
		panel.add(label2);


		frame.add(panel);

		frame.setSize(800, 200);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		return frame;
	}
}
