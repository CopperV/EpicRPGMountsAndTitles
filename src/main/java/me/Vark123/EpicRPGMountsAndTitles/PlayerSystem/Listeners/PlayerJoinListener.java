package me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Vark123.EpicRPGMountsAndTitles.FileManager;
import me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.PlayerInfo;
import me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.PlayerManager;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PlayerInfo pi = FileManager.loadPlayerInfo(p);
		PlayerManager.get().registerPlayer(pi);
	}
	
}
