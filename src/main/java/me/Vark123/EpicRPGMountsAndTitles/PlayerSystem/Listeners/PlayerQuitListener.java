package me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Vark123.EpicRPGMountsAndTitles.FileManager;
import me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.PlayerManager;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		playerCleaner(e.getPlayer());
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		playerCleaner(e.getPlayer());
	}
	
	private void playerCleaner(Player p) {
		PlayerManager.get().getPlayerInfo(p)
			.ifPresent(pi -> {
				PlayerManager.get().unregisterPlayer(pi);
				FileManager.savePlayerInfo(pi);
			});
	}
	
}
