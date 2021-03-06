package org.nationsatwar.kitty.Sumo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Utility.AIUtility;
import org.nationsatwar.kitty.Utility.ConfigHandler;

public class BehaviorController extends BukkitRunnable {
	
	protected Kitty plugin;
	private final SumoObject sumo;

	private float engageRadius;
	private List<EntityType> engageTypes;
	
	private float maxFollowRange = 8;

	private int healthRegeneration;
	private int healthRegenerationDelay;
	
	private int timeSinceStruck = 0;
	private int timeSinceOrder = 0;
	private int timeSinceAttack = 0;
	
	private boolean followingOrder;
	
	private Entity attackOrderTarget;
	private Location moveOrderLocation;
	
	public BehaviorController(Kitty plugin, SumoObject sumo) {
		
		this.plugin = plugin;
		this.sumo = sumo;
		
		engageTypes = new ArrayList<EntityType>();
		
		loadConfigBehaviors();
	}
	
	/**
	 * Timer is ran every second
	 */
	public void run() {
		
		// Properties to adjust
		timeSinceStruck++;
		timeSinceOrder++;
		timeSinceAttack++;
		
		// Regenerate Health
		if (timeSinceStruck >= healthRegenerationDelay)
			sumo.replenishHealth(healthRegeneration);
		
		// Orders last 10 seconds (TEMPORARY)
		if (timeSinceOrder >= 40)
			followingOrder = false;
		
		// Cancel behavior if following a specified order
		if (followingOrder) {
			
			if (attackOrderTarget != null && !attackOrderTarget.isDead())
				sumo.targetEntity(attackOrderTarget);
			else if (moveOrderLocation != null)
				sumo.setPath(moveOrderLocation);
			else
				followingOrder = false;
			
			return;
		}
		
		// Behaviors
		if (!sumo.isEngaged)
			moveToPlayer();
		
		attackNearbyEnemies();
	}
	
	public void issueAttackOrder(Entity target) {
		
		followingOrder = true;
		timeSinceOrder = 0;
		attackOrderTarget = target;
	}
	
	public void issueMoveOrder(Location moveLocation) {
		
		followingOrder = true;
		timeSinceOrder = 0;
		moveOrderLocation = moveLocation;
	}
	
	/**
	 * Loads the Sumo's behaviors as specified by the Config file
	 */
	public void loadConfigBehaviors() {
		
		File sumoFile = new File(ConfigHandler.getFullSumoPath(sumo.getSumoName()));
		FileConfiguration sumoConfig = YamlConfiguration.loadConfiguration(sumoFile);
		
		engageTypes.clear();
		
		engageRadius = (float) sumoConfig.getDouble(ConfigHandler.engageRadius);
		
		for (String entityType : sumoConfig.getStringList(ConfigHandler.engageTypes))
			engageTypes.add(EntityType.fromName(entityType));
		
		healthRegeneration = (int) sumoConfig.getDouble(ConfigHandler.healthRegeneration);
		healthRegenerationDelay = (int) sumoConfig.getDouble(ConfigHandler.healthRegenerationDelay);
	}
	
	/**
	 * Resets timeSinceStruck to 0
	 */
	public void resetTimeSinceStruck() {
		
		timeSinceStruck = 0;
	}
	
	/**
	 * Moves directly to the player's location
	 */
	private void moveToPlayer() {
		
		Player master = sumo.getMaster();
		
		// Bats need to be constantly updated to work properly
		if (sumo.getEntity() instanceof Bat) {
			
			Location moveLocation = AIUtility.randomizeLocation(master.getLocation(), maxFollowRange);
			sumo.setPath(moveLocation);
			return;
		}
		
		double distanceToMaster = master.getLocation().distance(sumo.getEntity().getLocation());
		
		// Update location if the player is outside the follow range
		if (distanceToMaster > maxFollowRange) {
			
			Location moveLocation = AIUtility.randomizeLocation(master.getLocation(), maxFollowRange);
			sumo.setPath(moveLocation);
			return;
		}
		
		if (sumo.getDestinationLocation() == null)
			return;
		
		double distanceToDestination = sumo.getDestinationLocation().distance(sumo.getEntity().getLocation());
		
		// If Sumo is just hanging around, then set a random chance that they'll 'wander' somewhere else
		if (distanceToDestination < 3) {
			
			float randomNumber = AIUtility.getRandomNumber(0, 20);
			
			if (randomNumber < 1) {
			
				Location moveLocation = AIUtility.randomizeLocation(master.getLocation(), maxFollowRange);
				sumo.setPath(moveLocation);
				return;
			}
		}
	}
	
	/**
	 * Gets a list of all enemies inside the engage radius and attacks the closest enemy to the player
	 */
	private void attackNearbyEnemies() {

		Entity closestEntity = null;
		double distance = engageRadius;
		
		// Gets the closest entity to the Sumo's master
		for (Entity entity : sumo.getMaster().getNearbyEntities(engageRadius, engageRadius, engageRadius)) {
						
			if (!engageTypes.contains(entity.getType()))
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
		
		if (closestEntity == null)
			return;
		
		sumo.targetEntity(closestEntity);

		distance = sumo.getEntity().getLocation().distance(closestEntity.getLocation());
		
		if (distance < sumo.getAttackRange() && timeSinceAttack >= sumo.getAttackSpeed()) {
			
			((LivingEntity) closestEntity).damage(sumo.getAttackDamage(), sumo.getEntity());
			timeSinceAttack = 0;
		}
	}
}