package me.litwar.battleroyale.Models;

import me.litwar.battleroyale.Configuration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Arena {

    private World world;
    private boolean allowOcean;
    private Biome biome;
    private Location spawn;
    private ArrayList<Location> platforms;
    private int worldBorderSize;
    private int chestCount;
    private int enchantmentTablesCount;

    public Arena(World world, Biome biome, int worldBorderSize, int chestCount, int enchantmentTablesCount) {
        this.world = world;
        this.biome = biome;
        this.worldBorderSize = worldBorderSize;
        this.platforms = new ArrayList<>();
        this.chestCount = chestCount;
        this.enchantmentTablesCount = enchantmentTablesCount;
        setArena(true);
        setChests();
        setEnchantmentTables();
    }

    public Arena(World world, boolean allowOcean, int worldBorderSize, int chestCount, int enchantmentTablesCount) {
        this.world = world;
        this.allowOcean = allowOcean;
        this.worldBorderSize = worldBorderSize;
        this.platforms = new ArrayList<>();
        this.chestCount = chestCount;
        this.enchantmentTablesCount = enchantmentTablesCount;
        setArena(false);
        setChests();
        setEnchantmentTables();
    }

    public World getWorld() {
        return this.world;
    }

    public boolean isAllowOcean() {
        return this.allowOcean;
    }

    public Biome getBiome() {
        return this.biome;
    }

    public Location getSpawn() {
        return this.spawn;
    }

    public int getWorldBorderSize() {
        return this.worldBorderSize;
    }

    public ArrayList<Location> getPlatforms() {
        return this.platforms;
    }

    public void shrinkArena(int finalSize, int time) {
        world.getWorldBorder().setSize(finalSize, time);
    }

    public boolean isCloseToSpawn(Location location) {
        if ((location.getX() > (this.spawn.getX() - 5) && location.getX() < (this.spawn.getX() + 5)) && (location.getZ() > (this.spawn.getZ() - 5) && location.getZ() < (this.spawn.getZ() + 5))) {
            return true;
        }
        return false;
    }

    private void setArena(boolean hasBiome) {
        if (hasBiome) {
            setArenaSpawnWithBiome();
        } else {
            setArenaSpawn();
        }
        setPlatforms();
        this.world.setSpawnLocation(this.spawn);
        world.getWorldBorder().setCenter(this.spawn);
        world.getWorldBorder().setSize(this.worldBorderSize);
        world.getWorldBorder().setWarningTime(0);
        world.getWorldBorder().setDamageBuffer(0);
    }

    private void setArenaSpawnWithBiome() {
        int spawnX;
        int spawnZ;
        boolean hasBiome;
        do {
            spawnX = getRandomCoordinate();
            spawnZ = getRandomCoordinate();
            hasBiome = hasBiome(spawnX, spawnZ);
        } while (!hasBiome);

        this.spawn = new Location(this.world, spawnX, 160, spawnZ);
    }

    private void setArenaSpawn() {
        System.out.println(this.allowOcean);
        int spawnX;
        int spawnZ;
        boolean hasOcean;
        do {
            spawnX = getRandomCoordinate();
            spawnZ = getRandomCoordinate();
            hasOcean = hasOcean(spawnX, spawnZ);
        } while (!this.allowOcean && hasOcean);

        this.spawn = new Location(this.world, spawnX, 160, spawnZ);
    }

    private boolean hasBiome(int spawnX, int spawnZ) {
        return this.world.getBlockAt(spawnX, 70, spawnZ).getBiome() == this.biome;
    }

    private boolean hasOcean(int spawnX, int spawnZ) {
        if (world.getBlockAt(spawnX, world.getHighestBlockAt(spawnX, spawnZ).getY()-1, spawnZ).isLiquid()) {
            return true;
        }
        Block block;
        boolean hasOcean = false;
        for (int x = spawnX - (Configuration.worldBorder / 2); x < spawnX + (Configuration.worldBorder / 2); x += 8) {
            for (int z = spawnZ - (Configuration.worldBorder / 2); x < spawnZ + (Configuration.worldBorder / 2); x += 8) {
                block = this.world.getBlockAt(x, 70, z);
                if (block.getBiome() == Biome.OCEAN || block.getBiome() == Biome.DEEP_OCEAN || block.getBiome() == Biome.RIVER) {
                    hasOcean = true;
                    break;
                }
            }
            if (hasOcean) {
                break;
            }
        }

        return hasOcean;
    }

    private void setPlatforms() {
        this.platforms.add(new Location(this.world, this.spawn.getX() - (this.worldBorderSize / 4.0), 160, this.spawn.getZ() - (this.worldBorderSize / 4.0)));
        this.platforms.add(new Location(this.world, this.spawn.getX() + (this.worldBorderSize / 4.0), 160, this.spawn.getZ() - (this.worldBorderSize / 4.0)));
        this.platforms.add(new Location(this.world, this.spawn.getX() - (this.worldBorderSize / 4.0), 160, this.spawn.getZ() + (this.worldBorderSize / 4.0)));
        this.platforms.add(new Location(this.world, this.spawn.getX() + (this.worldBorderSize / 4.0), 160, this.spawn.getZ() + (this.worldBorderSize / 4.0)));

        for (Location loc : this.platforms) {
            loc.getChunk().load();
            for (int x = (int) loc.getX() - 2; x < (int) loc.getX() + 2; x++) {
                for (int z = (int) loc.getZ() - 2; z < (int) loc.getZ() + 2; z++) {
                    Block block = this.world.getBlockAt(x, (int) loc.getY(), z);
                    block.setType(Material.BEDROCK);
                }
            }
        }
    }

    public void removePlatforms() {
        for (Location loc : this.platforms) {
            loc.getChunk().load();
            for (int x = (int) loc.getX() - 2; x < (int) loc.getX() + 2; x++) {
                for (int z = (int) loc.getZ() - 2; z < (int) loc.getZ() + 2; z++) {
                    Block block = this.world.getBlockAt(x, (int) loc.getY(), z);
                    block.setType(Material.AIR);
                }
            }
        }
    }

    private int getRandomCoordinate() {
        return ThreadLocalRandom.current().nextInt(-30000000, 30000000);
    }

    private void setChests() {
        int halfArenaSize = Configuration.worldBorder / 2;
        int x, z;
        Block chestBlock;
        Inventory inventory;
        boolean shouldUnload = false;
        int selectedItem;
        int blockLootRng;
        int extraLootRng;
        int simpleLootRng;
        int rareLootRng;
        int godLootRng;
        ArrayList<ItemStack> invArray;
        for (int i = 0; i < this.chestCount; i++) {
            x = ThreadLocalRandom.current().nextInt((int) spawn.getX() - halfArenaSize, (int) spawn.getX() + halfArenaSize);
            z = ThreadLocalRandom.current().nextInt((int) spawn.getZ() - halfArenaSize, (int) spawn.getZ() + halfArenaSize);
            chestBlock = this.world.getHighestBlockAt(x, z);
            if (!chestBlock.getChunk().isLoaded()) {
                chestBlock.getChunk().load();
                shouldUnload = true;
            }
            chestBlock.setType(Material.CHEST);
            inventory = ((Chest) chestBlock.getState()).getInventory();

            invArray = new ArrayList<>();
            blockLootRng = ThreadLocalRandom.current().nextInt(0, 100);
            extraLootRng = ThreadLocalRandom.current().nextInt(0, 100);
            simpleLootRng = ThreadLocalRandom.current().nextInt(0, 100);
            rareLootRng = ThreadLocalRandom.current().nextInt(0, 100);
            godLootRng = ThreadLocalRandom.current().nextInt(0, 100);
            if (blockLootRng < Configuration.lootPercentages.get(0)) {
                selectedItem = ThreadLocalRandom.current().nextInt(0, Configuration.blockLoot.length);
                invArray.addAll(Arrays.asList(Configuration.blockLoot[selectedItem]));
            }
            if (extraLootRng < Configuration.lootPercentages.get(1)) {
                selectedItem = ThreadLocalRandom.current().nextInt(0, Configuration.extraLoot.length);
                invArray.addAll(Arrays.asList(Configuration.extraLoot[selectedItem]));
            }
            if (simpleLootRng < Configuration.lootPercentages.get(2)) {
                selectedItem = ThreadLocalRandom.current().nextInt(0, Configuration.simpleLoot.length);
                if (Configuration.simpleLoot[selectedItem][0].getType().equals(Material.BOW)) {
                    invArray.add(Configuration.getRandomArrow());
                }
                invArray.addAll(Arrays.asList(Configuration.simpleLoot[selectedItem]));
            }
            if (simpleLootRng < Configuration.lootPercentages.get(3)) {
                invArray.add(Configuration.getRandomPotion());
            }
            if (rareLootRng < Configuration.lootPercentages.get(4)) {
                selectedItem = ThreadLocalRandom.current().nextInt(0, Configuration.rareLoot.length);
                invArray.addAll(Arrays.asList(Configuration.rareLoot[selectedItem]));
            }
            if (godLootRng < Configuration.lootPercentages.get(5)) {
                selectedItem = ThreadLocalRandom.current().nextInt(0, Configuration.godLoot.length);
                invArray.addAll(Arrays.asList(Configuration.godLoot[selectedItem]));
            }
            inventory.addItem(invArray.toArray(new ItemStack[0]));

            new Location(world, chestBlock.getX(), chestBlock.getY() + 1, chestBlock.getZ()).getBlock().setType(Material.GLOWSTONE);
//            chestBlock = new Location(world, chestBlock.getX(), chestBlock.getY() + 1, chestBlock.getZ()).getBlock();
//            chestBlock.setType(Material.GLOWSTONE, true);
//            chestBlock.setType(chestBlock.getType());
//            chestBlock.getState().update(true);

            if (shouldUnload) {
                chestBlock.getChunk().unload();
                shouldUnload = false;
            }
        }
    }

    private void setEnchantmentTables() {
        int halfArenaSize = Configuration.worldBorder / 2;
        int x, z;
        Block tableBlock;
        boolean shouldUnload = false;
        for (int i = 0; i < this.enchantmentTablesCount; i++) {
            x = ThreadLocalRandom.current().nextInt((int) spawn.getX() - halfArenaSize, (int) spawn.getX() + halfArenaSize);
            z = ThreadLocalRandom.current().nextInt((int) spawn.getZ() - halfArenaSize, (int) spawn.getZ() + halfArenaSize);
            tableBlock = this.world.getHighestBlockAt(x, z);
            if (!tableBlock.getChunk().isLoaded()) {
                tableBlock.getChunk().load();
                shouldUnload = true;
            }
            tableBlock.setType(Material.ENCHANTMENT_TABLE);
            if (shouldUnload) {
                tableBlock.getChunk().unload();
                shouldUnload = false;
            }
        }
    }
}