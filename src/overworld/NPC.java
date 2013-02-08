package overworld;


public class NPC extends OverworldObject {
	
	private String questName;
	private String sceneName;
	private String sceneName2;
	private String sceneName1;
	private String sceneName3;
	private String recipe;
	
	public NPC(int zone, int x, int y) {
		super(zone, x, y);
		
	}
	/** Sets the associated quest with this NPC */
	public void setQuestName(String questName){this.questName = questName;}
	/** Gets the associated quest with this NPC */
	public String getQuestName(){return questName;}
	/** Sets the associated cutscene with this NPC */
	public void setSceneName(String sceneName){this.sceneName = sceneName;}
	/** Gets the associated cutscene with this NPC */
	public String getSceneName(){return sceneName;}
	/** Sets the associated intermediate cutscene with this NPC */
	public void setSceneName1(String sceneName){this.sceneName1 = sceneName;}
	/** Gets the associated intermediate cutscene with this NPC */
	public String getSceneName1(){return sceneName1;}
	
	/** Sets the associated post-quest cutscene with this NPC */
	public void setSceneName2(String sceneName){this.sceneName2 = sceneName;}
	/** Gets the associated post-quest cutscene with this NPC */
	public String getSceneName2(){return sceneName2;}
	
	/** Sets the associated post-quest cutscene with this NPC */
	public void setSceneName3(String sceneName){this.sceneName3 = sceneName;}
	/** Gets the associated post-quest cutscene with this NPC */
	public String getSceneName3(){return sceneName3;}
	
	/** Sets the associated post-quest cutscene with this NPC */
	public void setRecipe(String recipe){this.recipe = recipe;}
	/** Gets the associated post-quest cutscene with this NPC */
	public String getRecipe(){return recipe;}
}
