package menu;

import java.util.ArrayList;
import java.util.HashMap;

import menu.ExitTab.ExitItem;
import menu.Tab.Item;
import model.Inventory;
import model.PlayerStats;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.TextField;

import content.FontLoader;

import quest.Quest;
import util.KeyBindings;
import util.RenderUtil;
import NetworkUtil.Action;
import client.ActionType;
import client.EpicGameContainer;
import client.EpicnessAppShell;

public class ExitShopTab extends Tab{
	public static final int RETURN = 0;
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
				if(this.mode==RETURN){
					EpicGameContainer.MAIN.shopIsOpen = false;	
				} 
			} else if (key==kb.right || key==kb.left) return true;
			return false;
		}
		
	}


	public ExitShopTab() {
		super("Exit");
		this.items = new ArrayList<Item>();
		this.items.add(new ExitItem("Exit Shop",RETURN));
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
