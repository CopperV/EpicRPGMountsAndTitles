package me.Vark123.EpicRPGMountsAndTitles;

import org.bukkit.Bukkit;

import me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.Listeners.PlayerJoinListener;
import me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.Listeners.PlayerQuitListener;

public final class ListenerManager {

	private ListenerManager() { }
	
	public static void registerListeners() {
		Main inst = Main.getInst();
		
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), inst);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), inst);
	}
	
}
