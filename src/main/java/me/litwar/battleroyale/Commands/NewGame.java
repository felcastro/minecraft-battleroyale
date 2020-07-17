package me.litwar.battleroyale.Commands;

import me.litwar.battleroyale.Configuration;
import me.litwar.battleroyale.Models.Arena;
import me.litwar.battleroyale.Models.Match;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class NewGame implements CommandExecutor {

    private JavaPlugin plugin;

    public NewGame(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Preparando nova partida...");
            World world = plugin.getServer().getWorlds().get(0);

            // Set pre-match configuration
            Configuration.damageEnabled = false;
            Configuration.movementEnabled = false;
            Configuration.scoreboard.getTeam("brplayers").setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
            world.setTime(Configuration.worldTime);

            // Validates and sets a new arena
            Arena arena;
            if (args.length >= 1) {
                try {
                    Biome biome = Biome.valueOf(args[0].toUpperCase().replace(' ', '_'));
                } catch (IllegalArgumentException ex) {
                    sender.sendMessage("Invalid biome.");
                }
                arena = new Arena(world, Biome.valueOf(args[0].toUpperCase().replace(' ', '_')), Configuration.worldBorder, Configuration.chestCount, Configuration.enchantmentTablesCount);
            } else {
                arena = new Arena(world, Configuration.allowOceanLevels, Configuration.worldBorder, Configuration.chestCount, Configuration.enchantmentTablesCount);
            }

            // Sets new match
            ArrayList<Player> players = new ArrayList<>();
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                players.add(player);
            }
            Configuration.currentMatch = new Match(players, arena);
            Configuration.currentMatch.preparePlayers();

            Bukkit.broadcastMessage(ChatColor.GREEN + "Partida pronta! Aguardando inicialização...");
            Bukkit.broadcastMessage(ChatColor.GREEN + "Número de baús: " + Configuration.chestCount);
            Bukkit.broadcastMessage(ChatColor.GREEN + "Número de mesas de encantamento: " + Configuration.enchantmentTablesCount);
            Bukkit.broadcastMessage(ChatColor.GREEN + "Tamanho da borda: " + Configuration.worldBorder);

            return true;
        }

        sender.sendMessage("You are not an admin.");
        return false;
    }


}
