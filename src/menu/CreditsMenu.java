package menu;

import java.util.ArrayList;
import java.util.HashMap;

import menu.MainMenu.MainMenuItem;
import menu.Tab.Item;
import model.Inventory;
import model.PlayerStats;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

import quest.Quest;
import util.GameConstants;
import util.KeyBindings;
import util.Logger;
import util.RenderUtil;
import client.EpicGameContainer;
import content.FontLoader;

public class CreditsMenu extends Tab{

	private Color TEXT_COLOR = new Color(0,0,0);
	private Color BACKGROUND_COLOR = new Color(200,200,200);
	TextField textItems;
	
	class CreditsItem extends Item{
		int command = -1;
		
		CreditsItem(String text, int command){
			super(text);
			this.command = command;
		}
		CreditsItem(String text, String keyName) {
			super(text+ " "+ keyName);
		}

		@Override
		boolean keyPressed(int key) {
			KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
					KeyBindings.DVORAK : KeyBindings.QWERTY;
			if (command==0) EpicGameContainer.mode = EpicGameContainer.MAIN_MENU_MODE;
			else if (key==kb.left || key==kb.right) return true;
			return false;
		}
		
	}
	/** Creates a menu to describe the controls of the game. The mainMenu flag indicates whether this is part of the main menu, or the esc menu. */
	public CreditsMenu(boolean mainMenu) {
		super("Credits");
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		this.items = new ArrayList<Item>();
		if (mainMenu) this.items.add(new CreditsItem("Go back to Main Menu", 0));
		this.items.add(new CreditsItem("Cory Li: ", "producer, jack of all trades"));
		this.items.add(new CreditsItem("Alex Jiang: ","quest master, level designer"));
		this.items.add(new CreditsItem("Ruwen Liu: ","main artist, story writer"));
		this.items.add(new CreditsItem("Kaivan Wadia: ","AI mastermind, ability designer"));
		this.items.add(new CreditsItem("Sam Powers: ","controller, menu designer"));
		this.items.add(new CreditsItem("Ben Agre: ","networking, willpath"));
		this.items.add(new CreditsItem("Jackie Hung: ","UI designer, overworld artist"));
		this.items.add(new CreditsItem("Haitao Mao: ","architect, visionary"));
		
		this.items.add(new CreditsItem("Slick: ","game engine"));
		this.items.add(new CreditsItem("Java: ","best programming language"));
		this.items.add(new CreditsItem("Tiled: ","level design engine"));
		this.items.add(new CreditsItem("nocius: ","background music"));
		this.items.add(new CreditsItem("EternalEndeavor: ","background music"));
		this.items.add(new CreditsItem("Tainlorr: ","background music"));
		this.items.add(new CreditsItem("viewtifulday: ","background music"));
		this.items.add(new CreditsItem("GAMBIT staff: ","guidance"));
		
	}

	public void render(GameContainer gc, Graphics g){
		render(gc,g,LEFT_OFFSET,TOP_OFFSET,GameConstants.SCREEN_WIDTH-LEFT_OFFSET*2,GameConstants.SCREEN_HEIGHT-TOP_OFFSET*2, true);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g, int x0, int y0, int width,
			int height, boolean hasFocus) {

		int tempHeight = height-RenderUtil.PIXEL_HEIGHT-TOP_OFFSET;
		int tempWidth = width-RenderUtil.PIXEL_WIDTH-LEFT_OFFSET*2;
		RenderUtil.renderPrettyBox(g, x0+LEFT_OFFSET-RenderUtil.PIXEL_WIDTH, y0+TOP_OFFSET-RenderUtil.PIXEL_HEIGHT, tempWidth, tempHeight);
		
		if (textItems == null) textItems = new TextField(gc, FontLoader.ITEM_FONT, x0+LEFT_OFFSET, y0+titleHeight+TOP_OFFSET, tempWidth-RenderUtil.PIXEL_WIDTH-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);
		
		renderTitle(gc, g, x0+LEFT_OFFSET, y0+TOP_OFFSET, tempWidth, titleHeight, "Controls", textItems);
		renderItems(gc, g, x0+LEFT_OFFSET, y0+titleHeight+TOP_OFFSET, tempWidth-RenderUtil.PIXEL_WIDTH, tempHeight-titleHeight, hasFocus, textItems);
		
	}

	@Override
	void update(Inventory inventory, HashMap<String, Quest> quests,
			PlayerStats stats) {
	}
	
	@Override
	public boolean keyPressed(int key){
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		if (key==kb.up) previousItem();
		else if (key==kb.down) nextItem();
		else return this.items.get(currIndex).keyPressed(key);
		return false;
			
	}

}
