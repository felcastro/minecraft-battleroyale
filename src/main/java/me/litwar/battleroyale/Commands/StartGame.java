package me.litwar.battleroyale.Commands;

import me.litwar.battleroyale.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public class StartGame implements CommandExecutor {

    private JavaPlugin plugin;

    public StartGame(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {

            // Starting timers
            Bukkit.broadcastMessage(ChatColor.GREEN + "Tempo de fechamento: " + Configuration.worldBorderShrinkTime + " segundos (" + Configuration.worldBorderShrinkTime/60 + " minutos)");
            Bukkit.broadcastMessage(ChatColor.GREEN + "Iniciando partida em");
            Bukkit.broadcastMessage(ChatColor.GREEN + "5");
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.broadcastMessage(ChatColor.GREEN + "4");
            }, 20);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.broadcastMessage(ChatColor.GREEN + "3");
            }, 40);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.broadcastMessage(ChatColor.GREEN + "2");
            }, 60);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.broadcastMessage(ChatColor.GREEN + "1");
            }, 80);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.broadcastMessage(ChatColor.GREEN + "PARTIDA INICIADA!");
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Ativando PVP em 30 segundos...");
                Configuration.movementEnabled = true;
                Configuration.currentMatch.startMatch(1, Configuration.worldBorderShrinkTime);
            }, 100);

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.broadcastMessage(ChatColor.RED + "PVP ATIVADO!");
                Configuration.damageEnabled = true;
                Configuration.scoreboard.getTeam("brplayers").setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);

                Configuration.currentMatch.getArena().removePlatforms();
            }, 20 * 35);

            return true;
        }

        return false;
    }
}
