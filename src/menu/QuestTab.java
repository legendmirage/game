package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.Inventory;
import model.PlayerStats;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.TextField;

import client.EpicGameContainer;

import quest.Quest;
import quest.component.QuestComponent;
import util.KeyBindings;
import util.RenderUtil;
import content.FontLoader;

public class QuestTab extends Tab {
	
	class QuestItem extends Item{
		ArrayList<String> tasks;
		QuestItem(String text, ArrayList<String> subitems) {
			super(text);
			this.tasks = subitems;
			// TODO Auto-generated constructor stub
		}

		@Override
		boolean keyPressed(int key) {
			KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
					KeyBindings.DVORAK : KeyBindings.QWERTY;
			if (key==kb.left || key==kb.right) return true;
			return false;
		}
		
	}

	private HashMap<String,Quest> quests;
	TextField text, textItems;
	
	public QuestTab(HashMap<String, Quest> quests) {
		super("Quests");
		this.quests = quests;
	}

	@Override
	void render(GameContainer gc, Graphics g, int x0, int y0, int width,
			int height, boolean hasFocus) {

		//Left column

		int tempHeight = height-2*RenderUtil.PIXEL_HEIGHT;
		int tempWidth = 5*width/12-2*RenderUtil.PIXEL_WIDTH;
		RenderUtil.renderPrettyBox(g, x0+LEFT_OFFSET-RenderUtil.PIXEL_WIDTH, y0+TOP_OFFSET-RenderUtil.PIXEL_HEIGHT, tempWidth, tempHeight);

		if (textItems == null) textItems = new TextField(gc, FontLoader.ITEM_FONT, x0+LEFT_OFFSET, y0+titleHeight+TOP_OFFSET, tempWidth-RenderUtil.PIXEL_WIDTH-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);

		renderTitle(gc,g,x0+LEFT_OFFSET,y0+TOP_OFFSET,tempWidth,titleHeight, "Current Quests", textItems);
		renderItems(gc,g, x0+LEFT_OFFSET,y0+TOP_OFFSET+titleHeight,tempWidth-RenderUtil.PIXEL_WIDTH,tempHeight-titleHeight, hasFocus, textItems);
		
		//Right column: tasks for the currently selected quest
		String questName ="";
		if (this.items.size()>0) questName = this.items.get(currIndex).text;
		
		if (text==null) text = new TextField(gc, FontLoader.ITEM_FONT, x0, y0+titleHeight, 7*width/12-GAP_SIZE-LEFT_OFFSET, itemHeight);
		renderTitle(gc, g, x0+5*width/12+GAP_SIZE, y0, 7*width/12-GAP_SIZE-LEFT_OFFSET, titleHeight, "Quest: "+questName,text);
		renderTitle(gc, g, x0+5*width/12+GAP_SIZE, y0+titleHeight, 7*width/12-GAP_SIZE-LEFT_OFFSET, titleHeight, "Tasks: ", true, text);
		
		text.setTextColor(TEXT_COLOR);
		text.setBackgroundColor(UNSELECTABLE_ITEM_COLOR);
		
		int itemCount = 0;
		if(currIndex>=0 && currIndex<items.size()) {
			for (String t : ((QuestItem)items.get(currIndex)).tasks){
				text.setLocation(x0+5*width/12+GAP_SIZE, y0+titleHeight*2+itemHeight*itemCount);
				text.setText(t);
				text.render(gc,g);
				itemCount++;
			}

		}
		
	}

	@Override
	void update(Inventory inventory, HashMap<String, Quest> quests,
			PlayerStats stats) {
		this.items = new ArrayList<Item>();
		this.quests = quests;
		ArrayList<String> subitems;
		
		for (Entry<String, Quest> quest: this.quests.entrySet()){
			subitems = new ArrayList<String>();
			for (QuestComponent component: quest.getValue().componentsObtained){
				subitems.add(component.toString()+": DONE");
			}
			for (QuestComponent component: quest.getValue().componentsLeft){
				subitems.add(component.toString());
			}
			this.items.add(new QuestItem(quest.getKey(),subitems));
		}
		
	}

}
