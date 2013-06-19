package org.nationsatwar.kitty;

import java.util.HashMap;

import org.nationsatwar.kitty.Sumo.Sumo;

public final class SumoManager {

	protected final Kitty plugin;
	
	// Key: Player Name | Value: Sumo
	private HashMap<String, Sumo> playerSumos;

	public SumoManager(Kitty plugin) {
		
		playerSumos = new HashMap<String, Sumo>();
		
		this.plugin = plugin;
	}
	
	public Sumo getPlayerSumo(String playerName) {
		
		return playerSumos.get(playerName);
	}
	
	public void addSumo(String playerName, Sumo sumo) {
		
		playerSumos.put(playerName, sumo);
	}
}