package me.Vark123.EpicRPGMountsAndTitles.PlayerSystem;

import java.util.List;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class PlayerInfo {

	private Player player;
	@Setter
	private String titleId;
	private List<String> unclockedTitles;
	
}
