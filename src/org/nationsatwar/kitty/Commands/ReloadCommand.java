package org.nationsatwar.kitty.Commands;

import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Sumo.SumoObject;

public final class ReloadCommand {

	protected final Kitty plugin;
	
	public ReloadCommand(Kitty plugin) {
		
		this.plugin = plugin;
	}

	/**
	 * Handles the 'Reload' command.
	 * <p>
	 * Will reload all the properties associated with the sumoName to the configurations as specified in 
	 * their respective config files.
	 * 
	 * @param player  Person sending the command
	 * @param entityName  The entity to spawn
	 */
	public final void execute(String sumoName) {
		
		sumoName = sumoName.toLowerCase();
		
		for (SumoObject sumo : plugin.sumoManager.getAllSumos()) {
			
			if (sumo.getSumoName().toLowerCase().equals(sumoName))
				sumo.loadConfigProperties();
		}
	}
}