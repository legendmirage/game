package debug;


import model.GameModel;


public class DebugTeleport extends DebugCommand{

	@Override
	public String run(String args) {
		
		String[] parsed = args.split(" ");
		int zoneID = Integer.parseInt(parsed[0]);
		int x, y;
		if(parsed.length > 1){
			x = Integer.parseInt(parsed[1]);
			y = Integer.parseInt(parsed[2]);
			GameModel.MAIN.enterZone(zoneID, x, y);
		}
		else{
			x = GameModel.MAIN.zones.get(zoneID).portals.get(0).x;
			y = GameModel.MAIN.zones.get(zoneID).portals.get(0).y;
			GameModel.MAIN.enterZone(zoneID, x, y + 1);
		}
		
		return null;
	
	}

}
