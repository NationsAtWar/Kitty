package org.nationsatwar.kitty.Sumo;

import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.PathEntity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.nationsatwar.kitty.Kitty;

public final class Sumo {
	
	protected Kitty plugin;
	private final Entity entity;
	private Player master;
	
	public BehaviorController behaviorController;
	
	private float speed = 0.3f;
	
	public Sumo(Kitty plugin, Entity entity, Player master) {
		
		this.plugin = plugin;
		this.entity = entity;
		this.master = master;
    	
		behaviorController = new BehaviorController(plugin, this);
		behaviorController.runTaskTimer(plugin, 0, 20);
	}
	
	// Getters
	public Entity getEntity() {
		
		return entity;
	}
	
	public Player getMaster() {
		
		return master;
	}
	
	public float getSpeed() {
		
		return speed;
	}
	
	// Setters
	public void setMaster(Player master) {
		
		this.master = master;
	}

	public void setSpeed(float speed) {
		
		this.speed = speed;
	}
	
	/**
	 * Sets a new path for the Sumo to head towards
	 * 
	 * @param location The new location to head towards
	 */
	public void setPath(Location location) {
		
		EntityLiving livingEntity = ((CraftLivingEntity) entity).getHandle();
		
        PathEntity path = livingEntity.getNavigation().a(location.getX(), location.getY(), location.getZ());
        livingEntity.getNavigation().a(path, speed);
	}
}