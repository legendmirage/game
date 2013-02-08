package model;

import enemy.EnemyDropTable;
import entrypoint.Tutorial;
import graphics.OverworldObjectRenderer;
import graphics.PopupRenderer;
import item.ItemFactory;
import item.ItemType;
import item.use.ItemUse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.yaml.snakeyaml.Yaml;

import client.EpicGameContainer;

import overworld.OverworldConstants;
import overworld.OverworldObject;
import overworld.OverworldTileMap;
import util.GameConstants;
import util.Loader;
import util.Logger;
import battle.BattleConstants;
import battle.BattleCreature;
import battle.BattleModel;
import battle.HitBox;
import crafting.Recipe;
import crafting.RecipeFactory;

/** Represents a played-controlled character. <br>
 * This object persists through the whole zone, in and out of battles. <br>
 */
public class Player extends OverworldObject {
	/** Hidden counter to make sure each instance of this class has a unique id. */
	private static int objectCount = 0;
	
	/** The player's stats. Contains utilities for managing stat attributes. */
	public final PlayerStats stats;
	/** The player's inventory. Contains utilities for managing the inventory. */
	public final Inventory inventory;
    /** The recipes that the player cannot use until they are unlocked. */
	private final HashSet<String> lockedRecipes;
	
	/** zone ID of the location that the player goes back to when he retreats. */
	private int retreatZone;
	/** x coordinate of the location that the player goes back to when he retreats. */
	private int retreatX;
	/** y coordinate of the location that the player goes back to when he retreats. */
	private int retreatY;
	
	/** zone ID of the location that the player goes back to when he dies. */
	private int respawnZone;
	/** x coordinate of the location that the player goes back to when he dies. */
	private int respawnX;
	/** y coordinate of the location that the player goes back to when he dies. */
	private int respawnY;
	
	/** The player just changed zones. Monsters won't chase him yet. */
	public boolean justSpawned;
	
	/** Current health of the player. Note that damage persists between battles now. */
	public int hp;
	
	/** Maximum health of the player. */
	public int maxHP;
	/** The battle hitboxes of the player */
	public ArrayList<HitBox> hitBoxes;
	
	/** Player Battle Renderer */
	public Map battleAnimationMap;
    
    
    public Player(int zone, int x, int y) {
    	super(zone, x, y);
    	id = objectCount++;
    	
		this.stats = new PlayerStats();
		this.inventory = new Inventory();
		this.lockedRecipes = new HashSet<String>();
		
		this.respawnZone = zone;
		this.respawnX = x;
		this.respawnY = y;
		this.justSpawned = true;
		setRetreat();
		updateStatDependencies();
		this.hp = maxHP;
		this.hitBoxes = new ArrayList<HitBox>();
		HitBox hb = new HitBox(0, 0, (int)BattleConstants.DEFAULT_PLAYER_WIDTH, (int)BattleConstants.DEFAULT_PLAYER_HEIGHT, true, 1);
		this.hitBoxes.add(hb);
		
		SpriteSheet ss = null;
		try {
			ss = new SpriteSheet("res/sprite/cecil_sprites.png", OverworldConstants.TILE_WIDTH, OverworldConstants.TILE_HEIGHT);
			Yaml yaml = new Yaml();
			battleAnimationMap = (Map)yaml.load(Loader.open("res/sprite/battle_cecil.yaml"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
		this.renderer = new OverworldObjectRenderer(this, ss);
	}
	
    /** Sets the player's current location as his retreat location. */
    public void setRetreat() {
    	retreatZone = zone;
    	retreatX = x;
    	retreatY = y;
    }
	/** The player just retreated. Return him at his retreat location. */
	public void retreat() {
		Logger.log("Played has retreated.");
		
		GameModel.MAIN.enterZone(retreatZone, retreatX, retreatY);
	}
	
	/** The player just died in battle. Respawn all the way back to the beginning. */
	public void die() {
		Logger.log("Played has died");
		PopupRenderer.addPopup("You died...Restarting");
		if (EpicGameContainer.MAIN.showTutorial && !Tutorial.finished){
			Tutorial.initialized = false;
			GameModel.MAIN.enterZone(34, 27, 27);
		}
		else{
			PopupRenderer.removePermanentPopups();
			GameModel.MAIN.enterZone(respawnZone, respawnX, respawnY);
		}
		if(GameConstants.TEST_MODE)
			hp = maxHP;
		else
			hp = maxHP/5;
	}
	
	@Override
	public void move(OverworldTileMap map) {
		super.move(map);
		if(lastMoveTick == GameModel.MAIN.tickNum)
			justSpawned = false;
	}
	
	public void unlockRecipe(String itemName) {
		if(!isRecipeLocked(itemName)) return;
		itemName = itemName.toLowerCase();
		lockedRecipes.remove(itemName);
		PopupRenderer.addPopup(itemName + " unlocked!");
	}
	
	public boolean isRecipeLocked(String itemName) {
		if(itemName==null) return false;
		itemName = itemName.toLowerCase();
		return lockedRecipes.contains(itemName);
	}
	
	public void lockRecipe(String itemName) {
		if(itemName==null) return;
		itemName = itemName.toLowerCase();
		if(RecipeFactory.get(itemName)==null) {
			Logger.err("Tried to lock recipe for item \""+itemName+"\", " +
					"but the recipe does not exist.");
			return;
		}
		lockedRecipes.add(itemName);
	}

	/** Can the player see the recipe for the given item in the craft menu? */
	public boolean canSeeRecipe(String itemName) {
		Recipe recipe = RecipeFactory.get(itemName);
		if(recipe==null) return false;
		if(isRecipeLocked(itemName)) return false;
		for(ItemType reagent: recipe.reagents.keySet()) {
			if(!inventory.isDiscovered(reagent.name)) return false;
		}
		return true;
	}
	/** Can this player craft the given item? */
	public boolean canCraft(String itemName) {
		if(!canSeeRecipe(itemName)) return false;
		Recipe recipe = RecipeFactory.get(itemName);
		for(ItemType reagent: recipe.reagents.keySet()) {
			if(inventory.getItemCount(reagent.name) < recipe.reagents.get(reagent)) return false;
		}
		return true;
	}
    /** Craft the given item if possible. Otherwise, does nothing. */
    public void craft(String itemName) {
    	if(!canCraft(itemName)) return;
    	Recipe recipe = RecipeFactory.get(itemName);
    	for(ItemType reagent: recipe.reagents.keySet()) {
			inventory.removeItem(reagent.name, recipe.reagents.get(reagent));
		}
		inventory.addItem(itemName);
    }
    /** Can the player use the given item? */
    public boolean canUseItem(String itemName) {
    	ItemType item = ItemFactory.get(itemName);
    	if(item==null || item.useEffects.isEmpty()) return false;
    	if(inventory.getItemCount(itemName) == 0) return false;
    	return true;
    }
    /** Use the given item if possible. Otherwise, does nothing. */
    public void useItem(String itemName) {
    	if(!canUseItem(itemName)) return;
    	inventory.removeItem(itemName);
    	for(ItemUse effect: ItemFactory.get(itemName).useEffects) {
    		effect.use(this);
    	}
    }
    
    public static void setInitialObjectCount(int i) {
    	objectCount=i;
    }
    
    /** This player just finished a battle where an enemy of the given type died. */
	public void lootEnemy(BattleCreature cr) {
		String itemName = cr.specialDrop!=null ? cr.specialDrop : 
			EnemyDropTable.computeDrop(cr.type);
		if(itemName==null) return;
		inventory.addItem(itemName);
		BattleModel.MAIN.itemsGainedThisBattle.add(itemName);
	}
	
	/** This method does things like make sure that the player health is correct depending on what
	 * kind of armor he has equipped and what his stats are allocated in.
	 */
	public void updateStatDependencies() {
		int oldMaxHP = maxHP;
		maxHP = 500+100*stats.get("vitality");
		if(maxHP>oldMaxHP) {
			hp += maxHP-oldMaxHP;
		}
		
		moveSpeed = (int)(OverworldConstants.PLAYER_BASE_MOVE_SPEED / 
				(1+0.1*Math.pow(stats.get("speed"),0.75)));
	}
	
	@Override
    public String toString() {
    	return id + ":" + stats.name;
    }

}
