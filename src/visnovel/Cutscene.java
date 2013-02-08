package visnovel;

import graphics.RenderComponent;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import client.EpicGameContainer;

import content.MenuLoader;

import util.GameConstants;
import util.Logger;

/** Cutscene factory generator */
public class Cutscene implements CutsceneDSL, RenderComponent {
	
	public static int MAX_PORTRAITS = 2;
	
	public static int SIDE_LEFT  = 1;
	public static int SIDE_RIGHT = 2;
	public static int SIDE_FREE  = 3;
	public static int SIDE_LEFT_LEFT  = 4;
	public static int SIDE_RIGHT_RIGHT  = 5;
	
	public static HashMap<String,Image> PORTRAITS = new HashMap<String,Image>();
	public static HashMap<String,Cutscene> SCENES = new HashMap<String,Cutscene>();
	
	
	public class CutsceneException extends RuntimeException {
		private static final long serialVersionUID = -1267375667519367074L;
		public CutsceneException(String msg) {
			super("CutsceneException: ".concat(msg));
		}
	}
	
	public class CutsceneEvent {
		/** Whether the advance dialog key is required to advance this event */
		public boolean requireAdvance = false;
	}
	public class DialogEvent extends CutsceneEvent {
		public String characterName;
		public String dialog;
		public DialogEvent(String characterName, String dialog) {
			this.requireAdvance = true;
			this.characterName = characterName;
			this.dialog = dialog;
		}
		public String toString() {return "Dlg";}
	}
	public class EnterEvent extends CutsceneEvent {
		public String characterName;
		public int side;
		public EnterEvent(String characterName, int side) {
			this.characterName = characterName;
			this.side = side;
		}
		public String toString() {return "Ent";}
	}
	public class ExitEvent extends CutsceneEvent {
		public String characterName;
		public ExitEvent(String characterName) {
			this.characterName = characterName;
		}
		public String toString() {return "Ext";}
	}
	
	// CUTSCENE VARIABLES
	/** Portraits on the left side */
	private final ArrayList<String> leftPortraits;
	/** Portraits on the right side */
	private final ArrayList<String> rightPortraits;
	/** The current dialog on the screen */
	private String currentDialog;
	/** List of events to happen */
	private final ArrayList<CutsceneEvent> eventList;
	/** Whether this is a playable instance of a Cutscene */
	private final boolean isPlayable;
	/** List iterator */
	private int curEventIdx;

	/** Cannot be instantiated */
	private Cutscene(ArrayList<CutsceneEvent> events, boolean playable) {
		leftPortraits = new ArrayList<String>();
		rightPortraits = new ArrayList<String>();
		if(events == null) eventList = new ArrayList<CutsceneEvent>();
		else eventList = events;
		isPlayable = playable;
		curEventIdx = 0;
	}

	/** Editable cutscene */
	private Cutscene() {
		this(null, false);
	}
	
	/** Playable cutscene */
	private Cutscene(ArrayList<CutsceneEvent> events) {
		this(events, true);
	}

	/** Begins generation of a new Cutscene */
	public static Cutscene start(String sceneName) {
		Cutscene scene = new Cutscene();
		SCENES.put(sceneName, scene);
		return scene;
	}
	
	@Override
	/** {@inheritDoc} */
	public Cutscene enter(String characterName) {
		eventList.add(new EnterEvent(characterName, SIDE_FREE));
		return this;
	}

	@Override
	/** {@inheritDoc} */
	public Cutscene enterLeft(String characterName) {
		eventList.add(new EnterEvent(characterName, SIDE_LEFT));
		return this;
	}
	
	@Override
	/** {@inheritDoc} */
	public Cutscene enterRight(String characterName) {
		eventList.add(new EnterEvent(characterName, SIDE_RIGHT));
		return this;
	}
	
	@Override
	/** {@inheritDoc} */
	public Cutscene exits(String characterName) {
		eventList.add(new ExitEvent(characterName));
		return this;
	}
	
	@Override
	/** {@inheritDoc} */
	public Cutscene speaks(String characterName, String dialog) {
		eventList.add(new DialogEvent(characterName, dialog));
		return this;
	}
	
	@Override
	/** {@inheritDoc} */
	public Cutscene narrate(String narration) {
		eventList.add(new DialogEvent("", narration));
		return this;
	}
	
	/** Generates a copy of the Cutscene for playing */
	public static Cutscene play(String sceneName) {
		
		if(EpicGameContainer.MAIN.showDialogue == true){
			Logger.log("Playing Scene: " + sceneName);
			
			Cutscene scene = SCENES.get(sceneName);
			
			ArrayList<CutsceneEvent> eventcopy = new ArrayList<CutsceneEvent>();
			for(CutsceneEvent e : scene.eventList) {
				eventcopy.add(e);
			}
		
			// do an initial step to bootstrap any important events
			return new Cutscene(eventcopy).step();
		}
		return null;
	}

	/** Steps through an event of the Cutscene */
	public Cutscene step() {
		if(!isPlayable) throw new CutsceneException("Cutscene Not Playable");
		
		CutsceneEvent curEvent;
	
		do{
			curEvent = eventList.get(curEventIdx);
			if(curEvent instanceof EnterEvent) {
				EnterEvent e = (EnterEvent)curEvent;
				
				if(e.side == SIDE_LEFT) {
					if(leftPortraits.size() >= MAX_PORTRAITS) throw new CutsceneException("Too many portraits on left");
					leftPortraits.add(e.characterName);
				} else if (e.side == SIDE_RIGHT) {
					if(rightPortraits.size() >= MAX_PORTRAITS) throw new CutsceneException("Too many portraits on left");
					rightPortraits.add(e.characterName);
				} else {
					if(leftPortraits.size() == 0) {
						leftPortraits.add(e.characterName);
					} else if(rightPortraits.size() == 0)  {
						rightPortraits.add(e.characterName);
					} else {
						throw new CutsceneException("No free portrait side. (Explicitely use left/right for more than 1)");
					}
				}
			} else if(curEvent instanceof DialogEvent) {
				DialogEvent e = (DialogEvent)curEvent;
				if(e.characterName.equals("")) {
					currentDialog = e.dialog;
				} else {
					currentDialog = e.characterName + ": " + e.dialog;
				}
			} else if(curEvent instanceof ExitEvent) {
				ExitEvent e = (ExitEvent)curEvent;
				leftPortraits.remove(e.characterName);
				rightPortraits.remove(e.characterName);
			}
			curEventIdx++;
			Logger.log(curEventIdx + "/" + eventList.size());
			
		} while(!curEvent.requireAdvance && curEventIdx < eventList.size());
		
		return this;
	}

	public String toString() {
		return "<Cutscene" + " p:" + this.isPlayable + " l:" + this.eventList.size();
	}
	
	/** Returns whether or not a cutscene is completed */
	public boolean isDone() {
		return curEventIdx >= eventList.size();
	}
	
	public void drawPortrait(Image charImg, int side) {
		
		int sh = GameConstants.SCREEN_HEIGHT;
		int sw = GameConstants.SCREEN_WIDTH;
	
		int h = (int) (charImg.getHeight()/1.5);
		int w = (int) (charImg.getWidth()/1.5);
	
		if (side == SIDE_LEFT) {
			charImg.draw(-(int)(.25*w), sh - h, w, h);
		}
		else if (side == SIDE_LEFT_LEFT) {
			charImg.draw((int)(.1*w), sh - h, w, h);
		}
		else if(side == SIDE_RIGHT) {
			charImg.draw(sw - (int)(.75*w) + w, sh - h, -w, h);
		}
		else if (side == SIDE_RIGHT_RIGHT) {
			charImg.draw(sw - (int)(1.1*w) + w, sh - h, -w, h);
		}
		
	}
	
	
	public void render(GameContainer gc, Graphics g) {
		int sh = GameConstants.SCREEN_HEIGHT;
		int sw = GameConstants.SCREEN_WIDTH;
		
		if(leftPortraits.size() >= 2) {
			drawPortrait(PORTRAITS.get(leftPortraits.get(1)), SIDE_LEFT_LEFT);
		}
		if(leftPortraits.size() >= 1) {
			drawPortrait(PORTRAITS.get(leftPortraits.get(0)), SIDE_LEFT);
		}
		if(rightPortraits.size() >= 2) {
			drawPortrait(PORTRAITS.get(rightPortraits.get(1)), SIDE_RIGHT_RIGHT);
		}
		if(rightPortraits.size() >= 1) {
			drawPortrait(PORTRAITS.get(rightPortraits.get(0)), SIDE_RIGHT);
		}
		
		if(currentDialog != null) {
			g.setColor(Color.black);
			g.drawImage(MenuLoader.DIALOGUE_BG, 0, sh-150);
			g.setColor(Color.white);
			g.drawString(currentDialog, 150, sh-120);
		}
	}
	
}
