package menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import model.Inventory;
import model.PlayerStats;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.TextField;

import quest.Quest;
import util.GameConstants;
import util.Logger;
import util.RenderUtil;
import content.FontLoader;

/** Abstract class that encapsulates the key methods that Menu tabs will need */
public abstract class Tab{
	//A tab is a vertical list of items that do something when you hit enter (or perhaps a button for equipping?)
	//It contains its own display information, also
	
	/** The object that the tab class will step through as the user hits down or up*/
	abstract class Item{
		String text;
		
		Item(String text){
			this.text = text;
		}

		/** keyPressed returns whether or not an action was done, and thus the menu should refresh */
		abstract boolean keyPressed(int key);
		
	}
	
	public class ItemAlphabeticalComparator implements Comparator<Item> {
		@Override
		public int compare(Item o1, Item o2) {
			return o1.text.compareTo(o2.text);
		}
	}
	
	/** The name of the tab*/
	protected String name;
	/** The items displayed by the tab. */
	protected ArrayList<Item> items;
	/** The item the user currently has selected*/
	protected int currIndex = 0;
	protected static int GAP_SIZE = 20;
	
	protected static int ALPHA = 250;
	protected static Color SELECTED_ITEM_COLOR = new Color(220,200,230,ALPHA);
	protected static Color UNSELECTED_ITEM_COLOR = new Color(240,230,250,ALPHA);
	protected static Color UNSELECTABLE_ITEM_COLOR = new Color(180,180,255,ALPHA);
	protected static Color TITLE_BACKGROUND_COLOR = new Color(255,255,255,0);
	protected static Color SUBTITLE_BACKGROUND_COLOR = new Color(120,120,160,ALPHA);
	protected static Color TEXT_COLOR = new Color(0,0,0);
	protected static Color GENERAL_BACKGROUND = new Color(255,255,255,ALPHA);
	
	//Display variables
	/** Height of the standard object displayed in the tab */
	protected int itemHeight = 30;
	protected int titleHeight = 35;
	protected int[] scrollIndexArray;
	protected int LEFT_OFFSET = 5, TOP_OFFSET = 15;
	TextField text, textTitle;
	
	/** Constructor that takes the name of the tab */
	public Tab(String name, int numSelectable){
		this.name = name;
		this.scrollIndexArray = new int[numSelectable];
		this.items = new ArrayList<Item>();
	}
	public Tab(String name){
		this(name,1);
	}
	
	/** Updates the information contained within the menu, based on the actions of the player, called from Player 
	 */
	abstract void update(Inventory inventory, HashMap<String, Quest> quests, PlayerStats stats);
	
	/** Partially resets the tab, as necessary */
	public void reset(){};
	
	/** Key press handler for the tab*/
	public boolean keyPressed(int key){
		return keyPressed(key,'a');
	}
	public boolean keyPressed(int key, char c){
		if (this.items.size()>0 && currIndex<this.items.size()) return this.items.get(currIndex).keyPressed(key);
		else return true;
	}
	/** Tab renderer*/
	abstract void render(GameContainer gc, Graphics g, int x0, int y0, int width, int height, boolean hasFocus);
	
	/** General item iterator; goes to the next item*/
	public int nextItem(){
		if (items.size()<=0) {
			currIndex=0;
			return -1;
		}

		currIndex++;
		if (currIndex>items.size()-1){
			currIndex = items.size()-1;
			return -1;
		}
		return 0;
	}
	/** General item iterator; goes to the previous item*/
	public int previousItem(){
		currIndex--;
		if (currIndex<0){
			currIndex = 0;
			return -1;
		}
		return 0;
	}
	/** Helper method to render the items within the tab  */
	public void renderItems(GameContainer gc, Graphics g, int x0, int y0, int width, int height, boolean hasFocus, TextField text){
		ArrayList<String> toDisplay = new ArrayList<String>();
		for (Item item: this.items){
			toDisplay.add(item.text);
		}
		renderItems(gc,g,x0,y0,width,height, hasFocus, toDisplay,0, text); 
	}

	public int determineOffset(int scrollableIndex, int height, boolean hasFocus, int index){
		int indexOffset = index-(int)(((float)height)/itemHeight)+1;	
		int currScrollOffset = scrollIndexArray[scrollableIndex];
		//check to see if we should scroll up, down, or leave it alone
		if (!hasFocus) 
			indexOffset = currScrollOffset;
		else{
			if (index-currScrollOffset<0) 
				indexOffset = index;
			else if (indexOffset<0 || currScrollOffset>indexOffset) 
				indexOffset = currScrollOffset;
			scrollIndexArray[scrollableIndex] = indexOffset;
		}
		return indexOffset;
	}
	/** Helper method to render the items within the tab  */
	public void renderItems(GameContainer gc, Graphics g, int x0, int y0, int width, int height, boolean hasFocus, ArrayList<String> toDisplay, int scrollableIndex, TextField text){


		g.setColor(GENERAL_BACKGROUND);	
		
		//Items
		//Check to see if the current text field is the same as the old one, and if it isn't, remake it
		/*if (text==null || !(text.getHeight() == itemHeight && text.getX() == x0 && text.getY()==y0 && text.getWidth() == width-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH))
			text = new TextField(gc, FontLoader.ITEM_FONT, x0, y0, width-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);
		*/
		text.setBorderColor(null);
		text.setTextColor(TEXT_COLOR);	
		
		
		int indexOffset= determineOffset(scrollableIndex,height,hasFocus, currIndex); 
		if (indexOffset>toDisplay.size()-1) indexOffset = 0;
		
		int index = indexOffset;
		
		while ((((index-indexOffset+1)*itemHeight)<=(height))&&(index<toDisplay.size())){
			if ((index == currIndex)&&hasFocus) {
				g.setColor(SELECTED_ITEM_COLOR);
				g.fillRect(x0, (index-indexOffset)*itemHeight+y0, RenderUtil.CURSOR_LEFT_OFFSET+RenderUtil.CURSOR_WIDTH, itemHeight);
				
				RenderUtil.renderCursor(gc, g, x0, (int)((index-indexOffset+.5)*itemHeight)+y0);

				text.setBackgroundColor(SELECTED_ITEM_COLOR);
			}
			else {
				g.setColor(UNSELECTED_ITEM_COLOR);
				g.fillRect(x0, (index-indexOffset)*itemHeight+y0, RenderUtil.CURSOR_LEFT_OFFSET+RenderUtil.CURSOR_WIDTH, itemHeight);
				text.setBackgroundColor(UNSELECTED_ITEM_COLOR);
			}

			g.setColor(GENERAL_BACKGROUND);
			if((index-indexOffset)*itemHeight+y0> GameConstants.SCREEN_HEIGHT)
				break;
			text.setLocation(x0+RenderUtil.CURSOR_LEFT_OFFSET+RenderUtil.CURSOR_WIDTH, (index-indexOffset)*itemHeight+y0);
			text.setText(toDisplay.get(index));
			text.render(gc, g);
			index++;
			
		}
		index--;
		//if there are more items than space to display it, show a page down arrow at the bottom
		if (toDisplay.size()-indexOffset>(int)(height/itemHeight)){
			RenderUtil.renderScrollDownIndicator(gc, g, x0+width-RenderUtil.SCROLL_INDICATOR*2, (int)((index-indexOffset+.5)*itemHeight+y0),false);
		}
		//If there are items above the display space, show a page up arrow at the top
		if (indexOffset!=0){
			RenderUtil.renderScrollDownIndicator(gc, g, x0+width-RenderUtil.SCROLL_INDICATOR*2, y0,true);
		}

	}

	public void renderTitle(GameContainer gc, Graphics g, int x0, int y0, int width, int height, String title, TextField textTitle){
		renderTitle(gc,g,x0,y0,width,height,title,false, textTitle);
	}
	public void renderTitle(GameContainer gc, Graphics g, int x0, int y0, int width, int height, String title, boolean isSubtitle, TextField textTitle){


		g.setColor(new Color(255,255,255));	
		//Title
		//If this title is the same as the last one, don't remake it
		/*if (textTitle==null || !(textTitle.getX() == x0 && textTitle.getY() == y0 && textTitle.getHeight() == height && textTitle.getWidth() == width))
			textTitle = new TextField(gc, FontLoader.TITLE_FONT, x0, y0, width, height);
	*/
		textTitle.setTextColor(TEXT_COLOR);
		if (!isSubtitle) textTitle.setBackgroundColor(TITLE_BACKGROUND_COLOR);
		else textTitle.setBackgroundColor(SUBTITLE_BACKGROUND_COLOR);
		textTitle.setLocation(x0, y0);
		textTitle.setBorderColor(null);
		textTitle.setText(title);
		textTitle.render(gc, g);
	}
	
}
