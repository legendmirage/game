package menu;

import item.ItemFactory;
import item.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.GameModel;
import model.Inventory;
import model.PlayerStats;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
import crafting.RecipeFactory;

public class CraftTab extends Tab{
	private ArrayList<Item> reagentsOwnedList, recipes;
	int recipeIndex, reagentIndex;
	int MIN_INVENTORY_HEIGHT = 5;

	class RecipeItem extends Item{
		
		public ArrayList<String> reagentNames, questQuantity, userQuantity;
		
		RecipeItem(String text, ArrayList<String> reagentNames, ArrayList<String> questQuantity, ArrayList<String> userQuantity) {
			super(text);
			this.reagentNames = reagentNames;
			this.questQuantity = questQuantity;
			this.userQuantity = userQuantity;
		}

		@Override
		boolean keyPressed(int key) {
			Action action;
			KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
					KeyBindings.DVORAK : KeyBindings.QWERTY;
			if (key == kb.space || key==kb.enter) {
				if (items==recipes){
					action = new Action(ActionType.CRAFT,this.text);
					EpicGameContainer.MAIN.protocol.sendAction(action);
				}
				return true;
			} else if (key== kb.left||key==kb.right) return true;
			
			return false;
			
		}
		
	}
	
	private PlayerStats stats;
	private Inventory inventory;
	TextField textWide, textMini, textInstruction, textItems, textReagents;
	
	public CraftTab(Inventory inventory, PlayerStats stats) {
		super("Craft",2);
		this.stats = stats;
		this.inventory = inventory;
		
		this.recipes = new ArrayList<Item>();
		this.reagentsOwnedList = new ArrayList<Item>();
		this.items = this.recipes;
		this.recipeIndex = 0;
		this.reagentIndex = 0;
		this.currIndex = this.recipeIndex;
		
	}
	
	@Override
	public void reset(){
		this.items = this.recipes;
		this.currIndex = this.recipeIndex;
		this.reagentIndex = 0;
	}


	@Override
	void render(GameContainer gc, Graphics g, int x0, int y0, int width,
			int height, boolean hasFocus) {
		TextField text;

		if (textWide == null) textWide = new TextField(gc, (org.newdawn.slick.Font) FontLoader.ITEM_FONT, x0, y0, 4*width/12-GAP_SIZE, itemHeight);
		if (textMini == null) textMini = new TextField(gc, (org.newdawn.slick.Font) FontLoader.ITEM_FONT, x0, y0, 3*width/24-LEFT_OFFSET/2, itemHeight);
		if (textReagents == null) textReagents = new TextField(gc, (org.newdawn.slick.Font) FontLoader.ITEM_FONT, x0, y0, 7*width/12-GAP_SIZE-LEFT_OFFSET-RenderUtil.PIXEL_WIDTH*2-RenderUtil.CURSOR_WIDTH, itemHeight);
		if (textInstruction == null) textInstruction = new TextField(gc, (org.newdawn.slick.Font) FontLoader.ITEM_FONT, x0, y0, 7*width/12-GAP_SIZE-LEFT_OFFSET, itemHeight);
		
		boolean onRecipes = this.items==this.recipes;
		if (onRecipes) this.recipeIndex = this.currIndex; 
		else this.reagentIndex = this.currIndex;

		textWide.setTextColor(TEXT_COLOR);
		textWide.setBackgroundColor(UNSELECTABLE_ITEM_COLOR);
		
		textMini.setTextColor(TEXT_COLOR);
		textMini.setBackgroundColor(UNSELECTABLE_ITEM_COLOR);
		
		//Left column		
		int tempHeight = height-RenderUtil.PIXEL_HEIGHT;
		int tempWidth = 5*width/12-2*RenderUtil.PIXEL_WIDTH;
		RenderUtil.renderPrettyBox(g, x0+LEFT_OFFSET-RenderUtil.PIXEL_WIDTH, y0+TOP_OFFSET-RenderUtil.PIXEL_HEIGHT, tempWidth, tempHeight);
		ArrayList<String> toDisplay = new ArrayList<String>();
		for (Item item: this.recipes){
			toDisplay.add(item.text);
		}
		if (textItems == null) textItems = new TextField(gc, FontLoader.ITEM_FONT, x0+LEFT_OFFSET, y0+titleHeight+TOP_OFFSET, tempWidth-RenderUtil.PIXEL_WIDTH-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);

		renderTitle(gc,g,x0+LEFT_OFFSET,y0+TOP_OFFSET,tempWidth,titleHeight, "Available Recipes", textItems);
		renderItems(gc,g, x0+LEFT_OFFSET,y0+titleHeight+TOP_OFFSET,tempWidth-RenderUtil.PIXEL_WIDTH,tempHeight-titleHeight, hasFocus&&onRecipes, toDisplay, 0, textItems);
		
		//Right column
		
		//Reagents
		String recipeName ="";
		if (this.recipes.size()>0 && onRecipes) recipeName = this.recipes.get(currIndex).text;
		
		int x = x0+5*width/12+GAP_SIZE;
		
		//Reagent names, item quantities
		if (this.recipes.size()>0){
			if ( y0+titleHeight*2+itemHeight*((RecipeItem)this.recipes.get(currIndex)).reagentNames.size()+this.MIN_INVENTORY_HEIGHT*itemHeight>height){
				Logger.log("Your recipe is hella long. Please adapt the code if this is going to be a consistent problem.");
			}
		}

		renderTitle(gc,g,x,y0,width-(7*width/12+GAP_SIZE),titleHeight,recipeName+" Recipe", textInstruction);
		
		renderTitle(gc,g,x,y0+titleHeight,5*width/12-GAP_SIZE,titleHeight,"Reagents Required", true, textWide);
		text = textWide;
		if (onRecipes && this.recipes.size()>0){
			for (int i = 0; i<((RecipeItem)recipes.get(currIndex)).reagentNames.size(); i++){
				text.setLocation(x, y0+titleHeight*2+itemHeight*i);
				text.setText(((RecipeItem)recipes.get(currIndex)).reagentNames.get(i));
				text.render(gc,g);
			}
		}
		
		x += 4*width/12-GAP_SIZE;
		renderTitle(gc,g,x,y0+titleHeight,3*width/24-LEFT_OFFSET/2,titleHeight,"Needed", true, textMini);
		text = textMini;
		if (onRecipes && this.recipes.size()>0){
			for (int i = 0; i<((RecipeItem)recipes.get(currIndex)).questQuantity.size(); i++){
				if(y0+titleHeight*2+itemHeight*i>GameConstants.SCREEN_HEIGHT)
					break;
				text.setLocation(x, y0+titleHeight*2+itemHeight*i);
				text.setText(((RecipeItem)recipes.get(currIndex)).questQuantity.get(i));
				text.render(gc,g);
			}
		}
		
		x += 3*width/24-LEFT_OFFSET/2;
		renderTitle(gc,g,x,y0+titleHeight,width/6-LEFT_OFFSET/2,titleHeight,"Owned", true, textMini);
		text = textMini;

		if (onRecipes && this.recipes.size()>0){
			for (int  i = 0; i<((RecipeItem)recipes.get(currIndex)).userQuantity.size(); i++){
				if(y0+titleHeight*2+itemHeight*i>GameConstants.SCREEN_HEIGHT)
					break;
				text.setLocation(x, y0+titleHeight*2+itemHeight*i);
				text.setText(((RecipeItem)recipes.get(currIndex)).userQuantity.get(i));
				if (Integer.parseInt(((RecipeItem)recipes.get(currIndex)).userQuantity.get(i))<Integer.parseInt(((RecipeItem)recipes.get(currIndex)).questQuantity.get(i)))
					text.setTextColor(new Color(255,0,0));
				else text.setTextColor(TEXT_COLOR);
				text.render(gc,g);
			}
			
	
			//Instruction text
			text = textInstruction;
			text.setTextColor(TEXT_COLOR);
			text.setBackgroundColor(UNSELECTED_ITEM_COLOR);
			
			text.setLocation(x0+5*width/12+GAP_SIZE, y0+titleHeight*2+itemHeight*((RecipeItem)this.recipes.get(currIndex)).reagentNames.size());
			String instruction;
			if (this.items.size()==0) instruction = "";
			else instruction = "Press space to craft";
			text.setText(instruction);
			text.render(gc,g);
		}

		int currY;
		//if (this.recipes.size()>0)
			//currY = titleHeight*2+itemHeight*((RecipeItem)this.recipes.get(this.recipeIndex)).reagentNames.size();
		currY = titleHeight*2+itemHeight*3; //Fixed recipe length
		
		if (onRecipes) currY += itemHeight*2;
		else currY = titleHeight*2+itemHeight;

		tempWidth = 7*width/12-GAP_SIZE-LEFT_OFFSET-RenderUtil.PIXEL_WIDTH;
		tempHeight = height-currY-TOP_OFFSET*2+RenderUtil.PIXEL_HEIGHT;
		int tempX = x0+5*width/12+GAP_SIZE-RenderUtil.PIXEL_WIDTH;
		int tempY = y0+currY + TOP_OFFSET - RenderUtil.PIXEL_HEIGHT;
		RenderUtil.renderPrettyBox(g, tempX, tempY, tempWidth, tempHeight);
		
		renderTitle(gc,g,tempX+RenderUtil.PIXEL_WIDTH, tempY+TOP_OFFSET-RenderUtil.PIXEL_HEIGHT,tempWidth,titleHeight,"Reagents Owned",false, textReagents);
		currY += titleHeight;
		
		toDisplay = new ArrayList<String>();
		for (Item item: this.reagentsOwnedList){
			toDisplay.add(((RecipeItem)item).userQuantity.get(0)+ "   " + item.text);
		}
		renderItems(gc,g, tempX+RenderUtil.PIXEL_WIDTH, tempY+TOP_OFFSET-RenderUtil.PIXEL_HEIGHT+titleHeight,tempWidth-RenderUtil.PIXEL_WIDTH,tempHeight-titleHeight, hasFocus && !onRecipes, toDisplay, 1, textReagents);
		
	}

	@Override
	void update(Inventory inventory, HashMap<String, Quest> quests, PlayerStats stats) {
		boolean onRecipes = this.items ==this.recipes;
		this.stats = stats;
		this.inventory = inventory;
		
		populateRecipes();
		populateInventory();
		
		if (onRecipes) this.items = this.recipes;
		else this.items = this.reagentsOwnedList;
	}
	
	void populateInventory(){
		this.reagentsOwnedList = new ArrayList<Item>();
		ArrayList<String> reagentsOwnedQuantity; // dummy list to fit inventory into recipe item form...could do otherwise, but whatever
		
		for (String item: this.inventory.getAllItems()){
			if (!this.inventory.canSetActiveItem(item, 0)&&(!this.inventory.canSetPassiveItem(item, 0)&&ItemFactory.get(item).useEffects.size()==0)){
				reagentsOwnedQuantity = new ArrayList<String>();
				reagentsOwnedQuantity.add(this.inventory.getItemCount(item)+"");
				reagentsOwnedList.add(new RecipeItem(item,null,null,reagentsOwnedQuantity));
			}
		}
		
	}
	
	void populateRecipes(){
		ArrayList<String> reagents, userQuantity, questQuantity;
		
		this.recipes = new ArrayList<Item>();
		for(String recipeName: RecipeFactory.getAllRecipeNames()) {
			if(!GameModel.MAIN.player.canSeeRecipe(recipeName)) continue;
			if(!GameModel.MAIN.player.canCraft(recipeName)) continue;
			reagents = new ArrayList<String>();
			userQuantity = new ArrayList<String>();
			questQuantity = new ArrayList<String>();
			for (Entry<ItemType, Integer> reagent : RecipeFactory.get(recipeName).reagents.entrySet()){
				String reagentName = reagent.getKey().name;
				reagents.add(reagentName);
				questQuantity.add(reagent.getValue()+"");
				int count = this.inventory.getItemCount(reagentName);
				userQuantity.add(count+"");
			}

			this.recipes.add(new RecipeItem(recipeName,reagents, questQuantity, userQuantity));
		}

		for(String recipeName: RecipeFactory.getAllRecipeNames()) {
			if(!GameModel.MAIN.player.canSeeRecipe(recipeName)) continue;
			if(GameModel.MAIN.player.canCraft(recipeName)) continue;
			reagents = new ArrayList<String>();
			userQuantity = new ArrayList<String>();
			questQuantity = new ArrayList<String>();
			for (Entry<ItemType, Integer> reagent : RecipeFactory.get(recipeName).reagents.entrySet()){
				String reagentName = reagent.getKey().name;
				reagents.add(reagentName);
				questQuantity.add(reagent.getValue()+"");
				int count = this.inventory.getItemCount(reagentName);
				userQuantity.add(count+"");
			}

			this.recipes.add(new RecipeItem(recipeName,reagents, questQuantity, userQuantity));
		}
		
		
	}
	


	/** Key press handler for the tab*/
	public boolean keyPressed(int key){
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		if (key==kb.left){
			if (this.items==this.reagentsOwnedList){
				this.items = this.recipes;
				this.reagentIndex = this.currIndex;
				this.currIndex = this.recipeIndex;
				return false;
			}
			else return true;
		} else if (key==kb.right){
			if (this.items ==this.recipes){
				this.items = this.reagentsOwnedList;
				this.recipeIndex = this.currIndex;
				this.currIndex = this.reagentIndex;
				return false;
			}
			else return true;
		}
		else if (this.items.size()>0) return this.items.get(currIndex).keyPressed(key);
		else return true;
	}

}
