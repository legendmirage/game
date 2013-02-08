package overworld;

/** Portals are just entrances from one zone into another zone. <br>
 * They do not necessarily look like magical portals, the name is just a functional label. <br>
 * Portals are not for battles, those are just fields in the BattleModel itself. <br>
 * Portals are not bidirectional, the are one way. <br> 
 * If you want a two way portal, you need two portal objects. <br>
 * 
 */
public class Portal {
	/** Hidden counter to make sure each instance of this class has a unique id. */
	private static int objectCount = 0;
	
	/** Unique identifier. */
	public int id;
	/** x coordinate of the portal in fromZone. */
	public final int x;
	/** y coordinate of the portal in fromZone. */
	public final int y;
	/** width of the portal */
	public final int width;
	/** height of the portal */
	public final int height;
	/** ID of the zone the portal takes the player to. */
	public final int toZoneID;
	/** ID of the portal in the toZone this portal takes you to (map id). */
	public final int toPortalID;
	/** Can players see this portal? We could potentially have invisible portals (imagine a hidden passage). */
	public boolean visible;
	/** Name of Cutscene associated with portal*/
	public String cutscene;
	/** Name of quest associated with portal*/
	public String quest;
	/** Name of cutscene that allows portal to be active*/
	public String cutsceneActive;
	
	
	public Portal(int toZoneID, int toPortalID, int x, int y, int width, int height, boolean visible) {
		this.id = objectCount++;
		
		this.toZoneID = toZoneID;
		this.toPortalID = toPortalID;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visible = visible;
		this.cutscene = null;
		this.quest = null;
		this.cutsceneActive = null;
	}
}
