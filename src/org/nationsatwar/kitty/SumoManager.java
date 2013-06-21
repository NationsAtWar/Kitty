package org.nationsatwar.kitty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Entity;
import org.nationsatwar.kitty.Sumo.SumoObject;

public final class SumoManager {

	protected final Kitty plugin;
	
	// Key: Player Name | Value: Sumo
	private HashMap<String, List<SumoObject>> playerSumos;

	public SumoManager(Kitty plugin) {
		
		playerSumos = new HashMap<String, List<SumoObject>>();
		
		this.plugin = plugin;
	}
	
	public SumoObject getEntitySumo(Entity entity) {
		
		for (List<SumoObject> playerSumoList : playerSumos.values())
			for (SumoObject sumo : playerSumoList)
				if (sumo.getEntity().equals(entity))
					return sumo;
		
		return null;
	}
	
	public List<SumoObject> getPlayerSumos(String playerName) {
		
		return playerSumos.get(playerName);
	}
	
	public List<SumoObject> getPlayerSumos(String playerName, String sumoName) {
		
		List<SumoObject> playerSumoList = new ArrayList<SumoObject>();
		
		for (SumoObject sumo : playerSumos.get(playerName))
			if (sumo.getSumoName().toLowerCase().equals(sumoName))
				playerSumoList.add(sumo);
		
		return playerSumoList;
	}
	
	public List<SumoObject> getAllSumos() {
		
		List<SumoObject> sumoList = new ArrayList<SumoObject>();
		
		for (List<SumoObject> playerSumoList : playerSumos.values())
			for (SumoObject sumo : playerSumoList)
				sumoList.add(sumo);
		
		return sumoList;
	}
	
	public void addSumo(String playerName, SumoObject sumo) {
		
		if (!playerSumos.containsKey(playerName)) {
			
			List<SumoObject> newList = new ArrayList<SumoObject>();
			newList.add(sumo);
			playerSumos.put(playerName, newList);
			
		} else
			playerSumos.get(playerName).add(sumo);
	}
}