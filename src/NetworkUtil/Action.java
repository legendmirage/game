package NetworkUtil;

import model.GameModel;
import client.ActionType;
import client.EpicGameContainer;

/**
 * Players perform actions to change the state of the game. <br>
 * Actions are immutable. <br>
 */
public class Action implements Comparable<Action>{
	/** The id of the creature that performed this action. */
	public final int userID;
	public final ActionType type;
	public final int tickNum;
	public final String arg;
	/** Creates an action. */
	public Action(int userID, ActionType type, String arg) {
		this.userID = userID;
		this.type = type;
		this.arg = arg;
		this.tickNum = GameModel.MAIN.tickNum;
	}
	/** Creates an action. By default, the player ID is the one of my player. */
	public Action(ActionType type, String arg) {
		this(EpicGameContainer.MAIN.myID, type, arg);
	}

	/** Creates an action for a type of action which does not need an argument. */
	public Action(ActionType type) {
		this(EpicGameContainer.MAIN.myID, type, "");
	}

	/** Creates an action for a type of action which does not need an argument. */
	public Action(int playerID, ActionType type) {
		this(playerID, type, "");
	}

	@Override
	public int compareTo(Action o) {
		if(this.tickNum>o.tickNum)
			return 1;
		else if(this.tickNum<o.tickNum)
			return -1;
		else if(this.tickNum==o.tickNum)
			if(o.userID==this.userID)
				return 0;
			else if(this.userID>o.userID)
				return 1;
			else
				return -1;
		return 0;				
	}
	public boolean equals(Action o) {
		return (tickNum== o.tickNum && userID==o.userID && type== o.type && arg.equals(o.arg));
	}
	public int hashCode(){
		return tickNum*type.ordinal()+userID;
	}

}
