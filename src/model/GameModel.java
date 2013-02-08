package model;

import entrypoint.Tutorial;
import graphics.PopupRenderer;
import item.ItemFactory;
import item.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import overworld.MonsterGroup;
import overworld.OverworldConstants;
import overworld.OverworldTileMap;
import overworld.ZoneModel;
import quest.Quest;
import quest.QuestFactory;
import util.GameConstants;
import util.Logger;
import util.Rand;
import visnovel.Cutscene;
import NetworkUtil.Action;
import battle.BattleModel;
import client.EpicGameContainer;
import content.OverworldLoader;

/** The mother class for the entire model of the game. <br>
 * The model of the game's MVC design. <br>
 * Having this object means you know the exact state of the model of the game. <br>
 * That means you can reproduce the game state from scratch if you just have the data in this object. <br>
 * 
 * This is also a singleton.
 */
public class GameModel {
	/** The singleton game model. There should not be more than one game model active at one time. */
	public static GameModel MAIN;
	
	/** The number of times that the model has updated. */
	public int tickNum;
	
	/** The player. */
	public Player player;
	
	/** Map from zone IDs to zones. <br>
	 * Always contains all the zones in the game. <br>
	 */
	public final HashMap<Integer, ZoneModel> zones;
	
	/** Map from Quest name to Quest progress. <br>
	 * Always contains all the Quests in the game. <br>
	 */
	public final HashMap<String, Quest> quests;
	
	/** Counts how many of each monster type have been killed in this game session. <br>
	 * Maps from the name of the EnemyType to the number killed of that type. <br>
	 */
	private final HashMap<String, Integer> killCounts;
	
	/** The current rendering cutscene. These are global cutscenes
	 * that receive first-class support in the main game model as 
	 * both players simultaniously watch the same events happen
	 */
	public Cutscene curScene;
	
	/** List of zone Cutscenes that have been played, so they can't be played again <br>
	 */
	public final ArrayList<String> cutscenesDone;
	
	/** List of Quest names that have been completed. <br>
	 */
	public final ArrayList<String> questsDone;
	
	public final LinkedList<Action> actionQueue;
	public Rand rand;
	public String currentMusic;
	public static GameModel startState;
	/** Creates a new GameModel, and sets it as the singleton object GameModel.MAIN. */
	public GameModel(int seed) {
		Logger.log("Initializing GameModel");
		MAIN = this;
		rand = new Rand(seed);
		tickNum = 0;
		zones = new HashMap<Integer, ZoneModel>();
		actionQueue = new LinkedList<Action>();
		quests = new HashMap<String, Quest>();
		killCounts = new HashMap<String, Integer>();
		questsDone = new ArrayList<String>();
		cutscenesDone = new ArrayList<String>();
		loadWorldData();
	}
	
	/** Initializes the player. */
	public void initPlayer() {
		EpicGameContainer.MAIN.bgmPlayer.play(zones.get(0).music);
		if (player==null) player = new Player(0, 27, 27);
		else {
			player.zone = 0;
			player.x = 27;
			player.y = 27;
			player.hp = player.maxHP;
		}

		GameModel.MAIN.player.inventory.clearDiscoveredItemMemory();
		
		player.lockRecipe("cirrus steam");
		player.lockRecipe("nacreous steam");
		
		player.inventory.addItem("Health Potion", 2);

		if (EpicGameContainer.MAIN.showTutorial && !Tutorial.finished) Tutorial.init();
		else if(GameConstants.TEST_MODE)
			testInitPlayer();
	}
	private void testInitPlayer() {
		player.inventory.addItem("steam", 100);
		for(ItemType type: ItemFactory.getAllItems().values())
			player.inventory.addItem(type.name);
		player.unlockRecipe("cirrus steam");
		player.unlockRecipe("nacreous steam");
		player.inventory.setActiveItem("Ruby of Burning", 0);
		player.inventory.setActiveItem("Aquamarine of Needles", 1);
		player.inventory.setActiveItem("Quartz of Repel", 2);
	}
	public void loadWorldData() {
		
		// Load zones
		for (OverworldTileMap map : OverworldLoader.zoneMaps){
			ZoneModel zone = new ZoneModel(map);
			zones.put(zone.id,zone); 
		}

		// Load monsters
		for (int i = 0; i<zones.size();i++){
			for (Entry<Integer, MonsterGroup> monster: zones.get(i).map.loadMonsters(i).entrySet()){
				zones.get(i).addMonsterGroup(monster.getValue());
			}
		}
		
		// Initialize player
		initPlayer();
	}
	
	public String toString() {
		return player.toString();
	}
	
	/** Adds the battle model to the global store of battle models. */
	public void startBattle(MonsterGroup mg) {
		Logger.log("Entering battle against group "+mg.id);
		
		new BattleModel(mg);
		player.moving = false;
		player.stopMoving();
	}
		
	/** The battle ends. Should only be called from BattleModel.update(). */
	public void endBattle(boolean retreated, int hpAfterBattle) {
		Logger.log("Battle ended, player has "+hpAfterBattle+" health remaining");
		Player pl = player;
		pl.moving = false;
		pl.moveCooldown = OverworldConstants.MOVE_COOLDOWN_ENDING_BATTLE;
		pl.hp = hpAfterBattle;
		if(pl.hp<=0) {
			pl.die();
		} else if(retreated) {
			pl.retreat();
		} else {
			// Player didn't die and didn't retreat, so the monsters must have been vanquished
			zones.get(pl.zone).monsters.remove(BattleModel.MAIN.mg.id);
		}
		
		BattleModel.MAIN = null;
		Tutorial.setStepDone(17);
	}
	
	/** The player moves to a new zone. */
	public void enterZone(int zoneID, int x, int y) {
		Player pl = player;
		pl.moving = false;
		pl.moveCooldown = OverworldConstants.MOVE_COOLDOWN_ENTERING_ZONE;
		pl.zone = zoneID;
		pl.x = x;
		pl.y = y;
		pl.setRetreat();
		pl.justSpawned = true;
		EpicGameContainer.MAIN.bgmPlayer.play(zones.get(zoneID).music);
		
		if(zoneID==10) {
			pl.unlockRecipe("cirrus steam");
		}
		if(zoneID==20) {
			pl.unlockRecipe("nacreous steam");
		}
	}
	
	/** Adds quests */
	public void addQuest(String questName) {
		quests.put(questName, new Quest(QuestFactory.get(questName)));
		PopupRenderer.addPopup("Quest added: "+ questName);
	}
	
	/** Ticks the game model, updating everything. Should only be called from ModelUtil.updateModel(). <br>
	 * This is essentially the core update loop of the model. <br>
	 * This should be very high level code, consistent mostly of calls to sub-models to update themselves. <br>
	 */
	public synchronized void update() {
		
		// Increment the tick number
		tickNum++;
		
		// Apply all queued actions
		while(!actionQueue.isEmpty()) {
			applyAction(actionQueue.pollFirst());
		}
		
		// Update battle or zone
		if(!EpicGameContainer.MAIN.isPaused()) {
			if(BattleModel.MAIN!=null) {
				BattleModel.MAIN.update();
			} else {
				zones.get(player.zone).update();
			}
		}
		
		//Update quests
		for(Quest quest: quests.values()){
			quest.updateProgress();
		}
	}
	
	/** Adds an action the the action queue. */
	public synchronized void addAction(Action action) {
		if(actionQueue!=null)
			actionQueue.addLast(action);
	}
	
	/** Applies an action in the action queue. */
	public void applyAction(Action action) {
		if(BattleModel.MAIN!=null) {
			BattleModel.MAIN.applyAction(action);
		} else {
			zones.get(player.zone).applyAction(action);
		}
	}
	
	/** Returns the total number of enemies of the given type that have been killed in this game session. */
	public int getKillCount(String enemyName) {
		if(enemyName==null) return 0;
		enemyName = enemyName.toLowerCase();
		Integer i = killCounts.get(enemyName);
		return i==null ? 0 : i;
	}
	
	/** An enemy of the given type has been killed. Increment the kill count. */
	public void incrementKillCount(String enemyName) {
		if(enemyName==null) return;
		enemyName = enemyName.toLowerCase();
		if(!killCounts.containsKey(enemyName)){
			killCounts.put(enemyName, 1);
		} else{
			killCounts.put(enemyName, killCounts.get(enemyName)+1);
		}
	}
}
