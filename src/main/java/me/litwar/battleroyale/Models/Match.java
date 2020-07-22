package me.litwar.battleroyale.Models;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Match {

    private Arena arena;
    private int playersCount;
    private ArrayList<Player> players;
    private HashMap<String, Integer> kills;
    private boolean inPreparation;
    private boolean inProgress;
    private boolean isOver;
    private int nextSpawnPlatform;

    public Match(ArrayList<Player> players, Arena arena) {
        this.players = players;
        this.arena = arena;
        this.inPreparation = false;
        this.inProgress = false;
        this.isOver = false;
        this.nextSpawnPlatform = 0;
        this.playersCount = players.size();
        this.kills = new HashMap<String, Integer>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getPlayersCount() {
        return this.playersCount;
    }

    public Arena getArena() {
        return this.arena;
    }

    public HashMap<String, Integer> getKills() {
        return this.kills;
    }

    public boolean isOver() {
        return this.isOver;
    }

    public boolean isInPreparation() {
        return this.inPreparation;
    }

    public void setInPreparation(boolean inPreparation) {
        this.inPreparation = inPreparation;
    }

    public boolean isInProgress() {
        return this.inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public void preparePlayers() {
        this.inPreparation = true;
        this.nextSpawnPlatform = 0;

        Collections.shuffle(this.players);
        for (Player player : this.players) {
            preparePlayer(player);
            teleportPlayer(player);
        }
    }

    private void preparePlayer(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setExp(0);
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.getInventory().clear();
        player.getEquipment().clear();
        player.getActivePotionEffects().clear();

        player.getEquipment().setChestplate(new ItemStack(Material.ELYTRA));
        player.getInventory().addItem(new ItemStack(Material.BREAD, 15));
        player.getInventory().addItem(new ItemStack(Material.FIREWORK, 1));
        player.getInventory().addItem(new ItemStack(Material.COMPASS));
    }

    public void startMatch(int finalSize, int shrinkTime) {
        this.inProgress = true;
        this.arena.shrinkArena(finalSize, shrinkTime);
    }

    public boolean addPlayer(Player player) {
        if (this.inPreparation) {
            this.players.add(player);
            this.playersCount++;
            preparePlayer(player);
            teleportPlayer(player);
            return true;
        }
        return false;
    }

    public boolean removePlayer(Player player) {
        if (this.inPreparation) {
            this.playersCount--;
            return this.players.removeIf(p -> p.getName().equals(player.getName()));
        }
        return false;
    }

    public int killPlayer(Player killed, Entity killer) {
        if (this.players.removeIf(p -> p.getName().equals(killed.getName()))){
            if (this.players.size() <= 1) {
                this.isOver = true;
            }
        }

        if(killer != null) {
            kills.put(killer.getName(), kills.getOrDefault(killer.getName(), 0) + 1);
        }

        return this.players.size();
    }

    private void teleportPlayer(Player player) {
        Location nextPlat = this.arena.getPlatforms().get(this.nextSpawnPlatform);
        player.teleport(new Location(this.arena.getWorld(), nextPlat.getX(), nextPlat.getY() + 1, nextPlat.getZ()));
        this.nextSpawnPlatform = (this.nextSpawnPlatform < this.arena.getPlatforms().size() - 1) ? this.nextSpawnPlatform + 1 : 0;
    }

}
