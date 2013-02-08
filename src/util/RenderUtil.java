package util;

import java.awt.image.RescaleOp;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import content.MenuLoader;

public class RenderUtil {
	public static final int CURSOR_WIDTH = MenuLoader.CURSOR_MAIN.getWidth();
	public static final int CURSOR_HEIGHT = MenuLoader.CURSOR_MAIN.getHeight();
	public static final int CURSOR_LEFT_OFFSET = 2;
	public static final int SCROLL_INDICATOR = 20;
	private static final Color CURSOR_COLOR = new Color(255, 255, 0), SCROLL_COLOR = new Color(255,100,0);
	private static int flashDelay = 35, lastTick = 0;
	private static boolean flashSwitch = true;
	public static int PIXEL_WIDTH = MenuLoader.TAB_DOWN_CENTER.getWidth();
	public static int PIXEL_HEIGHT = MenuLoader.TAB_DOWN_CENTER.getHeight();
	public static Color TAB_COLOR = new Color(156, 177, 231, 255);
	
	public static void renderCursor(GameContainer gc, Graphics g, int x0, int y0){
		renderCursor(gc,g,x0,y0,false);
	}
	public static void renderCursor(GameContainer gc, Graphics g, int x0, int y0, boolean flash){
		
		if (GameModel.MAIN==null||!flash || flashSwitch){
			g.drawImage(MenuLoader.CURSOR_MAIN, x0, y0-CURSOR_HEIGHT/2);
		}
		else if (flash&&!flashSwitch){
			g.drawImage(MenuLoader.CURSOR_FLASH, x0, y0-CURSOR_HEIGHT/2);
		}
		if (GameModel.MAIN==null) return;
		if (GameModel.MAIN.tickNum-lastTick > flashDelay) {
			flashSwitch=!flashSwitch;
			lastTick = GameModel.MAIN.tickNum;
		}
	}
	
	public static void renderScrollDownIndicator(GameContainer gc, Graphics g, int x, int y, boolean pointsUp){
		Polygon cursorTriangle = new Polygon();
		if (!pointsUp){
			cursorTriangle.addPoint(x, y);
			cursorTriangle.addPoint(x+SCROLL_INDICATOR, y);
			cursorTriangle.addPoint(x+SCROLL_INDICATOR/2, y+SCROLL_INDICATOR/2);
		}
		else {
			cursorTriangle.addPoint(x, y+SCROLL_INDICATOR);
			cursorTriangle.addPoint(x+SCROLL_INDICATOR, y+SCROLL_INDICATOR);
			cursorTriangle.addPoint(x+SCROLL_INDICATOR/2, y+SCROLL_INDICATOR/2);
			
		}
		Color lastColor = g.getColor();
		g.setColor(SCROLL_COLOR);
		g.fill(cursorTriangle);
		g.setColor(lastColor);
		
	}
	
	/** Draw a box with the art assets. x0 and y0 indicate the top left outer corner */
	public static void renderPrettyBox(Graphics g, int x0, int y0, int innerWidth, int innerHeight){
		renderPrettyBox(g, x0, y0, innerWidth, innerHeight, 255);
	}
	public static void renderPrettyBox(Graphics g, int x0, int y0, int innerWidth, int innerHeight, int alpha){
		renderPrettyBox(g, x0, y0, innerWidth, innerHeight, alpha, false);
	}
	public static void renderPrettyBox(Graphics g, int x0, int y0, int innerWidth, int innerHeight, int alpha, boolean isPermanent){
		innerWidth = (int)(innerWidth/PIXEL_WIDTH)*PIXEL_WIDTH;
		innerHeight = (int)(innerHeight/PIXEL_HEIGHT+1)*PIXEL_HEIGHT;
		Color alphaOverlay;
		if (!isPermanent) alphaOverlay= new Color(156, 177, 231, alpha);
		else alphaOverlay = new Color(200, 170, 231, alpha);
		
		//draw top and bottom
		
		for (int x = x0+PIXEL_WIDTH; x<= x0+innerWidth; x+=PIXEL_WIDTH){
			MenuLoader.TAB_UP_CENTER.draw(x, y0, alphaOverlay);
			MenuLoader.TAB_DOWN_CENTER.draw(x,y0+innerHeight+PIXEL_HEIGHT, alphaOverlay);
			//g.drawImage(MenuLoader.TAB_UP_CENTER,x,y0);	
			//g.drawImage(MenuLoader.TAB_DOWN_CENTER,x,y0+innerHeight+PIXEL_HEIGHT);	
		}
		//draw sides
		for (int y = y0+PIXEL_HEIGHT; y<= y0+innerHeight; y+=PIXEL_HEIGHT){
			MenuLoader.TAB_LEFT_CENTER.draw(x0,y,alphaOverlay);
			MenuLoader.TAB_RIGHT_CENTER.draw(x0+innerWidth+PIXEL_WIDTH,y, alphaOverlay);
			//g.drawImage(MenuLoader.TAB_LEFT_CENTER,x0,y);	
			//g.drawImage(MenuLoader.TAB_RIGHT_CENTER,x0+innerWidth+PIXEL_WIDTH,y);	
		}
		//draw corners
		//top left
		MenuLoader.TAB_UP_LEFT.draw(x0,y0, alphaOverlay);
		//g.drawImage(MenuLoader.TAB_UP_LEFT, x0,y0);
		//top right
		MenuLoader.TAB_UP_RIGHT.draw(x0+PIXEL_WIDTH+innerWidth,y0, alphaOverlay);
		//g.drawImage(MenuLoader.TAB_UP_RIGHT, x0+PIXEL_WIDTH+innerWidth,y0);
		//down left
		MenuLoader.TAB_DOWN_LEFT.draw(x0, y0+PIXEL_HEIGHT+innerHeight,alphaOverlay);
		//g.drawImage(MenuLoader.TAB_DOWN_LEFT, x0,y0+PIXEL_HEIGHT+innerHeight);
		//down right
		MenuLoader.TAB_DOWN_RIGHT.draw(x0+PIXEL_WIDTH+innerWidth,y0+PIXEL_HEIGHT+innerHeight, alphaOverlay);
		//g.drawImage(MenuLoader.TAB_DOWN_RIGHT, x0+PIXEL_WIDTH+innerWidth,y0+PIXEL_HEIGHT+innerHeight);
		
		//Render tab interior
		Color prevColor = g.getColor();
		g.setColor(alphaOverlay); //new Color(TAB_COLOR.r,TAB_COLOR.g, TAB_COLOR.b, alpha));
		g.fillRect(x0+PIXEL_WIDTH, y0+PIXEL_HEIGHT, innerWidth, innerHeight);
		g.setColor(prevColor);
		
	}

}
