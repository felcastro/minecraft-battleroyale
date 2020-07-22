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
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Arrays;

public final class BattleRoyale extends JavaPlugin implements Listener {

    private ItemStack shield = new ItemStack(Material.SHIELD, 1);
    private ArrayList<Material> axes = new ArrayList<>();
    private ArrayList<String> forbiddenNames = new ArrayList<>();

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
        axes.add(Material.WOOD_AXE);
        axes.add(Material.STONE_AXE);
        axes.add(Material.IRON_AXE);
        axes.add(Material.GOLD_AXE);
        axes.add(Material.DIAMOND_AXE);
        forbiddenNames.add("Aegis");
        forbiddenNames.add("Crown of Immortality");
        forbiddenNames.add("Achilles Chestplate");
        forbiddenNames.add("Achilles Leggings");
        forbiddenNames.add("Hermes Boots");
        forbiddenNames.add("Excalibur");
        forbiddenNames.add("Mjölnir");
        forbiddenNames.add("Doom Bow");
        forbiddenNames.add("ADMIN POTION");

        // Add default values to config.yml
        config.addDefault("attackSpeed", 30);
        config.addDefault("axeAttackSpeed", 6);
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
        lp.add(10);
        lp.add(8);
        lp.add(1);
        config.addDefault("lootPercentages", lp);
        config.options().copyDefaults(true);
        saveConfig();

        // Load config.yml values
        Configuration.attackSpeed = config.getInt("attackSpeed");
        Configuration.axeAttackSpeed = config.getInt("axeAttackSpeed");
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
        getCommand("setaxeattackspeed").setExecutor(new SetAxeAttackSpeed(this));
        getCommand("setworldborder").setExecutor(new SetWorldBorder(this));
        getCommand("setbordershrink").setExecutor(new SetWorldBorderShrink(this));
        getCommand("setlootpercentage").setExecutor(new SetLootPercentages(this));
        getCommand("setallowoceanlevel").setExecutor(new SetAllowWaterLevel(this));
        getCommand("setchestcount").setExecutor(new SetChestCount(this));
        getCommand("setallowmobs").setExecutor(new SetAllowMobs(this));
        getCommand("setworldtime").setExecutor(new SetWorldTime(this));

        getServer().dispatchCommand(Bukkit.getConsoleSender(), "newgame");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Configuration.currentMatch.getPlayers()) {
                if (axes.contains(player.getInventory().getItemInMainHand().getType())) {
                    player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Configuration.axeAttackSpeed);
                } else if (player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getBaseValue() != Configuration.attackSpeed) {
                    player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Configuration.attackSpeed);
                }
            }
        }, 0, 3);
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
        if (event.getBlock().getType().equals(Material.ENCHANTMENT_TABLE) || event.getBlock().getType().equals(Material.ANVIL)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.TNT) && !Configuration.currentMatch.getArena().isCloseToSpawn(event.getBlock().getLocation())) {
            event.getBlock().setType(Material.AIR);
            Location loc = new Location(world, event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
            Entity tnt = world.spawn(loc, TNTPrimed.class);
            world.playSound(loc, Sound.ENTITY_TNT_PRIMED, 1, 1);
            ((TNTPrimed) tnt).setFuseTicks(30);

        } else if (event.getBlock().getType().equals(Material.TNT)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Não permitido no centro da arena");
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block: event.blockList()) {
            block.getState().update(true);
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
            event.setJoinMessage(ChatColor.GREEN + event.getPlayer().getName() + " entrou na partida!");
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
            if (e != null) {
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
                for (Player player : getServer().getOnlinePlayers()) {
                    if (player.getGameMode().equals(GameMode.SPECTATOR) || player.getName().equals(event.getEntity().getPlayer().getName())) {
                        player.sendMessage(ChatColor.BLUE + e.getName() + " ficou com " + (int) event.getEntity().getPlayer().getKiller().getHealth() + " corações.");
                    }
                }
            }
            Location deathLocation = event.getEntity().getLocation();
            if (!Configuration.currentMatch.getArena().isCloseToSpawn(deathLocation)) {
                Location sideDeathLocation = new Location(world, (int) deathLocation.getX() + 1, (int) deathLocation.getY(), (int) deathLocation.getZ() - 1);
                ItemStack[] inventory = event.getEntity().getInventory().getContents();

                world.getBlockAt(deathLocation).setType(Material.CHEST);
                Chest chest = ((Chest) world.getBlockAt(deathLocation).getState());
                world.getBlockAt(sideDeathLocation).setType(Material.CHEST);
                Chest chestOnTop = ((Chest) world.getBlockAt(sideDeathLocation).getState());

                int itemsAdded = 0;
                for (ItemStack item : inventory) {
                    if (itemsAdded < 30 && item != null && !item.getType().equals(Material.AIR)) {
                        chest.getInventory().addItem(item);
                        itemsAdded++;
                    } else if (item != null && !item.getType().equals(Material.AIR)) {
                        chestOnTop.getInventory().addItem(item);
                    }
                }
                event.setKeepInventory(true);
            }
        }
        event.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);
    }

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
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Entity damaged = event.getEntity();
            if (damager.getInventory().getItemInMainHand() != null &&
                    damager.getInventory().getItemInMainHand().hasItemMeta() &&
                    damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Mjölnir")) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                    world.strikeLightning(damaged.getLocation());
                }, 10);
            }
        }
    }

    @EventHandler
    public void onPlayerEggThrow(PlayerEggThrowEvent event) {
        event.setHatching(false);
        Location location = event.getEgg().getLocation();
        for (int i = (int) location.getX() - 3; i <= location.getX() + 3; i++) {
            for (int j = (int) location.getZ() - 3; j <= location.getZ() + 3; j++) {
                Block block = world.getBlockAt(i, (int) location.getY(), j);
                block.setType(Material.FIRE);
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

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event){
        if (event.getView().getType() == InventoryType.ANVIL) {
            if (event.getRawSlot() == 2) {
                if (event.getInventory().getItem(2).hasItemMeta() &&
                        (forbiddenNames.contains(event.getInventory().getItem(2).getItemMeta().getDisplayName()))) {
                    event.setCancelled(true);
                } else if (event.getInventory().getItem(0).hasItemMeta() &&
                        forbiddenNames.contains(event.getInventory().getItem(0).getItemMeta().getDisplayName())) {
                    event.setCancelled(true);
                } else if (event.getInventory().getItem(1).hasItemMeta() &&
                        forbiddenNames.contains(event.getInventory().getItem(1).getItemMeta().getDisplayName())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(event.toWeatherState());
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
