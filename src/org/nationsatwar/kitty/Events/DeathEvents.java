package org.nationsatwar.kitty.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Sumo.SumoObject;

public final class DeathEvents implements Listener {
	
	protected final Kitty plugin;
    
    public DeathEvents(Kitty plugin) {
    	
        this.plugin = plugin;
    }
    
    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
    	
    	// Cancels behavior controller if the killed entity is a Sumo
    	for (SumoObject sumo : plugin.sumoManager.getAllSumos())
    		if (sumo.getEntity().equals(event.getEntity())) {
    			
    			sumo.behaviorController.cancel();
    			return;
    		}
    }
}