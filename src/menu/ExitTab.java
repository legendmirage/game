package menu;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.TextField;

import content.FontLoader;

import NetworkUtil.Action;

import client.ActionType;
import client.EpicGameContainer;
import client.EpicnessAppShell;

import quest.Quest;
import util.GameConstants;
import util.KeyBindings;
import util.Logger;
import util.RenderUtil;

import menu.Tab.Item;
import model.GameModel;
import model.Inventory;
import model.PlayerStats;

public class ExitTab extends Tab{

	public static final int EXIT = 0, RESTART = 1, CONTROLS = 2, RETURN = 3, SAVE = 4;
	TextField textItems;

	class ExitItem extends Item{
		int mode;

		ExitItem(String text, int mode) {
			super(text);
			this.mode = mode;
		}

		@Override
		boolean keyPressed(int key) {
			KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
					KeyBindings.DVORAK : KeyBindings.QWERTY;
			if (key==kb.space || key==kb.enter){
				if (this.mode==EXIT){
					EpicnessAppShell.close();
				} else if (this.mode==RESTART){
					EpicnessAppShell.restart();
				} else if(this.mode==RETURN){
					Action action = new Action(ActionType.OPEN_MENU);
					EpicGameContainer.MAIN.protocol.sendAction(action);
					
				} 
				else if (this.mode==SAVE){
					//Ben's code goes here
				}
			} else if (key==kb.right || key==kb.left) return true;
			return false;
		}
		
	}


	public ExitTab() {
		super("Exit");
		this.items = new ArrayList<Item>();
		this.items.add(new ExitItem("Return to game",RETURN));
		this.items.add(new ExitItem("Return to main menu (ALL PROGRESS WILL BE LOST)",RESTART));
		//this.items.add(new ExitItem("Save the game", SAVE));
		this.items.add(new ExitItem("Exit the game",EXIT));
	}

	
	@Override
	void render(GameContainer gc, Graphics g, int x0, int y0, int width,
			int height, boolean hasFocus) {

		int tempHeight = height-RenderUtil.PIXEL_HEIGHT-TOP_OFFSET;
		int tempWidth = width-RenderUtil.PIXEL_WIDTH-LEFT_OFFSET*2;
		RenderUtil.renderPrettyBox(g, x0+LEFT_OFFSET-RenderUtil.PIXEL_WIDTH, y0+TOP_OFFSET-RenderUtil.PIXEL_HEIGHT, tempWidth, tempHeight);
		if (textItems == null) textItems = new TextField(gc, FontLoader.ITEM_FONT, x0+LEFT_OFFSET, y0+titleHeight+TOP_OFFSET, tempWidth-RenderUtil.PIXEL_WIDTH-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);
		
		renderTitle(gc, g, x0+LEFT_OFFSET, y0+TOP_OFFSET, tempWidth, titleHeight, "Exit Menu", textItems);
		renderItems(gc, g, x0+LEFT_OFFSET, y0+titleHeight+TOP_OFFSET, tempWidth-RenderUtil.PIXEL_WIDTH, tempHeight-titleHeight, hasFocus, textItems);
		
	}


	@Override
	void update(Inventory inventory, HashMap<String, Quest> quests,
			PlayerStats stats) {
		// TODO Auto-generated method stub
		
	}
}
