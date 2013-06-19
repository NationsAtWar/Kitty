package org.nationsatwar.kitty.Events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Sumo.Sumo;

public final class TargetEvents implements Listener {
	
	protected final Kitty plugin;
    
    public TargetEvents(Kitty plugin) {
    	
        this.plugin = plugin;
    }
    
    @EventHandler
    private void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
    	
    	// Cancel event if target is not a player
    	if (!event.getTarget().getType().equals(EntityType.PLAYER))
    		return;
    	
    	Player player = (Player) event.getTarget();
    	Sumo sumo = plugin.sumoManager.getPlayerSumo(player.getName());
    	
    	// If player is the target and blah blah blah
    	if (sumo != null && sumo.getMaster().equals(player)) {
    		
    		if (sumo.getEntity().equals(event.getEntity()))
        		event.setTarget(null);
    	}
    }
}