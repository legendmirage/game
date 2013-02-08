package battle.ai;

import model.GameModel;
import util.U;
import NetworkUtil.Action;
import ability.Ability;
import ability.effect.AbilityEffect;
import ability.effect.Projectile;
import ability.type.Projectile360;
import ability.type.RisingLava;
import battle.BattleCreature;
import battle.BattleModel;
import client.ActionType;


/**
 * An AI which moves towards the player until it reaches a certain distance. It shoots projectiles once in
 * range and uses shockwave when player is close by.
 * Main feature: Dodges projectiles thrown by player.
 *
 */
public class SauceAI extends AI {
	private boolean torching;
	
	public SauceAI(BattleCreature cr) {
		super(cr);
		this.torching = false;
	}

	@Override
	public void run() {
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		float dx = closestPlayer.mx()-cr.mx();
		float dy = closestPlayer.my()-cr.my();
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		
		if (!torching) {
			if (U.r()<0.05) {
				if (adx>cr.getWidth()*1.5) {
					if(dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					}
				} else if (adx<=cr.getWidth()*1.5) {
					if (dx<0) {
						act(ActionType.MOVE_RIGHT);
					} else {
						act(ActionType.MOVE_LEFT);
					}
				}
			}
		} else {
			if (U.r()<0.05) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			}
		}
		
		// Cast Torch
		if (U.r()<0.02) {
			if (adx<cr.getWidth()*1.5  && adx<1000) {
				act(ActionType.ABILITY, "0");
				torching = true;
				return;
			}
		}
		
		if (U.r()<0.05) {
			if (adx<1000) {
				act(ActionType.ABILITY, "1");
			}
		}
	}
}
