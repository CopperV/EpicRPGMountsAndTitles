package me.Vark123.EpicRPGMountsAndTitles.TitleSystem.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGMountsAndTitles.TitleSystem.TitleMenuManager;

public class TitleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("rpgtitle"))
			return false;
		if(!(sender instanceof Player))
			return false;
		
		Player p = (Player) sender;
		TitleMenuManager.get().openMenu(p);
		return true;
	}

}
