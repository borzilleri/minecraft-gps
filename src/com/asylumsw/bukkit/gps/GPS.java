package com.asylumsw.bukkit.gps;

import java.io.File;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;

/**
 *
 * @author jonathan
 */
public class GPS extends JavaPlugin {

	private final GPSPlayerListener playerListener = new GPSPlayerListener(this);

	public GPS(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File plugin, ClassLoader cLoader) {
		super(pluginLoader, instance, desc, plugin, cLoader);
	}

	public void onEnable() {
		// TODO: Place any custom enable code here including the registration of any events

		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);

		// EXAMPLE: Custom code, here we just output some info so we can check all is well
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	public void onDisable() {
		// EXAMPLE: Custom code, here we just output some info so we can check all is well
		System.out.println("GPS Disabled.");
	}
}
