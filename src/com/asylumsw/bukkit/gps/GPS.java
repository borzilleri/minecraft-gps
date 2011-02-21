package com.asylumsw.bukkit.gps;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author jonathan
 */
public class GPS extends JavaPlugin {
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	@Override
	public void onDisable() {
		System.out.println("GPS Disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if( !cmd.getName().equalsIgnoreCase("gps") ) return false;
		if( !(sender instanceof Player) ) return false;
		Player player = (Player)sender;
		player.sendMessage(this.getCoordinates(player.getLocation()));
		return true;
	}

	public static void main(String[] args) {}

		private String getCoordinates(Location loc) {
		String message = "";

		message = String.format(
						"%sLoc:[ %s%s, %s%s ] Facing:[ %s%s ] Alt:[ %s%s ]",
						ChatColor.GRAY, this.generateLatitude(loc.getBlockX()), ChatColor.GRAY,
						this.generateLongitude(loc.getBlockZ()), ChatColor.GRAY,
						this.generateOrientation(loc.getYaw()), ChatColor.GRAY,
						this.generateAltitude(loc.getBlockY()), ChatColor.GRAY);

		return message;
	}

	private String getDegreesPadding(Double degrees) {
		String padding = "";
		if (degrees < 100) {
			padding += "0";
		}
		if (degrees < 10) {
			padding += "0";
		}
		return padding;
	}

	protected String generateOrientation(Float degrees) {
		/*
		 * if degrees is negative, it means we were rotating anti-clockwise,
		 * so convert it into the proper positive value.
		 */
		if( 0 > degrees ) {
			degrees = 360 - Math.abs(degrees) % 360;
		}

		/*
		 * 0 degrees represents West, so adjust the value so 0 degrees is north.
		 */
		degrees = (degrees + 270) % 360;

		String textDirection = "";

		// Determine North/South facing.
		if (degrees <= 78.75 || degrees > 281.25) {
			textDirection = "N";
		}
		else if (degrees > 101.25 && degrees <= 258.75) {
			textDirection = "S";
		}

		// Determine East/West facing.
		if (degrees > 11.25 && degrees <= 168.75) {
			textDirection += "E";
		}
		else if (degrees > 190.25 && degrees <= 348.75) {
			textDirection += "W";
		}

		// Determine Additional north/east/south/west facing for 3-letter precision.
		if ((degrees > 326.25 || degrees <= 33.75) && !textDirection.equalsIgnoreCase("N")) {
			textDirection = "N" + textDirection;
		}
		else if ((degrees > 56.25 && degrees <= 123.75) && !textDirection.equalsIgnoreCase("E")) {
			textDirection = "E" + textDirection;
		}
		else if ((degrees > 146.25 && degrees <= 213.75) && !textDirection.equalsIgnoreCase("S")) {
			textDirection = "S" + textDirection;
		}
		else if ((degrees > 236.25 && degrees <= 303.75) && !textDirection.equalsIgnoreCase("W")) {
			textDirection = "W" + textDirection;
		}


		String directionPadding = "";
		if (textDirection.length() < 2) {
			directionPadding += '_';
		}
		if (textDirection.length() < 3) {
			directionPadding += '_';
		}

		return ChatColor.GRAY + "(" + directionPadding
						+ ChatColor.LIGHT_PURPLE + textDirection + ChatColor.GRAY + ") "
						+ this.getDegreesPadding((double) degrees)
						+ ChatColor.LIGHT_PURPLE + String.format("%.1f", degrees);
	}

	private String generateAltitude(int altitude) {
		return ChatColor.GRAY + this.getDegreesPadding((double)altitude)
						+ ChatColor.BLUE + altitude
						+ ChatColor.GRAY + "m";

	}

	private String generateLatitude(int degrees) {
		return ChatColor.GRAY + this.getDegreesPadding((double)Math.abs(degrees))
						+ ChatColor.GREEN + Math.abs(degrees)
						+ (degrees < 0 ? "N" : degrees > 0 ? "S" : "");
	}

	private String generateLongitude(int degrees) {
		return ChatColor.GRAY + this.getDegreesPadding((double)Math.abs(degrees))
						+ ChatColor.GREEN + Math.abs(degrees)
						+ (degrees < 0 ? "E" : degrees > 0 ? "W" : "");
	}
}
