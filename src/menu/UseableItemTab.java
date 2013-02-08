package menu;

import item.ItemFactory;
import item.component.ItemComponent;
import item.use.ItemUse;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.TextField;

import content.FontLoader;

import NetworkUtil.Action;
import client.ActionType;
import client.EpicGameContainer;
import entrypoint.Tutorial;

import quest.Quest;
import util.KeyBindings;
import util.Logger;
import util.RenderUtil;

import menu.InventoryTab.InventoryItem;
import menu.Tab.Item;
import model.Inventory;
import model.PlayerStats;

public class UseableItemTab extends Tab{
	class InventoryItem extends Item{
		String instruction, itemName;
		ArrayList<String> ability;
		String num;
		Inventory inventory;
		int slotNum;
		Color selectedColor, unselectedColor;

		
		//slotNum of -1 indicates you're in the itemsInInventory
		InventoryItem(String num, String itemName, String instruction, Inventory inventory, Color colorSel, Color colorUnsel, int slotNum, String moreInfo){
			this(num, itemName, instruction, inventory, colorSel, colorUnsel, slotNum);
			this.text += "  -  "+moreInfo;
		}
		InventoryItem(String num, String itemName, String instruction, Inventory inventory, Color colorSel, Color colorUnsel, int slotNum) {
			super(num+"   "+itemName);
			this.num = num;
			this.itemName = itemName;
			this.instruction = instruction;
			this.inventory = inventory;
			this.selectedColor = colorSel;
			this.unselectedColor = colorUnsel;
			this.slotNum = slotNum;

			ability = new ArrayList<String>();
			if (itemName!="" && ItemFactory.get(itemName).useEffects.size()!=0) {
				for (ItemUse effect: ItemFactory.get(itemName).useEffects) ability.add(effect.getEffectName());
			}
		}

		@Override
		boolean keyPressed(int key) {
			Action action = new Action(ActionType.DO_NOTHING,"");
			KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
					KeyBindings.DVORAK : KeyBindings.QWERTY;

			if (key==kb.enter || key==kb.space){
				if (itemName.equals("health potion")) Tutorial.setStepDone(13);
				if (currIndex>0 && inventory.getItemCount(itemName)==1) currIndex--;
				action = new Action(ActionType.USE, itemName);
				
			}

			
			EpicGameContainer.MAIN.protocol.sendAction(action);
			return false;
			
		}
		
	}
	
	Color useableColorUnselected = new Color(220,220,250,ALPHA);
	Color useableColorSelected = new Color(200,200,220,ALPHA);
	
	Inventory inventory;
	TextField textField, textItems;
	
	UseableItemTab(Inventory inventory){
		super("Items",3);
		this.inventory = inventory;
		this.currIndex = 0;
	}
	
	void populateItems() {
		this.items = new ArrayList<Item>();
		String instruction;
		//Three for loops to make it sort useable items above active items above passive items. Could be done way efficiently with a linked list. But fuckit.
		for (String itemName: this.inventory.getAllItems()){
			if (ItemFactory.get(itemName).useEffects.size()>0) {
				instruction = "Useable Item. Press space to use.";
				items.add(new InventoryItem(this.inventory.getItemCount(itemName)+"", 
						itemName, instruction, this.inventory,useableColorSelected, useableColorUnselected,-1));
			}
			else instruction = ""; 
		}
	}

	@Override
	void render(GameContainer gc, Graphics g, int x0, int y0, int width,
			int height, boolean hasFocus) {
		int tempx = x0+LEFT_OFFSET;
		int tempy = y0+TOP_OFFSET;
		int tempWidth = width-LEFT_OFFSET*2-RenderUtil.PIXEL_WIDTH, tempHeight = height-TOP_OFFSET*2-titleHeight-itemHeight*2;
		RenderUtil.renderPrettyBox(g, tempx-RenderUtil.PIXEL_WIDTH, tempy-RenderUtil.PIXEL_HEIGHT, tempWidth, tempHeight);
		
		if (textItems == null) textItems = new TextField(gc, FontLoader.ITEM_FONT, x0+LEFT_OFFSET, y0+titleHeight+TOP_OFFSET, tempWidth-RenderUtil.PIXEL_WIDTH-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);

		renderTitle(gc,g, tempx,tempy,tempWidth,titleHeight, "Items Possessed", textItems);
		renderItems(gc,g, tempx,tempy+titleHeight,tempWidth-RenderUtil.PIXEL_WIDTH,tempHeight-titleHeight, hasFocus, textItems);
		
		ArrayList<String> abilitiesToDisplay = new ArrayList<String>();
		String title = "";
		if (currIndex<this.items.size()) {
			title += ((InventoryItem)this.items.get(this.currIndex)).itemName;
			abilitiesToDisplay = ((InventoryItem)items.get(currIndex)).ability;
		}

		if (textField==null) textField = new TextField(gc, (org.newdawn.slick.Font) FontLoader.ITEM_FONT, x0, y0, width, itemHeight);
		renderTitle(gc, g, x0+LEFT_OFFSET, tempy+tempHeight + RenderUtil.PIXEL_HEIGHT+itemHeight, width, titleHeight, title, textField);

		int count = 1;
		textField.setBackgroundColor(SUBTITLE_BACKGROUND_COLOR);
		textField.setTextColor(TEXT_COLOR);
		textField.setBorderColor(null);
		for (String ability: abilitiesToDisplay){
			textField.setLocation(x0,  tempy+tempHeight + RenderUtil.PIXEL_HEIGHT+count*itemHeight+titleHeight);
			if (ability==null) ability = "No abilities";
			textField.setText(ability);
			textField.render(gc,g);
			count++;
		}
		
	}

	@Override
	void update(Inventory inventory, HashMap<String, Quest> quests,
			PlayerStats stats) {
		this.inventory = inventory;
		populateItems();
	}
	
	@Override
	public boolean keyPressed(int key){
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		if (key==kb.right||key==kb.left) return true;
		if (this.items.size()>0) return this.items.get(currIndex).keyPressed(key);
		return false;
	}
}
