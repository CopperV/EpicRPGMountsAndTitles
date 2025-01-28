package me.Vark123.EpicRPGMountsAndTitles;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.Vark123.EpicInventory.Pagination.InventoryManager;
import me.Vark123.EpicRPGMountsAndTitles.Placeholders.TitlePlaceholders;

@Getter
public class Main extends JavaPlugin {

	@Getter
	private static Main inst;

	private InventoryManager inventoryManager;
	
	@Override
	public void onEnable() {
		inst = this;
		
		CommandManager.setExecutors();
		ListenerManager.registerListeners();
		FileManager.init();

		inventoryManager = new InventoryManager(inst);
		inventoryManager.invoke();
		
		new TitlePlaceholders().register();
	}

	@Override
	public void onDisable() {
		
	}
	
	
}
