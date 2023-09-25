package me.Vark123.EpicRPGMountsAndTitles.TitleSystem;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.mutable.MutableInt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import me.Vark123.EpicRPGMountsAndTitles.Main;
import me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.PlayerManager;

public final class TitleMenuManager {

	private static final TitleMenuManager inst = new TitleMenuManager();
	
	private final ItemStack previous;
	private final ItemStack next;
	
	private final ItemStack empty;
	private final ItemStack back;
	
	private TitleMenuManager() {
		previous = new ItemStack(Material.ARROW);{
			ItemMeta im = previous.getItemMeta();
			im.setDisplayName("§fPoprzednia");
			previous.setItemMeta(im);
		}
		next = new ItemStack(Material.ARROW);{
			ItemMeta im = next.getItemMeta();
			im.setDisplayName("§fNastepna");
			next.setItemMeta(im);
		}

		empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);{
			ItemMeta im = empty.getItemMeta();
			im.setDisplayName(" ");
			empty.setItemMeta(im);
		}
		back = new ItemStack(Material.BARRIER);{
			ItemMeta im = back.getItemMeta();
			im.setDisplayName("§cPowrot");
			back.setItemMeta(im);
		}
	}
	
	public static final TitleMenuManager get() {
		return inst;
	}
	
	public void openMenu(Player p) {
		RyseInventory.builder()
			.title("§e§lTWOJE TYTULY")
			.rows(6)
			.disableUpdateTask()
			.provider(getProvider(p))
			.build(Main.getInst())
			.open(p);
	}
	
	private InventoryProvider getProvider(Player p) {
		return new InventoryProvider() {
			@Override
			public void init(Player player, InventoryContents contents) {
				PlayerManager.get().getPlayerInfo(p)
					.ifPresent(pi -> {
						List<Title> titles = pi.getUnclockedTitles().stream()
								.map(id -> TitleManager.get().getTitleById(id))
								.filter(title -> title.isPresent())
								.map(title -> title.get())
								.filter(title -> !title.getId().equals(pi.getTitleId()))
								.collect(Collectors.toList());
						if(!pi.getTitleId().equals("0"))
							titles.add(TitleManager.get().getTitleById("0").get());
						titles.sort((t1, t2) -> {
							String id1 = t1.getId();
							String id2 = t2.getId();
							if(id1.length() == id2.length())
								return id1.compareTo(id2);
							else
								return Integer.compare(id1.length(), id2.length());
						});
						MutableInt index = new MutableInt();
						titles.stream()
							.forEach(title -> {
								ItemStack it = title.getItem();
								contents.set(index.getAndIncrement(), IntelligentItem.of(it, click -> {
									p.closeInventory();
									if(TitleManager.get().getTitleCooldowns().containsKey(p.getUniqueId())
											&& (new Date().getTime() - TitleManager.get().getTitleCooldowns().get(p.getUniqueId()).getTime()) < TitleManager.get().TITLE_COOLDOWN) {
										p.sendMessage("§cZbyt malo czasu minelo od ostatniej zmiany tytulu!");
										return;
									}
									
									pi.setTitleId(title.getId());
									if(title.getId().equals("0"))
										p.sendMessage("§aUsunales swoj tytul");
									else 
										p.sendMessage("§aZmieniles swoj tytul na §r"+title.getTitle());
									p.sendMessage("§aKolejny tytul bedziesz mogl wybrac za §7§o60 §aminut!");
									TitleManager.get().getTitleCooldowns().put(p.getUniqueId(), new Date());
								}));
							});
					});
			}
		};
	}
	
}
