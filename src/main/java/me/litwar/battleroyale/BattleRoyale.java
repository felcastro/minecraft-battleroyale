package me.litwar.battleroyale;

import me.litwar.battleroyale.Commands.*;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public final class BattleRoyale extends JavaPlugin implements Listener {

    private ItemStack shield = new ItemStack(Material.SHIELD, 1);
    private FileConfiguration config = getConfig();
    private World world;

    @Override
    public void onEnable() {
        System.out.println("Initializing BattleRoyale plugin...");

        world = getServer().getWorlds().get(0);

        // Initializing Gamerules
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("announceAdvancements", "false");
        world.setGameRuleValue("disableElytraMovementCheck", "true");
        world.setGameRuleValue("spectatorsGenerateChunks", "false");

        // Initializing Team
        Configuration.scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        if (Configuration.scoreboard.getTeams().size() == 0) {
            Team players = Configuration.scoreboard.registerNewTeam("brplayers");
            players.setColor(ChatColor.YELLOW);
            players.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            players.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        }

        // Other server configuration
        world.setStorm(false);

        // Add default values to config.yml
        config.addDefault("attackSpeed", 30);
        config.addDefault("worldBorder", 1000);
        config.addDefault("worldBorderShrinkTime", 480);
        config.addDefault("worldTime", 2000);
        config.addDefault("chestCount", 1000);
        config.addDefault("enchantmentTablesCount", 50);
        config.addDefault("allowOceanLevels", false);
        config.addDefault("allowMobs", false);
        ArrayList<Integer> lp = new ArrayList<Integer>();
        lp.add(100);
        lp.add(100);
        lp.add(100);
        lp.add(15);
        lp.add(8);
        lp.add(1);
        config.addDefault("lootPercentages", lp);
        config.options().copyDefaults(true);
        saveConfig();

        // Load config.yml values
        Configuration.attackSpeed = config.getInt("attackSpeed");
        Configuration.worldBorder = config.getInt("worldBorder");
        Configuration.worldBorderShrinkTime = config.getInt("worldBorderShrinkTime");
        Configuration.worldTime = config.getInt("worldTime");
        Configuration.chestCount = config.getInt("chestCount");
        Configuration.enchantmentTablesCount = config.getInt("enchantmentTablesCount");
        Configuration.allowOceanLevels = config.getBoolean("allowOceanLevels");
        Configuration.allowMobs = config.getBoolean("allowMobs");
        Configuration.lootPercentages = config.getIntegerList("lootPercentages");

        // Registering Events
        getServer().getPluginManager().registerEvents(this, this);

        // Register Commands
        getCommand("newgame").setExecutor(new NewGame(this));
        getCommand("startgame").setExecutor(new StartGame(this));
        getCommand("rank").setExecutor(new Rank(this));
        getCommand("setattackspeed").setExecutor(new SetAttackSpeed(this));
        getCommand("setworldborder").setExecutor(new SetWorldBorder(this));
        getCommand("setbordershrink").setExecutor(new SetWorldBorderShrink(this));
        getCommand("setlootpercentage").setExecutor(new SetLootPercentages(this));
        getCommand("setallowoceanlevel").setExecutor(new SetAllowWaterLevel(this));
        getCommand("setchestcount").setExecutor(new SetChestCount(this));
        getCommand("setallowmobs").setExecutor(new SetAllowMobs(this));
        getCommand("setworldtime").setExecutor(new SetWorldTime(this));

        getServer().dispatchCommand(Bukkit.getConsoleSender(), "newgame");
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!Configuration.damageEnabled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMovement(PlayerMoveEvent event) {
        if (!Configuration.movementEnabled && (event.getFrom().getX() != event.getTo().getX() && event.getFrom().getZ() != event.getTo().getZ())) {
            event.getTo().setX(event.getFrom().getX());
            event.getTo().setZ(event.getFrom().getZ());
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!Configuration.allowMobs) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (event.getRecipe().getResult().equals(shield)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.ENCHANTMENT_TABLE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.TNT) && !Configuration.currentMatch.getArena().isCloseToSpawn(event.getBlock().getLocation())) {
            event.getBlock().setType(Material.AIR);
            Entity tnt = world.spawn(new Location(world, event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()), TNTPrimed.class);
            ((TNTPrimed) tnt).setFuseTicks(30);
        } else if (event.getBlock().getType().equals(Material.TNT)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Não permitido no centro da arena");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Configuration.scoreboard.getTeam("brplayers").addEntry(event.getPlayer().getDisplayName());
        event.getPlayer().setScoreboard(Configuration.scoreboard);
        event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Configuration.attackSpeed);
        if (Configuration.currentMatch != null && Configuration.currentMatch.isInProgress()) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        } else if (Configuration.currentMatch != null && Configuration.currentMatch.isInPreparation()) {
            Configuration.currentMatch.addPlayer(event.getPlayer());
            Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + " entrou na partida!");
            System.out.println("Players in match: " + Configuration.currentMatch.getPlayers().size());
        }
        if (!config.contains("wins." + event.getPlayer().getName())) {
            config.set("wins." + event.getPlayer().getName(), 0);
        }
        if (!config.contains("kills." + event.getPlayer().getName())) {
            config.set("kills." + event.getPlayer().getName(), 0);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (Configuration.currentMatch.isInProgress()) {
            if (Configuration.currentMatch.killPlayer(event.getEntity().getPlayer(), event.getEntity().getKiller()) == 1) {
                Player winner = Configuration.currentMatch.getPlayers().get(0);
                winner.sendTitle(ChatColor.YELLOW + "", "WINNER WINNER CHICKEN DINNER", 10, 100, 20);
                Bukkit.broadcastMessage(ChatColor.GREEN + winner.getName() + " é o vencedor!");
                config.set("wins." + winner.getName(), config.getInt("wins." + winner.getName()) + 1);
                saveConfig();
            } else {
                event.setDeathMessage(ChatColor.RED + event.getDeathMessage() + " | Restam " + Configuration.currentMatch.getPlayers().size() + " jogadores.");
            }
            Entity e = event.getEntity().getPlayer().getKiller();
            if(e != null) {
                config.set("kills." + e.getName(), config.getInt("kills." + e.getName()) + 1);
                saveConfig();
                if (Configuration.currentMatch.getPlayersCount() == Configuration.currentMatch.getPlayers().size() + 1) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + e.getName() + " FIRSTBLOOD!");
                } else {
                    switch (Configuration.currentMatch.getKills().get(e.getName())) {
                        case 2:
                            Bukkit.broadcastMessage(ChatColor.AQUA + e.getName() + " DOMINATING!");
                            break;
                        case 3:
                            Bukkit.broadcastMessage(ChatColor.AQUA + e.getName() + " RAMPAGE!");
                            break;
                        case 4:
                            Bukkit.broadcastMessage(ChatColor.AQUA + e.getName() + " KILLING SPREE!");
                            break;
                        case 5:
                            Bukkit.broadcastMessage(ChatColor.AQUA + e.getName() + " UNSTOPPABLE!");
                            break;
                        case 6:
                            Bukkit.broadcastMessage(ChatColor.AQUA + e.getName() + " GODLIKE!");
                            break;
                        case 7:
                            Bukkit.broadcastMessage(ChatColor.AQUA + e.getName() + " LUDICROUSKILL!");
                            break;
                        case 8:
                            Bukkit.broadcastMessage(ChatColor.AQUA + e.getName() + " HOLY SHIT!");
                            break;
                    }
                }
            }
        }
        event.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);
    }

//    @EventHandler
//    public void onPlayerDropItem(PlayerDropItemEvent event) {
//        event.getItemDrop().remove();
//    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Configuration.currentMatch.isInProgress() && !Configuration.currentMatch.isOver()) {
            if (Configuration.currentMatch.killPlayer(event.getPlayer(), null) == 1) {
                Player winner = Configuration.currentMatch.getPlayers().get(0);
                winner.sendTitle(ChatColor.YELLOW + "", "WINNER WINNER CHICKEN DINNER", 10, 100, 20);
                Bukkit.broadcastMessage(ChatColor.GREEN + winner.getName() + " é o vencedor!");
                config.set("wins." + winner.getName(), config.getInt("wins." + winner.getName()) + 1);
                saveConfig();
            } else {
                event.setQuitMessage(ChatColor.RED + event.getQuitMessage() + " | Restam " + Configuration.currentMatch.getPlayers().size() + " jogadores.");
            }
        } else if (Configuration.currentMatch.isInPreparation() && !Configuration.currentMatch.isOver()) {
            if (Configuration.currentMatch.removePlayer(event.getPlayer())) {
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + " abandonou a partida!");
                System.out.println("Players in match: " + Configuration.currentMatch.getPlayers().size());
            }
        }
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        try {
            if (event.getItem().hasItemMeta() && event.getItem().getItemMeta().getDisplayName().equals("ADMIN POTION")) {
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + event.getPlayer().getName() + " se tornou um deus!");
                Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + event.getPlayer().getName() + " voltou ao normal.");
                }, 20 * 15);
            }
        } catch (NullPointerException ex) {
            System.out.println("Item consume error: " + event.getItem().getType());
        }
    }

//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event) {
//        if (event.getPlayer().getEquipment().getHelmet() != null) {
//            System.out.println(event.getPlayer().getEquipment().getHelmet().getItemMeta().getDisplayName());
//            if (event.getAction().equals(Action.) && event.getPlayer().getEquipment().getHelmet().getItemMeta().getDisplayName().equals("Crown of Immortality")) {
//                System.out.println("Right clicked anywhere");
//                event.getPlayer().teleport(event.getPlayer().getTargetBlock(null, 100).getLocation());
//            }
//        }
//    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(event.toWeatherState());
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
