package hand_tracking;

import java.awt.AWTException;

public class HandMouse extends ControlMouse{
	HandRecognition hand;

	HandMouse(HandRecognition reco) throws AWTException{
		super(reco.getCentroid().x,reco.getCentroid().y);

		hand = reco;
	}

	void update(){
		setPosition(hand.getCentroid().x,hand.getCentroid().y);
		mouseMove();
	}


}
