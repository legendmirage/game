package debug;

import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import util.GameConstants;
import util.KeyBindings;
import util.Logger;
import client.EpicGameContainer;

public class DebugOverlay {
	private String onScreenText;
	private int defaultTextLength;
	private Color textColor = new Color(255,0,0);
	private String defaultString = "> ";
	private HashMap<String,Class<? extends DebugCommand> > commandList;
	
	private final LinkedList<String> history;
	private int textHeight = 15, historyIndex=-1;
	
	public DebugOverlay(){
		onScreenText = defaultString;
		defaultTextLength = defaultString.length();
		commandList = new HashMap<String,Class<? extends DebugCommand> >();
		history = new LinkedList<String>();
	}
	public void exitConsole(){
		onScreenText = defaultString;
	}

	private void addToHistory(String command){
		history.addFirst(command);
		if(history.size() > 18) history.removeLast();
		historyIndex = -1;
	}
	private void pastCommand(int change){
		historyIndex+=change;
		if (historyIndex>=history.size()) historyIndex = history.size()-1;
		if (historyIndex<=-1) {
			historyIndex = -1;
			onScreenText = defaultString;
			return;
		}
		else if (historyIndex>18) historyIndex = 18;
		onScreenText = history.get(historyIndex);
	}
	public void sendCommand(){
		String command="";
		String args = "";
		int splitIndex = onScreenText.indexOf(" ", defaultTextLength+1);
		
		//If there are args given by the user
		if (splitIndex>defaultTextLength){
			command = onScreenText.substring(defaultTextLength,splitIndex);
			args = onScreenText.substring(splitIndex+1,onScreenText.length());
			
		}
		//If there are no args given by the user
		else {
			command = onScreenText.substring(defaultTextLength,onScreenText.length());
		}

		Logger.log("Command: " + command + "; args: " + args + ". Command has been found? " + commandList.containsKey(command));
		
		if (commandList.containsKey(command)){
			try {
				String result = commandList.get(command).getDeclaredConstructor().newInstance().run(args);
				if(result != null) addToHistory(result);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else {
			addToHistory("command not found");
		}

		addToHistory(onScreenText);
		onScreenText = defaultString;
	}
	
	/** Add a command to the debug console
	 * 
	 * @param commandString The text to enter to indicate the entry belongs to this command. (Ie in "> spawn Dustling", spawn is the commandString)
	 * @param toExecute The class whose run command should be executed when the command is entered.
	 */
	public void addCommand(String commandString, Class<? extends DebugCommand> toExecute){
		commandList.put(commandString, toExecute);		
	}
	
	public void keyPressed(int keyVal, char keyChar){

		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;

		if (keyVal==Input.KEY_RSHIFT||keyVal==Input.KEY_LSHIFT||keyVal==Input.KEY_CAPITAL) return;
		if (keyVal==kb.enter) sendCommand(); 
		else if (keyVal==kb.up) pastCommand(1);
		else if (keyVal==kb.down) pastCommand(-1); 
		else if (keyVal==kb.delete && onScreenText.length()>defaultTextLength) onScreenText = onScreenText.substring(0,onScreenText.length()-1);
		else onScreenText+=keyChar;


	}
	
	public void render(GameContainer gc, Graphics g){
		
		int hpos = GameConstants.SCREEN_HEIGHT - 300;
		
		g.setColor(new Color(0,0,0,200));
		g.fillRect(0, hpos , 500, 300);
		
		g.setColor(textColor);
		g.drawString(onScreenText, 0, hpos);
		for (int i = 0; i<history.size(); i++){
			if (history.get(i)!=null)
				g.drawString(history.get(i),0,hpos + (i+1)*textHeight);
		}
	}

}
