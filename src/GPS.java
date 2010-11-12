/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonathan
 */
public class GPS extends Mod {
	/**
	 *
	 * NOTE: The player's coordinate map like this:
	 * X-value: Latitude (north-south)
	 * Y-value: Altitude
	 * Z-value: Longitude (east-west)
	 *
	 * @param player
	 * @param command
	 * @param isAdmin
	 * @return boolean
	 */
	protected boolean parseCommand(Player player, String[] tokens, boolean isAdmin) {
		String command = tokens[0].substring(1);
		
		if( command.equalsIgnoreCase("help") || command.equalsIgnoreCase("commands") ) {
			player.sendChat("GPS Commands: !gps");
			return true;
		}
		else if(command.equalsIgnoreCase("gps")) {
			Player target;
			if( tokens.length > 1 ) {
				target = Server.getPlayer(tokens[1]);
			} else {
				target = player;
			}

			this.getCoordinates(player, target);
			return true;
		}
		return false;
	}

	@Override
	public boolean onPlayerCommand(Player player, String[] command, boolean isAdmin) {
		return this.parseCommand(player, command, isAdmin);
	}

	@Override
	public boolean onPlayerChat(Player player, String command, boolean isAdmin) {
		String[] tokens = command.split(" ");
		return this.parseCommand(player, tokens, isAdmin);
	}
	
	protected void getCoordinates(Player player, Player target) {
		Location loc = target.getLocation();
		Double latitude = loc.getX();
		Double longitude = loc.getZ();
		Double altitude = loc.getY();
		Float orientation = target.getRotation();

		String playerLabel = "";
		if( player.getId() != target.getId() ) {
			playerLabel = String.format("%s: ", target.getName());
		}

		player.sendChat(String.format(
			"%sPosition: %.1f,%.1f; Altitude: %.0f; Orientation: %.1f",
			playerLabel, latitude, longitude, altitude, orientation)
		);
	}
}
