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

public class SetAttackSpeed implements CommandExecutor {

    private JavaPlugin plugin;

    public SetAttackSpeed(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() && args.length > 0) {
            Configuration.attackSpeed = Integer.parseInt(args[0]);

            Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
            for (Player player : players) {
                player.getAttribute(Attribute.GENERIC_ATTACK_SPEED ).setBaseValue(Configuration.attackSpeed);
            }

            plugin.getConfig().set("attackSpeed", Configuration.attackSpeed);
            plugin.saveConfig();
            Bukkit.broadcastMessage(ChatColor.YELLOW + "A velocidade de ataque agora é " + Configuration.attackSpeed);
            return true;
        }

        return false;
    }
}
