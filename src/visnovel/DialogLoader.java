package visnovel;


public class DialogLoader {
	
	
	public static void loadAll() {
		
		Cutscene.start("test")
		.enter("Haitao")
		.speaks("Haitao", "I'm tired...")
		.speaks("Haitao","I shouldn't have drank all that alcohol last night....")
		.speaks("Haitao", "Also.. why are my clothes missing...")
		.enter("Sam")
		.speaks("Sam", "Hahahaha, you look like a dork!")
		.speaks("Sam", "I am also your future love interest.... please save me and not Jackie.. I am far cuter!")
		.exits("Sam")
		.speaks("Haitao", "Foot in my face I gues...")
		.exits("Haitao");
		
		Cutscene.start("getQuest1")
		.enter("Haitao")
		.speaks("Haitao", "Hiya Sam!")
		.enter("Sam")
		.speaks("Sam", "Hi Haitao, could you kill three rats for me?")
		.speaks("Haitao", "Sure thing!")
		.exits("Sam")
		.exits("Haitao");
		
		Cutscene.start("questDone1")
		.enter("Haitao")
		.speaks("Haitao", "I killed the rats for you, Sam!")
		.enter("Sam")
		.speaks("Sam", "Awesome! You're the best!")
		.speaks("Haitao", "Wanna come over tonight and repay the favor?")
		.exits("Sam")
		.exits("Haitao");
		
		Cutscene.start("getQuest2")
		.enter("Factory Owner")
		.speaks("Factory Owner", "Hey Kid!")
		.enter("Haitao")
		.speaks("Haitao", "Hello Sir.")
		.speaks("Factory Owner", "I need some goblins killed. Kill two goblins for me \n and maybe I won't eat you for breakfast")
		.speaks("Haitao", "Ye-ssir, I'll get on th-at...")
		.exits("Factory Owner")
		.exits("Haitao");
		
		Cutscene.start("questDone2")

		.enter("Haitao")
		.speaks("Haitao", "I did what you wanted, sir.")
		.enter("Factory Owner")
		.speaks("Factory Owner", "So you did. You have potential. Maybe you aren't \n like the rest of the good for nothing kids.")
		.speaks("Haitao", "Thank you, sir.")
		.exits("Factory Owner")
		.exits("Haitao");
		
		Cutscene.start("getSauceQuest")
		
		.enter("Sam")
		.speaks("Sam", "Hi Cecil! The town cook needs your help! His famous sauce was stolen. \n Here's the note from him.")
		.enter("Cecil")
		.speaks("Cecil", "Hmmm, 'My famous sauce was cooling on my windowsill just the other day; \n I go to do the laundry, and it disappears! I bet those neighborhood rascals \n have stolen it again! Go teach those ruffians a lesson for me!'")
		.speaks("Sam", "Haha, I saw some some kids go through the forest that's just north \n of here. Be careful of the forest - you may need more abilities to pass through it. \n You can get some quests and rewards from the other people around town.")
		.speaks("Cecil", "Okay, I will go through the forest and find his sauce!")
		.exits("Sam")
		.exits("Cecil");
		
		Cutscene.start("getSauceInter")
		
		.enter("Sam")
		.speaks("Sam", "The town cook is getting impatient...")
		.enter("Cecil")
		.speaks("Cecil", "Okay...")
		.exits("Cecil")
		.exits("Sam");
		
		
		Cutscene.start("meetKid")
		
		.enter("Kid")
		.speaks("Kid", " *sob sob*")
		.enter("Cecil")
		.speaks("Cecil", " Isn't that one of the neighborhood kids? \n I need to go through the woods to get across the river. ")
		.exits("Kid")
		.exits("Cecil");
		
		Cutscene.start("kidInter")
		
		.enter("Kid")
		.speaks("Kid", "Thanks again! You're the best.")
		.enter("Cecil")
		.speaks("Cecil", "No problem!")
		.exits("Kid")
		.exits("Cecil");
		
		Cutscene.start("meetSauce")
		
		.enter("Cecil")
		.speaks("Cecil", "What's that??")
		.speaks("Sauce Monster", "RAORRRRRRRRRRR!!!!!!!")
		.speaks("Cecil", "Something tells me that the special ingredient wasn't \n the only thing that got added to the sauce. What horrible spelling!")
		.speaks("Sauce Monster", "HIIIIIIIIIIIIIISSSSSSSSSSSSP")
		.speaks("Cecil", "Sigh, I never get a break.")
		.exits("Cecil");
		
		Cutscene.start("sauceDone")
		
		.enter("Kid")
		.speaks("Kid", " *Sob Sob* Hey, thanks. We were just gonna eat it. \n We never imagined that it would turn around and try to eat US!")
		.enter("Cecil")
		.speaks("Cecil", " The Steam must have binded to this metal pot in response \n to the chef's strong, protective feelings for his food. \n Let's get this back to the city before it causes anymore trouble.")
		.exits("Kid")
		.exits("Cecil");
		

		Cutscene.start("getDustlings")
		
		.enter("Shannon")
		.speaks("Shannon", " Ahhh, you've got to help me Cecil! Dustlings are singing outside of my window \n at night and I need to study for entrance exams into the Steam Academy! \n I just can't concentrate.")
		.enter("Cecil")
		.speaks("Cecil", "How can I help you?")
		.speaks("Shannon", "Kill the Dustlings for me! They live in the forest during the day. \n If you kill 5 Dustlings, I can give you a nice gem.")
		.speaks("Cecil", "Okay!")
		.exits("Shannon")
		.exits("Cecil");
		
		Cutscene.start("getDustlingsInter")
		
		.enter("Shannon")
		.speaks("Shannon", "Don't talk to me until you've killed the Dustlings!")
		.enter("Cecil")
		.speaks("Cecil", "Okay...")
		.exits("Cecil")
		.exits("Shannon");
		
		Cutscene.start("dustlingsDone")
		
		.enter("Shannon")
		.speaks("Shannon", "Thanks so much, Cecil. I can study in peace now!")
		.enter("Cecil")
		.speaks("Cecil", "No problem!")
		.speaks("Shannon", "Here is the Blustering Quartz, it contains a whirlwind ability  \n that knocks back enemies and does damage.")
		.speaks("Cecil", "Thanks!")
		.exits("Shannon")
		.exits("Cecil");
		
		Cutscene.start("getSoldiers")
		
		.enter("Jackie")
		.speaks("Jackie", " Help me, Cecil! The Remnant Soldiers are preventing my secret admirer\n from leaving a rose outside of my window at night everyday.")
		.enter("Cecil")
		.speaks("Cecil", "Uh... what do you want me to do?")
		.speaks("Jackie", "Kill the Remnant Soldiers for me! They live in the forest. \n Kill 3 of each type of soldier.")
		.speaks("Cecil", "Hmm, do I get anything for doing that?")
		.speaks("Jackie", "I'll give you a special gem!")
		.exits("Jackie")
		.exits("Cecil");
		
		Cutscene.start("getSoldiersInter")
		
		.enter("Jackie")
		.enter("Cecil")
		.speaks("Jackie", "I don't wanna hear from you until you've killed the Remnant Soldiers.")
		.speaks("Cecil", "Okay...")
		.exits("Jackie")
		.exits("Cecil");
		
		Cutscene.start("soldiersDone")
		
		.enter("Jackie")
		.speaks("Jackie", "Thanks so much, Cecil! My secret admirer has been giving me roses again.")
		.enter("Cecil")
		.speaks("Cecil", "No problem!")
		.speaks("Jackie", "Here is the Ruby of Flames, it contains a fireball ability. \n You can shoot your enemies from far away!")
		.speaks("Cecil", "Thanks so much!")
		.exits("Jackie")
		.exits("Cecil");
		
		Cutscene.start("getOgres")
		
		.enter("Elise")
		.speaks("Elise", "Cecil, it's terrible!")
		.enter("Cecil")
		.speaks("Cecil", "Sigh... what now...")
		.speaks("Elise", "The Ogres have been dissecting cats! It's animal abuse!")
		.speaks("Cecil", "What do you want me to do about it?")
		.speaks("Elise", "Kill an Ogre and his teacup minions, and I'll give you a reward.")
		.exits("Elise")
		.exits("Cecil");
		
		Cutscene.start("getOgresInter")
		
		.enter("Elise")
		.speaks("Elise", "Go away, you haven't killed the ogre yet.")
		.enter("Cecil")
		.speaks("Cecil", "Okay...")
		.exits("Cecil")
		.exits("Elise");
		
		Cutscene.start("ogresDone")
		
		.enter("Elise")
		.speaks("Elise", "Thanks so much, Cecil! The cats will be forever in your debt.")
		.enter("Cecil")
		.speaks("Cecil", "Wonderful, what's my reward?")
		.speaks("Elise", "Here is the Eye of Aquamarine. You can pierce through monsters using a waterjet!")
		.speaks("Cecil", "Thanks!")
		.exits("Elise")
		.exits("Cecil");
		
		Cutscene.start("tutorial")
		
		.enter("Sam")
		.speaks("Sam", "Welcome to the town of Odell! I'm Sam, the town Quest Master. \n" +
				"The first thing you'll need to know is that you should press SPACE to step\n" +
				"through dialog, interact with people, and interact with the Menu.")
		.speaks("Sam", "Good! You got it. To open the menu, press ESC. Here you can craft, \n" +
				"view your inventory and quest progress, and equip items. Go ahead and do that now. ")
		.speaks("Sam", "The arrow buttons can be used to navigate around the menu. \n" +
				"Items can be equipped by first selecting the item, and then \n" +
				"pressing the button next to the slot you want to equip it to.")
		.speaks("Sam","Note that not all items can be equipped to all slots.\n" +
				"Active items, such as gems, grant you abilities. \n" +
				"Consumable items, such as health potion, can be used in battle.")
		.speaks("Sam", "Finally, passive items just need to be equipped to grant you their power. \n" +
				"Unfortunately, you don't have any items you can equip yet.")
		.speaks("Sam", "To craft items, navigate to the crafting menu, select the recipe you want \n" +
				"to make, and hit ENTER to craft. \n" +
				"However, I'm afraid you currently don't have the reagents necessary to make anything. ")
		.speaks("Sam", "Now let's talk about moving around the overworld. Press ESC again to \n " +
				"close the menu. The arrow keys can be used to move around the overworld. \n" +
				"Simply walk into portals to move between maps.")
		.speaks("Sam","If you happen to encounter a monster while traveling, \n" +
				"you'll be transported into a battle map. Here you can use your melee attack by \n" +
				"pressing R, or any of your abilities by pressing Q, W, or E.")
		.speaks("Sam", "If you successfully kill monsters, they may drop items that you can use \n" +
				"to craft items.")
		.speaks("Sam", "That's basically all you need to know to get started! \n" +
				"I think there are a number of people around town who need favors done,\n" +
				"so I'd talk to a few of them before wandering off into the wilds.")
		.speaks("Sam", "Good luck!")
		.exits("Sam");
		
		
		Cutscene.start("Zone1")
		.enter("Elise")
		.speaks("Elise", " I need 10 dustling tails for some stupid ass reason")
		.enter("Cecil")
		.speaks("Cecil", " But I'm a busy guy...")
		.speaks("Elise", " I'll give you a rare gem if you do it!")
		.speaks("Cecil", " Okay, sounds good!")
		.exits("Elise")
		.exits("Cecil");
		
		Cutscene.start("Zone1Inter")
		.enter("Elise")
		.speaks("Elise", " Give me those dustling tails!")
		.enter("Cecil")
		.speaks("Cecil", " Okay, I'm working on it")
		.exits("Elise")
		.exits("Cecil");
		
		Cutscene.start("Zone1End")
		.enter("Elise")
		.speaks("Elise", " Thanks for the 10 dustling tails! No doubt someone will benefit from them. \n Here's the Ruby of Flames! You can use a projectile ability now.")
		.enter("Cecil")
		.speaks("Cecil", " Great, thanks!")
		.speaks("Elise", " Go to the other side of the forest. Your destiny is waiting.")
		.exits("Elise")
		.exits("Cecil");
		
		
		Cutscene.start("Zone2")
		.enter("Jackie")
		.speaks("Jackie", " My grandmother is dying! I need two health and energy potions.")
		.enter("Cecil")
		.speaks("Cecil", " How do I get those?")
		.speaks("Jackie", " Well, hopefully, you have the recipe for those...")
		.speaks("Cecil", " Hmmm...")
		.speaks("Jackie", " I'll give you a rare gem if you can save my grandmother!")
		.exits("Jackie")
		.exits("Cecil");
		
		Cutscene.start("Zone2Inter")
		.enter("Jackie")
		.speaks("Jackie", " My grandmother is dying as we speak!")
		.enter("Cecil")
		.speaks("Cecil", " Okay, I'm working on it")
		.exits("Jackie")
		.exits("Cecil");
		
		Cutscene.start("Zone2End")
		.enter("Jackie")
		.speaks("Jackie", " Thanks for these potions! My grandmother will be well again.")
		.enter("Cecil")
		.speaks("Cecil", " No problem")
		.speaks("Jackie", " Here is the Blustering Quartz!")
		.exits("Jackie")
		.exits("Cecil");
		
		Cutscene.start("Zone3")
		.enter("Kid")
		.speaks("Kid", " There's a Sauce Monster in the forest after this one!")
		.enter("Cecil")
		.speaks("Cecil", " So?")
		.speaks("Kid", " Well, I want sauce, get it for me!")
		.speaks("Cecil", " How do I defeat him?")
		.speaks("Kid", " You'll need a Shielding Onyx. That's all I can tell you.")
		.exits("Kid")
		.exits("Cecil");
		
		Cutscene.start("Zone3Inter")
		.enter("Kid")
		.speaks("Kid", " Get that Shield and get me some sauce!")
		.enter("Cecil")
		.speaks("Cecil", " Okay, I'm working on it")
		.exits("Kid")
		.exits("Cecil");
		
		Cutscene.start("Zone3Done")
		.enter("Kid")
		.speaks("Kid", " Thank you so much! Now I can eat pasta with sauce!")
		.enter("Cecil")
		.speaks("Cecil", "You're welcome")
		.speaks("Kid", "Game Over, you win!")
		.exits("Kid")
		.exits("Cecil");
		
		Cutscene.start("Tombstone1")
		.enter("Cecil")
		.speaks("Tombstone", "Here lies the recipe for a Health Potion")
		.speaks("Tombstone", "You've gained the Health Potion Recipe!")
		.exits("Cecil");
		
		Cutscene.start("TombstoneInter")
		.enter("Cecil")
		.speaks("Tombstone", "You've already read this")
		.exits("Cecil");
		
		Cutscene.start("Tombstone2")
		.enter("Cecil")
		.speaks("Tombstone", "Here lies the recipe for a Energy Potion")
		.speaks("Tombstone", "You've gained the Energy Potion Recipe!")
		.exits("Cecil");
			
		Cutscene.start("Tombstone3")
		.enter("Cecil")
		.speaks("Tombstone", "I have invisible ink written here. Need 20 dusts to uncover my secrets.")
		.exits("Cecil");
		
		Cutscene.start("Tombstone3Done")
		.enter("Cecil")
		.speaks("Tombstone", "Here lies the recipe for the Shielding Onyx")
		.speaks("Tombstone", "You've gained the Shielding Onyx Recipe!")
		.exits("Cecil");
		
		
		
		/////////////////////////// Start of Actual Dialogue \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		
		
		
		//Zone 1\\
		Cutscene.start("NoGoHome")
		.enter("Cecil")
		.speaks("Cecil", "I can't turn back now...")
		.exits("Cecil");
		
		Cutscene.start("Start2")
		.enter("Cecil")
		.speaks("Cecil", "Okay. This is it. The city. Gotta find a job.")
		.exits("Cecil");
		
		Cutscene.start("StartSam")
		.enter("Sam")
		.speaks("Sam", "You're new in town, aren't you? I heard the factory needs some workers.")
		.exits("Sam");
		Cutscene.start("StartBen")
		.enter("Ben")
		.speaks("Ben", "You're penniless! Don't talk to me until you can pay me for my valuable items.")
		.exits("Ben");
		Cutscene.start("StartHaitao")
		.enter("Lucas")
		.speaks("Haitao", "You need a job? Go to the Factory!")
		.exits("Lucas");
		
		Cutscene.start("Factory")
		.enter("Factory Boss")
		.speaks("Factory Boss", "You need a job? You can clean up the steam at my factory")
		.enter("Cecil")
		.speaks("Cecil", "...Steam?")
		.speaks("Factory Boss", "What do you mean, you've never heard of Steam? It's so simple! It just does\n what you tell it to do!")
		.speaks("Cecil", "Wow, really?")
		.speaks("Factory Boss", "Neat, right? Except sometimes the steam can get out of hand. I need you \nto desteamify rogue animals that have been ruining factory productivity. \nYou can start right away.")
		.exits("Factory Boss")
		.speaks("Cecil", "Well, it's a job. ")
		.exits("Cecil");
		
		Cutscene.start("FactoryInter")
		.enter("Factory Boss")
		.speaks("Factory Boss", "What are you waiting for?? Get into the factory and get to work!")
		.exits("Factory Boss");
		
		Cutscene.start("MeetAlma")
		.enter("Cecil")
		.speaks("Cecil", " hm... Ratlike creature...")
		.speaks("Dustling", " KRIIIIIIII!!!!")
		.speaks("Cecil", " These aren't normal rats! They are drenched in Steam!!")
		.speaks("Dustling", " KREEEEEE-CH!")
		.speaks("Cecil", " What should I do? Okay, the guy said that this stuff will do whatever \n I tell it to, right? I want it stop doing that. Ahem... er. STOP THAT, you.")
		.speaks("Dustling", " ...")
		.enter("Alma")
		.speaks("Alma", " You big idiot, you gotta weaken it, first.")
		.speaks("Cecil", " ??? ")
		.speaks("Alma", " Just trust me on this, kid. Swirl your hands in the steam and try to \n absorb its power. Then push the Dustling away by pressing R. That'll teach him!")
		.exits("Alma")
		.speaks("Cecil", " Dustling? Who are y-")
		.speaks("Dustling", " KRIIIII!!!!!")
		.speaks("Cecil", " Okay, okay. Hit the monster with my hands. Got it!")
		.exits("Cecil");
		
		Cutscene.start("GetSteam")
		.enter("Alma")
		.speaks("Alma", " Geez, the boss always \"forgets\" to tell you guys about \nthis detail. See all that Steam you're cleaning up? It's filled with the \nintentions and wishes of the people who put them into the vessels. \nOnce that gets mixed up enough, confused Remnants like this are born.")
		.enter("Cecil")
		.speaks("Cecil", " This is a pretty dangerous job...")
		.speaks("Alma", " Che, I've seen your moves, kid, you're a natural at using Steam! \nDitch this job and be a headhunter! ")
		.speaks("Cecil", " Headhunter?")
		.speaks("Alma", " Professionals that exterminate Remnants, for pay. See the steam \nyou've collected from subduing that Remnant? You can use that to create and buy \nuseful items. If you're lucky, the Remnant will be carrying more than steam \nand you can use them to craft! ")		
		.speaks("Cecil", " I guess I can give this Headhunter job a try. ")
		.speaks("Alma", " Head upstairs, there's more work to be done there! You can view your \njobs by pressing ESC and moving to the Quests. Bring me 6 Steam to \nprove you've gotten rid of the pests!")
		.exits("Alma")
		.exits("Cecil");
		
		Cutscene.start("GetSteamInter")
		.enter("Alma")
		.speaks("Alma", "Stop lingering around here! Go upstairs and clean it up!")
		.exits("Alma");
		
		Cutscene.start("CantLeaveFactory")
		.enter("Cecil")
		.speaks("Cecil", "I can't leave, I still have a job to do...")
		.exits("Cecil");
		
		Cutscene.start("Start")
		.enter("Alma")
		.speaks("Alma", "Kid, WAKE UP!!")
		.speaks("Alma", "...")
		.speaks("Alma", "Do I need to kick you?")
		.enter("Cecil")
		.speaks("Cecil", "Merphh, wah? What happened? Where am I?")
		.speaks("Alma", "You got your butt handed to you by an avatar of the Maker of Life, that's what happened.")
		.speaks("Cecil", "!!!")
		.speaks("Cecil", "I remember now!!")
		.speaks("Alma", " Yes. Luckily for you, the avatar was too stupid to finish you off. \nYou're in the town of Odell!")
		.speaks("Cecil", " ...hmmm... Fire from Heaven... ")
		.speaks("Alma", " What are you talking about, Kid?")
		.speaks("Cecil", " No, this feeling. It's the same feeling that I got when my village was destroyed  \nby flames. Oh, this ruby I'm holding! It has the same aura as the flames on that day!")
		.speaks("Alma", " Hm. Fire from Heaven? Sounds more like the work of a powerful \nSteam user to me.")
		.speaks("Cecil", " ...")
		.enterRight("Sam")
		.speaks("Sam", "What's happened here? Do I hear, Maker of Life?")
		.exits("Cecil")
		.enterLeft("Cecil")
		.speaks("Cecil", "Ah! Who are you?")
		.speaks("Alma", "This is Sam, the guild master. Yeah, this squirt was trying to take him on.")
		.speaks("Cecil", "Hey, I'm no squirt! I need to defeat him! He destroyed my family and friends\n - my whole village!")
		.speaks("Sam", "Wow kid, good luck with that... you have a long journey ahead of you. \nYou should join my guild though. Our agents roam around in the northern \nforest and beaches. They can help you get stronger.")
		.speaks("Alma", "Not to mention, the forest and beach are filled with steam Remnants \nthat will cut you to bits.")
		.speaks("Sam", "Stop scaring him Alma. You see the fire in his eyes? This kid has spirit. \nI believe he can do it.")
		.speaks("Cecil", "Stop calling me kid, the name's Cecil.")
		.speaks("Alma", "Well Cecil, I feel like you'll die without me. I'm coming with you.")
		.speaks("Cecil", "Do what you want.")
		.speaks("Sam", "Well, you should first find Haitao, the star crafter. He is a collector \nof power items. He can help you out. I last saw him collecting \nherbs in the forest up north.")
		.exits("Sam")
		.exits("Alma")
		.exits("Cecil");
		
		Cutscene.start("SamInter")
		.enter("Sam")
		.speaks("Sam", "Good luck on your journey, Kid!")
		.enter("Cecil")
		.speaks("Cecil", "Gah, stop calling me kid!!")
		.exits("Cecil")
		.exits("Sam");

		
		Cutscene.start("FactoryDone")
		.enter("Factory Boss")
		.speaks("Factory Boss", "Thanks for taking care of those annoying creatures!")
		.exits("Factory Boss");
		
		Cutscene.start("MeetSam")
		.enter("Sam")
		.speaks("Sam", " Aw, I had a call about a rogue Remnant. I get here, and you've \n already mopped it up, kid. You've got sharp eyes as always, Alma.")
		.enter("Cecil")
		.speaks("Cecil", " !!! Who are you? Alma, do you know this guy?")
		.speaks("Sam", " I'm Sam from Regis Sky Guild. Would you like to join my guild?")
		.enterRight("Alma")
		.speaks("Alma", " Sam, you are always butting into things! He's just a novice,\n you can't send him out to the fields!")
		.speaks("Sam", " Oh, you worrywart. You're just jealous because you can't go out\n there, Alma. He'll be fine.")
		.speaks("Cecil", " Why can't you go out there, Alma?")
		.speaks("Sam", " The aria of madness in the Steam out there is so strong that \nit'll corrupt the steam keeping her alive. ")
		.speaks("Cecil", " These monsters... Say, Sam, I want to find out more about the \nones who razed my village to the ground....")
		.speaks("Alma", " Hold on a second. Before you can even think about going out there, \nyou're going to need a vessel for your Steam. If you are going \nto fight the real Remnants, just your hands won't do. You need something old and\n of great emotional attachment. Do you have anything like that?")
		.speaks("Cecil", " This fountain pen. It was my Grandfather's.")
		.speaks("Alma", " Ugh. So small... Well, whatever, it's the thought that counts, anyways. \nLet's go see Haitao the Star Crafter. He makes the best weapons! ")
		.speaks("Sam", " You're going with him, Alma? But your steam....")
		.speaks("Alma", " If Cecil kills the monsters before they get to me, I should be fine. \nSo I'll just hide behind you in all the battles. Don't die, though, \nbecause then it's all over for me too.")
		.exits("Sam")
		.exits("Alma")
		.exits("Cecil");
		
		Cutscene.start("SteamIntro")
		
		.speaks("Factory Boss", "What do you mean, you've never heard of Steam? It's the greatest\n thing of the century! It turns teacups into self-warming teacups! \n Sandals into flying sandals! ")
		.speaks("Factory Boss", "Children's toys into your dedicated, personal servants!  \n Steam is the miracle of life!")
		.speaks("Cecil", "Uh...");
		
		Cutscene.start("MeetFakeHaitao")
		.enter("Lucas")
		.speaks("Haitao", " Drat... none here, either.")
		.enter("Cecil")
		.speaks("Cecil", " Excuse me. Are you Haitao?")
		.speaks("Haitao", " Don't see a lot of people around here these days ~ You looking for me?")
		.speaks("Cecil", " Well, I was hopin-")
		.speaks("Haitao", "Great! Just the man I was looking for! ")
		.speaks("Cecil", " Hold up just a-")
		.speaks("Haitao", " Come~ Let us retrieve Echidna Fang from the Jubilee Cemetary in the West ~")
		.speaks("Cecil", " I just want-")
		.speaks("Haitao", " Ah~ If you help me, I will give you something good ~")
		.speaks("Cecil", " A weapon! A weapon!")
		.speaks("Haitao", " Fine, fine ~ Let's be off ~~~")
		.exits("Lucas")
		.speaks("Cecil", " Er, Alma?")
		.enterLeft("Alma")
		.speaks("Alma", " Well, he is rumored to be quite... eccentric.")
		.speaks("Cecil", " Sigh... let's go.")
		.exits("Cecil");
		
		Cutscene.start("SamInter")
		.enter("Sam")
		.speaks("Sam", "Go find Haitao! He's in the forest to the right.")
		.exits("Sam");
		
		Cutscene.start("Shop")
		.enter("Ben")
		.speaks("Ben", "You wanna buy stuff kid? I've got everything you want. If you walk, \nI can convert every 100 steps to 1 steam!")
		.exits("Ben");
		
		Cutscene.start("MeetFakeHaitaoInter")
		.enter("Lucas")
		.speaks("Haitao", "Let's go to Jubilee! It's West.")
		.exits("Lucas");
		
		Cutscene.start("MeetFakeHaitaoDone")
		.enter("Lucas")
		.speaks("Haitao", "Thanks for the Fang!")
		.exits("Lucas");
		
		Cutscene.start("Caracal")
		.enter("Lucas")
		.enter("Cecil")
		.speaks("Haitao", " Ah! There it is! ~~~~")
		.speaks("Cecil", " That's an herb???")
		.speaks("Haitao", " Yes yes")
		.speaks("Haitao", " Why is an herb called the fang of... nevermind. Let's just get out of here...")
		.speaks("Haitao", " Ah -")
		.speaks("Caracal", " GROWLLLLL!!!!!!")
		.exits("Lucas")
		.exits("Cecil");
		
		Cutscene.start("LucasCaught")
		.enter("Ben")
		.enter("Cecil")
		.speaks("Ben", " Hey, what were you doing attacking my pet!")
		.speaks("Cecil", " .. that thing is your pet?!")
		.speaks("Ben", " yeah, how'd you think I survive out here? By fighting off the \nRemnants myself? Hahaha!")
		.speaks("Cecil", " oh.")
		.speaks("Ben", " You were doing pretty well though, not many can beat my Caracal..\n Luckily, for your sake, he'll make a full recovery.")
		.speaks("Cecil", " Who are you?")
		.speaks("Ben", " I am Ben, the great arch sage of Jubilee! You can find my identical brothers \nscattered throughout the land. Come talk to us if you want to buy potions\n to strengthen you!")
		.speaks("Cecil", " Err.. begging your pardon, your sage-ness.. Haitao and I just\n wanted some Echidna's Fang.")
		.speaks("Ben", " Eh? Haitao you say? Haven't seen the bloke in ages. Where is he?")
		.enterRight("Lucas")
		.speaks("Cecil", " Right in front of you")
		.speaks("Haitao", " ...")
		.speaks("Ben", " I know I'm getting a little on in years, but if that's Haitao, \nthen I'm Franklin! Who the heck are you!")
		.speaks("Haitao Impersonator", " Ahh ~~~ My cover has been blown!")
		.exits("Lucas")
		.speaks("Cecil", " ... he ran away...")
		.enterRight("Alma")
		.speaks("Alma", " Say, Ben, do you know anything about a particularly strange \nphenomenon: Fire from the Sky? ")
		.speaks("Ben", " Oho! What have we here! A talking Remnant. Haven't seen one of \nthose in a while. And who are you?")
		.speaks("Alma", " The name's Alma.")
		.speaks("Ben", " My my, aren't you an interesting specimen. Ah yes. 'Fire from the Sky', \nwas it. 50 years ago, the same thing... He was a Remnant just like you. \nInferno was his name. He called forth meteors from the heavens. ")
		.speaks("Cecil", "  I.. I think I saw him!")
		.speaks("Ben", " Impossible. He was killed at the end of the last Great War.")
		.speaks("Cecil", " Caer Lido. The village of Caer Lido: He destroyed my home, everything I had...")
		.speaks("Ben", " There is a shrine to him.. in the land of Fire. The locals there worship \nhim as a protector god. Inferno was a benevolent soul. I wouldn't be surprised \nif one of the disciples of fire is having a fine old time masquerading around \nas his master and doing no good. You might want to check it out.")
		.exits("Ben")
		.speaks("Cecil", " the Land of Fire..")
		.exits("Alma")
		.exits("Cecil");
	
	
	
		//Zone 2\\
		
		Cutscene.start("GetSauceQuest")
		
		.enter("Sam")
		.speaks("Sam", "Hi Cecil! The town cook needs your help! His famous sauce was stolen. \n Here's the note from him.")
		.enter("Cecil")
		.speaks("Cecil", "Hmmm, 'My famous sauce was cooling on my windowsill just the other day; \n I go to do the laundry, and it disappears! I bet those neighborhood rascals \n have stolen it again! Go teach those ruffians a lesson for me!'")
		.speaks("Sam", "Haha, I saw some some kids go through the forest that's just north east \n of here. ")
		.speaks("Cecil", "Okay, I will go through the forest and find his sauce!")
		.exits("Sam")
		.exits("Cecil");
		
		Cutscene.start("GetSauceInter")
		
		.enter("Sam")
		.speaks("Sam", "The town cook is getting impatient...")
		.enter("Cecil")
		.speaks("Cecil", "Okay...")
		.exits("Cecil")
		.exits("Sam");
		
		Cutscene.start("MeetSauce")
		
		.enter("Cecil")
		.speaks("Cecil", "What's that??")
		.speaks("Sauce Monster", "RAORRRRRRRRRRR!!!!!!!")
		.speaks("Cecil", "Something tells me that the special ingredient wasn't \n the only thing that got added to the sauce. What horrible spelling!")
		.speaks("Sauce Monster", "HIIIIIIIIIIIIIISSSSSSSSSSSSP")
		.speaks("Cecil", "Sigh, I never get a break.")
		.exits("Cecil");

		Cutscene.start("KidThanks")
		
		.enter("Kid")
		.speaks("Kid", " *Sob Sob* Hey, thanks. We were just gonna eat it. \n We never imagined that it would turn around and try to eat US!")
		.enter("Cecil")
		.speaks("Cecil", " The Steam must have binded to this metal pot in response \n to the chef's strong, protective feelings for his food. \n Let's get this back to the city before it causes anymore trouble.")
		.exits("Kid")
		.exits("Cecil");
		
		Cutscene.start("KidInter")
		
		.enter("Kid")
		.speaks("Kid", "Thanks again! You're the best.")
		.enter("Cecil")
		.speaks("Cecil", "No problem!")
		.exits("Kid")
		.exits("Cecil");
		
		Cutscene.start("SauceDone")
		
		.enter("Sam")
		.speaks("Sam", "Awesome, thanks for getting all that mess resolved! Here's a special something!")
		.enter("Cecil")
		.speaks("Cecil", "Thanks!")
		.exits("Cecil")
		.exits("Sam");
		
		
		Cutscene.start("LucasAppears")
		.enter("Cecil")
		.speaks("Cecil", " I wonder where that Haitao impersonator went...")
		.enter("Alma")
		.speaks("Alma", " Yeah, I can't believe I thought that shabby little squirt was a Star Crafter.")
		.speaks("Cecil", " Yeah, after that run-in with the Caracal, they had to seal that part of \nthe forest because it was too dangerous. What an idiot.")
		.enterRight("Lucas")
		.speaks("Lucas", " Hey! I've got good character design!")
		.speaks("Cecil", " Ahhh!!!!")
		.speaks("Alma", " Get him!")
		.speaks("Lucas", " Ahhhh!!!!")
		.exits("Lucas")
		.speaks("Alma", "He's running towards the Eastern forest! That place is a maze, but once you get to \nthrough it's a dead end. We can trap him there!")
		.exits("Cecil")
		.exits("Alma");
		
		Cutscene.start("LucasEncounter")
		.enter("Cecil")
		.speaks("Cecil", " Okay, I've got you for sure!")
		.enter("Lucas")
		.speaks("Lucas", " No where to run, guess I gotta rough it out...")
		.exits("Cecil")
		.exits("Lucas");
		
		

		Cutscene.start("scene0")
		
		.enter("Lucas")
		.speaks("Lucas", " Ngh...\n")
		.enter("Guard")
		.speaks("Guard", " Hey, I heard some commotion - this is a peaceful forest. What are you \nguys doing??\n")
		.speaks("Lucas", " We just had a uh... misunderstanding! Don't worry...\n")
		.speaks("Guard", " Hey! Haven't I seen you somewhere?\n")
		.speaks("Lucas", " Ah-\n")
		.enterLeft("Cecil")
		.speaks("Cecil", " ...\n")
		.speaks("Cecil", " Nope.  My brother and I just moved into the city a week ago. We were \njust arguing because he drank all our money away.\n")
		.speaks("Lucas", " Broth-!!\n")
		.speaks("Cecil", "  Sorry, officer. Won't happen again.\n")
		.speaks("Guard", " Well, alright. Be careful! There's someone running around \nimpersonating a sage! You kids keep your noses clean! The next time I catch \nyou brawling, it'll be a night in the slammer for you!\n")
		.exits("Guard")
		.exits("Cecil")
		.enterRight("Cecil")
		.speaks("Lucas", "...\n")
		.speaks("Cecil", " Hey, who are you giving this herb to?\n")
		.speaks("Lucas", " Hehe~~ Herb? I just thought it had a pretty name~~~\n")
		.speaks("Cecil", " Don't play dumb with me.  I've seen this herb before; it's something \nthat people of Caer Lido used to pray for someone on death's door. Unwritten \nVerses? Unsung songs? Unfulfilled dreams? These are the props you need for the \nprayer.\n")
		.speaks("Lucas", "... \n")
		.speaks("Lucas", "  You got me you got me. I actually have a sick aunt...\n")
		.speaks("Lucas", "  Is what you want me to say, right!~?\n")
		.speaks("Lucas", " You are mistaken~ I just do it for the money~ timid Idiots believe \nanything if you tell them that it'll extend their lives. It's quite a \nprofitab-oomph!\n")
		.exits("Lucas")
		.speaks("Lucas", " You punched me!\n")
		.speaks("Cecil", " I should have turned you in to the Guard!\n")
		.speaks("Haitao", " ...So... noisy\n")
		.speaks("Lucas", " !!!\n")
		.speaks("Cecil", " !!!\n")
		.enterLeft("Haitao")
		.enterLeft("Lucas")
		.speaks("Haitao", " ...Stop sitting on me.... I was having a good nap.\n")
		.exits("Lucas")	
		.speaks("Cecil", " Wait a second... could you be....\n")
		.speaks("Haitao", " I'm Haitao. Hi.\n")
		.enterRight("Alma")
		.speaks("Alma", " All this time... he was just sleeping under this bush....\n")
		.speaks("Haitao", "Yeah...")
		.exits("Lucas")
		.exits("Alma");
		
		Cutscene.start("scene1")
		.enter("Haitao")
		.enter("Cecil")
		.speaks("Haitao", " It's quite simple, really.... Steam comes in levels...You can just \nlook at the color.\n")
		.speaks("Cecil", " !!! It's warm!\n")
		.speaks("Haitao", " A sophisticated magician does not wave a Steam-infused weapon around \nlike a stick.... He strategically channels the Steam of the world through his \nbody and shapes it to his will... try casting a fireball now.\n")
		.speaks("Cecil", " Whoa!\n")
		.enterLeft("Lucas")
		.speaks("Lucas", " AHHH! Not on me!~~~\n")
		.exits("Lucas")
		.speaks("Cecil", " the attack was so much stronger!\n")
		.speaks("Lucas", " TT_TT\n")
		.speaks("Haitao", " The advantage of this setup is that you can easily make abilities \nstronger and even make new ones from raw materials. \n")
		.speaks("Cecil", " haha, I can't wait to go to the guild and show the others!\n")
		.exits("Haitao")
		.exits("Cecil");
		
		//Lucas joins
		Cutscene.start("scene2")
		.enter("Cecil")
		.enter("Lucas")
		.speaks("Cecil", " why are you following me?\n")
		.speaks("Lucas", " You saved my skin from the policeman~ I owe you one~~~\n")
		.speaks("Cecil", " Ain't I lucky\n")
		.speaks("Lucas", " Haha, I'm useful, actually~ word on the street is you're looking to go \nto the land of fire~~~~\n")
		.speaks("Cecil", " Yeah?\n")
		.speaks("Lucas", " Well, there's a gate to the entrance which is locked to everyone but \nblood descendants of the original founders of that land.\n")
		.speaks("Cecil", "...yeah?\n")
		.speaks("Lucas", " Well~ That's where I came from~~ Why don't I take you there and open \nthat cute little gate for you, as an expression of my gratitude~\n")
		.speaks("Cecil", "....Alma?\n")
		.enterRight("Alma")
		.speaks("Alma", " What he says about the gate is true.\n")
		.speaks("Cecil", ".... -_-ll\n")
		.speaks("Lucas", " Great!~~~ Let's GOOOO~~~~\n")
		.exits("Cecil")
		.exits("Lucas")
		.exits("Alma");
		
		//Elise joins
		Cutscene.start("scene3")
		.enter("Cecil")
		.enter("Lucas")
		.speaks("Cecil", " Lucas\n")
		.speaks("Lucas", " ~~~\n")
		.speaks("Cecil", " Lucas.\n")
		.speaks("Lucas", " ~~~\n")
		.speaks("Cecil", " Lucas! Do you know where we're going!\n")
		.speaks("Lucas", " Towards the sound of this beautiful music~~~\n")
		.speaks("Cecil", " Music, wait. Say what? \n")
		.exits("Cecil")
		.exits("Lucas")
		.speaks("Elise", " Eek!\n")
		.speaks("Lucas", " Oh glorious maiden~ Grace mine ears again with the soulful serenade of \nthine righteous voi-\n")
		.speaks("Elise", " I'll murder you!\n")
		.enterLeft("Cecil")
		.enterLeft("Lucas")
		.enterRight("Elise")
		.speaks("Cecil", " Ah- Elise!\n")
		.speaks("Elise", " Cecil. What are you doing here?\n")
		.speaks("Cecil", " I'm making a pilgrimage to Inferno's shrine in the Land of Fire.\n")
		.speaks("Lucas", " But I thought we were gonna go there to ki- ouch~\n")
		.speaks("Elise", " Oh. I have a few missions to take care of in the area. Mind if I join \nyou?\n")
		.speaks("Cecil", " Um. Sure?\n")
		.exits("Cecil")
		.exits("Elise")
		.exits("Lucas");
		
		//almost to the beach
		Cutscene.start("scene4")
		.enter("Cecil")
		.enter("Lucas")
		.speaks("Cecil", " Well. This is a gate alright. Lucas.\n")
		.speaks("Lucas", " Sigh, I'm on it, I'm on it....\n")
		.exits("Lucas")
		.exits("Cecil")
		.speaks("Lucas", " ...\n")
		.speaks("Lucas", " AHH!!!\n")
		.enterLeft("Lucas")
		.speaks("Lucas", " A monster!! Just A MONSTER? Do you realize that this is the guardian \ngod of the city we're talking about here?!!! What the heck is it doing out \nhere on this side of the gate???!!! Or Alive!!\n")
		.enterLeft("Alma")
		.speaks("Alma", " Look!\n")
		.speaks("Alma", " There's nothing but cinders on the other side of the gate.  Looks like \nthe land of fire is totally burnt out.\n")
		.exits("Lucas")
		.exits("Alma")
		.enterRight("Cecil")
		.speaks("Cecil", " How awful...\n")
		.enterLeft("Lucas")
		.speaks("Lucas", " Are you thinking?\n")
		.speaks("Cecil", "...\n")
		.speaks("Lucas", " Uh-uh, ain't no way I'm fighting that thing!\n")
		.speaks("Cecil", " We released the seal to the gate. If we don't stop him here, Odell's in \ndeep trouble. One way or another, we have to contain him!\n")
		.speaks("Lucas", " HOW???\n")
		.exits("Lucas")
		.speaks("Cecil", " !\n")
		.enterLeft("Alma")
		.speaks("Alma", " The pen! It's glowing!\n")
		.speaks("Cecil", " Such power! It's reacting to Inferno! Almost as if it's telling me to \nfight!\n")
		.speaks("Alma", "... \n")
		.enterRight("Lucas")
		.speaks("Lucas", " TT_TT Ohhhhhhh.... \n")
		.exits("Cecil")
		.exits("Alma")
		.exits("Lucas");
		
		

		Cutscene.start("sceneInferno")
		.enter("Cecil")
		.enter("Alma")
		.speaks("Cecil", " Ha... ha.... Ha.....\n")
		.speaks("Alma", " What is it?\n")
		.speaks("Cecil", " These parts.... I recognize them. These were once objects from Caer \nLido.\n")
		.speaks("Alma", " ....You mean...\n")
		.speaks("Cecil", " Yeah. It seemed all so surreal before, like a dream, but now, I'm \nstaring at this ridiculous teapot and it's finally hit me... they're really \ngone, aren't they? One day, I will be like this, too, nothing but the sum of \nmy un-unified parts.\n")
		.speaks("Alma", " Little Hatchling, in the end, we must all pay our debts to the Maker of \nLife. \n")
		.speaks("Cecil", " I...\n")
		.speaks("Alma", " But human fate is a tangled skein of mysteries. I no longer believe it \nto be mere coincidence that you were the sole survivor of your village's \ndemise. A fallen angel he may have been, Inferno was divine. That weapon you \nhold. It is a god-slayer.\n")
		.speaks("Cecil", " !\n")
		.speaks("Alma", " It just doesn't add up. That the sleeping Inferno would be corrupted \njust in time to face the bearer of a divine weapon; something is amiss. The \nplot might be thicker than we previously imagined.\n")
		.exits("Cecil")
		.exits("Alma");
		
		
		//beach start
		Cutscene.start("scene5")
		.enter("Lucas")
		.enterLeft("Cecil")
		.speaks("Lucas", " Cecil, look!\n")
		.enter("Jackie")
		.speaks("Jackie", " nggnnn.\n")
		.exits("Lucas")
		.speaks("Cecil", " A girl in the wreckage!\n")
		.speaks("Jackie", " the Angel! You killed the Angel!\n")
		.speaks("Cecil", " Uh... well, uh...He sort of razed this place to the ground...\n")
		.speaks("Jackie", "  I'm gonna tell Kaivan all about it, and you guys will be in biiiig \ntrouble!\n")
		.speaks("Cecil", " uh...\n")
		.enterRight("Elise")
		.speaks("Elise", " Hold up just a second.\n")
		.exits("Lucas")
		.exits("Jackie")
		.enterLeft("Jackie")
		.speaks("Jackie", " Eek. Scary.\n")
		.speaks("Elise", " What do you know about these monsters?\n")
		.speaks("Jackie", " Nothing....\n")
		.speaks("Jackie", " ....\n")
		.speaks("Jackie", " .........................\n")
		.speaks("Jackie", " Nothing. Secret ninja art!\n")
		.exits("Jackie")
		.exits("Cecil")
		.exits("Elise")
		.speaks("Lucas", " Ack.\n")
		.enter("Cecil")
		.speaks("Cecil", " What a strange girl -_-lll\n")
		.enter("Alma")
		.speaks("Alma", " Did that girl just say �Kaivan�?\n")
		.enterRight("Elise")
		.speaks("Elise", " Kaivan as The Kaivan, the King's advisor, Kaivan?\n")
		.speaks("Cecil", " well, there's your thick plot, Alma.\n")
		.speaks("Alma", " Hmph. I'm quite good, aren't I?\n")
		.exits("Alma")
		.speaks("Elise", " With those injuries, she can't have gotten far away. Let's scout the \narea for anything suspicious.\n")
		.exits("Lucas")
		.exits("Elise");
		
		//beach middle
		Cutscene.start("scene6")
		.enter("Alma")
		.speaks("Alma", " The Maker of Life... What is he doing?\n")
		.enter("Cecil")
		.speaks("Cecil", " Alma?\n")
		.speaks("Alma", " Like I said, right? We all pay our debts to the Maker of Life. If these \nsupposedly-dormant Angels are alive and kicking like this, it must mean that \nthe Maker is clearing their debits. \n")
		.speaks("Cecil", " You mean to say that he is real?\n")
		.enterLeft("Lucas")
		.speaks("Lucas", " He is a Steam construct which watches over other Steam constructs. ~~\n")
		.speaks("Alma", " !!!\n")
		.exits("Alma")
		.speaks("Lucas", " I've met him with his night parade once, you know?~ Seemed like a nice \nchap. His presence seems to calm the other Remnants down. Saved my life back \nin the day, you know?~~~\n")
		.speaks("Cecil", " And you didn't think to follow him around, did you?\n")
		.speaks("Lucas", " what's that?~~~~\n")
		.speaks("Cecil", " Nothing, nothing.\n")
		.enterRight("Elise")
		.speaks("Elise", " You mean to tell me that Steam is sentient?\n")
		.speaks("Lucas", " I dunno. Looked more to me like he's doing his job. Maybe he's just \nlike the other remnants. He's marching along, getting stuff done, and then, \none day, he just becomes too broken down to function well, and he turns rouge.\n")
		.speaks("Elise", " That can't be true!\n")
		.exits("Cecil")
		.exits("Elise")
		.exits("Lucas")
		.enter("Alma")
		.speaks("Alma", " she's right. The Maker is just...\n")
		.enter("Cecil")
		.speaks("Cecil", " Alma?\n")
		.speaks("Alma", " It's nothing. If memory serves, the nine-day Harvest moon festival is \ncoming up. If the Maker has indeed turned rogue, I bet he's going to pull \nsomething while the entire human population of this continent gathers around \nthe ceremonies at the Romulan opera house.\n")
		.enterLeft("Lucas")
		.speaks("Lucas", " Say, isn't that where Jamming Alexandra's next concert is going to be~?\n")
		.speaks("Cecil", "uh...\n")
		.speaks("Lucas", " I'm such a big fan~~~~\n")
		.exits("Alma")
		.exits("Lucas")
		.exits("Cecil");
		

		//beach end
		Cutscene.start("scene7")
		.enter("Elise")
		.speaks("Elise", " Hey...when this whole misunderstanding gets sorted out, let's all go \nwatch the fireworks. \n")
		.enter("Cecil")
		.speaks("Cecil", " hm? \n")
		.enterLeft("Lucas")
		.speaks("Lucas", " Ah, our little farmer shows his stripes~~ Every five years, the capital \nholds a festival to celebrate the beauty of the round harvest moon. At the \ncenterpiece of the festival, the Rose of Eternity is a Steam vessel used as an \namplifier for the fireworks. \n")
		.speaks("Elise", " It's a miracle of science, really. They say that Rose is a relic from \nthe Maker of Life himself.\n")
		.speaks("Cecil", " The Maker of Life...\n")
		.speaks("Elise", " Each of the big cities have Steam guardians, you know? Inferno, Vega, \nGilgamesh, and Arachne. The Maker of Life himself is the benevolent observer \nof Odell. The Rose is the proof of his promise to guard the capital for now \nand always.\n")
		.speaks("Cecil", " Why would such a kind spirit suddenly turn evil?\n")
		.speaks("Elise", " That's why this must be all a mistake! Don't you see, it's our mission \nto clear the Maker's name!\n")
		.exits("Elise")
		.exits("Cecil")
		.exits("Lucas")
		.speaks("Jackie", " UUUU... ... \n")
		.enter("Jackie")
		.speaks("Jackie", " Ow... I'm stuck... \n")
		.enter("Elise")
		.speaks("Elise", " It's that girl!\n")
		.enterLeft("Lucas")		
		.speaks("Lucas", " Um... Should we?\n")
		.exits("Lucas")
		.speaks("Elise", "  Here... Take my hand.\n")
		.speaks("Elise", "!\n")
		.speaks("Jackie", "!\n")
		.exits("Elise")
		.exits("Jackie")
		.speaks("Elise", " AH!\n")
		.enter("Kaivan")
		.enterLeft("Elise")
		.speaks("Kaivan", " Hand over that stylus and no one gets hurt.\n")
		.enterRight("Cecil")
		.speaks("Cecil", " ELISE!??\n")
		.speaks("Cecil", " Stylus? This fountain pen? What do you want this for?\n")
		.speaks("Kaivan", " What is a pen used for?\n")
		.speaks("Cecil", " I don't understand.\n")
		.exits("Elise")
		.speaks("Kaivan", " Wouldn't you like to know.\n")
		.speaks("Cecil", "ELISE!!\n")
		.exits("Kaivan")
		.exits("Cecil");
		

		//beach end of battle
		Cutscene.start("scene8")
		.enter("Kaivan")
		.speaks("Kaivan", " You are... strong...\n")
		.enter("Cecil")
		.speaks("Cecil", "  Who is the Maker of Life? What is he planning???Why are you after this \nstylus???\n")
		.enterRight("Alma")
		.speaks("Alma", " Cecil... Calm down...\n")
		.exits("Alma")
		.speaks("Kaivan", " Heh. Isn't it obvious? He wants a perfect world of Steam beings. \n")
		.speaks("Kaivan", " A place without anyone to fight... pretty dull, if you ask me.\n")
		.speaks("Cecil", " !!!\n")
		.speaks("Kaivan", " That's his pen you're holding, by the way. I think he wants it back.\n")
		.exits("Kaivan")
		.exits("Cecil")
		.speaks("Kaivan", " !!!!\n")
		.enter("Kaivan")
		.speaks("Kaivan", " AGGGHHH!!!\n")
		.exits("Kaivan")		
		.enterRight("Lucas")
		.speaks("Lucas", " He's dead!\n")
		.exits("Lucas")	
		.enterRight("Cecil")
		.speaks("Cecil", " Who-?\n")
		.exits("Cecil")
		.enterRight("Alma")
		.speaks("Alma", " This presence!\n")
		.exits("Alma")
		.enter("Jackie")
		.speaks("Jackie", " Maker! Let's make our getaway!\n")
		.exits("Jackie")
		.speaks("???", " :)\n")
		.enter("Cecil")
		.speaks("Cecil", " Elise!\n")
		.exits("Cecil")
		.speaks("???", " :)\n")
		.speaks(" MoL", " The Romulan Operahouse. See you there, Little Scribe :)\n")
		.exits("Cecil")
		.enter("Cecil")
		.speaks("Cecil", " That was-! He's taken Elise!\n")
		.enterLeft("Lucas")
		.speaks("Lucas", " Cecil, let's go!\n")
		.exits("Kaivan")
		.exits("Cecil");
		
		//start of operahouse
		Cutscene.start("scene9")
		.enter("Cecil")
		.speaks("Cecil", " I've seen the fireworks in Caer Lido before, but never so close to the \nsource.\n")
		.speaks("Cecil", " ...\n")
		.speaks("Cecil", " Wait for us, Elise! We'll see the fireworks together!\n")
		.exits("Cecil");
		

		Cutscene.start("scene10")
		.enter("Jackie")
		.speaks("Jackie", " Oomph.\n")
		.enter("Lucas")
		.speaks("Lucas", " Ouch.\n")
		.speaks("Jackie", " Sorry...\n")
		.speaks("Lucas", " !!! You! You're the one who attacked us last time!\n")
		.speaks("Jackie", " ...\n")
		.exits("Jackie")
		.speaks("Lucas", " Wait! Stop!\n")
		.enterRight("Cecil")
		.speaks("Cecil", "  What are you looking at? \n")
		.speaks("Lucas", " Ah. She ran. I have a bad feeling about this. \n")
		.exits("Cecil")
		.exits("Lucas");
		
		Cutscene.start("scene11")
		.speaks("Alex", " ahh, ahhh ahhhhhhhhhhhhhh\n")
		.enter("Cecil")
		.speaks("Cecil", " What's happening on stage???! \n")
		.enter("Lucas")
		.speaks("Lucas", " I don't know! This powerful voice!\n")
		.speaks("Alex", " AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH~~~\n")
		.enterLeft("Alma")
		.speaks("Alma", " Look! There, on the Rose's balcony!\n")
		.speaks("Lucas", " It's hitting resonant frequency! The glass case, it'll shatter!\n")
		.speaks("Cecil", " We can't lose sight of it! Everyone, on your guard!\n")
		.exits("Cecil")
		.exits("Lucas")
		.exits("Alma");
		

		Cutscene.start("scene12")
		.enter("Alex")
		.enter("Lucas")
		.speaks("Alex", " I'm so jealous of you.\n")
		.speaks("Lucas", " Ouch, Miss Villain, arrow straight into my heart. I'm your biggest fan, \nyou know~\n")
		.speaks("Alex", " You should understand me. We are the same.\n")
		.speaks("Lucas", " Oh, please~ I know I have very good character design and all, but we've \nonly just met~\n")
		.speaks("Alex", " You hear them too, right, these children. These children of metal and \nSteam.\n")
		.speaks("Lucas", " These silly things-\n")
		.speaks("Alex", " No, I am wrong. This is where we are different. They are different \naround you; they clamour in joy for your attention, your acknowledgement, your \napproval. If it's you, they'll gladly leap to their immolation.\n")
		.speaks("Alex", " The armor I bear is a mantle of sorrows. I can only hear the pain, the \nsuffering, the abandonment, the sin. The unceasing, sullen minor of their \nlaments are the epistles of hell.\n")
		.speaks("Alex", " We are the same, yet so different. Why does it have to be you? Why are \nyou their saint while I am their martyr?\n")
		.speaks("Lucas", " Miss Villain, you overestimate me. I am a simple man.\n")
		.speaks("Alex", " And so... I have decided. I will cut you open, and I will inherit the \nsource of this radiance.\n")
		.speaks("Lucas", " ... This Steam....So powerful, yet so fragile...\n")
		.speaks("Alex", " !!!\n")
		.speaks("Lucas", " I understand. Exalted Muse, this humble bard will spin you a tune in \nthe only key he knows:  the peal of swords!\n")
		.exits("Alex")
		.exits("Lucas");

		Cutscene.start("scene13")
		.enter("Cecil")
		.speaks("Cecil", " !!! Ah!\n")
		.enter("Cory")
		.speaks("Cory", " Stop\n")
		.speaks("Cecil", " Who goes there?!\n")
		.speaks("Cory", " No one... Just... A phantom of the operahouse :)\n")
		.exits("Cecil")
		.exits("Cory");
		
		Cutscene.start("TutorialIntro")
		.enter("Sam")
		.speaks("Sam", "OH SHIT! THE TOWN IS ON FIRE!\n" +
				"(Press space to advance text.)")
		.exits("Sam")
		.enter("Cecil")
		.speaks("Cecil", "What's happening?! Why...??")
		.enter("Sam")
		.speaks("Sam", "Monsters...everywhere....Save yourself!")
		.speaks("Cecil", "No! I have to save my family!")
		.speaks("Sam", "Urk...")
		.exits("Sam")
		.speaks("Cecil", "Uh oh....")
		.exits("Cecil");
		
		Cutscene.start("GrandfathersGifts")
		.enter("Grandfather")
		.enter("Cecil")
		.speaks("Cecil", "OH NO!!! GRANDFATHER!")
		.speaks("Grandfather", "I'm ... urk ... not so sure I'm going to make it.....")
		.speaks("Cecil", "No... you'll be fine. Let's get out of here!")
		.speaks("Grandfather", "I'll just ... slow you down....\n" +
				"But here, I have something for you. These are some items I've collected.\n")
		.speaks("Grandfather", "They should allow you to.....")
		.speaks("Grandfather", "...avenge......")
		.speaks("Grandfather", "...me........")
		.exits("Grandfather")
		.speaks("Cecil", "Grandfather no!")
		.speaks("Cecil", "...I'll kill those who did this....")
		.exits("Cecil");
		
		
		Cutscene.start("MakerRapeage")
		.enter("Maker")
		.speaks("Maker", "Impossible!\n" +
				"What power is this??")
		.speaks("Maker", "....\n"+
				"...You did not earn these powers...\n" +
				"I bet you don't even understand steam!")
		.speaks("Maker", "Your abilities are undeserved.")
		.speaks("Maker", "So I'll strip you of them.")
		.speaks("Maker", "Now let's see you fight!")
		.exits("Maker");
		
		Cutscene.start("AlmaTeachesCrafting")
		.enter("Alma")
		.speaks("Alma", "He took all your weapons. \n You'll die for sure if he comes back for you.")
		.speaks("Alma", "Here, let me show you how to craft one. \n")
		.speaks("Alma", "First, I'll give you the raw materials. \n" +
				"Second, you'll go into your menu and craft them.")
		.exits("Alma");
		
		Cutscene.start("MakerRapeage2")
		.enter("Maker")
		.speaks("Maker", "You ran away. How typical.")
		.speaks("Maker", "You're just a coward who has never seen a real battle.")
		.speaks("Maker", "Let me enlighten you....")
		.exits("Maker");
				
	
		Cutscene.start("finalScene")
		.enter("Cecil")
		.speaks("Cecil", " ... You!\n")
		.speaks("Maker of Life", " It's a fine night at the opera, isn't it?\n")
		.speaks("Cecil", " Give Elise back!\n")
		.speaks("Maker of Life", " Careful, that tone doesn't befit this passage. She is perfectly \nfine, see?\n")
		.enterRight("Elise")
		.speaks("Elise", " Cecil...\n")
		.exits("Elise")
		.speaks("Cecil", " Elise...\n")
		.enterLeft("Alma")
		.speaks("Alma", " What are you trying to do?! \n")
		.speaks("Maker of Life", " What would you say if I promised to spare this world... in \nexchange, I will simply be content with taking the life of this girl.\n")
		.exits("Alma")
		.enterRight("Elise")
		.speaks("Elise", " !!\n")
		.exits("Elise")
		.speaks("Cecil", " I don't believe you!\n")
		.speaks("Maker of Life", " You humans... are always so off-key, even the most mellifluent \nof your sopranos...One moment rational, one moment passionate, never content \nin the beauty of your moments.\n")
		.speaks("Maker of Life", " And yet, even the most mighty Titans of our race can be so \neasily crushed by your saplings?\n")
		.speaks("Cecil", " There's nothing you can't do in life when you've got a cause to die for.\n")
		.speaks("Maker of Life", " Such a statement, yet so pointless, when you think of the \nthousands of Remnant �dying� for humans everyday. What kind of cause are we \nliving for? Why is �life� such a special word? Is the average Dustling any \nless viable than a cat, or a dog?  Was Lucas any less �alive� than this girl?\n")
		.speaks("Cecil", " I...!\n")
		.speaks("Maker of Life", " ...Enough of this... I am born out of human desire. I am made \nin man's image, but I reject man.\n")
		.speaks("Cecil", " !!! What have you done! The rose!\n")
		.speaks("Maker of Life", " My stylus... for the girl's life.\n")
		.speaks("Cecil", " ....\n")
		.speaks("Elise", " Ah!\n")
		.speaks("Cecil", " Elise...\n")
		.enterLeft("Alma")
		.speaks("Alma", "  you don't know what you are doing! STOP!\n")
		.speaks("Maker of Life", " I am the Maker of Life.\n")
		.speaks("Maker of Life", " I am the arbiter of the Past and the Future, the Guardian of all\n souls, living and dead.")
		.exits("Alma")
		.exits("Cecil")
		.exits("Maker of Life");
		
		
		//Quest Dialogue\\
		Cutscene.start("Quest1")
		.enter("Factory Boss")
		.speaks("Factory Boss", "Looking for a job to do? I have lots of work available cleaning up\nsteam!")
		.enter("Cecil")
		.speaks("Cecil", "...Steam?")
		.speaks("Factory Boss", "It's the greatest thing of the century! It turns teacups into self-warming\nteacups! Sandals into flying sandals! Children's toys into your dedicated,\npersonal servants!  Steam is the miracle of life!")
		.speaks("Cecil", "Wow!")
		.speaks("Factory Boss", "Yup! You can help me out by cleaning up the Dustlings that were roaming\naround in my factory. Get me 3 steam, and I'll know you've done the\njob.")
		.exits("Factory Boss")
		.exits("Cecil");

		Cutscene.start("Quest1Inter")
		.enter("Factory Boss")
		.speaks("Factory Boss", "Get cleaning!")
		.exits("Factory Boss");

		Cutscene.start("Quest1Done")
		.enter("Factory Boss")
		.speaks("Factory Boss", "Thanks for the steam! Here's a gem, you can craft jewels with these. Jewels can be \nequipped and give you abilities!")
		.enter("Cecil")
		.speaks("Cecil", "Thanks for this information!")
		.exits("Factory Boss")
		.exits("Cecil");

		Cutscene.start("Quest1Over")
		.enter("Factory Boss")
		.speaks("Factory Boss", "Thanks for helping me out!")
		.exits("Factory Boss");

		Cutscene.start("Quest2")
		.enter("Sam")
		.speaks("Sam", "Cecil, I need someone to find Rukh and destroy him. He has been unleashing\nbad steam to the Axe fighters, Shield Fighters, and Vagrants, making\nthem rogue.")
		.enter("Cecil")
		.speaks("Cecil", "Where can I find him?")
		.speaks("Sam", "I heard he lives in the forest Northeast of here, North of the Burning\nforest. For your service, I'll give you a gem.")
		.exits("Sam")
		.exits("Cecil");

		Cutscene.start("Quest2Inter")
		.enter("Sam")
		.speaks("Sam", "Rukh is rumored to be in the forest are north of the Burning Forest.")
		.exits("Sam");

		Cutscene.start("Quest2Done")
		.enter("Sam")
		.speaks("Sam", "Thanks Cecil! The rogue Remnants have been fewer these days, now that\nyou've eliminated Rukh. The forests are safer for travellers!")
		.enter("Cecil")
		.speaks("Cecil", "Yeah, well, he wasn't that hard to defeat. You said you would give\nme a gem?")
		.speaks("Sam", "Yup, here you go!")
		.speaks("Cecil", "Great, thanks!")
		.exits("Sam")
		.exits("Cecil");
		Cutscene.start("Quest2Over")
		.enter("Sam")
		.speaks("Sam", "Did you know there are three levels of steam that exist in this world?  \nregular steam, cirrus steam, and nacreous steam. You can craft the more powerful\nsteam from the lesser steams as you continue along your journey. You won't be able \nto craft more powerful steam levels until you advance further.")
		.exits("Sam");


		Cutscene.start("Quest3")
		.enter("Haitao")
		.speaks("Haitao", "Arachne, the queen of spiders, has been terrorizing the Burning forest and\neating travellers as they pass through. If you get rid of that pest,\nI'll give you jewels beyond your imagination!")
		.enter("Cecil")
		.speaks("Cecil", "Why would I need jewels?")
		.speaks("Haitao", "You can equip them to give you powerful\nabilities.")
		.speaks("Cecil", "Oh, right. Okay, I will kill Arachne for you!")
		.exits("Haitao")
		.exits("Cecil");

		Cutscene.start("Quest3Inter")
		.enter("Haitao")
		.speaks("Haitao", "I hear Arachne is still eating up travellers � go kill her!")
		.exits("Haitao");

		Cutscene.start("Quest3Done")
		.enter("Haitao")
		.speaks("Haitao", "Thanks for killing Arachne. Travellers will forever be in your debt!\nI'll be off now...")
		.enter("Cecil")
		.speaks("Cecil", "Wait! You said you would give me jewels!")
		.speaks("Haitao", "Uh... oops, about that... I've lost them.")
		.speaks("Cecil", "You... lost... them?")
		.speaks("Haitao", "But, uh... I have some scrap metal for you here. It can be crafted\ninto jewels!!")
		.speaks("Cecil", "You...")
		.exits("Haitao")
		.exits("Cecil");

		Cutscene.start("Quest3Over")
		.enter("Haitao")
		.speaks("Haitao", "Uh, sorry about that losing my jewels... If you go more north, more\npowerful monsters will drop gems!")
		.exits("Haitao");

		Cutscene.start("Quest4")
		.enter("Cortez")
		.speaks("Cortez", "Ooh, shiny! Darn, just a rock... Sigh...")
		.enter("Cecil")
		.speaks("Cecil", "What are you looking for?")
		.speaks("Cortez", "Any sort of metal! I like shiny things!")
		.speaks("Cecil", "I can help you find metals if you give me something in return�")
		.speaks("Cortez", "All I have are a bunch of useless bottles...")
		.speaks("Cecil", "Sounds like a deal!")
		.speaks("Cortez", "Okay, find me Scrap Metal, Platinum, and Iridium � the three kinds\nof metal in this world.")
		.exits("Cortez")
		.exits("Cecil");

		Cutscene.start("Quest4Inter")
		.enter("Cortez")
		.speaks("Cortez", "You don't have every kind of metal yet, go go!")
		.exits("Cortez");

		Cutscene.start("Quest4Done")
		.enter("Cortez")
		.speaks("Cortez", "Wow, thanks for the metals! Ooooh, shiny shiny! You know that you can\ncraft the lesser metals into better metals? They can be used to create\ncharms and jewels for abilities?")
		.enter("Cecil")
		.speaks("Cecil", "Why did I just give them to you...")
		.speaks("Cortez", "Haha, it's okay! I'll give you some bottles to hold powerful potions!")
		.speaks("Cecil", "Alright.")
		.exits("Cortez")
		.exits("Cecil");

		Cutscene.start("Quest4Over")
		.enter("Cortez")
		.speaks("Cortez", "Ooooh, shiny!!")
		.exits("Cortez");
		
		Cutscene.start("Quest5")
		.enter("Kid")
		.speaks("Kid", "*cries* Ahhhhhhhhh...")
		.enter("Cecil")
		.speaks("Cecil", "Kid, what are you crying about? What's wrong?")
		.speaks("Kid", "My grandmother is dying!!! My parents died when I was young. She's\nthe only family I have left...")
		.speaks("Cecil", "What's wrong with her?")
		.speaks("Kid", "She's dying of Aurelia's curse. The only potion that can cure her uses\nthe three kinds of steam of this world. I'm too weak to kill monsters\nand get steam, but I'm a master at brewing potions!")
		.speaks("Cecil", "Aww, Kid... I just lost my grandfather in a fire... I will help you\nsave your grandmother!")
		.exits("Kid")
		.exits("Cecil");

		Cutscene.start("Quest5Inter")
		.enter("Kid")
		.speaks("Kid", "My grandmother raised me...")
		.exits("Kid");

		Cutscene.start("Quest5Done")
		.enter("Cecil")
		.speaks("Cecil", "Here Kid, here's the steam to save your grandmother, hope it'll work...")
		.enter("Kid")
		.speaks("Kid", "Thank you so much... I owe you so much!  All I have are these bottles...")
		.speaks("Cecil", "That will be helpful, thanks!")
		.exits("Cecil")
		.exits("Kid");

		Cutscene.start("Quest5Over")
		.enter("Kid")
		.speaks("Kid", "Thanks again... my grandmother is getting better!")
		.exits("Kid");

		Cutscene.start("Quest6")
		.enter("Cecil")
		.speaks("Cecil", "What are you doing here in the lonely cove?")
		.enter("Annie")
		.speaks("Annie", "I'm scared to go through the grove South of here to go home... there's\na rumor that there's a powerful Steam remnant in the form of a pretty\ngirl named Shannon who's terrorizing travellers and stealing their\nbelongings. I really wanted to bring these shells back for my sister.")
		.speaks("Cecil", "I can get rid of her for you!")
		.speaks("Annie", "Really?! That would be absolutely wonderful! Thanks so much!")
		.exits("Cecil")
		.exits("Annie");

		Cutscene.start("Quest6Inter")
		.enter("Annie")
		.speaks("Annie", "I'm going to make a necklace for my sister. She's turning twelve!")
		.exits("Annie");

		Cutscene.start("Quest6Done")
		.enter("Cecil")
		.speaks("Cecil", "I've defeated Shannon the thief for you. She was indeed, very pretty.")
		.enter("Annie")
		.speaks("Annie", "Really?! You're so nice � here is some steam I found along the beach.\nThey will probably be useful for a traveller like you")
		.speaks("Cecil", "Thanks a lot!")
		.exits("Cecil")
		.exits("Annie");

		Cutscene.start("Quest6Over")
		.enter("Annie")
		.speaks("Annie", "Thanks again. I'll go home after I finish collecting shells.")
		.exits("Annie");

		Cutscene.start("Quest7")
		.enter("Kai")
		.speaks("Kai", "Hey man, whacha doing wandering these parts? It's dangerous around\nhere...")
		.enter("Cecil")
		.speaks("Cecil", "I can protect myself just fine! What are you doing around here?")
		.speaks("Kai", "I'm just tanning, man. I might also be protecting that girl over there.\nShe's too reckless for her own good. Coming here just to collect shells...\nwhat an idiot.")
		.speaks("Cecil", "Why is it dangerous?")
		.speaks("Kai", "Dude, haven't you heard of Aurelia? She's an ogre that lures in the\nbeach southeast of here. She lures travellers in with that damn pretty\ngirl that lives in its cage. Really freaky, I tell you.")
		.speaks("Cecil", "What does this Aurelia do to travellers?")
		.speaks("Kai", "She eats them... duh.")
		.speaks("Cecil", "Oh.")
		.speaks("Kai", "You think you can protect yourself?? Kill her, and then I'll believe\nyou.")
		.speaks("Cecil", "I will and that'll show you how powerful I am!")
		.speaks("Kai", "I'll believe it when I see it.")
		.exits("Kai")
		.exits("Cecil");

		Cutscene.start("Quest7Inter")
		.enter("Kai")
		.speaks("Kai", "Tch, you'll stand no chance against Aurelia.")
		.exits("Kai");

		Cutscene.start("Quest7Done")
		.enter("Cecil")
		.speaks("Cecil", "I defeated Aurelia like you said. Do you believe my power now??")
		.enter("Kai")
		.speaks("Kai", "Wow, dude, you actually did it. Maybe you do stand a chance...")
		.speaks("Cecil", "Stand a chance for what?")
		.speaks("Kai", "Whatever. Here's a Strong Potion of Speed, Power, and Protection. Take\nthem and they'll make you stronger for your journey.")
		.speaks("Cecil", "Awesome, thanks!")
		.exits("Cecil")
		.exits("Kai");

		Cutscene.start("Quest7Over")
		.enter("Kai")
		.speaks("Kai", "Good luck on your journey, man. You'll need all the luck you can get.")
		.exits("Kai");
		Cutscene.start("Quest9")
		.enter("June")
		.speaks("June", "Sigh... I want to go to Yggdrasil, the Tree of Life, to pay my respects\nto the ancient tree. People say if you do this once a year, your fortunes\nwill be good for one year.")
		.enter("Cecil")
		.speaks("Cecil", "Why don't you just go?")
		.speaks("June", "People can't these days... Ruwen, the Remnant of the Ancients, has\nbeen spelling all the visitors to sleep... and they don't wake up.")
		.speaks("Cecil", "Hmm, where is this Ruwen?")
		.speaks("June", "In the North East forest � it's a maze to the middle where Yggdrasil\nlies. She just sits in front of the tree all day and night.")
		.speaks("Cecil", "I'll drive her away for you.")
		.exits("June")
		.exits("Cecil");

		Cutscene.start("Quest9Inter")
		.enter("June")
		.speaks("June", "Ruwen still sits there... I can't go pay my respects!")
		.exits("June");

		Cutscene.start("Quest9Done")
		.enter("June")
		.speaks("June", "Thanks for driving Ruwen away! People can go pay respects to Yggdrasil\nnow. Here is a gem for your efforts. You can use them to craft\njewels and gain abilities!")
		.enter("Cecil")
		.speaks("Cecil", "Thanks!")
		.exits("June")
		.exits("Cecil");

		Cutscene.start("Quest9Over")
		.enter("June")
		.speaks("June", "Did you know Yggdrasil is over ten thousand years old?")
		.exits("June");

		Cutscene.start("Quest10")
		.enter("Cecil")
		.speaks("Cecil", "Why are your eyes bound?")
		.enter("Viola")
		.speaks("Viola", "I had a bit of an accident at the bakery... I put in essence of spider\ninstead of sugar and it turned my cake into Remnants. They clawed my\neyes out...")
		.speaks("Cecil", "Oh no! Are there any potions that can help you?")
		.speaks("Viola", "Yes, but the potion master needs 2 Huge Vials, 2 Iridium, and 2 Nacreous\nSteam. Those things are rare these days.")
		.speaks("Cecil", "I can get those things for you.")
		.speaks("Viola", "Really? If you give me those, I can give you a few potions for vitality.")
		.exits("Cecil")
		.exits("Viola");

		Cutscene.start("Quest10Inter")
		.enter("Viola")
		.speaks("Viola", "My poor eyes...")
		.exits("Viola");

		Cutscene.start("Quest10Done")
		.enter("Cecil")
		.speaks("Cecil", "I got you the supplies and ingredients for your potion!")
		.enter("Viola")
		.speaks("Viola", "Wow, you're such a darling... Thank you so much! I'll get these to\nthe potion master right away. Here is a Potent Elixir of Vitality!")
		.speaks("Cecil", "Thanks!")
		.exits("Cecil")
		.exits("Viola");

		Cutscene.start("Quest10Over")
		.enter("Viola")
		.speaks("Viola", "My potion should be ready any day now...")
		.exits("Viola");
		
		Cutscene.start("Quest11")
		.enter("Horace")
		.speaks("Horace", "Laaaaaaaaaa... Laaaaa... ohhhhhhhhh")
		.enter("Cecil")
		.speaks("Cecil", "What are you doing?")
		.speaks("Horace", "Practicing for my concert. I am the singer, and I'll be accompanied\nby an entire orchestra!")
		.speaks("Cecil", "Oh, I'm going to Jammin Alexandra's concert at the opera house!")
		.speaks("Horace", "She's really an amazing singer... she is my inspiration.  Hmm... you\nknow what's missing in the orchestra right now? A mallet instrument\nmade of differently sized bottles. We just can't find that many different\nsizes. We need 6 sizes.")
		.speaks("Cecil", "I can give that to you!")
		.speaks("Horace", "Really? I have a health potion or two lying around here somewhere...\nI'll find it for you if you find some bottles for me.")
		.speaks("Cecil", "Sounds like a deal.")
		.exits("Horace")
		.exits("Cecil");

		Cutscene.start("Quest11Inter")
		.enter("Horace")
		.speaks("Horace", "Ohhhhhh... La... Dooo...")
		.exits("Horace");

		Cutscene.start("Quest11Done")
		.enter("Cecil")
		.speaks("Cecil", "Here are 6 differently sized bottles!")
		.enter("Horace")
		.speaks("Horace", "Here's a Full Health Potion. Don't know what you would want with this,\nbut it's all I have...")
		.speaks("Cecil", "Thanks!")
		.exits("Cecil")
		.exits("Horace");

		Cutscene.start("Quest11Over")
		.enter("Horace")
		.speaks("Horace", "Do Re Mi Fa Soh La Ti Doooooo!!!!")
		.exits("Horace");
		Cutscene.start("Quest12")
		.enter("Raymond")
		.speaks("Raymond", "Hi there, little boy, what are you doing around here? You don't look\nlike you belong here...")
		.enter("Cecil")
		.speaks("Cecil", "Uh... I stumbled in here by accident. This opera house just looked\nso beautiful, that I couldn't help but explore.")
		.speaks("Raymond", "Of course...")
		.speaks("Cecil", "But now that I'm here, you don't happen to have potions, do you?")
		.speaks("Raymond", "Hmm? What do you need potions for?")
		.speaks("Cecil", "Um... I'm feeling a bit sick...")
		.speaks("Raymond", "Well, tell you what, little boy, I'll give you potions if you give\nme 2 Rubies, 2 Aquamarines, and 2 Quartzes.")
		.speaks("Cecil", "Yes sir.")
		.exits("Raymond")
		.exits("Cecil");

		Cutscene.start("Quest12Inter")
		.enter("Raymond")
		.speaks("Raymond", "Where are my jewels, boy?")
		.exits("Raymond");

		Cutscene.start("Quest12Done")
		.enter("Cecil")
		.speaks("Cecil", "Here are the jewels you wanted, sir.")
		.enter("Raymond")
		.speaks("Raymond", "I'm impressed, boy. I won't ask how you got these. Here are two Potent\nElixirs of Vitality.")
		.speaks("Cecil", "Thank you, sir.")
		.speaks("Raymond", "If you run along, I'll pretend I never saw you.")
		.exits("Cecil")
		.exits("Raymond");

		Cutscene.start("Quest12Over")
		.enter("Raymond")
		.speaks("Raymond", "Hm, who are you?")
		.exits("Raymond");

		Cutscene.start("Quest8")
		.enter("Eve")
		.speaks("Eve", "That annoying Inferno has been burning up the forest where ever I go!\nI absolutely hate this barrenness.  It makes my heart weep inside...")
		.enter("Cecil")
		.speaks("Cecil", "I'm sorry, miss...")
		.speaks("Eve", "Destroy him for me! He's probably hiding in some trees, burning them\nup. I last saw him going North.")
		.speaks("Cecil", "Okay. I will hunt him down for you!")
		.exits("Eve")
		.exits("Cecil");

		Cutscene.start("Quest8Inter")
		.enter("Eve")
		.speaks("Eve", "Inferno is still burning down the forest...")
		.exits("Eve");

		Cutscene.start("Quest8Done")
		.enter("Cecil")
		.speaks("Cecil", "I've destroyed Inferno for you. He's done with forest burning.")
		.enter("Eve")
		.speaks("Eve", "Yay, great! I can spend my days wandering around the forest again ~\nAdmiring the beauty around me... Wanderer, you need steam right? Here\nis a Nacreous Steam for your help.")
		.speaks("Cecil", "Thanks!")
		.exits("Cecil")
		.exits("Eve");

		Cutscene.start("Quest8Over")
		.enter("Eve")
		.speaks("Eve", "The world is beautiful, no?")
		.exits("Eve");

	}
}
