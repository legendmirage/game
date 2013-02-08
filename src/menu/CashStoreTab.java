package menu;

import graphics.PopupRenderer;
import item.ItemFactory;
import item.ItemType;
import item.use.ItemUse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

import content.FontLoader;

import NetworkUtil.Action;
import client.ActionType;
import client.EpicGameContainer;

import menu.Tab.Item;
import menu.UseableItemTab.InventoryItem;
import model.Inventory;
import model.PlayerStats;


import quest.Quest;
import util.KeyBindings;
import util.Logger;
import util.RenderUtil;

public class CashStoreTab extends Tab{

	class InventoryItem extends Item{
		String instruction, itemName;
		ArrayList<String> ability;
		String num;
		Inventory inventory;
		int slotNum;
		Color selectedColor, unselectedColor;
		
		//For shop, because...yeah
		InventoryItem(String num, String itemName, Inventory inventory){
			this(num,itemName,"",inventory,Tab.SELECTED_ITEM_COLOR, Tab.UNSELECTED_ITEM_COLOR,0);
			this.text += " |  Cost: " + ItemFactory.get(itemName).getCost();
		}
		
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
			return false;
		}
		
	}
	
	Inventory inventory;
	TextField textItems;
	
	public CashStoreTab(Inventory inventory) {
		super("Buy",3);
		this.inventory = inventory;
	}


	@Override
	void update(Inventory inventory, HashMap<String, Quest> quests,
			PlayerStats stats) {
		this.items = new ArrayList<Item>();
		this.inventory = inventory;

        for (Entry<String, ItemType> item : ItemFactory.getAllItems().entrySet()){
        	if (item.getValue().useEffects.size()!=0)
        		this.items.add(new InventoryItem("1", item.getKey(),(Inventory)null));
        }
		
	}
	
	@Override
	public boolean keyPressed(int key, char c){

		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		if (key==kb.right||key==kb.left) return true;
		else if (key==kb.enter||key==kb.space){
			buyItem(((InventoryItem)this.items.get(currIndex)).itemName);
			return false;
		}
		else if (this.items.size()>0 && currIndex<this.items.size()) return this.items.get(currIndex).keyPressed(key);
		return true;
	}
	
	public void buyItem(String name){
		Logger.log("Buying " + name);
		int cost = ItemFactory.get(name).getCost();
		if (this.inventory.getItemCount("steam")>=cost){
			this.inventory.removeItem("steam", cost);
			this.inventory.addItem(name);
		}
		else {
			PopupRenderer.addPopup("Not enough steam");
		}
	}

	@Override
	void render(GameContainer gc, Graphics g, int x0, int y0, int width,
			int height, boolean hasFocus) {
		int tempx = x0+LEFT_OFFSET;
		int tempy = y0+TOP_OFFSET;
		int tempWidth = width-LEFT_OFFSET*2-RenderUtil.PIXEL_WIDTH, tempHeight = height-TOP_OFFSET*2-titleHeight-itemHeight*2;
		RenderUtil.renderPrettyBox(g, tempx-RenderUtil.PIXEL_WIDTH, tempy-RenderUtil.PIXEL_HEIGHT, tempWidth, tempHeight);
		if (textItems==null) textItems = new TextField(gc, FontLoader.ITEM_FONT, tempx, tempy, tempWidth-RenderUtil.PIXEL_WIDTH-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);

		renderTitle(gc,g, tempx,tempy,tempWidth,titleHeight, "Shop Inventory", textItems);
		renderItems(gc,g, tempx,tempy+titleHeight,tempWidth-RenderUtil.PIXEL_WIDTH,tempHeight-titleHeight, hasFocus, textItems);
		
		ArrayList<String> abilitiesToDisplay = new ArrayList<String>();
		String title = "";
		if (currIndex<this.items.size()) {
			title += ((InventoryItem)this.items.get(this.currIndex)).itemName;
			abilitiesToDisplay = ((InventoryItem)items.get(currIndex)).ability;
		}

		if (text==null) text = new TextField(gc, (org.newdawn.slick.Font) FontLoader.ITEM_FONT, x0, y0, width, itemHeight);
		renderTitle(gc, g, x0+LEFT_OFFSET, tempy+tempHeight + RenderUtil.PIXEL_HEIGHT+itemHeight, width, titleHeight, title,textItems);

		int count = 1;
		text.setBackgroundColor(SUBTITLE_BACKGROUND_COLOR);
		text.setTextColor(TEXT_COLOR);
		text.setBorderColor(null);
		for (String ability: abilitiesToDisplay){
			text.setLocation(x0,  tempy+tempHeight + RenderUtil.PIXEL_HEIGHT+count*itemHeight+titleHeight);
			if (ability==null) ability = "No abilities";
			text.setText(ability);
			text.render(gc,g);
			count++;
		}
		
	}
	

}
