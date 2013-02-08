package graphics;

import java.util.ArrayList;

import model.GameModel;
import model.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import overworld.OverworldConstants;
import overworld.ZoneModel;

import client.EpicGameContainer;

import battle.BattleCreature;
import battle.BattleModel;
import battle.BattlePlayer;

import content.FontLoader;

import util.GameConstants;
import util.Logger;
import util.RenderUtil;
import util.U;

public class PopupRenderer {
	private static int WIDTH = 340, HEIGHT = 30, TEXT_OFFSET = 15;
	private static float initialX = GameConstants.SCREEN_WIDTH-WIDTH;
	private static float initialY = GameConstants.SCREEN_HEIGHT*2/30;
	private static int displayTimeout=500, timeUntilFade = 350;
	private static int initialFade = 200, fadeThreshold = 40;

	
	static ArrayList<Popup> popupList;

	public static void renderImpermanentPopups(GameContainer gc, Graphics g) {

		Font prevFont = g.getFont();
		g.setFont(FontLoader.POPUP_FONT);
		float xPose = initialX, yPose = initialY;

		if (popupList==null) popupList = new ArrayList<Popup>();
		ArrayList<Popup> toRemove = new ArrayList<Popup>();
		
		//Draw the impermanent popups
		for (Popup p: popupList) {
			if (p.isPermanent) continue;
			if (GameModel.MAIN.tickNum-p.initTime>displayTimeout && !p.isPermanent) toRemove.add(p);
			else{
				if (!p.isPermanent){
					p.fadeState = (int)(255-((float)(GameModel.MAIN.tickNum-p.initTime-timeUntilFade)/(displayTimeout-timeUntilFade))*255);
					if (p.fadeState<fadeThreshold){
						p.fadeState = 0;
						toRemove.add(p);
					}
					else if (p.fadeState>initialFade) p.fadeState = initialFade;
				}
				else p.fadeState = initialFade;
				g.setColor(new Color(0,0,0,p.fadeState));
				RenderUtil.renderPrettyBox(g, (int)initialX, (int)yPose, WIDTH-RenderUtil.PIXEL_WIDTH*2, HEIGHT-RenderUtil.PIXEL_HEIGHT*2, p.fadeState, p.isPermanent);
				g.drawString(p.text, initialX+TEXT_OFFSET, yPose+(HEIGHT/2)-TEXT_OFFSET/3);
			}
			yPose+=HEIGHT+RenderUtil.PIXEL_HEIGHT;
		}
		for (Popup r : toRemove) popupList.remove(r);
		g.setFont(prevFont);
	}
	public static void renderPermanentPopups(GameContainer gc, Graphics g) {
		//If the time has run out, remove the popup
		Font prevFont = g.getFont();
		g.setFont(FontLoader.POPUP_FONT);
		float xPose = initialX, yPose = initialY;
		if (BattleModel.MAIN!=null){ // In battle

			BattleCreature cr = BattleModel.MAIN.creatures.get(EpicGameContainer.MAIN.myID);
			xPose = cr.getRenderX()-20;
			yPose = cr.getRenderY()-20-3*HEIGHT;
		
		}
		else if (!EpicGameContainer.MAIN.menuIsOpen){ 
			xPose = GameModel.MAIN.player.getContinuousXLoc()*OverworldConstants.TILE_WIDTH-20;
			yPose = GameModel.MAIN.player.getContinuousYLoc()*OverworldConstants.TILE_HEIGHT-20-3*HEIGHT;
		}
		else {
			xPose = 40+WIDTH; yPose = 40;
		}
		if (xPose-WIDTH<0) xPose = WIDTH;
		
		if (popupList==null) popupList = new ArrayList<Popup>();
		ArrayList<Popup> toRemove = new ArrayList<Popup>();
		//Draw the permanent popups
		for (Popup p: popupList) {
			if (!p.isPermanent) continue;
			if (GameModel.MAIN.tickNum-p.initTime>displayTimeout && !p.isPermanent) toRemove.add(p);
			else{
				p.fadeState = 255;
				g.setColor(new Color(0,0,0,p.fadeState));
				RenderUtil.renderPrettyBox(g, (int)(xPose-WIDTH), (int)yPose, WIDTH-RenderUtil.PIXEL_WIDTH*2, HEIGHT-RenderUtil.PIXEL_HEIGHT*2, p.fadeState, p.isPermanent);
				g.drawString(p.text, xPose-WIDTH+TEXT_OFFSET, yPose+(HEIGHT/2)-TEXT_OFFSET/3);
			}
			yPose+=HEIGHT+RenderUtil.PIXEL_HEIGHT;
		}
		for (Popup r : toRemove) popupList.remove(r);

		g.setFont(prevFont);
		//g.resetTransform();
	}
	public static void addPopup(String text){
		addPopup(text,false);
	}
	
	public static void addPopup(String text, boolean isPermanent){
		if (popupList==null) popupList = new ArrayList<Popup>();
		popupList.add(new Popup(text,(int)initialY,isPermanent));
		
		/*
		//Move all other popups up
		ArrayList<Popup> toRemove = new ArrayList<Popup>();
		for (Popup p : popupList) {
			p.y-=HEIGHT+RenderUtil.PIXEL_HEIGHT;
			p.fadeState++;
			if (p.y<0) toRemove.add(p);
		}
		
		for (Popup r : toRemove) popupList.remove(r);*/
	}
	public static void makeAllPopupsImpermanent(){

		for (Popup p : popupList) {
			p.isPermanent = false;
		}
	}
	public static void removePermanentPopups(){
		ArrayList<Popup> toRemove = new ArrayList<Popup>();
		for (Popup p: popupList) {
			if (p.isPermanent) toRemove.add(p);
		}

		for (Popup r : toRemove) popupList.remove(r);
	}
	
}
