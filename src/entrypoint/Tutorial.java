package entrypoint;

import java.util.ArrayList;

import enemy.EnemyFactory;
import graphics.PopupRenderer;

import item.ItemFactory;
import item.ItemType;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import overworld.MonsterGroup;
import overworld.MonsterSpec;

import quest.Quest;

import content.FontLoader;
import content.OverworldLoader;

import battle.BattleModel;

import NetworkUtil.Action;


import client.ActionType;
import client.EpicGameContainer;

import util.GameConstants;
import util.Logger;
import visnovel.Cutscene;
import model.GameModel;
import model.Player;


public class Tutorial {
	public static int step = 0; 
	public static int initTitleY = GameConstants.SCREEN_HEIGHT, finalTitleX = 400, finalTitleY = 75, titleAnimTick =0, delayToFinish = 300;
	public static boolean stepDone = false, initialized = false, finished = false, battleHasBeenEntered = false;
	
	
	public static void init(){
		step0();
		initialized = true;
		stepDone = false;
		step = 0;
		finished = false;
		battleHasBeenEntered=false;
		GameModel.MAIN.player.hp = GameModel.MAIN.player.maxHP;
		GameModel.MAIN.player.zone = 34;
		GameModel.MAIN.player.x = 27;
		GameModel.MAIN.player.y = 27;
	}
	
	public static void nextStep(){
		if (!initialized) init();
		
		if (BattleModel.MAIN!=null) battleHasBeenEntered = true;
		boolean tempStepDone = checkIfStepIsDone();
		if (!(stepDone || tempStepDone)) return;
		step++;
		PopupRenderer.removePermanentPopups();
		//PopupRenderer.makeAllPopupsImpermanent();
		Logger.log("Current tutorial step: " + step);
		stepDone = false;
		
		switch (step){
			case 0:
				//step0();
				break;
			case 1:
				step1();
				break;
			case 2:
				step1Part2();
				break;
			case 3:
				step1Part3();
				break;
			case 4:
				step1Part4();
				break;
			case 5:
				step1Part5();
				break;
			case 6:
				step1Part6();
				break;
			case 7:
				step2();
				break;
			case 8:
				step2Part1point5();
				break;
			case 9:
				step2Part2();
				break;
			case 10:
				step2Part3();
				break;
			case 11:
				step2Part4();
				break;
			case 12:
				step2Part5();
				break;
			case 13:
				step2Part6();
				break;
			case 14:
				step3();
				break;
			case 15:
				step3Part2();
				break;
			case 16:
				step4();
				break;
			case 17:
				step4Part2();
				break;
			case 18:
				step5();
				break;
			case 19:
				step5Part2();
				break;
			case 20:
				step5Part3();
				break;
			case 21:
				step5Part4();
				break;
			case 22:
				step5Part5();
				break;
			case 23:
				step6();
				break;
			case 24:
				step6Part2();
				break;
				
			default:
				startGame();
				break;
		}
	}
	
	public static void startGame(){
		finished = true;
		GameModel.MAIN.player.hp = GameModel.MAIN.player.maxHP;
		//PopupRenderer.makeAllPopupsImpermanent();
		PopupRenderer.removePermanentPopups();
		GameModel.MAIN.initPlayer();
		GameModel.MAIN.player.unlockRecipe("Ruby of Burning");
		GameModel.MAIN.player.unlockRecipe("Ruby of Rage");
		GameModel.MAIN.player.unlockRecipe("Ruby of Samurai");
		GameModel.MAIN.player.unlockRecipe("Cirrus Steam");
	}
	
	public static boolean checkIfStepIsDone(){
		switch (step){
			case 0:
				if (GameModel.MAIN.curScene.isDone()) return true;
				else return false;
			case 1: case 2: case 3: case 4: case 5: // All battle cases should cease when the first battle ends...sometimes when the game lags, this causes a skip past tutorial steps
			case 6:
				if (battleHasBeenEntered && BattleModel.MAIN==null) 
					return true;
				else return false;
			case 7:
				if (GameModel.MAIN.curScene.isDone()) return true;
				else return false;
				
			case 8:
				if (EpicGameContainer.MAIN.menuIsOpen) return true;
				else return false;
			case 9:
				if (EpicGameContainer.MAIN.menu.currentTab==1) return true;
				else return false;
			case 10: case 11:
			case 12: 
				for (int i = 0; i<GameConstants.MAX_ACTIVE_ITEMS; i++){
					if (GameModel.MAIN.player.inventory.getActiveItem(i)==null) continue;
					if (GameModel.MAIN.player.inventory.getActiveItem(i).equals("Flawless Quartz of Lightning".toLowerCase())) return true;
				}
				return false;
			case 13:
				if (GameModel.MAIN.player.inventory.getItemCount("health potion")==0) return true;
				return false;
			case 14:
				if (EpicGameContainer.MAIN.menuIsOpen) return false;
				else return true;
			case 15:
				if (GameModel.MAIN.zones.get(GameModel.MAIN.player.zone).monsters.isEmpty()) return true;
				return false;
			case 16:
				if (GameModel.MAIN.curScene.isDone()) return true;
				else return false;
			case 18:
				if (GameModel.MAIN.curScene.isDone()) return true;
				else return false;
			case 19:
				if (EpicGameContainer.MAIN.menuIsOpen && EpicGameContainer.MAIN.menu.currentTab==2) return true;
				return false;
			case 20:
				if (GameModel.MAIN.player.inventory.getItemCount("Ruby of Fireballs")!=0) return true;
				return false;
			case 21:
				for (int i = 0; i<GameConstants.MAX_ACTIVE_ITEMS; i++){
					if (GameModel.MAIN.player.inventory.getActiveItem(i)==null) continue;
					if (GameModel.MAIN.player.inventory.getActiveItem(i).equals("Ruby of Fireballs".toLowerCase())) return true;
				}
				return false;
			case 22:
				if (!EpicGameContainer.MAIN.menuIsOpen) return true;
				return false;
			case 23:
				if (GameModel.MAIN.curScene.isDone()) return true;
				else return false;
			case 24:
				if (battleHasBeenEntered && BattleModel.MAIN==null) 
					return true;
				else return false;
				
				
		}
		return false;
	}
	
	public static void render(GameContainer gc, Graphics g){
		int height;
		if (!finished){
			if (step==0) g.drawImage(OverworldLoader.burningTownBackground, 0, 0);
			g.setColor(new Color(255,255,255));
			g.drawString("Press F9 to stop the tutorial", 0, 0);
			
			if (step>0 && step<6){
				if (titleAnimTick ==0) titleAnimTick = GameModel.MAIN.tickNum;
				height = (int)(((float)(GameModel.MAIN.tickNum-titleAnimTick)/delayToFinish) * (finalTitleY-initTitleY)+initTitleY);
				if (height < finalTitleY) height = finalTitleY;
				g.setFont(FontLoader.MAIN_MENU_TITLE_FONT);
				g.drawString("TUTORIAL", finalTitleX, height);
			}
		}
	}
	
	public static void setStepDone(int checkStep){
		if (step == checkStep) {
			stepDone = true;
			nextStep();
		}
	}
	
	/*Give the player all the cool things
	 *Play intro dialog, explaining how the town is burning down 
	 */
	private static void step0(){
		Player player = GameModel.MAIN.player;
		player.zone = 34; //Replace with the initial town
		GameModel.MAIN.curScene = Cutscene.play("TutorialIntro");
		

		ArrayList<String> toRemove = new ArrayList<String>();
		for (String itemName: GameModel.MAIN.player.inventory.getAllItems()){
			toRemove.add(itemName);
		}
		for (String itemName: toRemove){
			GameModel.MAIN.player.inventory.removeItem(itemName,GameModel.MAIN.player.inventory.getItemCount(itemName));
		}

		EpicGameContainer.showPopups = false;
		player.unlockRecipe("cirrus steam");
		player.unlockRecipe("nacreous steam");
		player.inventory.addItem("Ruby of Fireballs");
		player.inventory.setActiveItem("Ruby of Fireballs", 0);

		battleHasBeenEntered = false;

		EpicGameContainer.MAIN.bgmPlayer.play(GameModel.MAIN.zones.get(34).music);
	}
	
	/*
	 * Get the player into the first battle, with high level monsters
	 * Popups explain how to use QWER
	 */
	private static void step1(){ //1
		Player pl = GameModel.MAIN.player;
		ArrayList<MonsterSpec>types = new ArrayList<MonsterSpec>();
		types.add(new MonsterSpec(EnemyFactory.get("kaivantutorial")));
		GameModel.MAIN.zones.get(pl.zone).addMonsterGroup(new MonsterGroup(types,6,0,1,pl.zone,pl.x,pl.y));

		EpicGameContainer.showPopups = true;
		PopupRenderer.addPopup("Press R to do a melee attack", true);
	}
	private static void step1Part2(){//2

		PopupRenderer.addPopup("Press Q to do a fireball", true); //Make sure one actually gets cast, maybe do multiple attacks? with QWE...
	}
	private static void step1Part3(){//3
		PopupRenderer.addPopup("Notice: Attacks drain your AP bar", true);
		PopupRenderer.addPopup("To move around, use the arrow keys", true);
		PopupRenderer.addPopup("Press down to dash", true);
	}
	private static void step1Part4(){//4

		PopupRenderer.addPopup("Press up to jump", true);
	}

	private static void step1Part5(){//4
		PopupRenderer.addPopup("Press shift and move to moonwalk", true);
	}
	private static void step1Part6(){//5

		PopupRenderer.addPopup("Now finish the enemy.", true);
	}
	/*
	 *Explain how to equip things in the menu 
	 */
	private static void step2(){//6

		battleHasBeenEntered = false;
		GameModel.MAIN.curScene = Cutscene.play("GrandfathersGifts");
	}
	private static void step2Part1point5(){
		GameModel.MAIN.player.inventory.addItem("Aquamarine of Streams");
		GameModel.MAIN.player.inventory.addItem("Flawless Quartz of Lightning");
		GameModel.MAIN.player.inventory.addItem("Charm of Agility");
		GameModel.MAIN.player.inventory.addItem("Charm of Aerobics");
		GameModel.MAIN.player.inventory.addItem("Quartz of Seeking");
		GameModel.MAIN.player.inventory.addItem("Health Potion", 3);
		
		PopupRenderer.addPopup("You have new items!", true);
		PopupRenderer.addPopup("Let's equip them...", true);
		PopupRenderer.addPopup("Press ESC to open the menu", true);
	}
	private static void step2Part2(){//7
		PopupRenderer.addPopup("The left and right arrows change tabs", true);
		PopupRenderer.addPopup("Go to the Equip tab", true);
	}
	private static void step2Part3(){//8
		PopupRenderer.addPopup("Press down to scroll through the equips", true);
	}
	private static void step2Part4(){//9
		PopupRenderer.addPopup("Scroll down to Flawless Quartz of Lightning", true);
		PopupRenderer.addPopup("Press space to select it", true);
	}
	private static void step2Part5(){//10
		PopupRenderer.addPopup("You can now select where to equip it", true);
		PopupRenderer.addPopup("The options are Q, W, and E", true);
		PopupRenderer.addPopup("Use the arrows to choose one", true);
		PopupRenderer.addPopup("Press space to equip it there", true);
	}
	private static void step2Part6(){//11
		PopupRenderer.addPopup("Navigate to the Items menu", true);
		PopupRenderer.addPopup("Use a Health Potion with space", true);
	}
	
	/*
	 * Get the player into the second battle, with high level monsters
	 */
	private static void step3(){//12
		PopupRenderer.addPopup("Some items permanently increase stats", true);
		PopupRenderer.addPopup("So keep an eye out for those.", true);
		PopupRenderer.addPopup("Press ESC when you're ready to exit...", true);
	}
	private static void step3Part2(){ //13
		PopupRenderer.addPopup("Kill all the monsters in the town...", true);
	}
	
	/*
	 * The Maker of Life comes and takes all of your things, then beats you solidly
	 */
	private static void step4(){//14

		GameModel.MAIN.curScene = Cutscene.play("MakerRapeage");
	}
	private static void step4Part2(){//15
		ArrayList<String> toRemove = new ArrayList<String>();
		for (String itemName: GameModel.MAIN.player.inventory.getAllItems()){
			toRemove.add(itemName);
		}
		for (String itemName: toRemove){
			GameModel.MAIN.player.inventory.removeItem(itemName,GameModel.MAIN.player.inventory.getItemCount(itemName));
		}
		GameModel.MAIN.player.inventory.clearDiscoveredItemMemory();
		
		//Sets your current location as retreat - slightly hackish
		GameModel.MAIN.player.setRetreat();
		
		//Moves you to another location to fight Ruwen
		GameModel.MAIN.player.zone = 5;
		GameModel.MAIN.player.x = 7;
		GameModel.MAIN.player.y = 8;
		Action action = new Action(ActionType.SPAWN_MONSTER,"Ruwen");
		EpicGameContainer.MAIN.protocol.sendAction(action);

		PopupRenderer.addPopup("Press Y to retreat", true);
		PopupRenderer.addPopup("Avoid damage for 5 seconds to succeed.", true);
	}
	/*
	 * Alma sees you on the ground, and shows you how to craft a fireball staff and equip it
	 */
	private static void step5(){ //16

		GameModel.MAIN.curScene = Cutscene.play("AlmaTeachesCrafting");
	}
	private static void step5Part2(){ //17
		GameModel.MAIN.player.inventory.addItem("Ruby");
		GameModel.MAIN.player.inventory.addItem("Steam",7);
		GameModel.MAIN.player.lockRecipe("Ruby of Burning");
		GameModel.MAIN.player.lockRecipe("Ruby of Rage");
		GameModel.MAIN.player.lockRecipe("Ruby of Samurai");
		GameModel.MAIN.player.lockRecipe("Cirrus Steam");
		PopupRenderer.addPopup("Press ESC to enter the Menu",true);
		PopupRenderer.addPopup("Go to the Craft tab",true);
	}
	private static void step5Part3(){ //18
		PopupRenderer.addPopup("This tab shows the available recipes", true);
		PopupRenderer.addPopup("and the ingredients you own.", true);
		PopupRenderer.addPopup("Scroll down to Ruby of Fireballs", true);
		PopupRenderer.addPopup("and press space to craft.", true);
	}
	private static void step5Part4(){//19
		PopupRenderer.addPopup("To make this, you used 7 steam and 1 ruby.",true);
		PopupRenderer.addPopup("Now equip the Ruby of Fireballs",true);
	}
	private static void step5Part5(){//20
		PopupRenderer.addPopup("Press ESC to face the Maker again...",true);
		battleHasBeenEntered = false;
	}
	/*
	 * The Maker of Life comes back and beats you, causing you to pass out
	 */
	private static void step6(){ //21

		GameModel.MAIN.curScene = Cutscene.play("MakerRapeage2");
	}
	private static void step6Part2(){
		//Sets your current location as retreat - slightly hackish
		GameModel.MAIN.player.setRetreat();
		
		Action action = new Action(ActionType.SPAWN_MONSTER,"Cory");
		EpicGameContainer.MAIN.protocol.sendAction(action);
		
		finished = true;
	}

}
