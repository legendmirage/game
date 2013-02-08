package overworld;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Map extends BasicGameState{

	private TiledMap map;
	private int x,y;
	private int width, height;
	private int minX = 0, maxX = 9, minY = 0, maxY = 9;
	private Animation sprite;
	@SuppressWarnings("unused")
	private final double delta = .001;
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		map = new TiledMap("res/tilemap/testMapWithObjects.tmx");
		x = 0;
		y = 0;
		width = map.getTileWidth();
		height = map.getTileHeight();
		System.out.println(width);
		Image [] movement = {new Image("res/sprite/avt3_fr1.gif"),new Image("res/sprite/avt3_fr2.gif")};
		int [] duration = {300,300};
		sprite = new Animation(movement,duration,false); 
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		map.render(0,0,(int)x-10,(int)y-10,20,20, false);
		sprite.draw(320,320);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_UP)){
			sprite.update(1);	
			move(0,-1);
		}
		if (input.isKeyDown(Input.KEY_DOWN)){
			sprite.update(1);
			move(0,1);
		}
		if (input.isKeyDown(Input.KEY_LEFT)){
			sprite.update(1);
			move(-1,0);
		}
		if (input.isKeyDown(Input.KEY_RIGHT)){
			sprite.update(1);
			move(1,0);
		}
		if (input.isKeyDown(Input.KEY_SPACE)){
			game.enterState(1);
		}

		//System.out.println(map.getTileProperty(id, "WallType", "-1"));
		// TODO Auto-generated method stub
		
	}
	
	private int move(int dx, int dy){
		wait(50);
		if ((x+dx>maxX) || (y+dy>maxY)|| (x+dx<minX) || (y+dy<minY)) return -1;

		int layerID = map.getLayerIndex("Collision");
		int id = map.getTileId(x+dx, y+dy, layerID);
		System.out.println(id);
		if (id!=0) return -1;
		
		x+=dx;
		y+=dy;
		return 0;
		
	}
	
	public static void wait (int n){
	        long t0,t1;
	        t0=System.currentTimeMillis();
	        do{
	        		t1=System.currentTimeMillis();
	        }
	        while (t1-t0<n);
	}



}
