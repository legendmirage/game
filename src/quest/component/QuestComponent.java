package quest.component;


/** A class of things that a quest can require the player to do. */
public interface QuestComponent {
	
	/** For menu display of quests. Displays quest progress.*/
	@Override
	public String toString();
	
	/** Returns True if component is complete*/
	public boolean complete();
	
	
}
