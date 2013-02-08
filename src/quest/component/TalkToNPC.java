package quest.component;

import model.GameModel;

public class TalkToNPC implements QuestComponent {
	
	public String cutscene;
	public String questName;
	
	public TalkToNPC(String questName, String cut){
		this.cutscene = cut;
		this.questName = questName;
	}
	
	@Override
	public boolean complete() {
		if(GameModel.MAIN.cutscenesDone.indexOf(cutscene) != -1){
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return questName;
	}
}
