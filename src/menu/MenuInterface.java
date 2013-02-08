
	package menu;

	import graphics.GearPortrait;
	import graphics.RenderComponent;

	import java.util.HashMap;

	import model.GameModel;
	import model.Inventory;
	import model.Player;
	import model.PlayerStats;

	import org.newdawn.slick.Color;
	import org.newdawn.slick.Font;
	import org.newdawn.slick.GameContainer;
	import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
	import org.newdawn.slick.gui.TextField;

import audio.SFXPlayer;

	import client.EpicGameContainer;

	import quest.Quest;
	import util.GameConstants;
	import util.KeyBindings;
import util.Logger;
	import util.RenderUtil;
	import content.FontLoader;
import content.MenuLoader;

public abstract class MenuInterface implements RenderComponent{
	//Because we can't have access to the player directly, these get updated:
	protected Inventory inventory;
	protected HashMap<String, Quest> quests;
	protected PlayerStats stats;

	//So we know what WASD should do. If Menu, then controlled by Menu. If Tab then controlled by the specific tab you're on
	private enum focus {
		MENU, TAB;
	}

	protected Tab[] tabs;
	private focus currentFocus = focus.MENU;
	public int currentTab = 0;
	public boolean downPressed, upPressed, leftPressed, rightPressed;
	private int scrollDelay = 20, lastScroll = 0;
	
	//Display settings
	private int ALPHA = 200;
	private int TOP_OFFSET = 10;
	private int LEFT_OFFSET = 20;
	private Color TEXT_COLOR = new Color(0,0,0), CHAR_INFO_COLOR = new Color(255,255,255);
	private Color BACKGROUND_COLOR = new Color(200,200,200,ALPHA);

	int width = GameConstants.SCREEN_WIDTH;
	int height = GameConstants.SCREEN_HEIGHT;
	int HEALTH_OFFSET_X = 40, HEALTH_OFFSET_Y = 45;
	int TAB_LEFT_OFFSET = 139, TAB_BODY_WIDTH = 786+50;
	int PIXEL_WIDTH, PIXEL_HEIGHT;
	int TAB_HEIGHT, TAB_WIDTH;
	TextField text;
	
	public void pressAndHoldLoop(){
		down(); up(); left(); right();
	}
	
	public void down(){
		if (downPressed&&GameModel.MAIN.tickNum-lastScroll>scrollDelay){
			SFXPlayer.play("tick");
			if (currentFocus == focus.MENU && tabs[currentTab].items.size()>0){
				currentFocus = focus.TAB;
			}
			else{
				tabs[currentTab].nextItem();
			}
			lastScroll = GameModel.MAIN.tickNum;
		}
	}
	public void up(){
		if (upPressed&&GameModel.MAIN.tickNum-lastScroll>scrollDelay){
			if (currentFocus==focus.MENU){
				//Do nothing
			}
			else{
				SFXPlayer.play("tick");
				int allTheWayUp = tabs[currentTab].previousItem();
				if (allTheWayUp == -1) currentFocus = focus.MENU;
			}
			lastScroll = GameModel.MAIN.tickNum;
		}
		
	}
	public void left(){
		if (leftPressed&&GameModel.MAIN.tickNum-lastScroll>scrollDelay){
			SFXPlayer.play("tick");
			if (currentFocus == focus.MENU){
				currentTab--;
				if (currentTab<0) currentTab = tabs.length-1;
				tabs[currentTab].reset();
			}
			else{
				KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
						KeyBindings.DVORAK : KeyBindings.QWERTY;
				if (tabs[currentTab].keyPressed(kb.left)){
					currentTab--;
					if (currentTab<0) currentTab = tabs.length-1;
					tabs[currentTab].reset();
					currentFocus = focus.MENU;
					System.gc();

				}
			}
			lastScroll = GameModel.MAIN.tickNum;
		}
	}
	public void right(){
		if (rightPressed&&GameModel.MAIN.tickNum-lastScroll>scrollDelay){
			SFXPlayer.play("tick");
			if (currentFocus == focus.MENU){
				currentTab++;
				if (currentTab>tabs.length-1) currentTab = 0;
				tabs[currentTab].reset();
			}
			else{
				KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
						KeyBindings.DVORAK : KeyBindings.QWERTY;
				if (tabs[currentTab].keyPressed(kb.right)){
					currentTab++;
					System.gc();
					if (currentTab>tabs.length-1) currentTab = 0;
					tabs[currentTab].reset();
					currentFocus = focus.MENU;
				}
			}
			lastScroll = GameModel.MAIN.tickNum;
		}
	}

	public boolean keyPressed(int key, char c){
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		//To make it so that when you hit enter at the top of the inventory bar, it doesn't use your top consumable..
		if ((key==kb.enter||key==kb.space)&&(currentFocus==focus.MENU)&&!(tabs[currentTab].name=="Willpath")) return false;
		return tabs[currentTab].keyPressed(key, c);
	}
	

	
	/** Reset the menu to have the tabs at the top as the focus, and go to the first tab, for user convenience*/
	public void reset(){
		//Make it so that upon closing and reopening, the menu is reset for user convenience
		tabs[currentTab].reset();
		currentFocus = focus.MENU;
		currentTab = 0;
		System.gc();
	}
	
	public void update(Inventory inventory, HashMap<String, Quest> quests, PlayerStats stats){
		//Update the tabs
		this.inventory = inventory;
		for (Tab t : tabs){
			t.update(inventory, quests, stats);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		int x, y;
		Font prevFont = g.getFont();
		
		PIXEL_WIDTH = MenuLoader.TAB_DOWN_CENTER.getWidth();
		PIXEL_HEIGHT = MenuLoader.TAB_DOWN_CENTER.getHeight();
		TAB_HEIGHT = MenuLoader.TAB_SELECTED.getHeight();
		TAB_WIDTH = 139; //MenuLoader.TAB_SELECTED.getWidth();
		
		pressAndHoldLoop(); // Because threads, yay!
		
		//Setup font and text
		if (text==null) text = new TextField(gc, FontLoader.TITLE_FONT, LEFT_OFFSET, TOP_OFFSET, TAB_WIDTH, TAB_HEIGHT);
		text.setBackgroundColor(null);
		text.setBorderColor(null);

		//Render background
		g.setColor(BACKGROUND_COLOR);		
		g.fillRect(0, 0, width, height);
		g.drawImage(MenuLoader.HEADER_BACK,MenuLoader.HEALTH_BASE.getWidth()/2,TOP_OFFSET);
		g.drawImage(MenuLoader.HEADER_FRONT, 0 ,MenuLoader.HEADER_BACK.getHeight()/2+TOP_OFFSET);
	
	
		// Render Gear Portrait
		GearPortrait.render(gc, g);
		
		
		//Render character information
		Player pl = GameModel.MAIN.player;
		g.setFont(FontLoader.TITLE_FONT);
		g.setColor(TEXT_COLOR);
		g.drawString("Name: " + pl.stats.name, MenuLoader.HEALTH_BASE.getWidth()/2+HEALTH_OFFSET_X*2, MenuLoader.HEADER_BACK.getHeight()*1/3-5);
		g.drawString(GameModel.MAIN.zones.get(pl.zone).map.getName(), 630, 74);
		g.setColor(CHAR_INFO_COLOR);
		g.drawString("HP: " + pl.hp+"/"+pl.maxHP + "    Steam: " + this.inventory.getItemCount("steam"), MenuLoader.HEALTH_BASE.getWidth()/2+HEALTH_OFFSET_X*2, MenuLoader.HEADER_BACK.getHeight()*2/3+5);
		
		g.setColor(RenderUtil.TAB_COLOR);
		y = MenuLoader.HEADER_BACK.getHeight()+TOP_OFFSET+TAB_HEIGHT;
		x = TAB_LEFT_OFFSET;
	
		//Render tab exterior
		int TAB_BODY_HEIGHT = height-y-PIXEL_HEIGHT-TOP_OFFSET*2;
		x= TAB_LEFT_OFFSET;
		RenderUtil.renderPrettyBox(g, x-PIXEL_WIDTH, y, TAB_BODY_WIDTH, TAB_BODY_HEIGHT);

		//Render tabs at the top
		y-=TAB_HEIGHT;
		for (int i = 0; i<tabs.length; i++){
		
			if (currentTab==i) {
				g.drawImage(MenuLoader.TAB_SELECTED, x, y);
				g.drawImage(MenuLoader.TAB_LEFT_CENTER, x, y+TAB_HEIGHT);
				g.drawImage(MenuLoader.TAB_RIGHT_CENTER, x+TAB_WIDTH-PIXEL_WIDTH, y+TAB_HEIGHT);
				g.setColor(RenderUtil.TAB_COLOR);
				g.fillRect(x+PIXEL_WIDTH, y+TAB_HEIGHT, TAB_WIDTH-2*PIXEL_WIDTH, PIXEL_HEIGHT);
			}
			else 
				g.drawImage(MenuLoader.TAB_UNSELECTED, x, y);
			//g.fillRoundRect(x, y, TAB_WIDTH, TAB_HEIGHT,20);
			
			text.setLocation(x+LEFT_OFFSET+10, y+TAB_HEIGHT/4);
			text.setTextColor(TEXT_COLOR);
			text.setText(tabs[i].name);
			text.render(gc,g);

			if ((currentTab==i)&&(currentFocus==focus.MENU)) {
				RenderUtil.renderCursor(gc, g, x+RenderUtil.CURSOR_LEFT_OFFSET, y+TAB_HEIGHT*2/3);
			}

			x += TAB_WIDTH;
		}

		y+=TAB_HEIGHT;
		x= TAB_LEFT_OFFSET;
		
		tabs[currentTab].render(gc, g, x, y+PIXEL_HEIGHT, TAB_BODY_WIDTH, TAB_BODY_HEIGHT-2*PIXEL_HEIGHT, currentFocus==focus.TAB);
		//tabs[currentTab].render(g,0,50,width,height);
				
		g.setFont(prevFont);
	}

	}

