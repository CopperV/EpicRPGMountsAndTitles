package me.Vark123.EpicRPGMountsAndTitles.TitleSystem;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public final class TitleManager {

	private static final TitleManager inst = new TitleManager();
	
	@Getter(value = AccessLevel.NONE)
	public final int TITLE_COOLDOWN = 1000*60*60;
	
	private final Collection<Title> titles;
	private final Map<UUID, Date> titleCooldowns;
	
	private TitleManager() {
		titles = new HashSet<>();
		titleCooldowns = new ConcurrentHashMap<>();
		
		titles.add(Title.builder()
				.id("0")
				.title("")
				.display("§8[§c§lBRAK§8]")
				.build());
	}
	
	public static final TitleManager get() {
		return inst;
	}
	
	public void registerTitle(Title title) {
		titles.add(title);
	}
	
	public Optional<Title> getTitleById(String id) {
		return titles.stream()
				.filter(title -> title.getId().equals(id))
				.findAny();
	}
	
}
