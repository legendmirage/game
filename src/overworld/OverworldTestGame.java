package overworld;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class OverworldTestGame extends StateBasedGame{
	
	public OverworldTestGame() {
		super("Overworld Test");
		// TODO Auto-generated constructor stub
		this.addState(new Map());
		//this.addState(new BattleStateController(1));
	}

	public static void main(String[] arguments)
	{
		try
		{
			AppGameContainer app = new AppGameContainer(new OverworldTestGame());
			app.setDisplayMode(640, 640, false);
			app.start();
		}
		catch (SlickException e)
	    {
			e.printStackTrace();
	    }
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		this.getState(0).init(container,this);
		
	}
	    

}
