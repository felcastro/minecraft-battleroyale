package me.litwar.battleroyale.Commands;

import me.litwar.battleroyale.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class SetLootPercentages implements CommandExecutor {

    private JavaPlugin plugin;

    public SetLootPercentages(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() && args.length >= 6) {
            Configuration.lootPercentages = new ArrayList<>();
            Configuration.lootPercentages.add(Integer.parseInt(args[0]));
            Configuration.lootPercentages.add(Integer.parseInt(args[1]));
            Configuration.lootPercentages.add(Integer.parseInt(args[2]));
            Configuration.lootPercentages.add(Integer.parseInt(args[3]));
            Configuration.lootPercentages.add(Integer.parseInt(args[4]));
            Configuration.lootPercentages.add(Integer.parseInt(args[5]));

            plugin.getConfig().set("lootPercentages", Configuration.lootPercentages);
            plugin.saveConfig();

            Bukkit.broadcastMessage(ChatColor.YELLOW + "O percentual de loot agora é " + Arrays.toString(Configuration.lootPercentages.toArray()));

            return true;
        }

        return false;
    }

}
