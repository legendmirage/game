package content;

import item.ItemFactory;
import item.component.CheaperAirJump;
import item.component.CheaperDash;
import item.component.CritChance;
import item.component.DamageNearbyEnemies;
import item.component.HigherJump;
import item.component.IncreasedAP;
import item.component.IncreasedBasicAttackDamage;
import item.component.IncreasedMoveSpeed;
import item.component.ItemComponent;
import item.component.LongerDash;
import item.component.MoreAirJumps;
import item.component.SlowNearbyEnemies;
import item.component.StunResistance;
import item.component.TakeLessDamage;
import item.use.Heal;
import item.use.ItemUse;
import item.use.PermanentAttributeIncrease;
import org.newdawn.slick.SlickException;
import ability.Ability;
import ability.type.AbilityType;
import ability.type.Assassinate;
import ability.type.Blow;
import ability.type.ChainLightning;
import ability.type.DeepFreeze;
import ability.type.FireStorm;
import ability.type.HailStorm;
import ability.type.IceShield;
import ability.type.IceSpikes;
import ability.type.KnockbackNearbyEnemies;
import ability.type.Propel;
import ability.type.SeekingWind;
import ability.type.ShootLastingBeam;
import ability.type.ShootStraightProjectile;
import ability.type.Taunt;
import ability.type.Torch;

/** This class loads all the items. */
public class ItemTypeLoader {
	private static String loadingItemName;
	
	public static void loadAll() throws SlickException {
		
		/* ------------------------ Raw ingredients ------------------------*/
		
		// Gems
		
		x("Ruby");
		x("Aquamarine");
		x("Quartz");
		
		// Steam
		
		x("Steam");
		x("Cirrus Steam");
		x("Nacreous Steam");
		
		// Metal
		
		x("Scrap Metal");
		x("Platinum");
		x("Iridium");
		
		// Containers
		
		x("Bottle");
		x("Big Bottle");
		x("Huge Bottle");
		x("Vial");
		x("Big Vial");
		x("Huge Vial");
		x("Flask");
		
		// Special 
		x("Echidna Fang");
		x("unwritten steam");
		
		
		/* --------------------------- Potions -----------------------------*/
		
		// Potions (they heal you)
		
		x("Health Potion",10);
		o(new Heal(200));
		
		x("Greater Health Potion",20);
		o(new Heal(500));
		
		x("Super Health Potion");
		o(new Heal(2000));
		
		x("Ultra Health Potion");
		o(new Heal(5000));
		
		x("Full Health Potion");
		o(new Heal(1000000));
		
		// Elixirs (they give you permanent stat boosts)
		
		for(String s: new String[] {"Vitality", "Wisdom", "Speed", "Power", "Protection"}) {
			x("Elixir of "+s);
			o(new PermanentAttributeIncrease(s, 1));
			x("Strong Elixir of "+s);
			o(new PermanentAttributeIncrease(s, 3));
			x("Potent Elixir of "+s);
			o(new PermanentAttributeIncrease(s, 10));
		}
		
		
		// Draughts (they give you temporary buffs)
		
		
		
		/* --------------------------- Charms -----------------------------*/
		
		x("Charm of Aerobics");
		o(new HigherJump(100));
		o(new MoreAirJumps(1));
		x("Ancient Charm of Aerobics");
		o(new HigherJump(200));
		o(new MoreAirJumps(3));
		x("Legendary Charm of Aerobics");
		o(new HigherJump(300));
		o(new MoreAirJumps(9));
		
		x("Charm of Agility");
		o(new IncreasedMoveSpeed(1.25f));
		x("Ancient Charm of Agility");
		o(new LongerDash(25));
		o(new IncreasedMoveSpeed(1.5f));
		x("Legendary Charm of Agility");
		o(new LongerDash(50));
		o(new IncreasedMoveSpeed(1.75f));
		
		x("Charm of Chilling Winds");
		o(new SlowNearbyEnemies(300, 0.7f));
		x("Ancient Charm of Chilling Winds");
		o(new SlowNearbyEnemies(300, 0.5f));
		x("Legendary Charm of Chilling Winds");
		o(new SlowNearbyEnemies(500, 0.5f));
		
		x("Charm of Energy");
		o(new IncreasedAP(25));
		x("Ancient Charm of Energy");
		o(new IncreasedAP(50));
		x("Legendary Charm of Energy");
		o(new IncreasedAP(75));
		
		x("Charm of Fire Wall");
		o(new DamageNearbyEnemies(200, 5));
		x("Ancient Charm of Fire Wall");
		o(new DamageNearbyEnemies(300, 20));
		x("Legendary Charm of Fire Wall");
		o(new DamageNearbyEnemies(400, 100));
		
		x("Charm of Mobility");
		o(new CheaperDash(20));
		o(new CheaperAirJump(20));
		x("Ancient Charm of Mobility");
		o(new CheaperDash(15));
		o(new CheaperAirJump(15));
		x("Legendary Charm of Mobility");
		o(new CheaperDash(10));
		o(new CheaperAirJump(10));
		
		x("Charm of Potency");
		o(new CritChance(0.5f, 2));
		x("Ancient Charm of Potency");
		o(new CritChance(0.2f, 5));
		x("Legendary Charm of Potency");
		o(new CritChance(0.05f, 25));
		
		x("Charm of Resistance");
		o(new TakeLessDamage(2));
		x("Ancient Charm of Resistance");
		o(new TakeLessDamage(10));
		x("Legendary Charm of Resistance");
		o(new TakeLessDamage(50));
		
		x("Charm of Steadiness");
		o(new StunResistance(10));
		x("Ancient Charm of Steadiness");
		o(new StunResistance(25));
		x("Legendary Charm of Steadiness");
		o(new StunResistance(100));
		
		x("Charm of Weaponry");
		o(new IncreasedBasicAttackDamage(10));
		x("Ancient Charm of Weaponry");
		o(new IncreasedBasicAttackDamage(40));
		x("Legendary Charm of Weaponry");
		o(new IncreasedBasicAttackDamage(200));
		
		
		
		/* --------------------------- Jewels -----------------------------*/
		
		// Fire abilities
		
		s("Ruby of Fireballs", ShootStraightProjectile.class, 10, 20, 10, 0, false);
		t(900, 4, 40);
		t(900, 20, 50);
		t(900, 100, 60);
		
		s("Ruby of Rage", Taunt.class, 10, 100, 10, 0, false);
		t(200, 0.5f, 1.0f);
		t(400, 0.5f, 0.5f);
		t(600, 1.0f, 0.5f);
		
		s("Ruby of Burning", Torch.class, 10, 30, 10, 0, false);
		t(5, 0, 0);
		t(25, 0, 0);
		t(100, 0, 0);
		
		s("Ruby of Samurai", Assassinate.class, 40, 50, 10, 0, false);
		t(200, 10, 0);
		t(225, 50, 0);
		t(250, 250, 0);
		
		s("Flawless Ruby of the Storm", FireStorm.class, 100, 200, 100, 100, true);
		t(300, 500, 0);
		
		// Water abilities
		
		s("Aquamarine of Streams", ShootLastingBeam.class, 20, 100, 10, 0, false);
		t(200, 10, 95);
		t(300, 50, 95);
		t(300, 250, 195);
		
		s("Aquamarine of Needles", IceSpikes.class, 10, 30, 10, 0, false);
		t(500, 3, 100);
		t(500, 15, 120);
		t(500, 75, 140);
		
		s("Aquamarine of Hail", HailStorm.class, 50, 10, 100, 100, true);
		t(150, 4, 0.025f);
		t(200, 10, 0.05f);
		t(200, 25, 0.1f);
		
		s("Aquamarine of Warding", IceShield.class, 50, 25, 25, 0, false);
		t(0.5f, 0, 0);
		t(0.25f, 0, 0);
		t(0.1f, 0, 0);
		
		s("Flawless Aquamarine of the Deep", DeepFreeze.class, 80, 200, 100, 100, true);
		t(200, 500, 0);
		
		// Wind abilities
		
		s("Quartz of Repel", KnockbackNearbyEnemies.class, 30, 50, 50, 25, false);
		t(200, 5, 250);
		t(200, 20, 500);
		t(250, 100, 750);
		
		s("Quartz of Lightning", ChainLightning.class, 15, 10, 10, 0, false);
		t(8, 250, 0);
		t(20, 500, 0);
		t(50, 750, 0);
		
		s("Quartz of Seeking", SeekingWind.class, 20, 50, 10, 0, false);
		t(10, 1000, 0);
		t(50, 1000, 0);
		t(200, 2000, 0);
		
		s("Quartz of Propulsion", Propel.class, 50, 100, 50, 50, true);
		t(150, 1200, 40);
		t(250, 1200, 40);
		t(250, 1500, 60);
		
		s("Flawless Quartz of the Gust", Blow.class, 50, 50, 50, 50, true);
		t(1000, 100, 50);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static String s1;
	private static Class<? extends AbilityType> s2;
	private static int s3;
	private static int s4;
	private static int s5;
	private static int s6;
	private static boolean s7;
	private static int s8;
	private static void s(String jewelName, Class<? extends AbilityType> type,
			int apCost, int cooldown, int channelDelay, int stunDelay, boolean interruptable) {
		s1 = jewelName;
		s2 = type;
		s3 = apCost;
		s4 = cooldown;
		s5 = channelDelay;
		s6 = stunDelay;
		s7 = interruptable;
		s8 = 0;
	}
	private static void t(float arg0, float arg1, float arg2) {
		String s = s8==0?"":(s8==1?"Lucid ":"Flawless ");
		String level = s;
		s8++;
		x(s+s1);
		try {
			Ability x = new Ability(s2.newInstance(), s3, s4, arg0, arg1, arg2, s5, s6, s7);
			x.setImage(s1, level);
			o(x);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	private static void x(String itemName) {
		x(itemName, 50);
	}
	private static void x(String itemName, int steamCost) {
		loadingItemName = itemName;
		ItemFactory.create(loadingItemName);
		ItemFactory.addCost(loadingItemName, steamCost);
	}
	private static void o(Ability ability) {
		ItemFactory.setAbility(loadingItemName, ability);
	}
	private static void o(ItemComponent comp) {
		ItemFactory.addComponent(loadingItemName, comp);
	}
	private static void o(ItemUse use) {
		ItemFactory.addUseEffect(loadingItemName, use);
	}
	
	/** Cannot be instantiated.  */
	private ItemTypeLoader() {}
}
