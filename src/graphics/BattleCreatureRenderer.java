package graphics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.GameModel;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.yaml.snakeyaml.Yaml;

import util.Loader;
import util.Logger;
import util.U;
import battle.BattleConstants;
import battle.BattleCreature;
import battle.BattleModel;
import battle.HitBox;
import battle.ImpulseForce;
import client.EpicGameContainer;
import enemy.EnemyFactory;


/**
 * This class encapsulates all the state watching required to render a player
 */
public class BattleCreatureRenderer implements RenderComponent {
	
	BattleCreature creature;
	
	private Animation ani_run;
	private Animation ani_idle;
	private Animation ani_dash;
	private Animation ani_hit;
	private Animation ani_jump, ani_land, ani_fall, ani_air;
	private Animation ani_melee;
	private Animation ani_death;
	private Animation ani_castStart;
	private Animation ani_cast;
    private Animation ani_castEnd;
    private Animation ani_specialStart;
    private Animation ani_special;
    private Animation ani_specialEnd;
    private LinkedHashMap<Animation, int[]> ani_steam;
    
    private int spriteWidth;
	private int spriteHeight;
	
	private boolean landingStun = false;
	private boolean attackChannel = false;
	private boolean justDying = false;
	
	private int deathTime = -1;
	
	/** private static Yaml loader */
    private static Yaml yaml;
    /**static steam map thing. Really should be a constant :/ **/
    private static Map<String, Object> steamMap;
	
    private static final int MAX_PERIOD_CHANGE = 10;
    private static final int MIN_PERIOD = 20;
    
    private static final int FIRST_LEVEL = 13;
    private static final int SECOND_LEVEL = 26;
    private static final int THIRD_LEVEL = 39;
    
    static {
    	yaml = new Yaml();
    }
    
	/**
	 * Loading all animations in here to suppress the unchecked warnings
	 */
	@SuppressWarnings("unchecked")
	private void loadAllAnimations(Map<String, Object> animationMap) {
		try {
			ani_run = loadAnimation((Map<String, Object>)animationMap.get("run"), "frames");
			ani_idle = loadAnimation((Map<String, Object>)animationMap.get("idle"), "frames");
			ani_dash = loadAnimation((Map<String, Object>)animationMap.get("dash"), "frames");
			ani_jump = loadAnimation((Map<String, Object>)animationMap.get("jump"), "frames");
			
			ani_fall = loadAnimation((Map<String, Object>)animationMap.get("fall"), "frames");
			ani_air = loadAnimation((Map<String, Object>)animationMap.get("air"), "frames");
			ani_land = loadAnimation((Map<String, Object>)animationMap.get("land"), "frames");
			ani_hit = loadAnimation((Map<String, Object>)animationMap.get("hit"), "frames");
			ani_melee = loadAnimation((Map<String, Object>)animationMap.get("melee"), "frames");
			//need to make this more robust
			ani_cast = loadAnimation((Map<String, Object>)animationMap.get("cast"), "frames");
			ani_castStart = loadAnimation((Map<String, Object>)animationMap.get("cast"), "startframes");
            ani_castEnd = loadAnimation((Map<String, Object>)animationMap.get("cast"), "endframes");
			ani_special = loadAnimation((Map<String, Object>)animationMap.get("special"), "frames");
	        ani_specialStart = loadAnimation((Map<String, Object>)animationMap.get("special"), "startframes");
	        ani_specialEnd = loadAnimation((Map<String, Object>)animationMap.get("special"), "endframes");
			ani_death = loadAnimation((Map<String, Object>)animationMap.get("death"), "frames");
			
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	private void loadAllComponents(Map<String, Object> animationMap){
	    
	    try {
            loadSteam((ArrayList<LinkedHashMap>)animationMap.get("steam"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
        
	}

	private void loadHitBoxes(ArrayList<LinkedHashMap> data){
		int xpos;
		int ypos;
	    int width;
	    int height;
	    boolean collision;
	    float multiplier;
	    if (data != null){
	    	for (LinkedHashMap<String, Object> d: data){
	    		xpos = 0;
	    		ypos = 0;
	    		width = 100;
	    		height = 100;
		        collision = true;
		        multiplier = (float) 1.0;
		        
		        if (d.containsKey("xpos") && d.get("xpos") != null){
		            xpos = ((Integer)d.get("xpos")).intValue();
		        }
		        if (d.containsKey("ypos") && d.get("ypos") != null){
	                ypos = ((Integer)d.get("ypos")).intValue();
	            }
		        if (d.containsKey("width") && d.get("width") != null){
	                width = ((Integer)d.get("width")).intValue() ;
	            }
		        
		        if (d.containsKey("height") && d.get("height") != null){
	                height = ((Integer) d.get("height")).intValue() ;
	            }
	            if (d.containsKey("collision") && d.get("collision") != null){
	                collision = ((Boolean)d.get("collision")).booleanValue();
	            }
	            if (d.containsKey("multiplier") && d.get("multiplier") != null){
	                multiplier = ((Number) d.get("multiplier")).floatValue();
	            }
	            this.creature.hitBoxes.add(new HitBox(xpos, ypos, width, height, collision, multiplier));
	            this.creature.facingRightHitBoxes.add(new HitBox(spriteWidth-xpos-width, ypos, width, height, collision, multiplier));
	        }
	    }
	}
	
	public BattleCreatureRenderer(BattleCreature p, Map<String, Object> animationMap) {
		this.creature = p;
		 
		try {
            steamMap = (Map)yaml.load(Loader.open("res/sprite/battle_steam.yaml"));
        } catch(Exception e) {
            Logger.err("steam YAML load failed");
            e.printStackTrace();
        }
		
		ani_steam = new LinkedHashMap<Animation, int[]>();
		loadAllAnimations(animationMap);
		// TODO: make this a little better
        // we infer sprite hight and width from the first idle frame.
        if(animationMap.containsKey("height")) {
            spriteHeight = (Integer)animationMap.get("height");
            spriteWidth = (Integer)animationMap.get("width");
            
        } else {
            spriteHeight = ani_idle.getImage(0).getHeight();
            spriteWidth = ani_idle.getImage(0).getWidth();
        }
        
        //once we know the size of the sprite, we load components
        if(creature.type != null)
        	loadAllComponents(animationMap);
        
		// set the jump delay
		// BattleConstants.JUMP_INITIAL_DELAY * 10 / 4);
		if(ani_jump != null)
			U.setAnimationSpeed(ani_jump, BattleConstants.JUMP_INITIAL_DELAY * 10 / (ani_jump.getFrameCount()-1));
		
		// TODO: make this code a lot more generic
		if(ani_jump != null) ani_jump.stopAt(ani_jump.getFrameCount());
		if(ani_fall != null) ani_fall.stopAt(ani_fall.getFrameCount());
		
		
		if (animationMap.containsKey("hitboxes")){
		    loadHitBoxes((ArrayList<LinkedHashMap>) animationMap.get("hitboxes"));
		} else {
			this.creature.hitBoxes.add(new HitBox(0, 0, spriteWidth, spriteHeight, true, 1));
			this.creature.facingRightHitBoxes.add(new HitBox(0, 0, spriteWidth, spriteHeight, true, 1));
		}
	}
	
	/** Get the sprite width */
	public int getSpriteWidth() {
		return this.spriteWidth;
	}
	
	/** Get the sprite height */
	public int getSpriteHeight() {
		return this.spriteHeight;
	}

	/**
	 * Attempt to load an animation from a given speed, frames map
	 * @param data
	 * @return new animation object with the pieces all loaded
	 * @throws SlickException
	 */
	private Animation loadAnimation(Map<String, Object> data, String type) throws SlickException {
		if(data == null) return null;
        if (!data.containsKey(type)) return null;
		int speed = (Integer)data.get("speed");
		@SuppressWarnings("unchecked")
		List<String> frames = (List<String>)data.get(type);
		
		Image[] imgs = new Image[frames.size()];
		for(int i=0; i<frames.size(); i++) {
			imgs[i] = new Image(frames.get(i));
		}
		
		return new Animation(imgs, speed);
	}
	
	/**
	 * Attempt to load component from a given speed, frames map~
	 * @param data
	 * @return
	 * @throws SlickException
	 */
	private void loadSteam(ArrayList<LinkedHashMap> aniList) throws SlickException {

	    if(aniList != null){
	        for (LinkedHashMap<String, Object> data: aniList){

	            int period = (int)(Math.random()*MAX_PERIOD_CHANGE*Math.PI+MIN_PERIOD);
	            String color = "blue";
	            
	            //note. Offsets are from the center of the sprite. Different from sprite rendering offsets
	            int steamX = spriteWidth/2;
	            int steamY = 0;
	            int r_steamX = spriteWidth/2;
	            //these were the original heights of the drawing. We can downsize if need be
	            int steamWidth = 47;
	            int steamHeight = 70;
	            if (creature.type.level< FIRST_LEVEL){
	            	color = "blue";
	            	
	            }
	            else if (creature.type.level< SECOND_LEVEL){
	            	color = "green";

	            }

	            else if (creature.type.level< THIRD_LEVEL){

	            	color = "yellow";
	            }
	            
	            else{
	            	color = "red";

	            }            
	          
	            if (data.containsKey("xoffset")){
	                steamX = spriteWidth/2 + ((Integer)data.get("xoffset")).intValue();
	            }
	            if (data.containsKey("yoffset")){
	                steamY = (Integer)data.get("yoffset");
	            }
	            if (data.containsKey("r_xoffset")){
	                r_steamX = spriteWidth/2 + ((Integer)data.get("r_xoffset")).intValue();

	            }
	            if (data.containsKey("width")){
	                steamWidth = (Integer)data.get("width");
	            }
	            if (data.containsKey("height")){
	                steamHeight = (Integer)data.get("height");
	            }

	            int[] steamInfo = new int[]{steamX, steamY, r_steamX, steamWidth, steamHeight, period};
	            Animation steamAni = loadAnimation((Map<String, Object>)steamMap.get(color), "frames");


	            if(steamAni != null){ 
	                ani_steam.put(steamAni, steamInfo);
	            }

	        }
	    }
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		Animation current = null;
		boolean resetAnimations = false;
		
		// If the creature is dead, compute the total transparency
		float totalAlpha = 1.0f;
		if(!creature.alive) {
			if(deathTime < 0) {
				deathTime = GameModel.MAIN.tickNum;
			}
			totalAlpha= U.alphaRamp(100, 20, deathTime);
		}
		
		// Draw all the steam components
		if (ani_steam != null)
		{
		    for(Animation steamAni: ani_steam.keySet()){
		        int[] aniValues = ani_steam.get(steamAni);
		        
		        // the order goes: steamX, steamY, r_steamX, steamWidth, steamHeight, period
		        //used to do little loop-de-loops with the steam. Might be a little excessive...
	        	double animultiplier = Math.sin(1/(double)(aniValues[5])*GameModel.MAIN.tickNum);
                double animultiplierTwo = Math.sin(2/(double)(aniValues[5])*GameModel.MAIN.tickNum);
                double transparencyMultiplier = Math.cos(0.1/(double)(aniValues[5])*GameModel.MAIN.tickNum);
                Color steamFilterColor = new Color(1.f, 1.f, 1.f, (float) Math.abs(transparencyMultiplier * totalAlpha)); 
                	
                if (creature.facingRight) {
		            steamAni.draw(creature.getRenderX() + aniValues[0]+(int)(10*animultiplierTwo), creature.getRenderY()+aniValues[1]+(int)(aniValues[1]/2*animultiplier), aniValues[3], aniValues[4], steamFilterColor);
                }
		        else
		        {
		            steamAni.draw(creature.getRenderX()+spriteWidth-aniValues[2]+ (int)(10*animultiplierTwo), creature.getRenderY()+aniValues[1]+(int)(aniValues[1]/2*animultiplier), -aniValues[3], aniValues[4], steamFilterColor);
		        }
		    }
		}
		
		// Compute the frames we should be drawing
		if(creature.spellChannel != null && creature.spellChannel.isChanneling()) {
		    if(creature.spellChannel.ab.type.animation == null)
		        current = ani_melee;
		    else if(creature.spellChannel.ab.type.animation.equals("cast"))
				current = ani_cast;
			else if(creature.spellChannel.ab.type.animation.equals("special"))
			    current = ani_special;
			else
				current = ani_melee;
			
			if(current != null && attackChannel == false) {
				int castTicks = creature.spellChannel.ab.type.channelDelay;
				current.setSpeed((float)current.getFrameCount()/(castTicks));
				current.restart();
				current.stopAt(current.getFrameCount());
			}
			attackChannel = true;
		}
		else if(creature.impulse != null) {
			if(creature.impulse.cause.equals(ImpulseForce.DASH)) current = ani_dash;
			else if(creature.impulse.cause.equals(ImpulseForce.JUMP_DELAY)) current = ani_jump;
			else if(creature.impulse.cause.equals(ImpulseForce.LANDING_DELAY)) {
				if(ani_land != null) {
					current = ani_land;
					if(landingStun == false) {
						int stunTicks = creature.impulse.stunDuration;
						current.setSpeed(4.0f/stunTicks);
						current.restart();
						current.stopAt(current.getFrameCount());
					}
					landingStun = true;
				}
				landingStun = true;
			} else if(creature.impulse.cause.equals(ImpulseForce.LANDING_DELAY)) {
				current = ani_hit;
			} else if(creature.impulse.cause.equals(ImpulseForce.ASSASSINATE)) {
				current = ani_dash;
			}
		}
		else if(creature.inAir) {
			if (creature.type != null && creature.type.flying){
				if(creature.tryingToMoveLeft || creature.tryingToMoveRight)
					current = ani_run;
				else	
					current = ani_idle;
			}
			else{
				if(creature.vy > 0 ) current = ani_fall;
				else current = ani_air;
			}
		}
		else if(creature.tryingToMoveLeft || creature.tryingToMoveRight){
			current = ani_run;
			resetAnimations = true;
		}
		else {
			// play our idle animations
			current = ani_idle;
			resetAnimations = true;
		}
		
		if(resetAnimations) {
			// reset our once played animations
			landingStun = false;
			attackChannel = false;
			
			if(ani_jump != null)  {
				ani_jump.setCurrentFrame(0);
				ani_jump.restart();
				ani_jump.stop();
			}
		}
		
		if(current == null) {
			current = ani_idle;
		}
		
		if(!creature.alive && ani_death != null) {
			if(justDying == false) {
				justDying = true;
				ani_death.setCurrentFrame(0);
				ani_death.stopAt(ani_death.getFrameCount()-1);
				ani_death.start();
			} 
			current = ani_death;
		}

		
		// If we have a frame, draw it
		if(current != null) {
			if(!justDying)
				current.start();
			
			Color filterColor = new Color(1f,1f,1f,totalAlpha);
			if(creature.facingRight) {
				current.draw(creature.getRenderX(), creature.getRenderY(), spriteWidth, spriteHeight, filterColor);
			}
			else {
				current.draw(creature.getRenderX()+spriteWidth, creature.getRenderY(), -spriteWidth, spriteHeight, filterColor);
			}
		}
		
		// Draw health bar on top of monster
		int hp_w = 60;
		int hp_h = 8;
		int hp_y = 20;
		int padding = 2;
		int hp_div = 1000;
		int ybar = (int)creature.getScreenY() - hp_y;
		
		float p = (creature.hp/(float)creature.maxHP);
		g.setColor(new Color(.4f, .4f, .4f, .7f*totalAlpha));
		g.fillRoundRect(creature.mx()-hp_w/2-2*padding, ybar-padding, hp_w+4*padding, hp_h+ 2*padding, 5);
	
		if(creature.id == GameModel.MAIN.player.id) g.setColor(new Color(0f, 0f, 1f, .8f*totalAlpha));
		else g.setColor(new Color(1f, 0f, 0f, .8f*totalAlpha));
		g.fillRect(creature.mx()-hp_w/2, ybar, p*hp_w, hp_h);
		
		int bars = creature.maxHP / hp_div;
		for(int i = bars; i >=0; i--) {
			int xbar = (int)(creature.mx() - hp_w/2 +  hp_w * (i/(float)bars));
			g.setColor(new Color(.3f, .3f, .3f, .8f*totalAlpha));
			g.setLineWidth(1.0f);
			g.drawLine(xbar, ybar, xbar, ybar+hp_h);
		}
		
		
	
		// Draw all debug components
		if(EpicGameContainer.MAIN.debugDraw) {
			g.setColor(Color.red);
			g.drawString(creature.toString(), creature.getScreenX() , creature.getScreenY()-32);
//			g.drawString("PX:"+creature.getPX()+"PY:"+creature.getPY(), creature.getPX(), creature.getPY());
			
			if(creature.spellChannel != null && creature.spellChannel.isStunned()) g.setColor(Color.green);
			if(creature.spellChannel != null && creature.spellChannel.isChanneling()) g.setColor(Color.yellow);
			if(creature.impulse!= null && creature.impulse.isStunInEffect()) g.setColor(Color.blue);
			
			if(creature.facingRight) {
				for (HitBox hb : creature.facingRightHitBoxes) {
					if (hb.damageMultiplier>0) {
						g.setColor(Color.orange);
					} else {
						g.setColor(Color.red);
					}
					g.drawRect(hb.getScreenX(), hb.getScreenY(), hb.width, hb.height);
				}
				g.setColor(Color.pink);
				g.drawRect(creature.getRenderX(), creature.getRenderY(), spriteWidth, spriteHeight);
			} else {
				for (HitBox hb : creature.hitBoxes) {
					if (hb.damageMultiplier>0) {
						g.setColor(Color.orange);
					} else {
						g.setColor(Color.red);
					}
					g.drawRect(hb.getScreenX(), hb.getScreenY(), hb.width, hb.height);
				}
				g.setColor(Color.pink);
				g.drawRect(creature.getRenderX()+spriteWidth, creature.getRenderY(), -spriteWidth, spriteHeight);
			}
			return;
		}
		
	}
	
}
