package org.nationsatwar.kitty.Sumo;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nationsatwar.kitty.Kitty;

public class BehaviorController extends BukkitRunnable {
	
	protected Kitty plugin;
	private final Sumo sumo;
	
	private boolean engaged = false;
	
	public BehaviorController(Kitty plugin, Sumo sumo) {
		
		this.plugin = plugin;
		this.sumo = sumo;
	}
	
	// Timer is ran every second here
	public void run() {
		
		Kitty.log("What what: " + engaged);
		
		if (!engaged)
			moveToPlayer();
		
		attackNearbyEnemies();
	}
	
	private void moveToPlayer() {
		
		Player master = sumo.getMaster();
		sumo.setPath(master.getLocation());
	}
	
	private void attackNearbyEnemies() {

		Entity closestEntity = null;
		double distance = 100;
		
		for (Entity entity : sumo.getEntity().getNearbyEntities(10, 10, 10)) {
			
			if (entity.equals((Entity) sumo.getMaster()))
				continue;
			
			double checkDistance = entity.getLocation().distance(sumo.getEntity().getLocation());
			
			if (checkDistance < distance) {
				
				closestEntity = entity;
				distance = checkDistance;
			}
		}
		
		if (closestEntity == null) {
			
			engaged = false;
			return;
		}
		
		if (closestEntity instanceof LivingEntity) {
		
			engaged = true;
			
			sumo.setPath(closestEntity.getLocation());
			
			if (distance < 2)
				((LivingEntity) closestEntity).damage(4, sumo.getEntity());
		}
	}
}