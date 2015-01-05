package hand_tracking;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacv.CanvasFrame;

public class MakeFrames implements ActionListener{
	CanvasFrame binCanvas,handCanvas;
	HandRecognition hand;
	JFrame controler;
	JLabel label,label2;
	final int amount=10;

	MakeFrames(HandRecognition hand){
		this.hand = hand;
		controler = initControler();
		controler.setVisible(true);
		binCanvas = ShowImage.initCanvas(hand.img.width(), hand.img.height(), 400);
		handCanvas = ShowImage.initCanvas(hand.img.width(), hand.img.height(), 400);
	}

	public void update(){
		binCanvas.showImage(hand.binImg);
		handCanvas.showImage(hand.handImg);
	}

	public JFrame initControler(){
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
		label.setText(hand.getMaxThreshold().toString());
		label2 = new JLabel();
		label2.setText(hand.getMinThreshold().toString());

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


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Ymax +")&&hand.getMaxThreshold(0)+amount<=255){
			hand.setMaxThreshold(0, hand.getMaxThreshold(0)+amount);
		}
		if(e.getActionCommand().equals("Ymax -")&&hand.getMaxThreshold(0)-amount>=0){
			hand.setMaxThreshold(0, hand.getMaxThreshold(0)-amount);
		}
		if(e.getActionCommand().equals("Crmax +")&&hand.getMaxThreshold(1)+amount<=255){
			hand.setMaxThreshold(1, hand.getMaxThreshold(1)+amount);
		}
		if(e.getActionCommand().equals("Crmax -")&&hand.getMaxThreshold(1)-amount>=0){
			hand.setMaxThreshold(1, hand.getMaxThreshold(1)-amount);
		}
		if(e.getActionCommand().equals("Cbmax +")&&hand.getMaxThreshold(2)+amount<=255){
			hand.setMaxThreshold(2, hand.getMaxThreshold(2)+amount);
		}
		if(e.getActionCommand().equals("Cbmax -")&&hand.getMaxThreshold(2)-amount>=0){
			hand.setMaxThreshold(2, hand.getMaxThreshold(2)-amount);
		}
		if(e.getActionCommand().equals("Ymin +")&&hand.getMinThreshold(0)+amount<=254){
			hand.setMinThreshold(0, hand.getMinThreshold(0)+amount);
		}
		if(e.getActionCommand().equals("Ymin -")&&hand.getMinThreshold(0)-amount>=0){
			hand.setMinThreshold(0, hand.getMinThreshold(0)-amount);
		}
		if(e.getActionCommand().equals("Crmin +")&&hand.getMinThreshold(1)+amount<=255){
			hand.setMinThreshold(1, hand.getMinThreshold(1)+amount);
		}
		if(e.getActionCommand().equals("Crmin -")&&hand.getMinThreshold(1)-amount>=0){
			hand.setMinThreshold(1, hand.getMinThreshold(1)-amount);
		}
		if(e.getActionCommand().equals("Cbmin +")&&hand.getMinThreshold(2)+amount<=255){
			hand.setMinThreshold(2, hand.getMinThreshold(2)+amount);
		}
		if(e.getActionCommand().equals("Cbmin -")&&hand.getMinThreshold(2)-amount>=0){
			hand.setMinThreshold(2, hand.getMinThreshold(2)-amount);
		}

		label.setText(hand.getMaxThreshold().toString());
		label2.setText(hand.getMinThreshold().toString());
	}

}
