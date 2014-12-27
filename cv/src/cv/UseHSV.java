package cv;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacv.FrameGrabber.Exception;

public class UseHSV extends UseImage{

	UseHSV( ) throws Exception{
		super();

		max[0]=225;max[1]=185;max[2]=125;
		min[0]=60;min[1]=130;min[2]=100;
		frame = Controler();
		frame.setVisible(true);

		update();
	}

	public void update() throws Exception{
		img = grabber.grab();
		label.setText(max[0]+" "+max[1]+" "+max[2]);
		label2.setText(min[0]+" "+min[1]+" "+min[2]);
		ShowImage.HSVBinarization(img,binImg,max[0],min[0],max[1],min[1]);
		conImg = img.clone();
		ShowImage.drawLargestContour(conImg, binImg);
		canvas.showImage(binImg);
		canvas2.showImage(conImg);

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
		label.setText(max[0]+" "+max[1]+" "+max[2]);
		label2 = new JLabel();
		label2.setText(min[0]+" "+min[1]+" "+min[2]);

		for(Map.Entry<String,JButton> e:buttons.entrySet()){
			e.getValue().addActionListener(this);
		}
		panel.setLayout(new GridLayout(2,5));
		panel.add(buttons.get("Hmax +"));
		panel.add(buttons.get("Hmin +"));
		panel.add(buttons.get("Smax +"));
		panel.add(buttons.get("Smin +"));

		panel.add(label);

		panel.add(buttons.get("Hmax -"));
		panel.add(buttons.get("Hmin -"));
		panel.add(buttons.get("Smax -"));
		panel.add(buttons.get("Smin -"));
		panel.add(label2);


		frame.add(panel);

		frame.setSize(800, 200);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		return frame;
	}

}