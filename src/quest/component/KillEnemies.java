package quest.component;

import model.GameModel;
import enemy.EnemyType;

/** Kill a number of enemies of a specific type*/
public class KillEnemies implements QuestComponent{
	
	public EnemyType enemyType;
	public int numToKill;
	
	public KillEnemies(EnemyType enemyType, int num){
		this.enemyType = enemyType;
		this.numToKill = num;
	}
	
	/** For menu display of quests. Says how many monsters left to kill.*/
	@Override
	public String toString(){
		return ("Kill " + numToKill + " " + enemyType.name + "s" + " (" + getNumKilled( ) + "/" + numToKill + ")");
	}

	
	@Override
	public boolean complete(){
		if(getNumKilled() >= numToKill){
			return true;
		}
		return false;
	}
	
	/** Calculates number of enemies left to kill.*/
	private int getNumKilled(){
		return GameModel.MAIN.getKillCount(enemyType.name);
	}
	
}
