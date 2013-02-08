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
import org.newdawn.slick.gui.TextField;

import client.EpicGameContainer;

import quest.Quest;
import util.GameConstants;
import util.KeyBindings;
import util.RenderUtil;
import content.FontLoader;
import content.MenuLoader;

public class Menu extends MenuInterface{
	public Menu(){
		tabs = new Tab[6];
		int count = 0;
		downPressed = upPressed = leftPressed = rightPressed = false;
		
		tabs[count] = new UseableItemTab(inventory);
		count++;
		tabs[count] = new InventoryTab(inventory);
		count++;
		tabs[count] = new CraftTab(inventory, stats);
		count++;
		tabs[count] = new QuestTab(quests);
		count++;
		tabs[count] = new ControlsMenu(false);
		count++;
		tabs[count] = new ExitTab();
		//tabs[count] = new CashStoreTab(inventory);
		
	}

}
