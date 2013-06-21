package org.nationsatwar.kitty.Events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Sumo.SumoObject;

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
    	SumoObject sumo = plugin.sumoManager.getEntitySumo(event.getEntity());
    	
    	// Cancel event if targeting entity is not a sumo
    	if (sumo == null)
    		return;
    	
    	// Cancel the target if the player is the Sumo's Master
    	if (sumo.getMaster().equals(player))
    		event.setTarget(null);
    }
}