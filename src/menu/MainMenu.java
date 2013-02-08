package menu;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import menu.Tab.Item;
import model.Inventory;
import model.Player;
import model.PlayerStats;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

import audio.SFXPlayer;

import NetworkUtil.ConstantsAndUtil;

import quest.Quest;
import util.GameConstants;
import util.KeyBindings;
import util.Logger;
import util.RenderUtil;
import client.EpicGameContainer;
import client.EpicnessAppShell;
import content.FontLoader;
import content.OverworldLoader;

public class MainMenu extends Tab{

	private Color TEXT_COLOR = new Color(0,0,0);
	private Color MENU_UNSELECTED_COLOR = new Color(255,250,250, 0);
	private Color MENU_SELECTED_COLOR = new Color(180,200,230);
	public boolean serverStarted = false; //Doesn't let the user start the server more than once.
	public Image mainMenuImage;
	private int EXIT = 55;
	TextField text;
	
	class MainMenuItem extends Item{
		private int setModeTo;
		public String internalText;
		public Color backgroundColor;
		
		/** Create a new menu. 
		 * 
		 * @param text Text shown
		 * @param setModeTo What to set the EpicGameContainer mode to
		 * @param color What the background color should be. If null, it's the default.
		 */
		MainMenuItem(String text, int setModeTo, Color color) {
			super(text);
			this.setModeTo = setModeTo;
			this.backgroundColor = color;
		}
		
		MainMenuItem(String text, int setModeTo){
			super(text);
			this.setModeTo = setModeTo;
			this.backgroundColor = null;
			
		}

		String getIP(){
			Enumeration<NetworkInterface> nets;
			String ip = "";
			try {
				nets = NetworkInterface.getNetworkInterfaces();
				for (NetworkInterface netint : Collections.list(nets)){
			        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
					for (InetAddress inetAddress : Collections.list(inetAddresses)){
						if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress())
							ip += inetAddress.getHostAddress()+" ";
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
			}
			return ip;
		}
		
		@Override
		boolean keyPressed(int key) {
			KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
					KeyBindings.DVORAK : KeyBindings.QWERTY;
			
			if (key==kb.space || key==kb.enter) {
				//Don't attempt to start the server if it's already been started (doesn't work if you've restarted the game, though)
				if (serverStarted && this.setModeTo==EpicGameContainer.SERVER_MODE) return false;
				else if (this.setModeTo==EpicGameContainer.SERVER_MODE) {
					this.text = "Server started at " + getIP();
					//TODO: Check to see if this failed, and if it didn't start, don't say it did. Better yet, see if it's already running...
				} 
				else if (this.setModeTo==EpicGameContainer.MULTIPLAYER_MODE) {
					ConstantsAndUtil.serverAddress=this.text;
					this.text = "Waiting for teammate"; 
				}
				else if (this.setModeTo==EXIT)
					EpicnessAppShell.close();
				EpicGameContainer.mode = this.setModeTo;
				return true;
			}
			else if (kb.keys.contains(key)||kb.numkeys.contains(key)||key==kb.period||key==kb.numPeriod){
				if (this.setModeTo==EpicGameContainer.MULTIPLAYER_MODE){ //if this is true, then it's the Enter IP option...
					String toAdd = "";
					if (key==kb.period||key==kb.numPeriod) toAdd = ".";
					else if (kb.keys.contains(key)) toAdd = kb.keys.indexOf(key)+"";
					else if (kb.numkeys.contains(key)) toAdd = kb.numkeys.indexOf(key)+"";
					
					if (this.text=="Enter IP") this.text = toAdd;
					else this.text += toAdd;
				}
			}
			else if ((key==kb.delete)){
				if (this.setModeTo==EpicGameContainer.MULTIPLAYER_MODE){ //if this is true, then it's the Enter IP option...
					if (this.text=="Enter IP") this.text = "";
					else if (this.text.length()==0) return false;
					else this.text = this.text.substring(0,this.text.length()-1);
				}
				
			}
			return false;
		}
		
	}
	public MainMenu() {
		super("Main Menu");
		this.items = new ArrayList<Item>();
		this.items.add(new MainMenuItem("Single Player",EpicGameContainer.SINGLE_PLAYER_MODE));
		this.items.add(new MainMenuItem("View Controls",EpicGameContainer.CONTROLS_MODE));
		this.items.add(new MainMenuItem("Credits",EpicGameContainer.CREDITS_MODE));
		this.items.add(new MainMenuItem("Exit",EXIT));

		try {
			mainMenuImage = new Image("res/art/splash.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public void render(GameContainer gc, Graphics g, boolean getIP){
		if (getIP){
			String oldServerText = "Start Server";
			if (serverStarted) oldServerText = this.items.get(1).text;
			
			if (this.items.size()==4){ //If it hasn't been recreated...a bit hackish.
				this.items = new ArrayList<Item>();
				this.items.add(new MainMenuItem("Single Player",0));
				this.items.add(new MainMenuItem(oldServerText,1));
				this.items.add(new MainMenuItem("Join Multiplayer Game",4));
				this.items.add(new MainMenuItem("127.0.0.1",2,new Color(255,255,255)));
				this.items.add(new MainMenuItem("View Controls",3));
			}
			
		}
		render(gc,g,0,0,GameConstants.SCREEN_WIDTH,GameConstants.SCREEN_HEIGHT, true);
	}
	@Override
	public void render(GameContainer gc, Graphics g, int x0, int y0, int width,
			int height, boolean hasFocus) {

		g.drawImage(mainMenuImage,x0,y0);

		//I moved this here to easily allow the background of the text to be specified by a parameter of MainMenuItem
		width = 1024/3+40;
		height = itemHeight*this.items.size()+titleHeight;
		x0 = 650;
		y0 = 120;

		g.setColor(TEXT_COLOR);
		g.setFont(FontLoader.MAIN_MENU_TITLE_FONT);
		//g.drawString("The Unwritten Saga", 30, 30);
		//renderTitle(gc, g, x0, y0, width, titleHeight, "The Unwritten");
		
		//Render items

		g.setColor(GENERAL_BACKGROUND);	
		
		//Items
		y0 += titleHeight;
		if (text==null) text = new TextField(gc, FontLoader.ITEM_FONT, x0, y0, width-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);
		text.setBorderColor(null);
		text.setTextColor(TEXT_COLOR);	
		
		int indexOffset = currIndex-(int)height/itemHeight;
		if (indexOffset<0) indexOffset = 0;
		int index = indexOffset; 
		
		while ((((index-indexOffset)*itemHeight)<height)&&(index<this.items.size())){
			if ((index == currIndex)&&hasFocus) {
				g.setColor(MENU_SELECTED_COLOR);
				g.fillRect(x0, (index-indexOffset)*itemHeight+y0, RenderUtil.CURSOR_LEFT_OFFSET+RenderUtil.CURSOR_WIDTH, itemHeight);
				
				RenderUtil.renderCursor(gc, g, x0, (int)((index-indexOffset+.5)*itemHeight)+y0);
				if (((MainMenuItem)this.items.get(index)).backgroundColor==null)
					text.setBackgroundColor(MENU_SELECTED_COLOR);
				else text.setBackgroundColor(((MainMenuItem)this.items.get(index)).backgroundColor);
			}
			else {
				g.setColor(MENU_UNSELECTED_COLOR);
				g.fillRect(x0, (index-indexOffset)*itemHeight+y0, RenderUtil.CURSOR_LEFT_OFFSET+RenderUtil.CURSOR_WIDTH, itemHeight);
				if (((MainMenuItem)this.items.get(index)).backgroundColor==null)
					text.setBackgroundColor(MENU_UNSELECTED_COLOR);
				else text.setBackgroundColor(((MainMenuItem)this.items.get(index)).backgroundColor);
			}

			g.setColor(GENERAL_BACKGROUND);
			text.setLocation(x0+RenderUtil.CURSOR_LEFT_OFFSET+RenderUtil.CURSOR_WIDTH, (index-indexOffset)*itemHeight+y0);
			text.setText(this.items.get(index).text);
			text.render(gc, g);
			index++;
			
		}

		
	}

	@Override
	void update(Inventory inventory, HashMap<String, Quest> quests,
			PlayerStats stats) {
	}
	
	@Override
	public boolean keyPressed(int key){
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		SFXPlayer.play("tick");
		if (key==kb.up) previousItem();
		else if (key==kb.down) nextItem();
		else return this.items.get(currIndex).keyPressed(key);
		return false;
			
	}


}
