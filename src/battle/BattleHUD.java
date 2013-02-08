package battle;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import client.EpicGameContainer;
import content.BattleMapLoader;

/** This class draws the Heads-Up Display in battle mode. <br>
 * This is the part of the GUI that deals with health bars, timers, help text, cursor, guiding arrows, etc. <br>
 * 
 */
public class BattleHUD {
	BattleModel model;
	
	public BattleHUD(BattleModel model) {
		this.model = model;
	}
	
	
	public void render(GameContainer gc, Graphics g) {
		int myID = EpicGameContainer.MAIN.myID;
		BattlePlayer myPl = (BattlePlayer)model.creatures.get(myID);
		int hp = (int)(235.0 * myPl.hp / myPl.maxHP);
		int ap = (int)(235.0 * myPl.ap / myPl.maxAP);
		

        if (myPl.abilities[0]!=null){
            Image abilityQ = myPl.abilities[0].type.image;

            g.drawImage(abilityQ, 593, 0);
            renderCooldown(gc,g,592.0f, -15.0f, 0);
        }
        if (myPl.abilities[1]!=null){
            //Image abilityW = myPl.abilities[1].type.image;
            Image abilityW = myPl.abilities[1].type.image;
            g.drawImage(abilityW, 721,0);
            renderCooldown(gc,g,721.0f, -15.0f, 1);
        }
        if (myPl.abilities[2]!=null){
            //Image abilityE = myPl.abilities[2].type.image;
            Image abilityE = myPl.abilities[2].type.image;
        
            g.drawImage(abilityE, 853, 0);
            renderCooldown(gc,g,852.0f, -15.0f, 2);
        }

		
		g.drawImage(BattleMapLoader.battleHUD, 0, 0); //Putting BattleHUD in a factory seems a bit unnecessary...?

		g.setColor(Color.blue);
		//
		g.fillRect(7, 18, hp, 24);
		g.setColor(Color.yellow);
		//
		g.fillRect(307, 18, ap, 24);

		
		g.drawImage(BattleMapLoader.battleHUDTopLayer, 0, 0); //Putting BattleHUD in a factory seems a bit unnecessary...?
		//animate this?
		g.drawImage(BattleMapLoader.battleGear, 950, -55);
	
	}
	
	private void renderCooldown(GameContainer gc, Graphics g, float x, float y, int abilityNum){

		int myID = EpicGameContainer.MAIN.myID;
		BattlePlayer myPl = (BattlePlayer)model.creatures.get(myID);
		int total = myPl.abilities[abilityNum].type.cooldown;
		int left = GameModel.MAIN.tickNum-myPl.abilities[abilityNum].tickLastUsed;
		if (left>total) left = total;
		g.setColor(new Color(255,0,255,70));
		g.fillArc(x+12, y+23, 85.0f, 85.0f, 0.0f, (float)(360*left/total));
		
	}
	
}
