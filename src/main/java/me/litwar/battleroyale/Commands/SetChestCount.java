package me.litwar.battleroyale.Commands;

import me.litwar.battleroyale.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SetChestCount implements CommandExecutor {

    private JavaPlugin plugin;

    public SetChestCount(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() && args.length > 0) {
            Configuration.chestCount = Integer.parseInt(args[0]);

            plugin.getConfig().set("chestCount", Configuration.chestCount);
            plugin.saveConfig();
            Bukkit.broadcastMessage(ChatColor.YELLOW + "O número de baús agora é " + Configuration.chestCount);
            return true;
        }

        return false;
    }

}
