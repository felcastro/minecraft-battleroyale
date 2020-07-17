package me.litwar.battleroyale.Commands;

import me.litwar.battleroyale.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SetAllowMobs implements CommandExecutor {

    private JavaPlugin plugin;

    public SetAllowMobs(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() && args.length == 1) {
            if (args[0].equals("true")) {
                Configuration.allowMobs = true;
            } else {
                Configuration.allowMobs = false;
            }

            plugin.getConfig().set("allowMobs", Configuration.allowMobs);
            plugin.saveConfig();

            if (Configuration.allowMobs) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Mobs foram habilitados.");
            } else {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Mobs foram desabilitados.");
            }

            return true;
        }

        return false;
    }

}
