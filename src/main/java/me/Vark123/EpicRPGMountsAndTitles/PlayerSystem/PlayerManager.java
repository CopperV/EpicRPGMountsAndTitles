package me.Vark123.EpicRPGMountsAndTitles.PlayerSystem;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.bukkit.entity.Player;

import lombok.Getter;

@Getter
public final class PlayerManager {

	private static final PlayerManager inst = new PlayerManager();
	
	private final Collection<PlayerInfo> players;
	
	private PlayerManager() {
		players = new HashSet<>();
	}
	
	public static final PlayerManager get() {
		return inst;
	}
	
	public void registerPlayer(PlayerInfo pi) {
		players.add(pi);
	}
	
	public void unregisterPlayer(PlayerInfo pi) {
		players.remove(pi);
	}
	
	public Optional<PlayerInfo> getPlayerInfo(Player p) {
		return players.stream()
				.filter(pi -> pi.getPlayer().equals(p))
				.findAny();
	}
	
}
