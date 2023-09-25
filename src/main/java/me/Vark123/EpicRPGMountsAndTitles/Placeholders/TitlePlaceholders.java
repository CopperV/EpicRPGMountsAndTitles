package me.Vark123.EpicRPGMountsAndTitles.Placeholders;

import org.apache.commons.lang3.mutable.MutableObject;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.PlayerManager;
import me.Vark123.EpicRPGMountsAndTitles.TitleSystem.TitleManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class TitlePlaceholders extends PlaceholderExpansion {

	@Override
	public String getAuthor() {
			return "Vark123";
	}

	@Override
	public String getIdentifier() {
		return "epictitle";
	}

	@Override
	public String getVersion() {
		return "1.0.0";
	}

	@Override
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
		MutableObject<String> toReturn = new MutableObject<>("");
		PlayerManager.get().getPlayerInfo(player)
			.ifPresent(pi -> {
				switch(params.toLowerCase()) {
					case "title":
						TitleManager.get().getTitleById(pi.getTitleId())
							.ifPresent(title -> toReturn.setValue(title.getTitle()));
						break;
					case "bracketstitle":
						TitleManager.get().getTitleById(pi.getTitleId())
							.ifPresent(title -> toReturn.setValue("§8["+title.getTitle()+"§8]"));
						break;
				}
			});
		return toReturn.getValue();
	}
}