package org.nationsatwar.kitty.Sumo;

import java.io.File;

import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityFlying;
import net.minecraft.server.v1_5_R3.EntityGhast;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.PathEntity;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Utility.ConfigHandler;

public final class SumoObject {
	
	private static int maximumMoveRange = 12;
	
	protected Kitty plugin;
	private final Entity entity;
	private Player master;
	
	private String sumoName;
	
	public BehaviorController behaviorController;
	
	public boolean isEngaged = false;
	
	private float movementSpeed;
	private float attackRange;
	
	private int attackDamage;
	
	public SumoObject(Kitty plugin, Entity entity, Player master, String sumoName) {
		
		this.plugin = plugin;
		this.entity = entity;
		this.master = master;
		
		this.sumoName = sumoName;
    	
		behaviorController = new BehaviorController(plugin, this);
		behaviorController.runTaskTimer(plugin, 0, 20);
		
		EntityLiving livingEntity = ((CraftLivingEntity) entity).getHandle();
		
		livingEntity.setHealth(15);
		
		loadConfigProperties();
	}
	
	// Getters
	public Entity getEntity() {
		
		return entity;
	}
	
	public Player getMaster() {
		
		return master;
	}
	
	public float getMovementSpeed() {
		
		return movementSpeed;
	}
	
	public float getAttackRange() {
		
		return attackRange;
	}
	
	public int getAttackDamage() {
		
		return attackDamage;
	}
	
	public String getSumoName() {
		
		return sumoName;
	}
	
	// Setters
	public void setMaster(Player master) {
		
		this.master = master;
	}

	public void setSpeed(float movementSpeed) {
		
		this.movementSpeed = movementSpeed;
	}
	
	/**
	 * Loads the Sumo's properties as specified by the Config file
	 */
	public void loadConfigProperties() {
		
		File sumoFile = new File(ConfigHandler.getFullSumoPath(sumoName));
	    FileConfiguration sumoConfig = YamlConfiguration.loadConfiguration(sumoFile);
	    
	    movementSpeed = (float) ((float) sumoConfig.getInt(ConfigHandler.statsMovementSpeed) / 100);
	    attackRange = (float) sumoConfig.getInt(ConfigHandler.statsAttackRange);
	    attackDamage = sumoConfig.getInt(ConfigHandler.statsAttackDamage);
	    
	    Kitty.log(movementSpeed + "");
	}
	
	/**
	 * Sets a new path for the Sumo to head towards
	 * 
	 * @param location The new location to head towards
	 */
	public void setPath(Location location) {
		
		// Gets the nearest location that the pathing will allow
		if (location.distance(entity.getLocation()) > maximumMoveRange) {

			Vector vector = location.toVector().subtract(entity.getLocation().toVector());
			vector.normalize().multiply(maximumMoveRange);
			
			location = entity.getLocation().add(vector);
		}
		
		EntityLiving livingEntity = ((CraftLivingEntity) entity).getHandle();
		
        PathEntity path = livingEntity.getNavigation().a(location.getX(), location.getY(), location.getZ());
        livingEntity.getNavigation().a(path, movementSpeed); // Sets new path
        livingEntity.aO = movementSpeed; // Overrides speed
        
        // Creatures use a separate pathing method
        if (livingEntity instanceof EntityCreature) {
        	
        	EntityCreature creature = ((CraftCreature) entity).getHandle();
        	creature.setPathEntity(path);
        }
        
        // Ghasts don't use paths, they just move towards a specific location
        if (livingEntity instanceof EntityGhast) {
        	
        	EntityGhast ghast = (EntityGhast) livingEntity;
        	
        	ghast.c = location.getX();
        	ghast.d = location.getY() + 5;
        	ghast.e = location.getZ();
        }
	}
	
	/**
	 * Sets the Sumo to target the specified entity
	 * 
	 * @param location The new location to head towards
	 */
	public void targetEntity(Entity targetEntity) {
		
		// If no appropriate entity is found, cancel targets
		if (targetEntity == null) {
			
			isEngaged = false;
			
			EntityLiving sumoEntity = ((CraftLivingEntity) entity).getHandle();
			
			sumoEntity.setGoalTarget(null);
			
			if (sumoEntity instanceof EntityCreature) {
				
				((EntityCreature) sumoEntity).target = null;
				((EntityCreature) sumoEntity).setTarget(null);
			}
			
			return;
		}
		
		isEngaged = true;
		
		EntityLiving mcEntity = ((CraftLivingEntity) targetEntity).getHandle();
		EntityLiving craftSumo = ((CraftLivingEntity) entity).getHandle();

		craftSumo.a(mcEntity); // Targets living entity?
		
		if (craftSumo instanceof EntityFlying) {
			
			Location offsetLocation = targetEntity.getLocation();
			offsetLocation.setY(offsetLocation.getY() + 10);
			
			setPath(targetEntity.getLocation());
			
			Vector vector = targetEntity.getLocation().toVector().subtract(entity.getLocation().toVector());
			
			Projectile projectile = ((LivingEntity) entity).launchProjectile(Fireball.class);
			projectile.setVelocity(vector);
			
		} else {
			
			craftSumo.setGoalTarget(mcEntity);
			
			setPath(targetEntity.getLocation());
		}
	}
}