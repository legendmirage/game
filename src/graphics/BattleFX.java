package graphics;

public interface BattleFX extends RenderComponent {
	
	/** whether the effect is complete */
	public abstract boolean complete();
	
	/** updates every game tick */
	public abstract void update();
}
