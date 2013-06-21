package org.nationsatwar.kitty.Commands;

import org.bukkit.entity.Player;
import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Sumo.SumoObject;

public final class BeckonCommand {

	protected final Kitty plugin;
	
	public BeckonCommand(Kitty plugin) {
		
		this.plugin = plugin;
	}

	/**
	 * Handles the 'Summon' command.
	 * <p>
	 * Will create a new trigger for the developer to manipulate. This will create a config file
	 * in the '(worldname)/triggers/' directory.
	 * 
	 * @param player  Person sending the command
	 * @param entityName  The entity to spawn
	 */
	public final void execute(Player player) {
		
		for (SumoObject sumo : plugin.sumoManager.getPlayerSumos(player.getName()))
			sumo.setPath(player.getLocation());
	}
}