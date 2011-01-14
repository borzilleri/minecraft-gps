package com.asylumsw.bukkit.gps;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.Player;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.ChatColor;

/**
 *
 * @author jonathan
 */
public class GPSPlayerListener extends PlayerListener {

	private final GPS plugin;

	public GPSPlayerListener(GPS instance) {
		plugin = instance;
	}

	@Override
	public void onPlayerCommand(PlayerChatEvent event) {
		String[] split = event.getMessage().split(" ");
		Player player = event.getPlayer();

		if (split[0].equalsIgnoreCase("/gps")) {
			player.sendMessage(this.getCoordinates(player.getLocation()));


			Location location = player.getLocation();
			player.sendMessage("You are currently at " + location.getX() + "," + location.getY() + "," + location.getZ()
							+ " with " + location.getYaw() + " yaw and " + location.getPitch() + " pitch");
			event.setCancelled(true);
		}
	}

	private String getCoordinates(Location loc) {
		String message = "";

		message = String.format(
			"%sLoc:[ %s%s, %s%s ] Facing:[ %s%s ] Alt:[ %s%s ]",
			ChatColor.GRAY, this.generateLatitude(loc.getX()), ChatColor.GRAY,
			this.generateLongitude(loc.getZ()), ChatColor.GRAY,
			this.generateOrientation(loc.getYaw()), ChatColor.GRAY,
			this.generateAltitude(loc.getY()), ChatColor.GRAY
		);

		return message;
	}

	private String getDegreesPadding(Double degrees) {
		String padding = "";
		if( degrees < 100 ) padding += "0";
		if( degrees < 10 ) padding += "0";
		return padding;
	}

		protected String generateOrientation(Float degrees) {
		degrees = (degrees+270)%360;
		String textDirection = "";

		// Determine North/South facing.
		if( degrees <= 78.75 || degrees > 281.25 ) {
			textDirection = "N";
		} else if( degrees > 101.25 && degrees <= 258.75 ) {
			textDirection = "S";
		}

		// Determine East/West facing.
		if (degrees > 11.25 && degrees <= 168.75 ) {
			textDirection += "E";
		} else if( degrees > 190.25 && degrees <= 348.75 ) {
			textDirection += "W";
		}

		// Determine Additional north/east/south/west facing for 3-letter precision.
		if( (degrees > 326.25 || degrees <= 33.75) && !textDirection.equalsIgnoreCase("N") ) {
			textDirection = "N"+textDirection;
		} else if( (degrees > 56.25 && degrees <= 123.75) && !textDirection.equalsIgnoreCase("E") ) {
			textDirection = "E"+textDirection;
		} else if( (degrees > 146.25 && degrees <= 213.75) && !textDirection.equalsIgnoreCase("S") ) {
			textDirection = "S"+textDirection;
		} else if( (degrees > 236.25 && degrees <= 303.75) && !textDirection.equalsIgnoreCase("W") ) {
			textDirection = "W"+textDirection;
		}


		String directionPadding = "";
		if( textDirection.length() < 2 ) directionPadding += '_';
		if( textDirection.length() < 3 ) directionPadding += '_';

		return ChatColor.GRAY + "(" + directionPadding
						+ ChatColor.LIGHT_PURPLE + textDirection + ChatColor.GRAY + ") "
						+ this.getDegreesPadding((double)degrees)
						+ ChatColor.LIGHT_PURPLE + String.format("%.1f", degrees);
	}

	private String generateAltitude(Double altitude) {
				return ChatColor.GRAY + this.getDegreesPadding(altitude) +
						ChatColor.DARK_PURPLE + String.format("%.0f", altitude) +
						ChatColor.GRAY + "m";

	}

	private String generateLatitude(Double degrees) {
		return ChatColor.GRAY + this.getDegreesPadding(Math.abs(degrees))
						+ ChatColor.GREEN + String.format("%.1f", Math.abs(degrees))
						+ (degrees<0?"N":degrees>0?"S":"");
	}

	private String generateLongitude(Double degrees) {
		return ChatColor.GRAY + this.getDegreesPadding(Math.abs(degrees))
						+ ChatColor.GREEN + String.format("%.1f", Math.abs(degrees))
						+ (degrees<0?"E":degrees>0?"W":"");
	}
	
}
