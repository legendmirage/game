package menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import model.GameModel;
import model.Inventory;
import model.PlayerStats;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.TextField;

import content.FontLoader;

import NetworkUtil.Action;

import client.ActionType;
import client.EpicGameContainer;
import enemy.EnemyFactory;
import enemy.EnemyType;

import quest.Quest;
import util.GameConstants;
import util.KeyBindings;
import util.RenderUtil;

public class MonsterMenu extends Tab{
	private Color BACKGROUND_COLOR = new Color(200,200,200,200);
	TextField textItems;
	
	class MonsterItem extends Item{

		MonsterItem(String text) {
			super(text);
		}

		@Override
		boolean keyPressed(int key) {
			KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
					KeyBindings.DVORAK : KeyBindings.QWERTY;
			if (key==kb.enter || key == kb.space){
				Action action = new Action(ActionType.SPAWN_MONSTER,this.text);
				EpicGameContainer.MAIN.protocol.sendAction(action);
			}
			return false;
		}
		
	}

	public MonsterMenu() {
		super("Monster Menu");
		this.update();
		
	}

	public void render(GameContainer gc, Graphics g){
		render(gc, g, 0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT, true);
	}
	@Override
	public boolean keyPressed(int key){
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		if (key==kb.up){
			previousItem();
		} else if (key==kb.down){
			nextItem();
		}
		else return this.items.get(currIndex).keyPressed(key);
		
		return false;
	}
	@Override
	void render(GameContainer gc, Graphics g, int x0, int y0, int width,
			int height, boolean hasFocus) {
		g.setColor(BACKGROUND_COLOR);		
		g.fillRect(0, 0, width, height);
		if (textItems == null) textItems = new TextField(gc, FontLoader.ITEM_FONT, x0+LEFT_OFFSET, y0+titleHeight+TOP_OFFSET, width-RenderUtil.CURSOR_LEFT_OFFSET-RenderUtil.CURSOR_WIDTH, itemHeight);
		
		renderTitle(gc,g, x0,y0,width,titleHeight, "Monsters", textItems);
		renderItems(gc,g, x0,y0+titleHeight,width,height-titleHeight, hasFocus, textItems);
		
	}

	public void update(){
		update(null,null,null);
	}
	@Override
	void update(Inventory inventory, HashMap<String, Quest> quests,
			PlayerStats stats) {
		this.items = new ArrayList<Item>();
		for (EnemyType enemy: EnemyFactory.enemyTypes.values()){
			this.items.add(new MonsterItem(enemy.name));
		}
		Collections.sort(items, new Tab.ItemAlphabeticalComparator());
		
	}

}
