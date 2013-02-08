package content;

import item.ItemFactory;
import quest.QuestFactory;
import quest.component.GetItems;
import quest.component.KillEnemies;
import enemy.EnemyFactory;
import quest.component.TalkToNPC;
import util.Rand;
import util.U;


public class QuestLoader {
	
	public static String[] gems = {"Ruby", "Aquamarine", "Quartz"};
	public static void loadAll() {
		//Quests in Actual Game in Zone Order\\

		
		QuestFactory.create("Clean up the Factory");
		QuestFactory.addComponent("Clean up the Factory", new GetItems(ItemFactory.get("Steam"), 3));
		QuestFactory.reward("Clean up the Factory", ItemFactory.get(gems[(int) Math.floor(Math.random()*3)]), 1);
		
		QuestFactory.create("Kill the Steam spreader");
		QuestFactory.addComponent("Kill the Steam spreader", new KillEnemies(EnemyFactory.get("Rukh"), 1));
		QuestFactory.reward("Kill the Steam spreader", ItemFactory.get(gems[(int) Math.floor(Math.random()*3)]), 1);
		
		QuestFactory.create("Kill the giant spider");
		QuestFactory.addComponent("Kill the giant spider", new KillEnemies(EnemyFactory.get("Arachne"), 1));
		QuestFactory.reward("Kill the giant spider", ItemFactory.get("Scrap Metal"), 1);
		
		QuestFactory.create("Ooooh, shiny!");
		QuestFactory.addComponent("Ooooh, shiny!", new GetItems(ItemFactory.get("Scrap Metal"), 1));
		QuestFactory.addComponent("Ooooh, shiny!", new GetItems(ItemFactory.get("Platinum"), 1));
		QuestFactory.addComponent("Ooooh, shiny!", new GetItems(ItemFactory.get("Iridium"), 1));
		QuestFactory.reward("Ooooh, shiny!", ItemFactory.get("Vial"), 1);
		QuestFactory.reward("Ooooh, shiny!", ItemFactory.get("Big Vial"), 1);
		QuestFactory.reward("Ooooh, shiny!", ItemFactory.get("Huge Vial"), 1);
		
		
		QuestFactory.create("Save the Kid's grandmother");
		QuestFactory.addComponent("Save the Kid's grandmother", new GetItems(ItemFactory.get("Steam"), 2));
		QuestFactory.addComponent("Save the Kid's grandmother", new GetItems(ItemFactory.get("Cirrus Steam"), 2));
		QuestFactory.addComponent("Save the Kid's grandmother", new GetItems(ItemFactory.get("Nacreous Steam"), 2));
		QuestFactory.reward("Save the Kid's grandmother", ItemFactory.get("Bottle"), 1);
		QuestFactory.reward("Save the Kid's grandmother", ItemFactory.get("Big Bottle"), 1);
		QuestFactory.reward("Save the Kid's grandmother",ItemFactory.get("Huge Bottle"), 1);
		QuestFactory.reward("Save the Kid's grandmother", ItemFactory.get("Vial"), 1);
		QuestFactory.reward("Save the Kid's grandmother", ItemFactory.get("Big Vial"), 1);
		QuestFactory.reward("Save the Kid's grandmother", ItemFactory.get("Huge Vial"), 1);
		
		QuestFactory.create("Defeat the thief");
		QuestFactory.addComponent("Defeat the thief", new KillEnemies(EnemyFactory.get("Shannon"), 1));
		QuestFactory.reward("Defeat the thief", ItemFactory.get("Steam"), 1);
		QuestFactory.reward("Defeat the thief", ItemFactory.get("Cirrus Steam"), 1);
		QuestFactory.reward("Defeat the thief", ItemFactory.get("Nacreous Steam"), 1);
		
		QuestFactory.create("Kill the human-eater!");
		QuestFactory.addComponent("Kill the human-eater!", new KillEnemies(EnemyFactory.get("Aurelia"), 1));
		QuestFactory.reward("Kill the human-eater!", ItemFactory.get("Strong Elixir of Speed"), 1);
		QuestFactory.reward("Kill the human-eater!", ItemFactory.get("Strong Elixir of Power"), 1);
		QuestFactory.reward("Kill the human-eater!", ItemFactory.get("Strong Elixir of Protection"), 1);
		
		QuestFactory.create("Stop Inferno");
		QuestFactory.addComponent("Stop Inferno", new KillEnemies(EnemyFactory.get("Inferno"), 1));
		QuestFactory.reward("Stop Inferno", ItemFactory.get("Nacreous Steam"), 1);
		
		QuestFactory.create("Acquire precious gems");
		QuestFactory.addComponent("Acquire precious gems", new GetItems(ItemFactory.get("Ruby"), 2));
		QuestFactory.addComponent("Acquire precious gems", new GetItems(ItemFactory.get("Aquamarine"), 2));
		QuestFactory.addComponent("Acquire precious gems", new GetItems(ItemFactory.get("Quartz"), 2));
		QuestFactory.reward("Acquire precious gems", ItemFactory.get("Potent Elixir of Vitality"), 1);
		
		
		QuestFactory.create("Expel Ruwen from Yggdrasil");
		QuestFactory.addComponent("Expel Ruwen from Yggdrasil", new KillEnemies(EnemyFactory.get("Ruwen"), 1));
		QuestFactory.reward("Expel Ruwen from Yggdrasil", ItemFactory.get(gems[(int) Math.floor(Math.random()*3)]), 1);
		
		QuestFactory.create("Cure Viola's Blindness");
		QuestFactory.addComponent("Cure Viola's Blindness", new GetItems(ItemFactory.get("Huge Vial"), 2));
		QuestFactory.addComponent("Cure Viola's Blindness", new GetItems(ItemFactory.get("Cirrus Steam"), 2));
		QuestFactory.addComponent("Cure Viola's Blindness", new GetItems(ItemFactory.get("Nacreous Steam"), 2));
		QuestFactory.reward("Cure Viola's Blindness", ItemFactory.get("Potent Elixir of Vitality"), 1);
		
		QuestFactory.create("Acquire bottles for instrument");
		QuestFactory.addComponent("Acquire bottles for instrument", new GetItems(ItemFactory.get("Bottle"), 1));
		QuestFactory.addComponent("Acquire bottles for instrument", new GetItems(ItemFactory.get("Big Bottle"), 1));
		QuestFactory.addComponent("Acquire bottles for instrument", new GetItems(ItemFactory.get("Huge Bottle"), 1));
		QuestFactory.addComponent("Acquire bottles for instrument", new GetItems(ItemFactory.get("Vial"), 1));
		QuestFactory.addComponent("Acquire bottles for instrument", new GetItems(ItemFactory.get("Big Vial"), 1));
		QuestFactory.addComponent("Acquire bottles for instrument", new GetItems(ItemFactory.get("Huge Vial"), 1));
		QuestFactory.reward("Acquire bottles for instrument", ItemFactory.get("Full Health Potion"), 1);
		
		
		
		
		
		QuestFactory.create("Find a Job");
		QuestFactory.addComponent("Find a Job", new TalkToNPC("Search the town to find a Job", "Factory"));
		
		QuestFactory.create("Do your Job");
		QuestFactory.addComponent("Do your Job", new TalkToNPC("Go into the factory and talk to Alma", "GetSteam"));
		
		QuestFactory.create("Get Steam");
		QuestFactory.addComponent("Get Steam", new GetItems(ItemFactory.get("Steam"), 6));
		QuestFactory.addComponent("Get Steam", new TalkToNPC("Give Steam to Alma", "SteamDone"));
		QuestFactory.reward("Get Steam", ItemFactory.get("Ruby of Flames"), 1);
		
		QuestFactory.create("Leave Factory");
		QuestFactory.addComponent("Leave Factory", new TalkToNPC("Leave Factory", "MeetSam"));
		
		QuestFactory.create("Find Haitao");
		QuestFactory.addComponent("Find Haitao", new TalkToNPC("Find Haitao in the town", "MeetFakeHaitao"));
		
		QuestFactory.create("Jubilee Forest");
		QuestFactory.addComponent("Jubilee Forest", new TalkToNPC("Go to the Jubilee Forest up North", "Caracal"));
		
		QuestFactory.create("Find Kids and Sauce");
		QuestFactory.addComponent("Find Kids and Sauce", new KillEnemies(EnemyFactory.get("Sauce"), 1));
		
		
		
		/////Misc Quests
		
		QuestFactory.create("Kill One Ogre");
		QuestFactory.addComponent("Kill One Ogre", new KillEnemies(EnemyFactory.get("Ogre"), 1));
		
		QuestFactory.create("Remove Dustlings");
		QuestFactory.addComponent("Remove Dustlings", new GetItems(ItemFactory.get("Dustling Tail"), 10));
		QuestFactory.reward("Remove Dustlings", ItemFactory.get("Ruby of Flames"), 1);
		
		QuestFactory.create("Get Potions");
		QuestFactory.addComponent("Get Potions", new GetItems(ItemFactory.get("Health Potion"), 2));
		QuestFactory.reward("Get Potions", ItemFactory.get("Blustering Quartz"), 1);
		
		QuestFactory.create("Get 20 Dust");
		QuestFactory.addComponent("Get 20 Dust", new GetItems(ItemFactory.get("Dust"), 20));
		
		QuestFactory.create("Remove Remnant Soldiers");
		QuestFactory.addComponent("Remove Remnant Soldiers", new KillEnemies(EnemyFactory.get("Vagrant"), 3));
		QuestFactory.addComponent("Remove Remnant Soldiers", new KillEnemies(EnemyFactory.get("Axe Fighter"), 3));
		QuestFactory.addComponent("Remove Remnant Soldiers", new KillEnemies(EnemyFactory.get("Shield Fighter"), 3));
		QuestFactory.reward("Remove Remnant Soldiers", ItemFactory.get("Ruby of Flames"), 1);
		
		QuestFactory.create("Remove Ogres");
		QuestFactory.addComponent("Remove Ogres", new KillEnemies(EnemyFactory.get("Ogre"), 1));
		//QuestFactory.addComponent("Kill Ogres", new KillEnemies(EnemyFactory.get("Teacup"), 5));
		QuestFactory.reward("Remove Ogres", ItemFactory.get("Eye of Aquamarine"), 1);
		
		QuestFactory.create("You're Done!");
		
		QuestFactory.create("Sauce");
		QuestFactory.addComponent("Sauce", new GetItems(ItemFactory.get("Sauce"), 1));
	}
	
	/** Cannot be instantiated. */
	private QuestLoader() {}
}
