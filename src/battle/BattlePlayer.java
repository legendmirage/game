package battle;

import enemy.EnemyType;
import graphics.BattleCreatureRenderer;
import item.ItemFactory;
import item.ItemType;

import java.util.ArrayList;

import model.GameModel;
import model.Player;
import overworld.OverworldConstants;
import util.GameConstants;
import ability.Ability;
import ability.type.DashForward;
import ability.type.MeleeAttack;
import ability.type.PlayerBasicAttack;
import ability.type.Retreat;

/** A player in battle. */
public class BattlePlayer extends BattleCreature {

	/** The items that the player has equipped. These will provide passive effects in battle. */
	public ItemType[] equipment;
	/** The player's dash ability. */
	public BattleAbility dashAbility;
	/** The player's basic attack. It's a weak melee attack. */
	public BattleAbility basicAttack;
	/** The player's retreat ability. */
	public BattleAbility retreatAbility;
	
	/** Creates a BattlePlayer of the given Player, transferring stats and equipment 
	 * from the Player object. */
	public BattlePlayer(Player pl, int spawnZone) {
		super(spawnZone, pl.hp, pl.maxHP, pl.battleAnimationMap, null);
		this.id = pl.id;
		this.team = 1;
		this.maxAirJumps = GameConstants.TEST_MODE ? 55555 : 
			BattleConstants.PLAYER_DEFAULT_MAX_AIR_JUMPS;
		this.moveSpeed = BattleConstants.PLAYER_MOVE_SPEED * 
				OverworldConstants.PLAYER_BASE_MOVE_SPEED / pl.moveSpeed;
		this.maxAP = 100;
		this.ap = maxAP/2;
		this.jumpSpeed = BattleConstants.PLAYER_JUMP_SPEED;
		BattleModel.MAIN.playerAPCostMultiplier = 1/(1+0.1f*pl.stats.get("wisdom"));
		BattleModel.MAIN.playerAttackDamageMultiplier = 1+0.1f*pl.stats.get("power");
		BattleModel.MAIN.playerTakeDamageMultiplier = 1/(1+0.1f*pl.stats.get("protection"));
		
		this.abilities = new BattleAbility[GameConstants.MAX_ACTIVE_ITEMS];
		this.equipment = new ItemType[GameConstants.MAX_PASSIVE_ITEMS];
		
		for(int i=0; i<GameConstants.MAX_ACTIVE_ITEMS; i++) {
			ItemType itype = ItemFactory.get(pl.inventory.getActiveItem(i));
			if(itype==null) {
				abilities[i] = null;
			} else {
				abilities[i] = new BattleAbility(itype.ability);
			}
		}
		for(int i=0; i<GameConstants.MAX_PASSIVE_ITEMS; i++) {
			ItemType itype = ItemFactory.get(pl.inventory.getPassiveItem(i));
			equipment[i] = itype;
		}
		
		this.dashAbility = new BattleAbility(new Ability(new DashForward(), dashCost, dashDuration, dashDuration, 0, 0, 0, 0, false));
		this.basicAttack = new BattleAbility(new Ability(new PlayerBasicAttack(), BattleConstants.BASIC_ATTACK_AP_COST, BattleConstants.PLAYER_BASIC_ATTACK_COOLDOWN, 0, 0, 0, BattleConstants.BASIC_ATTACK_DELAY, 0, false));
		this.retreatAbility = new BattleAbility(new Ability(new Retreat(), BattleConstants.RETREAT_AP_COST, 0, 0, 0, 0, BattleConstants.RETREAT_CHANNEL_DURATION, BattleConstants.RETREAT_CHANNEL_DURATION, true));
		
	}
	
	@Override
	public void update() {
		updateCreatureAndPlayer();
		
		// Every second, regen action points
		if(GameModel.MAIN.tickNum % (1000/GameConstants.GAME_SPEED) == 0) {
			ap += (int)Math.round(BattleConstants.PLAYER_AP_REGEN*maxAP);
			if(ap>maxAP) ap = maxAP;
		}
	}
}
