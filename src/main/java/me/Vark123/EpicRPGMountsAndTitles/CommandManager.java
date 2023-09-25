package me.Vark123.EpicRPGMountsAndTitles;

import org.bukkit.Bukkit;

import me.Vark123.EpicRPGMountsAndTitles.TitleSystem.Commands.AdminTitleCommand;
import me.Vark123.EpicRPGMountsAndTitles.TitleSystem.Commands.TitleCommand;

public final class CommandManager {

	private CommandManager() { }
	
	public static void setExecutors() {
		Bukkit.getPluginCommand("rpgtitle").setExecutor(new TitleCommand());
		Bukkit.getPluginCommand("rpgtitleadmin").setExecutor(new AdminTitleCommand());
	}
	
}
