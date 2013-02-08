package menu;

import graphics.PopupRenderer;

import java.util.ArrayList;
import java.util.HashMap;

import model.Inventory;
import model.PlayerStats;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

import content.FontLoader;

import client.EpicGameContainer;

import quest.Quest;
import util.KeyBindings;
import util.Logger;
import util.RenderUtil;
import willpath.Fitbit;

public class WillpathTab extends Tab{
	class LoginInfo{
		String text = "";
		ArrayList<String> IDsGrabbed = new ArrayList<String>();
		public boolean keyPressed(int key, char c){

			KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
					KeyBindings.DVORAK : KeyBindings.QWERTY;
			
			if (key==Input.KEY_LSHIFT || key==Input.KEY_RSHIFT || key==Input.KEY_CAPITAL) return false; 
			else if (key==kb.delete && text.length()>0) text=text.substring(0, text.length()-1);
			else if (key==kb.enter){
				if(text.trim().equals("")){
					PopupRenderer.addPopup("Please enter your willpath uuid");
					}
				else if (!IDsGrabbed.contains(text.trim())){
					int steamAmount = Fitbit.getSteps(text.trim())/100;
					if(Fitbit.getSteps(text.trim()) == 0){
						PopupRenderer.addPopup("Check uuid");
					}
					else{
						PopupRenderer.addPopup("You have walked " + (Fitbit.getSteps(text.trim())) + " steps!" );
						inventory.addItem("steam", steamAmount);
						Logger.log("Steam x " + steamAmount + " added");
						IDsGrabbed.add(text.trim());
					}
				}
				else {
					PopupRenderer.addPopup("Already grabbed your willpath data");
				}
			}
			else {
				text+=c;
			}
			return false;
		}
	}

	LoginInfo LI;
	Inventory inventory;
	public WillpathTab() {
		super("Willpath");
		LI = new LoginInfo();
	}

	@Override
	void render(GameContainer gc, Graphics g, int x0, int y0, int width,
			int height, boolean hasFocus) {
		g.setColor(new Color(255,255,255,255));
		TextField text = new TextField(gc, (org.newdawn.slick.Font) FontLoader.ITEM_FONT, x0, y0, width-RenderUtil.PIXEL_WIDTH, itemHeight);
		text.setBackgroundColor(new Color(0,0,0,0));
		text.setBorderColor(null);
		text.setTextColor(TEXT_COLOR);
		
		text.setText("Login: " );
		text.render(gc,g);

		text.setBackgroundColor(new Color(255,255,255,255));
		text.setLocation(x0, y0+itemHeight);
		text.setText(LI.text);
		text.render(gc, g);
	}

	@Override
	void update(Inventory inventory, HashMap<String, Quest> quests,
			PlayerStats stats) {
		this.inventory = inventory;
	}
	
	@Override
	public boolean keyPressed(int key, char c){
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		if (key==kb.right||key==kb.left) return true;
		return this.LI.keyPressed(key, c);
	}

}
