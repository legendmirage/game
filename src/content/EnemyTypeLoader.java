package content;

import ability.Ability;
import ability.type.Assassinate;
import ability.type.Bind;
import ability.type.Blow;
import ability.type.ChainLightning;
import ability.type.FireStorm;
import ability.type.GiantLaserBeam;
import ability.type.GrappleHook;
import ability.type.HailStorm;
import ability.type.IceSpikes;
import ability.type.Kamikaze;
import ability.type.KnockbackNearbyEnemies;
import ability.type.MeleeAttack;
import ability.type.Mining;
import ability.type.Projectile360;
import ability.type.Projectile360Reloaded;
import ability.type.ProjectileReloaded;
import ability.type.RisingLava;
import ability.type.SeekingWind;
import ability.type.Shield;
import ability.type.ShootGuidedProjectile;
import ability.type.ShootLaser;
import ability.type.ShootStraightProjectile;
import ability.type.Taunt;
import ability.type.Torch;
import battle.ai.AI;
import battle.ai.AlexAI;
import battle.ai.ArachneAI;
import battle.ai.AureliaAI;
import battle.ai.AxeFighterAI;
import battle.ai.BellAI;
import battle.ai.BookmanAI;
import battle.ai.CaracalAI;
import battle.ai.CoryAI;
import battle.ai.CreepyAI;
import battle.ai.DustlingAI;
import battle.ai.GilgameshAI;
import battle.ai.HermanAI;
import battle.ai.InfernoAI;
import battle.ai.JackieAI;
import battle.ai.KaivanAI;
import battle.ai.KumaChanAI;
import battle.ai.LucasAI;
import battle.ai.MakerAI;
import battle.ai.NightShadeAI;
import battle.ai.OgreAI;
import battle.ai.RukhAI;
import battle.ai.RuwenAI;
import battle.ai.SauceAI;
import battle.ai.ShannonAI;
import battle.ai.ShieldFighterAI;
import battle.ai.TeaCupAI;
import battle.ai.TutorialAI;
import battle.ai.VagrantAI;
import battle.ai.VegaAI;
import battle.ai.WheeleyAI;
import enemy.EnemyFactory;

public class EnemyTypeLoader {
	
//  Normal Monsters	
//	Dustling
//	Tea Cup
//	Vagrant
//	Shield Fighter
//	Axe Fighter
//	Ogre
//	Wheeley
//	Herman
//	Bell
//	Caracal
//	KumaChan
//  Tree
//
//	Bosses
//	Caracal
//	Sauce
//	Aurelia
//	Alex
//	Rukh
//	Shannon
//	Cory
//	Ruwen
//	Kaivan
	
	public static void loadAll() {
		
		/* ------------------ REGULAR MONSTERS ----------------- */
		
		for(int i=0; i<=3; i++) {
			x("Dustling"+i, 0.6, 22, new int[]{2,8,19,29}[i], 5);
			o(new Ability(new MeleeAttack(), 0, 20, new int[]{2,5,12,19}[i], 50, 0, 0, 5, false, "melee"));
			o(DustlingAI.class);
			o("res/sprite/rat_sprites.png", "res/sprite/battle_rat.yaml");
		}
		
		for(int i=0; i<=3; i++) {
			x("Vagrant"+i, 1, 16, new int[]{3,10,20,30}[i], 6);
			o(600);
			o(new Ability(new MeleeAttack(), 0, 20, new int[]{3,7,15,40}[i], 90, 0, 20, 0, false, "melee"));
			o(new Ability(new ShootStraightProjectile(), 0, 50, 400, 5, 900, 20, 0, false, "melee"));
			o(VagrantAI.class);
			o("res/sprite/goblin_sprites.png", "res/sprite/battle_goblin.yaml");
		}
		
		for(int i=0; i<=2; i++) {
			x("Shield"+i, 2, 24, new int[]{13,23,33}[i], 10);
			o(new Ability(new MeleeAttack(), 0, 30, new int[]{15,30,70}[i], 70, 0, 20, 0, false, "melee"));
			o(ShieldFighterAI.class);
			o("res/sprite/goblin_sprites.png", "res/sprite/battle_shieldFighter.yaml");
		}
		
		for(int i=0; i<=3; i++) {
			x("Axe"+i, 0.6, 18, new int[]{4,11,21,31}[i], 7);
			o(600);
			o(new Ability(new MeleeAttack(), 0, 20, new int[]{5,15,40,120}[i], 70, 0, 20, 0, false, "melee"));
			o(AxeFighterAI.class);
			o("res/sprite/goblin_sprites.png", "res/sprite/battle_axeFighter.yaml");
		}
		
		for(int i=0; i<=1; i++) {
			x("Ogre"+i, 1.2, 35, new int[]{15,22}[i], 12);
			o(new Ability(new KnockbackNearbyEnemies(), 0, 50, 250, new int[]{10,20}[i], 700, 20, 20, false));
			o(new Ability(new ShootGuidedProjectile(), 0, 80, 200, new int[]{30,70}[i], 1500, 20, 0, false, "melee"));
			o(OgreAI.class);
			o("res/sprite/robot_sprites.png", "res/sprite/battle_ogre.yaml");
		}
		
		for(int i=0; i<=1; i++) {
			x("TeaCup"+i, 0.5, 13, new int[]{10,40}[i], 2);
			o(new Ability(new Kamikaze(), 0, 20, 100, new int[]{50,500}[i], 1000, 5, 0, false));
			o(TeaCupAI.class);
			o("flying");
			o("res/sprite/rat_sprites.png", "res/sprite/battle_teacup.yaml");
		}
		
		for(int i=0; i<=0; i++) {
			x("Bookman"+i, 1.5, 28, 41, 8);
			o(new Ability(new IceSpikes(), 0, 25, 300, 80, 800, 20, 0, false));
			o(new Ability(new Taunt(), 0, 30, 400, 1.0f, 0.0f, 20, 0, false));
			o("flying");
			o(BookmanAI.class);
			o("res/sprite/robot_sprites.png", "res/sprite/battle_bookman.yaml");
		}
		
		for(int i=0; i<=0; i++) {
			x("Creepy"+i, 0.6, 40, 7, 3);
			o(new Ability(new MeleeAttack(), 0, 20, 5, 50, 0, 0, 5, false, "melee"));
			o(CreepyAI.class);
			o("res/sprite/rat_sprites.png", "res/sprite/battle_creepy.yaml");
		}

		for(int i=0; i<=1; i++) {
			x("Kuma"+i, 0.3, 10, new int[]{15,38}[i], 13);
			o(800);
			o(new Ability(new FireStorm(), 0, 30, 200, new int[]{50,500}[i], 0, 20, 0, false));
			o(new Ability(new HailStorm(), 0, 30, 350, new int[]{10,100}[i], 0.05f, 20, 0, false));
			o(KumaChanAI.class);
			o("res/sprite/goblin_sprites.png", "res/sprite/battle_kumaChan.yaml");
		}
		
		for(int i=0; i<=0; i++) {
	        x("Wheeley"+i, 0.7, 16, 28, 8);
	        o(600);
	        o(new Ability(new ShootStraightProjectile(), 0, 30, 500, 25, 500, 20, 0, false));
	        o(WheeleyAI.class);
	        o("res/sprite/goblin_sprites.png", "res/sprite/battle_wheely.yaml");
		}
		
		for(int i=0; i<=0; i++) {
			x("Herman"+i, 2, 35, 25, 8);
			o(600);
			o(new Ability(new SeekingWind(), 0, 20, 50, 1000, 0, 20, 0, false));
			o(new Ability(new Mining(), 0, 30, 50, 10, 0, 20, 0, false));
			o(HermanAI.class);
			o("res/sprite/goblin_sprites.png", "res/sprite/battle_herman.yaml");
		}
		
		for(int i=0; i<=2; i++) {
			x("Caracal"+i, 1, 14, new int[]{24,34,42}[i], 10);
			o(600);
			o(new Ability(new ShootLaser(), 0, 30, 100, new int[]{200,500,1000}[i], 600, 10, 0, false));
			o(new Ability(new Bind(), 0, 30, 300, 250, 0.5f, 10, 0, false));
			o(CaracalAI.class);
			o("res/sprite/goblin_sprites.png", "res/sprite/battle_caracal.yaml");
		}
		
		for(int i=0; i<=0; i++) {
			x("Bell"+i, 1, 50, 1, 1);
			o(700);
			o(new Ability(new ShootStraightProjectile(), 0, 20, 400, 1, 500, 20, 0, false));
			o(BellAI.class);
			o("res/sprite/goblin_sprites.png", "res/sprite/battle_bell.yaml");
		}
		
		for(int i=0; i<=1; i++) {
			x("NightShade"+i, 0.7, 40, new int[]{34,39}[i], 12);
			o(new Ability(new Projectile360(), 0, 40, 500, new int[]{20,40}[i], 24, 20, 0, false));
			o(new Ability(new KnockbackNearbyEnemies(), 0, 30, 250, new int[]{30,50}[i], 250, 20, 0, false));
			o(NightShadeAI.class);
			o("res/sprite/robot_sprites.png", "res/sprite/battle_tree.yaml");
		}
		
		/* --------------------- BOSSES -------------------- */
		
		x("Sauce", 1.5, 16, 13, 15);
		o(new Ability(new Torch(), 0, 500, 7, 0, 0, 20, 0, false));
		o(new Ability(new Projectile360(), 0, 200, 400, 5, 20, 25, 0, false));
		o(SauceAI.class);
		o("res/sprite/robot_sprites.png", "res/sprite/battle_sauce.yaml");
		o("ruby", 1);
		
		x("Rukh", 2, 24, 16, 16);
		o("flying");
		o(new Ability(new ProjectileReloaded(), 0, 40, 5, 15, 900, 20, 0, false));
		o(new Ability(new Shield(), 0, 30, 100, 0, 0, 30, 0, false));
		o(RukhAI.class);
		o("res/sprite/robot_sprites.png", "res/sprite/battle_rukh.yaml");
		o("aquamarine", 1);
		
		x("Aurelia", 1, 35, 18, 17);
		o(new Ability(new KnockbackNearbyEnemies(), 0, 50, 250, 10, 900, 20, 20, false));
        o(new Ability(new HailStorm(), 0, 0, 2000, 3, 0.90f, 0, 0, false));
		o(new Ability(new FireStorm(), 0, 160, 200, 50, 0, 100, 0, false));
		o(new Ability(new ShootGuidedProjectile(), 0, 20, 400, 25, 1500, 20, 0, false, "melee"));
		o(AureliaAI.class);
        o("res/sprite/robot_sprites.png", "res/sprite/battle_aurelia.yaml");
        o("platinum", 1);
		
        x("Lucas", 0.8, 18, 16, 17);
		o(600);
		o(new Ability(new Blow(), 0, 40, 600, 50, 100, 20, 0, false));
		o(new Ability(new KnockbackNearbyEnemies(), 0, 40, 400, 50, 800, 20, 0, false));
		o(new Ability(new SeekingWind(), 0, 40, 200, 1000, 0, 20, 0, false));
		o(LucasAI.class);
		o("res/npcs/lucas_sprites.png", "res/sprite/battle_lucas.yaml");
		o("quartz", 1);
		
		x("Ruwen", 0.5, 40, 18, 17);
		o(new Ability(new ShootGuidedProjectile(), 0, 20, 100, 50, 1500, 20, 0, false, "melee"));
		o(new Ability(new KnockbackNearbyEnemies(), 0, 50, 250, 10, 900, 20, 20, false));
		o(new Ability(new HailStorm(), 0, 30, 250, 7, 0.25f, 150, 0, false));
		o("flying");
		o(RuwenAI.class);
		o("res/sprite/robot_sprites.png", "res/sprite/battle_ruwen.yaml");
		o("cirrus steam", 1);
		
		x("Arachne", 1, 28, 18, 19);
		o(ArachneAI.class);
		o(new Ability(new ShootLaser(), 0, 30, 100, 1000, 700, 30, 0, false));
		o(new Ability(new HailStorm(), 0, 30, 500, 25, 0.05f, 30, 0, false));
		o(new Ability(new Projectile360(), 0, 30, 300, 25, 20, 30, 0, false));
		o("flying");
		o("res/sprite/robot_sprites.png", "res/sprite/battle_arachne.yaml");
		o("ultimate health potion", 1);
		
		x("Vega", 0.9, 28, 24, 17);
		o(new Ability(new GrappleHook(), 0, 20, 1000, 750, 50, 30, 0, false));
		o(new Ability(new KnockbackNearbyEnemies(), 0, 20, 250, 50, 1000, 30, 0, false));
		o(VegaAI.class);
		o("flying");
		o("res/sprite/robot_sprites.png", "res/sprite/battle_vega.yaml");
		o("strong elixir of speed", 1);
		
        x("Jackie", 0.8, 18, 32, 17);
        o(700);
        o(new Ability(new Torch(), 0, 40, 100, 0, 0, 20, 0, false));
        o(new Ability(new GrappleHook(), 0, 30, 800, 350, 50, 30, 0, false));
        o(JackieAI.class);
        o("res/tilemap/Jackie_sprites.png", "res/sprite/battle_jackie.yaml");
        o("strong elixir of protection", 1);
		
		x("Shannon", 0.8, 18, 32, 16);
		o(new Ability(new Assassinate(), 0, 40, 400, 60, 0, 20, 0, false));
        o(new Ability(new FireStorm(), 0, 30, 75, 100, 0, 30, 0, false));
        o(new Ability(new ShootGuidedProjectile(), 0, 30, 300, 50, 800, 30, 0, false));
		o(ShannonAI.class);
		o(700);
		o("res/sprite/shannon_sprites.png", "res/sprite/battle_shannon.yaml");
		o("strong elixir of wisdom", 1);
		
		x("Kaivan", 1.2, 14, 35, 19);
		o(new Ability(new GiantLaserBeam(), 0, 30, 1000, 200, 190, 20, 0, false));
		o(new Ability(new ChainLightning(), 0, 30, 25, 300, 0, 30, 0, false));
		o(new Ability(new ProjectileReloaded(), 0, 30, 20, 25, 800, 30, 0, false));
		o(KaivanAI.class);
		o(700);
		o("res/sprite/robot_sprites.png", "res/sprite/battle_kaivan.yaml");
		o("unwritten steam", 1);

		x("Gilgamesh", 1, 28, 46, 16);
		o(new Ability(new ProjectileReloaded(), 0, 30, 100, 20, 150, 30, 0, false));
		o(new Ability(new GrappleHook(), 0, 30, 1000, 350, 300, 30, 0, false));
		o("flying");
		o(GilgameshAI.class);
		o("res/sprite/robot_sprites.png", "res/sprite/battle_gilgamesh.yaml");
		o("potent elixir of vitality", 1);
		
		x("Inferno", 1.2, 20, 46, 16);
		o(InfernoAI.class);
		o("flying");
		o(new Ability(new ShootGuidedProjectile(), 0, 20, 1000, 500, 1500, 20, 0, false, "melee"));
		o(new Ability(new FireStorm(), 0, 50, 100, 1000, 100, 200, 0, false));
		o(new Ability(new RisingLava(), 0, 500, 50, 500, 200, 20, 0, false));
		o("res/sprite/robot_sprites.png", "res/sprite/battle_inferno.yaml");
		o("potent elixir of power", 1);
		
		x("Alex", 1, 13, 48, 17);
		o(new Ability(new Projectile360(), 0, 40, 700, 200, 24, 20, 0, false));
		o(new Ability(new GiantLaserBeam(), 0, 40, 200, 50, 24, 20, 0, false));
		o(new Ability(new IceSpikes(), 0, 25, 300, 41, 800, 20, 0, false));
		o(AlexAI.class);
		o("res/sprite/robot_sprites.png", "res/sprite/battle_alex.yaml");
		o("unwritten steam", 1);
		
		x("Cory", 0.7, 13, 48, 17);
		o(new Ability(new Assassinate(), 0, 40, 300, 500, 0, 20, 0, false));
		o(new Ability(new KnockbackNearbyEnemies(), 0, 40, 160, 100, 800, 20, 0, false));
		o(new Ability(new ChainLightning(), 0, 20, 100, 300, 0, 20, 0, false));
		o("flying");
		o(CoryAI.class);
		o("res/sprite/robot_sprites.png", "res/sprite/battle_cory.yaml");
		o("unwritten steam", 1);
		
		x("MakerOne", 0.7, 13, 50, 17);
		o(new Ability(new Projectile360Reloaded(), 0, 30, 10, 20, 20, 30, 0, false));
		o(MakerAI.class);
		o("flying");
		o("res/sprite/robot_sprites.png", "res/sprite/battle_MoL1.yaml");
		
		x("MakerTwo", 0.7, 13, 50, 17);
		o(new Ability(new Projectile360Reloaded(), 0, 30, 10, 20, 20, 30, 0, false));
		o(new Ability(new ChainLightning(), 0, 30, 10, 800, 0, 30, 0, false));
		o(new Ability(new GrappleHook(), 0, 30, 700, 400, 0, 30, 0, false));
		o(new Ability(new GiantLaserBeam(), 0, 30, 500, 200, 200, 30, 0, false));
		o(new Ability(new FireStorm(), 0, 30, 200, 500, 0, 30, 0, false));
		o(MakerAI.class);
		o("flying");
		o("res/sprite/robot_sprites.png", "res/sprite/battle_MoL2.yaml");
		
		x("MakerThree", 0.7, 13, 50, 17);
		o("res/sprite/robot_sprites.png", "res/sprite/battle_MoL3.yaml");
		
		/*---------------------Tutorial------------------------*/
		x("KaivanTutorial", 0.6, 22, 15, 10);
		o(TutorialAI.class);
		o(new Ability(new ShootStraightProjectile(), 0, 30, 200, 1, 900, 30, 0, false));
		o("res/sprite/goblin_sprites.png", "res/sprite/battle_goblin.yaml");
	}
	
	private static String s1;
	private static void x(String name, double maxHPMultiplier, int overworldSpeed, int level, int power) {
		double maxHP = Math.pow(1.25, Math.pow(level, 0.85));
		if(power<=10) 
			maxHP*=power;
		else
			maxHP*=10+Math.pow(1.5,power-10);
		EnemyFactory.create(name, (int)(maxHP*maxHPMultiplier), 
				overworldSpeed, level, power);
		s1=name;
	}
	private static void o(String flag) {
		if(flag.toLowerCase().contains("flying")) EnemyFactory.makeFlying(s1);
	}
	private static void o(Class<? extends AI> ai) {
		EnemyFactory.setAI(s1, ai);
	}
	private static void o(String spriteSheet, String battleSheet) {
		EnemyFactory.addSpriteSheet(s1, spriteSheet);
		EnemyFactory.addBattleSheet(s1, battleSheet);
	}
	private static void o(Ability ability) {
		EnemyFactory.addAbility(s1, ability);
	}
	private static void o(int jumpVelocity) {
		EnemyFactory.makeJumping(s1, jumpVelocity);
	}
	private static void o(String itemName, double probability) {
		EnemyFactory.addDrop(s1, itemName, probability);
	}
	
	/** Cannot be instantiated. */
	private EnemyTypeLoader() {}
}
