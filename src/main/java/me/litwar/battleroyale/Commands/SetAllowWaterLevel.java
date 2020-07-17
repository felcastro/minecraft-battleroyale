package me.litwar.battleroyale.Commands;

import me.litwar.battleroyale.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SetAllowWaterLevel implements CommandExecutor {

    private JavaPlugin plugin;

    public SetAllowWaterLevel(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() && args.length == 1) {
            if (args[0].equals("true")) {
                Configuration.allowOceanLevels = true;
            } else {
                Configuration.allowOceanLevels = false;
            }

            plugin.getConfig().set("allowWaterLevel", Configuration.allowOceanLevels);
            plugin.saveConfig();

            if (Configuration.allowOceanLevels) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Níveis de oceano foram habilitados.");
            } else {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Níveis de oceano foram desabilitados.");
            }

            return true;
        }

        return false;
    }

}
