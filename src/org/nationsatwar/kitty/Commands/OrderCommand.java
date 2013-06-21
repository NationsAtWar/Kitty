package org.nationsatwar.kitty.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Sumo.SumoObject;

public final class OrderCommand {

	protected final Kitty plugin;
	
	public OrderCommand(Kitty plugin) {
		
		this.plugin = plugin;
	}

	/**
	 * Handles the 'Order' command.
	 * <p>
	 * Depends on what the player is looking at. If an entity, then the order will be to attack.
	 * If the location is just a block, then the entity will go there and wait for further instructions.
	 * If the location is sky, the entity will head in that general direction to 10 blocks
	 * 
	 * @param player  Person sending the command
	 * @param sumoName  The name of the Sumo to order around
	 */
	public final void execute(Player player, String sumoName) {
		
		sumoName = sumoName.toLowerCase();
    	
    	Entity target = getTarget(player, 100);
    	
    	Block block = player.getTargetBlock(null, 20);
		
		for (SumoObject sumo : plugin.sumoManager.getPlayerSumos(player.getName(), sumoName)) {
			
			Kitty.log(sumo.getSumoName());
			
			if (target != null) {
				
				sumo.behavior.issueAttackOrder(target);
				continue;
			}
			
			if (block != null) {
				
				sumo.behavior.issueMoveOrder(block.getLocation());
				continue;
			}
		}
	}
	
	/**
	 * Gets the closest entity that the player is looking at
	 * <p>
	 * Thanks DirtyStarfish!
	 * 
	 * @param player The player to check line of sight
	 * @param range How far to check line of sight
	 * 
	 * @return The entity that's in line of sight
	 */
	private final Entity getTarget(Player player, int range) {
		
		List<Entity> nearbyE = player.getNearbyEntities(range,range, range);
		ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();
		
		for (Entity e : nearbyE) {
			if (e instanceof LivingEntity)
				livingE.add((LivingEntity) e);
		}
		
		Entity target = null;
		BlockIterator bItr = new BlockIterator(player, range);
		Block block;
		Location loc;
		
		int bx, by, bz;
		double ex, ey, ez;
		
		// Loop through player's line of sight
		while (bItr.hasNext()) {
			
			block = bItr.next();
			bx = block.getX();
			by = block.getY();
			bz = block.getZ();
			
			// Check for entities near this block in the line of sight
			for (LivingEntity e : livingE) {
				
				loc = e.getLocation();
				ex = loc.getX();
				ey = loc.getY();
				ez = loc.getZ();
				
				if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && 
						(by-1 <= ey && ey <= by+2.5)) {
					
					// Entity is close enough, set target and stop
					target = e;
					break;
				}
			}
		}
		
		return target;
	}
}