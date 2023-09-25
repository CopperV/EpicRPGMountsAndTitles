package me.Vark123.EpicRPGMountsAndTitles.TitleSystem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Title {

	private String id;
	private String title;
	private String display;
	
	private ItemStack item;
	
	public ItemStack getItem() {
		if(item != null)
			return item.clone();
		
		item = new ItemStack(Material.PAPER);
		
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§3§oTytul §r"+this.display);
		item.setItemMeta(im);
		
		return item.clone();
	}
	
}
