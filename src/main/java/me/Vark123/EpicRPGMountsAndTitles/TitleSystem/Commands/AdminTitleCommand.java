package me.Vark123.EpicRPGMountsAndTitles.TitleSystem.Commands;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGMountsAndTitles.PlayerSystem.PlayerManager;
import me.Vark123.EpicRPGMountsAndTitles.TitleSystem.Title;
import me.Vark123.EpicRPGMountsAndTitles.TitleSystem.TitleManager;

public class AdminTitleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equals("rpgtitleadmin"))
			return false;
		if(!sender.hasPermission("rmat.admin")) {
			sender.sendMessage("§cNie masz uprawnien do tej komendy");
			return false;
		}
		if(args.length==0) {
			sender.sendMessage("§aPoprawne uzycie komendy:");
			sender.sendMessage("/rta list");
			sender.sendMessage("/rta add <nick> <id>");
			sender.sendMessage("/rta remove <nick> <id>");
			return false;
		}
		
		switch(args[0].toLowerCase()) {
			case "list":
				sender.sendMessage("§aDostepne tytuly:");
				TitleManager.get().getTitles().stream()
					.filter(title -> !title.getId().equals("0"))
					.forEach(title -> sender.sendMessage("  §4§l» §f"+title.getId()+". §r"+title.getDisplay()));
				break;
			case "add":
			{
				Player p = Bukkit.getPlayerExact(args[1]);
				if(p == null) {
					sender.sendMessage("§7§o"+args[1]+" §ajest offline");
					return false;
				}
				Optional<Title> oTitle = TitleManager.get().getTitleById(args[2]);
				if(oTitle.isEmpty()) {
					sender.sendMessage("§aTytul o ID §7§o"+args[2]+" §anie istnieje");
					return false;
				}
				Title title = oTitle.get();
				String titleId = title.getId();
				PlayerManager.get().getPlayerInfo(p)
					.ifPresent(pi -> {
						if(pi.getUnclockedTitles().contains(titleId))
							return;
						pi.getUnclockedTitles().add(titleId);
						sender.sendMessage("§aDodano tytul §r"+title.getTitle()+" §7§o"+p.getName());
					});
			}
				
				break;
			case "remove":
			{
				Player p = Bukkit.getPlayerExact(args[1]);
				if(p == null) {
					sender.sendMessage("§7§o"+args[1]+" §ajest offline");
					return false;
				}
				Optional<Title> oTitle = TitleManager.get().getTitleById(args[2]);
				if(oTitle.isEmpty()) {
					sender.sendMessage("§aTytul o ID §7§o"+args[2]+" §anie istnieje");
					return false;
				}
				Title title = oTitle.get();
				String titleId = title.getId();
				PlayerManager.get().getPlayerInfo(p)
					.ifPresent(pi -> {
						if(!pi.getUnclockedTitles().contains(titleId))
							return;
						pi.getUnclockedTitles().remove(titleId);
						if(pi.getTitleId().equals(titleId))
							pi.setTitleId("0");
						sender.sendMessage("§aUsunieto tytul §r"+title.getTitle()+" §7§o"+p.getName());
					});
			}
				break;
		}
		return true;
	}

}
