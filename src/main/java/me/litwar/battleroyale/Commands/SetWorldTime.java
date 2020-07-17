package me.litwar.battleroyale.Commands;

import me.litwar.battleroyale.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SetWorldTime implements CommandExecutor {

    private JavaPlugin plugin;

    public SetWorldTime(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() && args.length == 1) {
            Configuration.worldTime = Integer.parseInt(args[0]);

            plugin.getConfig().set("worldTime", Configuration.worldTime);
            plugin.saveConfig();
            Bukkit.broadcastMessage(ChatColor.YELLOW + "O horário das partidas agora é " + Configuration.worldTime);
            return true;
        }

        return false;
    }

}
