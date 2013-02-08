package content;

import client.EpicGameContainer;
import debug.DebugPlay;
import debug.DebugGiveItem;
import debug.DebugRemoveMonsters;
import debug.DebugMute;
import debug.DebugSpawnCommand;
import debug.DebugTeleport;
import debug.DebugDialogue;
import debug.DebugKill;

public class DebugCommandLoader {

	public static void loadAll() {

		EpicGameContainer.MAIN.debugConsole.addCommand("spawn", DebugSpawnCommand.class);
		EpicGameContainer.MAIN.debugConsole.addCommand("give", DebugGiveItem.class);
		EpicGameContainer.MAIN.debugConsole.addCommand("teleport", DebugTeleport.class);
		EpicGameContainer.MAIN.debugConsole.addCommand("dialog", DebugDialogue.class);
		EpicGameContainer.MAIN.debugConsole.addCommand("obliterate", DebugRemoveMonsters.class);
		EpicGameContainer.MAIN.debugConsole.addCommand("kill", DebugKill.class);
		EpicGameContainer.MAIN.debugConsole.addCommand("play", DebugPlay.class);
		EpicGameContainer.MAIN.debugConsole.addCommand("mute", DebugMute.class);
	}
}
