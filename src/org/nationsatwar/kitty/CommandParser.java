package org.nationsatwar.kitty;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class CommandParser implements CommandExecutor {

	protected final Kitty plugin;
	
	public CommandParser(Kitty plugin) {
		
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String argsLabel, String[] args) {
		
		// -kitty OR -kitty help
		if (args.length == 0 || args[0].equals("help"))
			helpCommand(sender);
		
		// -kitty create
		else if (args[0].equals("create"))
			createCommand(sender, args);
		
		// -kitty <non-applicable command>
		else {
			
			sender.sendMessage(ChatColor.DARK_RED + "Invalid command: type '/ispy' for help.");
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
	 * Returns help and parses the 'Create' command for execution.
	 * 
	 * @param sender  Person sending the command
	 * @param args  String of arguments associated with the command
	 */
	private void createCommand(CommandSender sender, String[] args) {
		
		
	}
}