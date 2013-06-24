package org.nationsatwar.kitty.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Sumo.SumoObject;

public final class TargetEvents implements Listener {
	
	protected final Kitty plugin;
    
    public TargetEvents(Kitty plugin) {
    	
        this.plugin = plugin;
    }
    
    @EventHandler
    private void onEntityTarget(EntityTargetEvent event) {
    	
    	for (SumoObject sumo : plugin.sumoManager.getAllSumos())
    		if (sumo.getEntity().equals(event.getEntity())) {
    			
        		event.setCancelled(true);
    			return;
    		}
    }
}