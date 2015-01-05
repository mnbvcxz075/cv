package hand_tracking;

import java.awt.AWTException;

public class HandMouse extends ControlMouse{
	HandRecognition hand;
	boolean isInterface;

	HandMouse(HandRecognition reco) throws AWTException{
		super();

		hand = reco;
	}

	HandMouse(HandRecognition reco,boolean bool) throws AWTException{
		super();

		hand = reco;
		isInterface=bool;
	}

	void update(){
		setPosition(hand.getCentroid().x,hand.getCentroid().y);
		if(isInterface){
			mouseMove();
		}
	}


}
