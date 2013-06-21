package org.nationsatwar.kitty.Sumo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nationsatwar.kitty.Kitty;

public class BehaviorController extends BukkitRunnable {
	
	protected Kitty plugin;
	private final SumoObject sumo;
	
	private List<EntityType> hostileTypes;
	
	private int maxEngageDistance = 24;
	
	public BehaviorController(Kitty plugin, SumoObject sumo) {
		
		this.plugin = plugin;
		this.sumo = sumo;
		
		hostileTypes = new ArrayList<EntityType>();

		hostileTypes.add(EntityType.BLAZE);
		hostileTypes.add(EntityType.CAVE_SPIDER);
		hostileTypes.add(EntityType.CREEPER);
		hostileTypes.add(EntityType.ENDER_DRAGON);
		hostileTypes.add(EntityType.ENDERMAN);
		hostileTypes.add(EntityType.GHAST);
		hostileTypes.add(EntityType.PIG_ZOMBIE);
		hostileTypes.add(EntityType.SKELETON);
		hostileTypes.add(EntityType.SPIDER);
		hostileTypes.add(EntityType.WITHER);
		hostileTypes.add(EntityType.ZOMBIE);
	}
	
	// Timer is ran every second here
	public void run() {
		
		if (!sumo.isEngaged)
			moveToPlayer();
		
		attackNearbyEnemies();
	}
	
	private void moveToPlayer() {
		
		Player master = sumo.getMaster();
		sumo.setPath(master.getLocation());
	}
	
	private void attackNearbyEnemies() {

		Entity closestEntity = null;
		double distance = maxEngageDistance;
		
		// Gets the closest entity to the Sumo's master
		for (Entity entity : sumo.getMaster().getNearbyEntities(maxEngageDistance, maxEngageDistance, maxEngageDistance)) {
						
			if (!hostileTypes.contains(entity.getType()))
				continue;
			
			if (entity.equals((Entity) sumo.getMaster()) || entity.equals(sumo.getEntity()))
				continue;
			
			if (!((LivingEntity) entity).hasLineOfSight(sumo.getMaster()))
				continue;
			
			double checkDistance = entity.getLocation().distance(sumo.getMaster().getLocation());
			
			if (checkDistance < distance) {
				
				closestEntity = entity;
				distance = checkDistance;
			}
		}
		
		sumo.targetEntity(closestEntity);
		
		if (distance < sumo.getAttackRange())
			((LivingEntity) closestEntity).damage(sumo.getAttackDamage(), sumo.getEntity());
	}
}