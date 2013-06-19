package org.nationsatwar.kitty.Utility;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.nationsatwar.kitty.Kitty;
import org.nationsatwar.kitty.Commands.BeckonCommand;
import org.nationsatwar.kitty.Commands.SummonCommand;

public final class CommandParser implements CommandExecutor {

	protected final Kitty plugin;

	private final SummonCommand summonCommand;
	private final BeckonCommand beckonCommand;
	
	public CommandParser(Kitty plugin) {
		
		summonCommand = new SummonCommand(plugin);
		beckonCommand = new BeckonCommand(plugin);
		
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String argsLabel, String[] args) {
		
		// -kitty OR -kitty help
		if (args.length == 0 || args[0].equals("help"))
			helpCommand(sender);
		
		// -kitty summon
		else if (args[0].equals("summon"))
			spawnCommand(sender, args);
		
		// -kitty beckon
		else if (args[0].equals("beckon"))
			beckonCommand(sender, args);
		
		// -kitty <non-applicable command>
		else {
			
			sender.sendMessage(ChatColor.DARK_RED + "Invalid command: type '/kitty' for help.");
			return false;
		}
		
		return true;
	}

	/**
	 * Handles the 'Help' command.
	 * <p>
	 * Returns a list of all possible commands to the command sender.
	 * 
	 * @param sender  Person sending the command
	 */
	private void helpCommand(CommandSender sender) {

		sender.sendMessage(ChatColor.DARK_RED + "[Nations at War]" + ChatColor.DARK_AQUA + " -=[KITTY]=-");
		sender.sendMessage(ChatColor.YELLOW + "Allows you to create and manage triggers.");
		sender.sendMessage(ChatColor.YELLOW + "Command List: Create");
	}

	/**
	 * Returns help and parses the 'Spawn' command for execution.
	 * 
	 * @param sender  Person sending the command
	 * @param args  String of arguments associated with the command
	 */
	private void spawnCommand(CommandSender sender, String[] args) {

		if (args.length <= 1 || args[1].equals("help")) {
			
			sender.sendMessage(ChatColor.DARK_RED + "[Nations at War]" + ChatColor.DARK_AQUA + " -=[SPAWN]=-");
			sender.sendMessage(ChatColor.DARK_AQUA + "i.e. '/ispy spawn <entity name>");
			sender.sendMessage(ChatColor.YELLOW + "Creates a new entity.");
			return;
		}
		
		// Cancel if command is sent from console
		if (!isPlayer(sender))
			return;
		
		// Gets the player sending the command
		Player player = (Player) sender;
		
		// Stores the full entity name
		String entityName = getRemainingString(1, args);
		
		// Execute Create Command
		summonCommand.execute(player, entityName);
	}

	/**
	 * Returns help and parses the 'Spawn' command for execution.
	 * 
	 * @param sender  Person sending the command
	 * @param args  String of arguments associated with the command
	 */
	private void beckonCommand(CommandSender sender, String[] args) {

		if (args.length > 1 && args[1].equals("help")) {
			
			sender.sendMessage(ChatColor.DARK_RED + "[Nations at War]" + ChatColor.DARK_AQUA + " -=[BECKON]=-");
			sender.sendMessage(ChatColor.DARK_AQUA + "i.e. '/ispy beckon");
			sender.sendMessage(ChatColor.YELLOW + "Beckons your entity to come back to you.");
			return;
		}
		
		// Cancel if command is sent from console
		if (!isPlayer(sender))
			return;
		
		// Gets the player sending the command
		Player player = (Player) sender;
		
		// Execute Create Command
		beckonCommand.execute(player);
	}

	/**
	 * Checks to see if the sender is an in-game player
	 * <p>
	 * Gives the console a message if false.
	 * 
	 * @param sender  The sender to check
	 * @return true if a player, false otherwise
	 */
	private static boolean isPlayer(CommandSender sender) {
		
		if (sender instanceof Player)
			return true;
		else {
			
			sender.sendMessage(ChatColor.YELLOW + "Must be a player to issue this command.");
			return false;
		}
	}

	/**
	 * Combines the remaining arguments into a single string
	 * 
	 * @param arrayStart  The start of the array to form the string at
	 * @param args  The command arguments to parse
	 * 
	 * @return fullString  The full parsed string of the remainder of the command arguments.
	 */
	private static String getRemainingString(int arrayStart, String[] args) {
		
		String fullString = (args.length > arrayStart ? args[arrayStart] : "");
		
		for (int i = arrayStart + 1; i < args.length; i++)
			fullString += " " + args[arrayStart];
		
		return fullString;
	}
}