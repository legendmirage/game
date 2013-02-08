package graphics;

import model.GameModel;

class Popup{
	int initTime, y, fadeState = 0;
	String text;
	boolean isPermanent = false;
	Popup(String text, int _y, boolean _isPermanent){
		this.text = text;
		this.initTime = GameModel.MAIN.tickNum;
		this.y = _y;
		this.isPermanent = _isPermanent;
	}
}