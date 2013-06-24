package org.nationsatwar.kitty.Utility;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.nationsatwar.kitty.Kitty;

public final class AIUtility {

	/**
	 * Creates all the default Sumo Files
	 */
	public static Location randomizeLocation(Location origin, float radius) {
		
		float random1 = getRandomNumber(-1, 1) * radius;
		float random2 = getRandomNumber(-1, 1) * radius;
		
		Location newLocation = origin.clone();
		
		newLocation.setX(newLocation.getX() + random1);
		newLocation.setZ(newLocation.getZ() + random2);
		
		Location highestBlock = newLocation.getWorld().getHighestBlockAt(newLocation).getLocation();
		highestBlock.setY(highestBlock.getY() + 1);
		
		if (origin.distance(highestBlock) <= radius)
			newLocation = highestBlock;
		
		float debugNumber = 0;
		
		while (!newLocation.getBlock().isEmpty()) {
			
			Vector vector = origin.toVector().subtract(newLocation.toVector()).normalize();
			newLocation = newLocation.add(vector);
			
			debugNumber++;
			if (debugNumber > 20) {
				
				Kitty.log("While Loop going on way too long");
				break;
			}
		}
		
		return newLocation;
	}
	
	public static float getRandomNumber(float minNumber, float maxNumber) {
		
		Random randomGenerator = new Random();

		float randomNumber = randomGenerator.nextFloat();
		float difference = maxNumber - minNumber;

		randomNumber *= difference;
		randomNumber += minNumber;
		
		return randomNumber;
	}
}