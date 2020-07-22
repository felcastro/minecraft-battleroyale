package me.litwar.battleroyale.Commands;

import me.litwar.battleroyale.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public class SetAxeAttackSpeed implements CommandExecutor {

    private JavaPlugin plugin;

    public SetAxeAttackSpeed(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() && args.length > 0) {
            Configuration.axeAttackSpeed = Integer.parseInt(args[0]);

            plugin.getConfig().set("axeAttackSpeed", Configuration.axeAttackSpeed);
            plugin.saveConfig();
            Bukkit.broadcastMessage(ChatColor.YELLOW + "A velocidade de ataque do machado agora é " + Configuration.axeAttackSpeed);
            return true;
        }

        return false;
    }

}
