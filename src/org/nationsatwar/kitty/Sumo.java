package org.nationsatwar.kitty;

import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.PathEntity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class Sumo {
	
	private final Entity entity;
	
	private Player master;
	
	private float speed = 0.3f;
	
	public Sumo(Entity entity, Player master) {
		
		this.entity = entity;
		this.master = master;
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