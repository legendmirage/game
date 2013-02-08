package overworld;

import item.ItemType;

import java.util.ArrayList;
import java.util.HashMap;

import model.GameModel;
import model.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import quest.Quest;
import quest.QuestFactory;
import quest.component.GetItems;
import quest.component.QuestComponent;
import quest.component.TalkToNPC;
import util.GameConstants;
import util.Logger;
import visnovel.Cutscene;
import NetworkUtil.Action;
import client.EpicGameContainer;
import enemy.EnemyFactory;
import enemy.EnemyType;



/** Represents a zone of the world map. <br>
 * One town might be a zone, or a dungeon, or an area of the outdoors. <br>
 * Zones only update if there is a player in the zone. <br>
 */
public class ZoneModel {
	/** Hidden counter to make sure each instance of this class has a unique id. */
	private static int objectCount = 0;
	
	/** Unique identifier. */
	public int id;
	/** Terrain of the zone. */
	public final OverworldTileMap map;
	
	/** Portals that go from this zone to another zone. Map from portal ID to portal. */
	public final ArrayList<Portal> portals;
	/** NPCs in this zone. Map from NPC name to NPC. */
	public final HashMap<String, NPC> npcs;
	/** Enemies in this zone. Map from group number to monster group. */
	public final HashMap<Integer, MonsterGroup> monsters;
	
	public int state = 0;
	
	public String music;
	
	
	/** Creates a new zone.
	 */
	public ZoneModel(OverworldTileMap tileMap){
		this.id = objectCount++;
		
		this.map = tileMap;
		this.portals = tileMap.getPortals();
		this.npcs = tileMap.getNPCS();
		this.monsters = new HashMap<Integer, MonsterGroup>();
		this.music = tileMap.getMusic();
		
	}

	/** Gets the width of the associated map */
	public int getWidth() {
		return map.getWidth();
	}
	
	/** Gets the height of the associated map */
	public int getHeight() {
		return map.getHeight();
	}
	
	
	/** If there is a portal at the given coordinates of this zone, return it. <br>
	 * Otherwise, return null. */
	public Portal getPortalAt(int x, int y) {
		for(Portal port: portals) {
			if (x>=port.x && x<=port.x+(port.width-1) && y>=port.y && y<=port.y+(port.height-1)) return port;
		}
		return null;
	}

	/** Sets the initial objectCount. Only used to restart the game. */
    public static void setInitialObjectCount(int i ) {
    	objectCount=i;
    }
	
    public boolean canTalkToNPC(NPC npc, int x, int y){
    	Player pl = GameModel.MAIN.player;
    	if((x==npc.x && y==npc.y+1 && pl.isFacingRight()) || (y==npc.y && x==npc.x + 1 && pl.isFacingDown()) || (y==npc.y + 1 && x==npc.x+2 && pl.isFacingLeft()) || (x==npc.x+1 && y==npc.y+2 && pl.isFacingUp())){
    		return true;
    	}
    	return false;
    }
    
	/** If there is a npc next to the player, return it. <br>
	 * Also acquire and complete quests from the NPC. <br>
	 * Otherwise, return null. */
	public void talkToNPCAt(int x, int y) {
		Player pl = GameModel.MAIN.player;
		GameModel gm = GameModel.MAIN;
		for(String name : npcs.keySet()) {
			NPC npc = npcs.get(name);
			if (canTalkToNPC(npc, x, y)) {	
				if(npc.getSceneName().equals("Shop")){
					gm.curScene = Cutscene.play("Shop");
					EpicGameContainer.MAIN.shop.reset();
					EpicGameContainer.MAIN.shopIsOpen = true;
					EpicGameContainer.MAIN.updateMenu();
					return;
				}
				
				String questName = npc.getQuestName();
				
				//remove quest if all Talktonpc components are completed
				String temp = null;
				for(String quest:gm.quests.keySet()){
					for(QuestComponent com:gm.quests.get(quest).components){
						if(!(com instanceof TalkToNPC)){
							break;
						}
						else if(gm.quests.get(quest).questCompleted()){
							temp = quest;
						}
					}
				}
				
				if(temp != null){
					gm.questsDone.add(temp);
					gm.quests.remove(temp);
				}
				
				//if quest contains talktothisnpc and the other components are done, add cutscene about to be said to cutscenesdone
				if(gm.quests.get(questName) != null && (gm.curScene == null || gm.curScene.isDone()) && npc.getSceneName2() != null){
					int sum = 0;
					for(QuestComponent com:gm.quests.get(questName).components){
						if(!(com instanceof TalkToNPC) && com.complete()){
							sum += 1;
						}
						else if(com instanceof TalkToNPC){
							sum +=1;
						}
						else {
							break;
							}
						}
					if(sum == gm.quests.get(questName).components.size()){
						GameModel.MAIN.cutscenesDone.add(npc.getSceneName2());
						gm.quests.get(questName).updateProgress();
					}
					
				}
					
				
				
				if(questName != null){
					
					/**If the quest doesn't exist yet and it hasn't been completed, add it */
					if(gm.quests.get(questName) == null && gm.questsDone.indexOf(questName) == -1 && gm.cutscenesDone.indexOf(npc.getSceneName()) == -1){
						gm.addQuest(questName);
						Logger.log("Acquired Quest from " + name);
						gm.curScene = Cutscene.play(npc.getSceneName());
						GameModel.MAIN.cutscenesDone.add(npc.getSceneName());
						state = 1;
	
					}
					
					/**If the quest is in the current quests, but is completed and there's no dialog going on, move it to the completed quest list. */
					else if(gm.quests.get(questName) != null 
							&& gm.quests.get(questName).questCompleted()
							&& (gm.curScene == null || gm.curScene.isDone())){
						
						gm.questsDone.add(questName);
						gm.quests.remove(questName);
						Logger.log("Thanks for completing the " + questName + " quest");
						gm.curScene = Cutscene.play(npc.getSceneName2());
						for(ItemType item: QuestFactory.get(questName).reward.keySet()){
							pl.inventory.addItem(item.name, QuestFactory.get(questName).reward.get(item));
							if(pl.inventory.getNumActiveItems() < 3){
								pl.inventory.setActiveItem(item.name, pl.inventory.getNumActiveItems());
							}
						}
						for(QuestComponent quest: QuestFactory.get(questName).components){
							if (quest instanceof GetItems){
								pl.inventory.removeItem(((GetItems) quest).itemType.name, ((GetItems)quest).numToGet);
								
							}
						}
						if (npc.getRecipe() != null){
							pl.unlockRecipe(npc.getRecipe());
						}
						gm.cutscenesDone.add(npc.getSceneName2());
						state = 1;
						
					}
					
					/**If the quest is in the current quests, but is not completed and there's no dialog going on, play intermediate dialog. */
				
					else if (gm.quests.get(questName) != null && (gm.curScene == null || gm.curScene.isDone()) && state == 0){
						Logger.log("Intermediate scene");
						gm.curScene = Cutscene.play(npc.getSceneName1());
						state = 1;
					}
					
					else if((gm.curScene == null || gm.curScene.isDone()) && state == 1){
						gm.curScene = null;
						state = 0;
					}
					//after quest is completed, play this cutscene
					else if (gm.cutscenesDone.indexOf(npc.getSceneName2()) != -1){
						gm.curScene = Cutscene.play(npc.getSceneName3());
						state = 1;
					}
				}
				
				else{
					if(GameModel.MAIN.cutscenesDone.indexOf(npc.getSceneName()) == -1 && state == 0){
						gm.curScene = Cutscene.play(npc.getSceneName());
						GameModel.MAIN.cutscenesDone.add(npc.getSceneName());
						if (npc.getRecipe() != null){
							pl.unlockRecipe(npc.getRecipe());
						}
						state = 1;
					}
					else if(state == 1 && (gm.curScene == null || gm.curScene.isDone())){
						gm.curScene = null;
						state = 0;
					}
					else{
						if(npc.getSceneName1() != null && (gm.curScene == null || gm.curScene.isDone())){
							gm.curScene = Cutscene.play(npc.getSceneName1());
						}
						else if (gm.curScene == null || gm.curScene.isDone()){
							gm.curScene = Cutscene.play(npc.getSceneName());
						}
						state = 1;
					}
				}

			}
			
			/** Update quests */
			for(Quest q: gm.quests.values()){
				q.updateProgress();
			}
		}
	}
	/*
	public void addMonsterGroup(String name, int x, int y) {
		EnemyType type = EnemyFactory.get(name);
		if(type==null) Logger.err("ERROR: Tried to make an enemy of an invalid type: "+name);
		
		MonsterGroup mg = new MonsterGroup(type, id, x, y);
		enemies.put(mg.id, mg);
	}
	*/
	public void addMonsterGroup(MonsterGroup monster){
		monsters.put(monster.id, monster);
	}
	public void render(GameContainer gc, Graphics g) {
		Player pl = GameModel.MAIN.player;
		map.render(g, pl.x, pl.y);
		
		//g.setColor(new Color(255, 255, 255, 0));
		//g.setColor(Color.magenta);
//		for(Portal port: portals) {
//			if(!port.visible) continue;
//			if(withinRenderBounds(port.x, port.y))
//				g.fillRect(port.x * OverworldConstants.TILE_WIDTH+2, port.y * OverworldConstants.TILE_HEIGHT+2, 
//						OverworldConstants.TILE_WIDTH*(port.width)-4, OverworldConstants.TILE_HEIGHT*(port.height)-4);
//						
//		}
		
		
		if(pl.zone == id)
			if(withinRenderBounds(pl.x, pl.y))
				pl.render(gc, g);
//		for(NPC npc : npcs) {
//			if(withinRenderBounds(npc.x, npc.y))
//				npc.render(g);
//		}
		for(MonsterGroup mg: monsters.values()) {
			if(withinRenderBounds(mg.x, mg.y))
				mg.render(gc, g);
		}
	}
	private boolean withinRenderBounds(int x, int y) {
		int cx = GameModel.MAIN.player.x;
		int cy = GameModel.MAIN.player.y;
		int dx = GameConstants.SCREEN_WIDTH / OverworldConstants.TILE_WIDTH + 2;
		int dy = GameConstants.SCREEN_HEIGHT / OverworldConstants.TILE_HEIGHT + 2;
		return x>=cx-dx && x<=cx+dy && y>=cy-dy && y<=cy+dy;
	}
	public void tryPortal() {
		Player pl = GameModel.MAIN.player;
		int zoneID = 0, portalID= 0, x, y;
		Portal portal = getPortalAt(pl.x, pl.y);
		GameModel gm = GameModel.MAIN;
//		
//		//if portal is inactive, play the cutscene associated with it
//		if(portal != null && portal.cutsceneActive != null && gm.cutscenesDone.indexOf(portal.cutsceneActive) == -1){
//			
//			gm.curScene = Cutscene.play(portal.cutscene);
//		}
		
		//see if portal is active
		if(portal != null && (gm.cutscenesDone.indexOf(portal.cutsceneActive) != -1 || portal.cutsceneActive == null)){
			//if portal does not go anywhere, play the relevant cutscene and acquire a quest
			if(portal != null && portal.toZoneID == -1){
				
				//complete Talk to NPC quest
				String temp = null;
				for(String quest:gm.quests.keySet()){
					for(QuestComponent com:gm.quests.get(quest).components){
						if(!(com instanceof TalkToNPC)){
							break;
						}
						else if(gm.quests.get(quest).questCompleted()){
							temp = quest;
						}
					}
				}
				
				if(temp != null){
					gm.questsDone.add(temp);
					gm.quests.remove(temp);
				}
				if(portal.cutscene != null && GameModel.MAIN.cutscenesDone.indexOf(portal.cutscene) == -1){

					if (Math.abs(pl.getContinuousXLoc() - portal.x)  < .1 && Math.abs(pl.getContinuousYLoc() - portal.y)  < .1)
					{
					pl.moving = false;
					GameModel.MAIN.curScene = Cutscene.play(portal.cutscene);
					GameModel.MAIN.cutscenesDone.add(portal.cutscene);
					}
	
					//if portal has a quest associated with it, add it.
					if(portal.quest != null && gm.quests.get(portal.quest) == null){
						GameModel.MAIN.addQuest(portal.quest);
					}
					
					
				}
				
			}
			
			//if portal does go somewhere, go there
			if(portal!=null && portal.toZoneID != -1) {
				
				
				//complete talktoNPC quest
				//complete Talk to NPC quest
				String temp = null;
				for(String quest:gm.quests.keySet()){
					for(QuestComponent com:gm.quests.get(quest).components){
						if(!(com instanceof TalkToNPC)){
							break;
						}
						else if(gm.quests.get(quest).questCompleted()){
							temp = quest;
						}
					}
				}
				
				if(temp != null){
					gm.questsDone.add(temp);
					gm.quests.remove(temp);
				}
				
				
				zoneID = portal.toZoneID;
				portalID = portal.toPortalID;
				Logger.log("Zone: " + zoneID + "Portal: " + portalID);
				x = GameModel.MAIN.zones.get(zoneID).portals.get(portalID).x;
				y = GameModel.MAIN.zones.get(zoneID).portals.get(portalID).y;
				int xoff = 0;
				int yoff = 0;
				if(pl.isFacingDown()){
					yoff = 1;
					
				}
				if(pl.isFacingRight()){
					xoff = 1;
				}
				if(pl.isFacingUp()){
					yoff = -1;
				}
				if(pl.isFacingLeft()){
					xoff = -1;
				}
	
				if(gm.quests.get(portal.cutscene) != null && !gm.cutscenesDone.contains(portal.cutscene)){
					
					gm.questsDone.add(portal.cutscene);
					gm.quests.remove(portal.cutscene);
				}
	
				GameModel.MAIN.enterZone(portal.toZoneID, x + xoff, y + yoff);
				if(portal.cutscene != null && GameModel.MAIN.cutscenesDone.indexOf(portal.cutscene) == -1){
					GameModel.MAIN.curScene = Cutscene.play(portal.cutscene);
					GameModel.MAIN.cutscenesDone.add(portal.cutscene);
				}
				
				//if portal has a quest associated with it, add it.
				if(portal.quest != null && gm.quests.get(portal.quest) == null){
					GameModel.MAIN.addQuest(portal.quest);
				}
				
				
				
				
			}
		}
	}
	
	public void update() {
		Player pl = GameModel.MAIN.player;
		
		// Update overworld objects
		tryPortal();
		pl.update(this);


		for(NPC npc : npcs.values()) {
			npc.update(this);
		}
		for(MonsterGroup mg: monsters.values()) {
			mg.update(this);
		}
		
		// See if there are any new battles
		for(MonsterGroup mg: monsters.values()) {
			if(pl.x==mg.x && pl.y==mg.y) {
				GameModel.MAIN.startBattle(mg);
				break;
			}
			if(mg.moving && pl.x==mg.x-mg.facingX && pl.y==mg.y-mg.facingY) {
				GameModel.MAIN.startBattle(mg);
				break;
			}
			if(pl.moving && pl.x-pl.facingX==mg.x && pl.y-pl.facingY==mg.y) {
				GameModel.MAIN.startBattle(mg);
				break;
			}
		}
	}
	
	public void applyAction(Action action) {
		Player pl = GameModel.MAIN.player;

		switch(action.type) {
		case MOVE_LEFT:
			pl.startMoving(-1, 0);
			break;
		case MOVE_RIGHT:
			pl.startMoving(1, 0);
			break;
		case MOVE_UP:
			pl.startMoving(0, -1);
			break;
		case MOVE_DOWN:
			pl.startMoving(0, 1);
			break;
			
		case STOP_MOVING:
			pl.stopMoving();
			break;
		case OPEN_MENU:
			if(action.userID==EpicGameContainer.MAIN.myID){
				pl.stopMoving();
				EpicGameContainer.MAIN.changeMenuState();
			}
			break;
		case ENTER:
			if(!(pl.moveCooldown==0 || (pl.moving && pl.moveCooldown<pl.lastMoveMoveSpeed*4/5))) break;
			talkToNPCAt(pl.x, pl.y);

			break;
		case CRAFT:
			if (pl.canCraft(action.arg)){
				pl.craft(action.arg);
				EpicGameContainer.MAIN.updateMenu();
			}
			break;
		case USE:
			if (pl.canUseItem(action.arg)){
				pl.useItem(action.arg);
				EpicGameContainer.MAIN.updateMenu();
			}
			break;
		
		case EQUIP_PASSIVE:
			String itemNamePassive = action.arg.substring(0,action.arg.length()-1);
			if (itemNamePassive.length()==0) {
				itemNamePassive = null;
			}
			pl.inventory.setPassiveItem(itemNamePassive, Integer.parseInt(action.arg.substring(action.arg.length()-1)));
			EpicGameContainer.MAIN.updateMenu();
			break;
			
		case EQUIP_ACTIVE:
			String itemNameActive = action.arg.substring(0,action.arg.length()-1);
			Logger.log(itemNameActive.length());
			if (itemNameActive.length()==0) {
				itemNameActive = null;
			}
			pl.inventory.setActiveItem(itemNameActive, Integer.parseInt(action.arg.substring(action.arg.length()-1)));
			EpicGameContainer.MAIN.updateMenu();
			break;
			
		case SPAWN_MONSTER:
			ArrayList<MonsterSpec>types = new ArrayList<MonsterSpec>();
			for (String monster: action.arg.split(",")){
				EnemyType tempEnemy = EnemyFactory.get(monster);
				if(tempEnemy==null) {
					Logger.err("ERROR: Tried to make an enemy of an invalid type: "+monster);
				} else{
					types.add(new MonsterSpec(tempEnemy));
				}
			}
			GameModel.MAIN.zones.get(pl.zone).addMonsterGroup(new MonsterGroup(types,0,0,1,pl.zone,pl.x,pl.y));
			break;
			
		default:
			break;
		}
	}
}
