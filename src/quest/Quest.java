package quest;
import java.util.ArrayList;
import quest.component.QuestComponent;


public class Quest {

	/** Complete list of components in a Quest */
	public ArrayList <QuestComponent> components= new ArrayList<QuestComponent>();
	
	/** Components left in a Quest, each component is updated once a task is complete */
	public ArrayList <QuestComponent> componentsLeft = new ArrayList<QuestComponent>();
	
	/** Components obtained in a Quest, each component is updated once a task is complete */
	public ArrayList <QuestComponent> componentsObtained = new ArrayList<QuestComponent>();
	
	public Quest(QuestInfo info){
		components = info.components;
		
		componentsLeft.addAll(components);
		
	}
	
    
    public void updateProgress(){
    	
    	ArrayList<QuestComponent> temp = new ArrayList<QuestComponent>(); 
    	
    	for(QuestComponent com: componentsLeft){
    		
    		if(com.complete()){
    			temp.add(com);
    		}
    	}
    	
    	for(QuestComponent com : temp){
    		componentsLeft.remove(com);
    		componentsObtained.add(com);
    	}  	
    	
    }


    
    public boolean questCompleted(){
    	if (componentsLeft.size() == 0) {
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public ArrayList<QuestComponent> getQuest(){
		return components;
	}
    
	public ArrayList<QuestComponent> getComponentsLeft(){
		return componentsLeft;
	}
	
	public ArrayList<QuestComponent> getComponentsObtained(){
		return componentsObtained;
	}
    
    
} 
