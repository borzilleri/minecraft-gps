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
	@Override
	public boolean onPlayerCommand(Player player, String[] command, boolean isAdmin) {
		String commandString = command[0].substring(1).toLowerCase();
		if( commandString.equalsIgnoreCase("loc") ) {
			Location loc = player.getLocation();
			Double latitude = loc.getX();
			Double longitude = loc.getZ();
			Double altitude = loc.getY();
			Float orientation = player.getRotation();

			player.sendChat(String.format(
				"Position: %.1f,%.1f; Altitude: %.0f; Orientation: %.1f",
				latitude, longitude, altitude, orientation)
			);
		}
		return false;
	}
}
