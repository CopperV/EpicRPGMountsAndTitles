package me.Vark123.EpicRPGMountsAndTitles;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.PlayerInfo;
import me.Vark123.EpicRPGMountsAndTitles.TitleSystem.Title;
import me.Vark123.EpicRPGMountsAndTitles.TitleSystem.TitleManager;

public final class FileManager {

	private static final File playerDir = new File(Main.getInst().getDataFolder(), "players");
	private static File oldDir = new File(Main.getInst().getDataFolder(), "old");
	
	private FileManager() { }
	
	public static void init() {
		if(!Main.getInst().getDataFolder().exists())
			Main.getInst().getDataFolder().mkdir();
		
		if(!playerDir.exists())
			playerDir.mkdir();
		
		if(oldDir.exists())
			convert();
		
		Main.getInst().saveResource("titles.yml", false);
		
		loadTitles();
	}
	
	private static void loadTitles() {
		File file = new File(Main.getInst().getDataFolder(), "titles.yml");
		if(!file.exists())
			return;
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(file);
		fYml.getKeys(false).stream()
			.map(fYml::getConfigurationSection)
			.forEach(section -> {
				String id = section.getString("id");
				String title = ChatColor.translateAlternateColorCodes('&', section.getString("title"));
				
				Title _title = Title.builder()
						.id(id)
						.title(title)
						.display(title)
						.build();
				TitleManager.get().registerTitle(_title);
			});
	}
	
	public static PlayerInfo loadPlayerInfo(Player p) {
		String uid = p.getUniqueId().toString();
		File file = new File(playerDir, uid+".yml");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(file);
		String currentTitle = fYml.getString("title.current", "0");
		List<String> unlockedTitles = fYml.getStringList("title.unlocked");
		
		return PlayerInfo.builder()
				.player(p)
				.titleId(currentTitle)
				.unclockedTitles(unlockedTitles)
				.build();
	}
	
	public static void savePlayerInfo(PlayerInfo pi) {
		Player p = pi.getPlayer();
		String uid = p.getUniqueId().toString();
		File file = new File(playerDir, uid+".yml");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(file);
		fYml.set("last-nick", p.getName());
		fYml.set("title.current", pi.getTitleId());
		fYml.set("title.unlocked", pi.getUnclockedTitles());
		try {
			fYml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void convert() {
		Arrays.asList(oldDir.listFiles()).stream()
			.filter(file -> file.isFile())
			.filter(file -> file.getName().endsWith(".yml"))
			.forEach(file -> {
				String nick = file.getName().replace(".yml", "");
				YamlConfiguration fYml = YamlConfiguration.loadConfiguration(file);
				System.out.println(nick);
				OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(nick);
				if(offPlayer == null)
					return;
				String uid = offPlayer.getUniqueId().toString();
				File file2 = new File(playerDir, uid+".yml");
				if(file2.exists()) {
					File toCompare1 = new File(oldDir, nick.toLowerCase()+".yml");
					YamlConfiguration fYml2 = YamlConfiguration.loadConfiguration(file2);
					String nick2 = fYml2.getString("last-nick");
					File toCompare2 = new File(oldDir, nick2.toLowerCase()+".yml");
					if(toCompare2.exists()
							&& FileUtils.isFileNewer(toCompare1, toCompare2))
						return;
					if(!toCompare2.exists())
						return;
				} else {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				YamlConfiguration fYml2 = YamlConfiguration.loadConfiguration(file);
				fYml2.set("last-nick", nick);
				fYml2.set("title.current", "0");
				fYml2.set("title.unlocked", fYml.getStringList("list"));
				
				try {
					fYml2.save(file2);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		oldDir.renameTo(new File(Main.getInst().getDataFolder(), "archive"));
	}
	
}
