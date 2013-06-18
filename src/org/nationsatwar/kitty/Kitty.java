package org.nationsatwar.kitty;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The iSpy parent class.
 * <p>
 * Custom scripting plugin for Minecraft
 * 
 * @author Aculem
 */
public final class Kitty extends JavaPlugin {
	
	public final CommandParser commandParser = new CommandParser(this);;
	
	private static final Logger log = Logger.getLogger("Minecraft");

	/**
	 * Initializes the plugin on server startup.
	 */
	public void onEnable() {
		
		// Set Command Executor
    	getCommand("kitty").setExecutor(commandParser);
    	
    	log("Kitty has been enabled.");
	}

	/**
	 * Handles the plugin on server stop.
	 */
	public void onDisable() {
		
		log("Kitty has been disabled.");
	}

	/**
	 * Plugin logger handler. Useful for debugging.
	 * 
	 * @param logMessage  Message to send to the console.
	 */
	public static void log(String logMessage) {
		
		log.info("Kitty: " + logMessage);
	}
}