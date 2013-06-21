package org.nationsatwar.kitty.Utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

import org.nationsatwar.kitty.Kitty;

public final class ConfigHandler {

	public static final String sumoEntityType = "Entity Type";

	public static final String healthMax = "Health.max";
	public static final String healthRegeneration = "Health.regeneration";
	public static final String healthRegenerationDelay = "Health.regenerationDelay";
	
	public static final String statsMovementSpeed = "Stats.movementSpeed";
	public static final String statsAttackSpeed = "Stats.attackSpeed";
	public static final String statsAttackRange = "Stats.attackRange";
	public static final String statsAttackDamage = "Stats.attackDamage";
	
	public static final String engageRadius = "Engage.radius";
	public static final String engageTypes = "Engage.types";
	
	private static final String sumoPath = "plugins/Kitty/";
	private static final String sumoExtension = ".yml";
	
	private static final String lineBreak = "\r\n";

	/**
	 * Creates all the default Sumo Files
	 */
	public static void createDefaultSumoFiles() {
		
		// Create necessary folders
		File sumoDirectory = new File(sumoPath);
		
		if (!sumoDirectory.exists())
			sumoDirectory.mkdir();
		
		// Create default Sumo Files
		createSumoFile("Bat");
		createSumoFile("Blaze");
		createSumoFile("Cave_Spider");
		createSumoFile("Chicken");
		createSumoFile("Cow");
		createSumoFile("Creeper");
		createSumoFile("Ender_Dragon");
		createSumoFile("Enderman");
		createSumoFile("Ghast");
		createSumoFile("Giant");
		createSumoFile("Iron_Golem");
		createSumoFile("Mushroom_Cow");
		createSumoFile("Ocelot");
		createSumoFile("Pig");
		createSumoFile("Pig_Zombie");
		createSumoFile("Sheep");
		createSumoFile("Skeleton");
		createSumoFile("Slime");
		createSumoFile("Snowman");
		createSumoFile("Spider");
		createSumoFile("Squid");
		createSumoFile("Villager");
		createSumoFile("Witch");
		createSumoFile("Wither");
		createSumoFile("Wolf");
		createSumoFile("Zombie");
	}

	/**
	 * Gets the full sumo path
	 */
	public static String getFullSumoPath(String sumoName) {
		
		return sumoPath + sumoName + sumoExtension;
	}

	/**
	 * Creates a Sumo File
	 * 
	 * @param sumoName  The name of the Sumo file to create
	 */
	private static void createSumoFile(String sumoName) {
		
		String fullSumoPath = sumoPath + sumoName + sumoExtension;
	    File sumoFile = new File(fullSumoPath);
		
	    FileConfiguration sumoConfig = YamlConfiguration.loadConfiguration(sumoFile);
	    FileConfigurationOptions sumoConfigOptions = sumoConfig.options();

	    // Creates default config parameters on creation
	    sumoConfig.addDefault(sumoEntityType, sumoName);
	    
	    sumoConfig.addDefault(healthMax, 15);
	    sumoConfig.addDefault(healthRegeneration, 1);
	    sumoConfig.addDefault(healthRegenerationDelay, 5);
	    
	    sumoConfig.addDefault(statsMovementSpeed, 30);
	    sumoConfig.addDefault(statsAttackSpeed, 30);
	    sumoConfig.addDefault(statsAttackRange, 5);
	    sumoConfig.addDefault(statsAttackDamage, 5);
	    
	    List<String> engageTypesList = new ArrayList<String>();
	    engageTypesList.add("Blaze");
	    engageTypesList.add("Cave_Spider");
	    engageTypesList.add("Creeper");
	    engageTypesList.add("Ender_Dragon");
	    engageTypesList.add("Enderman");
	    engageTypesList.add("Ghast");
	    engageTypesList.add("Pig_Zombie");
	    engageTypesList.add("Skeleton");
	    engageTypesList.add("Spider");
	    engageTypesList.add("Wither");
	    engageTypesList.add("Zombie");
	    
	    sumoConfig.addDefault(engageRadius, 20);
	    sumoConfig.addDefault(engageTypes, engageTypesList);
	    
	    sumoConfigOptions.copyDefaults(true);
	    
	    // Add header to config file
	    String header = "Kitty Sumo Config File" + lineBreak;
	    sumoConfigOptions.header(header);
	    sumoConfigOptions.copyHeader(true);
	    
	    // Save the file
	    try { sumoConfig.save(sumoFile); }
	    catch (IOException e) { Kitty.log("Error saving config file: " + e.getMessage()); }
	}
}