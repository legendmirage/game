package menu;

import item.ItemFactory;
import item.component.ItemComponent;
import item.use.ItemUse;

import java.util.ArrayList;
import java.util.HashMap;

import model.GameModel;
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
import NetworkUtil.Action;
import client.ActionType;
import client.EpicGameContainer;
import content.FontLoader;
import entrypoint.Tutorial;


public class InventoryTab extends Tab{
	Color activeColorUnselected = new Color(250,220,220,ALPHA);
	Color activeColorSelected = new Color(220,200,200,ALPHA);
	Color passiveColorUnselected = new Color(220,250,220,ALPHA);
	Color passiveColorSelected = new Color(200,220,200,ALPHA);
	Color useableColorUnselected = new Color(220,220,250,ALPHA);
	Color useableColorSelected = new Color(200,200,220,ALPHA);
	
	ArrayList<Item> activeEquips, passiveEquips, itemsInInventory;
	int currItemIndex, currEquipIndex;
	boolean equipsSelectedToEquip, equipsSelectedToRemove;
	int lastEquipMenu; //0 is active, 1 is passive. Used after updating to determine where the cursor should be.
	
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
			if (itemName!="" && ItemFactory.get(itemName).ability!=null) 
				this.ability.add(ItemFactory.get(itemName).ability.type.getAbilityName());
			else if (itemName!="" && ItemFactory.get(itemName).useEffects.size()!=0) {
				for (ItemUse effect: ItemFactory.get(itemName).useEffects) ability.add(effect.getEffectName());
			}
			else if (itemName!="" && ItemFactory.get(itemName).components.size()!=0){
				for (ItemComponent component: ItemFactory.get(itemName).components)
					ability.add(component.getEffectName());
			}
		}

		@Override
		boolean keyPressed(int key) {
			Action action = new Action(ActionType.DO_NOTHING,"");
			KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
					KeyBindings.DVORAK : KeyBindings.QWERTY;
			//Active
			if (key==kb.ability0 && this.inventory.canSetActiveItem(itemName, 0)) {
				action = new Action(ActionType.EQUIP_ACTIVE,itemName+"0");
			}
			else if (key==kb.ability1 && this.inventory.canSetActiveItem(itemName, 1)) {
				action = new Action(ActionType.EQUIP_ACTIVE,itemName+"1");
			}
			else if (key==kb.ability2 && this.inventory.canSetActiveItem(itemName, 2)) {
				action = new Action(ActionType.EQUIP_ACTIVE,itemName+"2");
			}
			//Passive
			else if (key==kb.passive0 && this.inventory.canSetPassiveItem(itemName, 0)) {
				action = new Action(ActionType.EQUIP_PASSIVE,itemName+"0");
			}
			else if (key==kb.passive1 && this.inventory.canSetPassiveItem(itemName, 1)) {
				action = new Action(ActionType.EQUIP_PASSIVE,itemName+"1");
			}
			else if (key==kb.passive2 && this.inventory.canSetPassiveItem(itemName, 2)) {
				action = new Action(ActionType.EQUIP_PASSIVE,itemName+"2");
			} 
			else if (key==kb.space||key==kb.enter){
				if (equipsSelectedToEquip){
				
					
					EpicGameContainer.MAIN.protocol.sendAction(action);
					Logger.log("Attempting to use: " + ((InventoryItem)itemsInInventory.get(currItemIndex)).itemName + " in slot: "+this.slotNum);
					action = new Action(ActionType.EQUIP_ACTIVE,((InventoryItem)itemsInInventory.get(currItemIndex)).itemName+this.slotNum);
					EpicGameContainer.MAIN.protocol.sendAction(action);
					action = new Action(ActionType.EQUIP_PASSIVE,((InventoryItem)itemsInInventory.get(currItemIndex)).itemName+this.slotNum);
					equipsSelectedToEquip = false;
					currIndex = currItemIndex;
				}
				else if (equipsSelectedToRemove){
					if (this.inventory.canSetActiveItem(itemName, 0)){
						action = new Action(ActionType.EQUIP_ACTIVE,this.slotNum+"");
						lastEquipMenu = 0;
					}
					else if (this.inventory.canSetPassiveItem(itemName, 0)){
						action = new Action(ActionType.EQUIP_PASSIVE,this.slotNum+"");
						lastEquipMenu = 1;
					}
				}
				else {
					action = new Action(ActionType.USE, itemName);
					if (this.inventory.canSetActiveItem(((InventoryItem)itemsInInventory.get(currItemIndex)).itemName, 0)) {
						if (itemName.equals("Flawless Quartz of Lightning".toLowerCase())) {
							Tutorial.setStepDone(11);
						}
						items = activeEquips;
						lastEquipMenu = 0;
						currIndex = 0;
						equipsSelectedToEquip = true;
					}
					else if (this.inventory.canSetPassiveItem(((InventoryItem)itemsInInventory.get(currItemIndex)).itemName, 0)) {
						items = passiveEquips;
						lastEquipMenu = 1;
						currIndex = 0;
						equipsSelectedToEquip = true;
					}
					
				} 
			}
			
			EpicGameContainer.MAIN.protocol.sendAction(action);
			return false;
			
		}
		
	}

	Inventory inventory;
	TextField text, textInv, textActive, textPassive, textEquipTitle;
	
	InventoryTab(Inventory inventory){
		super("Equips",3);
		this.inventory = inventory;
		this.currIndex = 0;
		this.itemsInInventory = new ArrayList<Item>();
		this.activeEquips = new ArrayList<Item>();
		this.passiveEquips = new ArrayList<Item>();
		this.currItemIndex = 0;
		this.currEquipIndex = 0;
		this.lastEquipMenu = 0;

		this.items = this.itemsInInventory;
		this.currIndex = this.currItemIndex;
		this.equipsSelectedToEquip = false;
		this.equipsSelectedToRemove = false;
	}
	
	void populateItems() {
		this.itemsInInventory = new ArrayList<Item>();
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		int slotNum;
		String instruction, description="";
		//Three for loops to make it sort useable items above active items above passive items. Could be done way efficiently with a linked list. But fuckit.

		for (String itemName: this.inventory.getAllItems()){	
			if (this.inventory.canSetActiveItem(itemName, 0)) {
				instruction = "Active Item. Press space to equip.";
				slotNum = getSlot(itemName);
				if (slotNum==-1){
					itemsInInventory.add(new InventoryItem(this.inventory.getItemCount(itemName)+"", 
						itemName, instruction, this.inventory,activeColorSelected, activeColorUnselected,-1));
				}
				else {
					if (slotNum==0) description = Input.getKeyName(kb.ability0)+" Equip";
					else if (slotNum==1) description = Input.getKeyName(kb.ability1)+" Equip";
					else if (slotNum==2) description = Input.getKeyName(kb.ability2) +" Equip";
					itemsInInventory.add(new InventoryItem(this.inventory.getItemCount(itemName)+"", 
							itemName, instruction, this.inventory,activeColorSelected, activeColorUnselected,-1, description));
				}
			}
		}
		for (String itemName: this.inventory.getAllItems()){
			if (this.inventory.canSetPassiveItem(itemName, 0)) {
				instruction = "Passive Item. Press space to equip.";
				slotNum = getSlot(itemName);
				if (slotNum==-1){
					itemsInInventory.add(new InventoryItem(this.inventory.getItemCount(itemName)+"", 
							itemName, instruction, this.inventory,passiveColorSelected, passiveColorUnselected,-1));
				}
				else {
					if (slotNum==0) description = "Passive Equip";
					else if (slotNum==1) description = "Passive Equip";
					else if (slotNum==2) description = "Passive Equip";
					itemsInInventory.add(new InventoryItem(this.inventory.getItemCount(itemName)+"", 
							itemName, instruction, this.inventory,passiveColorSelected, passiveColorUnselected,-1, description));
				}
			}
			else instruction = ""; 
		}
		
	}
	//returns the slot an item is equipped to
	private int getSlot(String itemName){
		for (int i=0; i<GameConstants.MAX_ACTIVE_ITEMS; i++){
			Logger.log(this.inventory.getActiveItem(i));
			if (this.inventory.getActiveItem(i)!=null && this.inventory.getActiveItem(i).equals(itemName)) return i;
		}
		for (int i=0; i<GameConstants.MAX_PASSIVE_ITEMS; i++){
			if (this.inventory.getPassiveItem(i)!=null && this.inventory.getPassiveItem(i).equals(itemName)) return i;
		}
		return -1;
	}
	
	void populateEquips(){

		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
	
		String textToDisplay, button="";
		this.activeEquips = new ArrayList<Item>();
		this.passiveEquips = new ArrayList<Item>();
		//Active items
		for (int i=0; i<GameConstants.MAX_ACTIVE_ITEMS; i++) {
			String item = this.inventory.getActiveItem(i);
			textToDisplay = item==null ? "" : item;
			
			switch(i){
				case 0: 
					button = Input.getKeyName(kb.ability0)+" | ";
					break;
				case 1:
					button = Input.getKeyName(kb.ability1)+" | ";
					break;
				case 2:
					button = Input.getKeyName(kb.ability2)+" | ";
					break;
			}
			this.activeEquips.add(new InventoryItem(button,textToDisplay,"",inventory,activeColorSelected, activeColorUnselected,i));
		}
		

		for (int i=0; i<GameConstants.MAX_PASSIVE_ITEMS; i++) {
			String item = this.inventory.getPassiveItem(i);
			textToDisplay = item==null ? "" : item;
			switch(i){
				case 0: 
					button = Input.getKeyName(kb.passive0)+" | ";
					break;
				case 1:
					button = Input.getKeyName(kb.passive1)+" | ";
					break;
				case 2:
					button = Input.getKeyName(kb.passive2)+" | ";
					break;
			}
			this.passiveEquips.add(new InventoryItem(button,textToDisplay,"",inventory,passiveColorSelected, passiveColorUnselected,i));
		}
	}
	
	@Override
	/** Call to update the inventory, quests, and stats whenever a change is made while the menu is up */
	void update(Inventory inventory, HashMap<String, Quest> quests, PlayerStats stats) {
		this.inventory = inventory;
		populateItems();
		populateEquips();
		
		if (!this.equipsSelectedToRemove && !this.equipsSelectedToEquip) this.items = this.itemsInInventory;
		else if (this.equipsSelectedToEquip){
			if (this.inventory.canSetActiveItem(((InventoryItem)this.itemsInInventory.get(currItemIndex)).itemName, 0)) this.items = this.activeEquips;
			else if (this.inventory.canSetPassiveItem(((InventoryItem)this.itemsInInventory.get(currItemIndex)).itemName, 0)) this.items = this.passiveEquips;
		}
		else if (this.equipsSelectedToRemove){
			if (this.lastEquipMenu == 0) this.items = this.activeEquips;
			else this.items = this.passiveEquips;
		}
		
	}
	@Override
	public void reset(){
		this.equipsSelectedToRemove = false;
		this.equipsSelectedToEquip = false;
		this.currIndex = this.currItemIndex;
		if (this.currIndex>=this.items.size() && this.items.size()>0) this.currIndex = this.items.size()-1;
		this.items = this.itemsInInventory;
		this.currEquipIndex = 0;
	}
	@Override
	/** Key press handler for the tab*/
	public boolean keyPressed(int key){
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		
		if (key==kb.left){
			if (!this.equipsSelectedToEquip && !this.equipsSelectedToRemove) return true; 
			this.equipsSelectedToRemove = false;
			this.equipsSelectedToEquip = false;
			this.items = this.itemsInInventory;
			this.currIndex = this.currItemIndex;
			
		} else if (key==kb.right){
			if (!this.equipsSelectedToEquip && !this.equipsSelectedToRemove){
					this.currIndex = 0;
					this.items = this.activeEquips;
					this.lastEquipMenu = 0;
					this.equipsSelectedToRemove = true;
				
			} else if (this.equipsSelectedToRemove) return true;
		}
		 else if (this.items.size()>0) return this.items.get(currIndex).keyPressed(key);

		return false;
	}

	/** Inventory-specific item iterator; goes to the next item*/
	public int nextItem(){
		if (items.size()<=0) {
			currIndex=0;
			return -1;
		}

		currIndex++;
		if (currIndex>items.size()-1){
			currIndex = items.size()-1;
			if (this.equipsSelectedToRemove && this.items == this.activeEquips) {
				this.items = this.passiveEquips;
				this.lastEquipMenu = 1;
				this.currIndex = 0;
			}
			return -1;
		}
		return 0;
	}
	
	@Override
	/** Inventory-specific item iterator; goes to the previous item*/
	public int previousItem(){
		currIndex--;
		if (currIndex<0){
			currIndex = 0;

			if (this.equipsSelectedToRemove && this.items == this.passiveEquips) {
				this.items = this.activeEquips;
				this.lastEquipMenu = 0;
				this.currIndex = this.activeEquips.size()-1;
			}
			if (!this.equipsSelectedToRemove && !this.equipsSelectedToEquip) return -1;
		}
		return 0;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g, int x0, int y0, int width, int height, boolean hasFocus) {
		if (text==null) text = new TextField(gc, (org.newdawn.slick.Font) FontLoader.ITEM_FONT, x0, y0, width/2-GAP_SIZE/2, itemHeight);
		int count; //item counter to make displaying properly easier
		//To display the keybindings
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		
		if (!this.equipsSelectedToRemove && !this.equipsSelectedToEquip) 
			currItemIndex = currIndex;
		else currEquipIndex = currIndex;
		
		//Items possessed rendering: constant determination
		String itemName = "";
		if (this.itemsInInventory.size()>0){
			itemName = ((InventoryItem)this.itemsInInventory.get(currItemIndex)).itemName;
		}
		int abilityDescriptionHeight=2;
		/*
		if (itemName!="" && ItemFactory.get(itemName)!=null) 
			abilityDescriptionHeight = Math.max(Math.max(ItemFactory.get(itemName).components.size(), ItemFactory.get(itemName).useEffects.size()),1);
		*/
		int itemSetHeight = height-titleHeight-itemHeight*(abilityDescriptionHeight+3)-RenderUtil.PIXEL_HEIGHT;
		int tempWidth = width/2-GAP_SIZE/2-LEFT_OFFSET, tempHeight;
		int tempx = x0+LEFT_OFFSET, tempy = y0+TOP_OFFSET;
		
		if (this.itemsInInventory.size()*itemHeight>itemSetHeight) {
			tempHeight = itemSetHeight+titleHeight;
		}
		else {
			tempHeight = this.itemsInInventory.size()*itemHeight+titleHeight;
		}
		int bottomY = y0+tempHeight+RenderUtil.PIXEL_HEIGHT; //Adding the Pixel height is a slightly hackish fix
		
		//Items possessed rendering: actual rendering
		RenderUtil.renderPrettyBox(g, tempx-RenderUtil.PIXEL_WIDTH, tempy-RenderUtil.PIXEL_HEIGHT, tempWidth, tempHeight);
		if (textInv == null) {
			textInv = new TextField(gc, FontLoader.ITEM_FONT, tempx, tempy+titleHeight, width/2-GAP_SIZE/2-RenderUtil.PIXEL_WIDTH-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);
	
			Logger.log("Remaking textInv");
			}
		renderTitle(gc,g, tempx,tempy,tempWidth,titleHeight, "Items Possessed", textInv);
		renderItems(gc,g, tempx,tempy+titleHeight,width/2-GAP_SIZE/2-RenderUtil.PIXEL_WIDTH,itemSetHeight, hasFocus&&(!this.equipsSelectedToRemove&&!this.equipsSelectedToEquip), this.itemsInInventory, currItemIndex, this.itemsInInventory.size(),false,this.equipsSelectedToEquip,0, textInv);
		
		
		//Item description rendering
		
		//TODO: Add image, other info, whatever we want?
		text.setTextColor(TEXT_COLOR);
		text.setBackgroundColor(UNSELECTABLE_ITEM_COLOR);

		//Ability text
		String title = "";
		if (currItemIndex<this.itemsInInventory.size() && !this.equipsSelectedToEquip && !this.equipsSelectedToRemove) 
			title += ((InventoryItem)this.itemsInInventory.get(this.currItemIndex)).itemName;
		else if (this.equipsSelectedToEquip || this.equipsSelectedToRemove){
			if (this.lastEquipMenu==0)
				title += ((InventoryItem)this.activeEquips.get(this.currEquipIndex)).itemName;
			else 
				title += ((InventoryItem)this.passiveEquips.get(this.currEquipIndex)).itemName;
		}
		renderTitle(gc, g, x0, bottomY+itemHeight, width/2-GAP_SIZE/2, titleHeight, title, text);

		ArrayList<String> abilitiesToDisplay = new ArrayList<String>();
		if (!this.equipsSelectedToRemove){
			if (this.currItemIndex<this.itemsInInventory.size()) {
				abilitiesToDisplay = ((InventoryItem)itemsInInventory.get(currItemIndex)).ability;
			}
		} else{
			if (this.lastEquipMenu==0){
				if (this.activeEquips.get(currIndex)!=null) 
					abilitiesToDisplay = ((InventoryItem)this.activeEquips.get(currIndex)).ability;
			}
			else if (this.passiveEquips.get(currIndex)!=null)
				abilitiesToDisplay = ((InventoryItem)this.passiveEquips.get(currIndex)).ability;
			
		}
		
		count = 1;
		text.setBackgroundColor(SUBTITLE_BACKGROUND_COLOR);
		text.setBorderColor(null);
		for (String ability: abilitiesToDisplay){
			text.setLocation(x0,  bottomY + titleHeight+itemHeight*count);
			if (ability==null) ability = "No abilities";
			text.setText(ability);
			text.render(gc,g);
			count++;
		}
		
		//Instruction text
		text.setLocation(x0,  bottomY+titleHeight+itemHeight*count);
		if (this.equipsSelectedToRemove) text.setText("Press space to unequip.");
		else if (currItemIndex<this.items.size()) 
			text.setText(((InventoryItem)items.get(currItemIndex)).instruction);
		text.render(gc,g);


		//Right column
		
		tempWidth = width/2-GAP_SIZE/2-LEFT_OFFSET-RenderUtil.PIXEL_WIDTH*2;
		tempHeight = itemHeight*(GameConstants.MAX_ACTIVE_ITEMS+GameConstants.MAX_PASSIVE_ITEMS+1)+titleHeight*2;
		int tempX = x0+width/2+GAP_SIZE/2-3;
		int tempY = y0-RenderUtil.PIXEL_HEIGHT+TOP_OFFSET;
		RenderUtil.renderPrettyBox(g, tempX, tempY, tempWidth, tempHeight);
		
		tempX += RenderUtil.PIXEL_WIDTH;
		tempY += RenderUtil.PIXEL_HEIGHT;
		tempWidth -= RenderUtil.PIXEL_WIDTH;
		renderTitle(gc,g, tempX,tempY,tempWidth,tempHeight, "Equips", text);
		tempY += titleHeight;
		text.setBackgroundColor(UNSELECTABLE_ITEM_COLOR);
				

		if (textEquipTitle==null) textEquipTitle = new TextField(gc, FontLoader.ITEM_FONT, tempX, tempY, tempWidth, itemHeight);
		//Active items equipped
		if (textActive == null) {
			textActive = new TextField(gc, FontLoader.ITEM_FONT, tempX, tempY, tempWidth-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);
			Logger.log("Remaking textActive");
		}
		renderTitle(gc,g, tempX,tempY,tempWidth,titleHeight, "Active Items", true, textEquipTitle);
		tempY += titleHeight;
		renderItems(gc,g, tempX, tempY,tempWidth, itemHeight*GameConstants.MAX_ACTIVE_ITEMS, this.items==this.activeEquips, this.activeEquips, currEquipIndex, GameConstants.MAX_ACTIVE_ITEMS, equipsSelectedToEquip&&this.items ==this.activeEquips,false,1, textActive);
		tempY += GameConstants.MAX_ACTIVE_ITEMS*itemHeight;

		
		//Passive items equipped
		if (textPassive == null) {
			textPassive = new TextField(gc, FontLoader.ITEM_FONT, tempX, tempY, tempWidth-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);
			Logger.log("Remaking textPassive");
		}
		renderTitle(gc,g, tempX,tempY,tempWidth,titleHeight, "Passive Items", true, textEquipTitle);
		tempY += titleHeight;
		renderItems(gc,g, tempX, tempY, tempWidth, itemHeight*GameConstants.MAX_PASSIVE_ITEMS, this.items==this.passiveEquips, this.passiveEquips, currEquipIndex, GameConstants.MAX_PASSIVE_ITEMS, equipsSelectedToEquip&&this.items==this.passiveEquips,false,2, textPassive);

		
		
	}


	/** Helper method to render the items within the tab  */
	public void renderItems(GameContainer gc, Graphics g, int x0, int y0, int width, int height, boolean hasFocus, ArrayList<Item> itemsToDisplay, int currentIndex, int itemLength, boolean flash, boolean showSelectedRegardlessOfFocus, int scrollableIndex, TextField textItems){

		
		g.setColor(GENERAL_BACKGROUND);	
		
		//Items
		//textItems = new TextField(gc, FontLoader.ITEM_FONT, x0, y0, width-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);
		textItems.setBorderColor(null);
		textItems.setTextColor(TEXT_COLOR);	


		int indexOffset= determineOffset(scrollableIndex,height,hasFocus, currIndex); 
		if (indexOffset>itemLength-1) indexOffset = 0;
		int index = indexOffset;
		
		while ((((index-indexOffset+1)*itemHeight)<=(height))&&(index<itemLength)){
			if ((index == currentIndex)&&(showSelectedRegardlessOfFocus||hasFocus)) {
				g.setColor(((InventoryItem)itemsToDisplay.get(index)).selectedColor);
				g.fillRect(x0, (index-indexOffset)*itemHeight+y0, RenderUtil.CURSOR_LEFT_OFFSET+RenderUtil.CURSOR_WIDTH, itemHeight);
				
				RenderUtil.renderCursor(gc, g, x0, (int)((index-indexOffset+.5)*itemHeight)+y0, flash);

				textItems.setBackgroundColor(((InventoryItem)itemsToDisplay.get(index)).selectedColor);
			}
			else {
				g.setColor(((InventoryItem)itemsToDisplay.get(index)).unselectedColor);
				g.fillRect(x0, (index-indexOffset)*itemHeight+y0, RenderUtil.CURSOR_LEFT_OFFSET+RenderUtil.CURSOR_WIDTH, itemHeight);
				textItems.setBackgroundColor(((InventoryItem)itemsToDisplay.get(index)).unselectedColor);;
			}

			g.setColor(GENERAL_BACKGROUND);
			textItems.setLocation(x0+RenderUtil.CURSOR_LEFT_OFFSET+RenderUtil.CURSOR_WIDTH, (index-indexOffset)*itemHeight+y0);
			textItems.setText(itemsToDisplay.get(index).text);
			textItems.render(gc, g);
			index++;
			
		}
		if (itemsToDisplay.size()-1-indexOffset>(int)(height/itemHeight)){
			index--;
			RenderUtil.renderScrollDownIndicator(gc, g, x0+width-RenderUtil.SCROLL_INDICATOR*2, (int)((index-indexOffset+.5)*itemHeight+y0),false);
		}
		//If there are items above the display space, show a page up arrow at the top
		if (indexOffset!=0){
			RenderUtil.renderScrollDownIndicator(gc, g, x0+width-RenderUtil.SCROLL_INDICATOR*2, y0,true);
		}
	}


}
