package me.litwar.battleroyale.Commands;

import me.litwar.battleroyale.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Map;

public class Rank implements CommandExecutor {

    private JavaPlugin plugin;

    public Rank(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StringBuilder str = new StringBuilder(ChatColor.GOLD + "JOGADOR | W / K\n");
        FileConfiguration config = plugin.getConfig();
        for (String key : plugin.getConfig().getConfigurationSection("wins").getKeys(false)) {
            str.append(key).append(" - ").append(config.get("wins."+key)).append(" | ").append(config.get("kills."+key)).append("\n");
        }
        sender.sendMessage(String.valueOf(str));

        return true;
    }

}
