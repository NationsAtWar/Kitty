package org.nationsatwar.kitty.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Sumo.Sumo;

public final class DeathEvents implements Listener {
	
	protected final Kitty plugin;
    
    public DeathEvents(Kitty plugin) {
    	
        this.plugin = plugin;
    }
    
    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
    	
    	Sumo sumo = plugin.sumoManager.getPlayerSumo("Aculem");
    	
    	if (sumo.getEntity().equals(event.getEntity()))
    		sumo.behaviorController.cancel();
    }
}