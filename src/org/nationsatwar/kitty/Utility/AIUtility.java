package org.nationsatwar.kitty.Utility;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class AIUtility {

	/**
	 * Creates all the default Sumo Files
	 */
	public static Location randomizeLocation(Location origin, float radius) {
		
		Random randomGenerator = new Random();
		
		float random1 = (randomGenerator.nextFloat() - 0.5f) * 2 * radius;
		float random2 = (randomGenerator.nextFloat() - 0.5f) * 2 * radius;
		
		Location newLocation = origin.clone();
		
		newLocation.setX(newLocation.getX() + random1);
		newLocation.setZ(newLocation.getZ() + random2);
		
		Location highestBlock = newLocation.getWorld().getHighestBlockAt(newLocation).getLocation();
		highestBlock.setY(highestBlock.getY() + 1);
		
		if (origin.distance(highestBlock) <= radius)
			newLocation = highestBlock;
		
		while (!newLocation.getBlock().isEmpty()) {

			Vector vector = origin.toVector().subtract(newLocation.toVector()).normalize();
			newLocation = newLocation.add(vector);
		}
		
		return newLocation;
	}
}