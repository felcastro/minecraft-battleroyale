package me.litwar.battleroyale.Structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class StructureBuilder {

    private World world;

    public StructureBuilder(World world) {
        this.world = world;
    }

    private void generateStructure(ArrayList<StructureLayer> layers, int x, int z) {
        Block startingBlock = this.world.getHighestBlockAt(x, z);
        startingBlock.getChunk().load();
        int currentLayer = 0;
        for (StructureLayer layer : layers) {
            for (int i = 0; i < layer.getCount(); i++) {
                for (StructureBlock block : layer.getBlocks()) {
                    this.world.getBlockAt(x + block.getxDifference(), startingBlock.getY() + currentLayer, z + block.getzDifference()).setType(block.getMaterial());
                }
                currentLayer++;
            }
        }
        startingBlock.getChunk().unload();
    }

    private void generateStructure(ArrayList<StructureLayer> layers, Location location) {
        if (!location.getChunk().isLoaded()) {
            location.getChunk().load();
        }
        int currentLayer = 0;
        for (StructureLayer layer : layers) {
            for (int i = 0; i < layer.getCount(); i++) {
                for (StructureBlock block : layer.getBlocks()) {
                    this.world.getBlockAt((int)location.getX() + block.getxDifference(), (int)location.getY() + currentLayer, (int)location.getZ() + block.getzDifference()).setType(block.getMaterial());
                }
                currentLayer++;
            }
        }
    }

    public void generateQuartzBase(Location location) {
        ArrayList<StructureLayer> layers = new ArrayList<>();
        ArrayList<StructureBlock> layer = new ArrayList<>();

        layer.add(new StructureBlock(0, 1, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(0, -1, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(1, 0, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-1, 0, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(1, 1, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(1, -1, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-1, 1, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-1, -1, Material.QUARTZ_BLOCK));
        layers.add(new StructureLayer(layer, 1));

        layer = new ArrayList<>();
        layer.add(new StructureBlock(0, 2, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(0, -2, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(2, 0, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-2, 0, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(1, 2, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-1, 2, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(1, -2, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-1, -2, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(2, 1, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(2, -1, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-2, 1, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-2, -1, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(2, 2, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(2, -2, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-2, 2, Material.QUARTZ_BLOCK));
        layer.add(new StructureBlock(-2, -2, Material.QUARTZ_BLOCK));
        layers.add(new StructureLayer(layer, 1));

        generateStructure(layers, location);
    }

    public void generateTower(int x, int z) {
        ArrayList<StructureLayer> layers = new ArrayList<>();
        ArrayList<StructureBlock> layer = new ArrayList<>();

        layer.add(new StructureBlock(0, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, -1, Material.AIR));
        layer.add(new StructureBlock(1, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 0, Material.LADDER));

        layers.add(new StructureLayer(layer, 2));

        layer = new ArrayList<>();
        layer.add(new StructureBlock(0, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(1, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 0, Material.LADDER));
        layers.add(new StructureLayer(layer, 10));

        layer = new ArrayList<>();
        layer.add(new StructureBlock(0, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(1, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 0, Material.LADDER));
        layer.add(new StructureBlock(1, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(1, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 2, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, -2, Material.COBBLESTONE));
        layer.add(new StructureBlock(2, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(-2, 0, Material.COBBLESTONE));
        layers.add(new StructureLayer(layer, 1));

        layer = new ArrayList<>();
        layer.add(new StructureBlock(0, 2, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, -2, Material.COBBLESTONE));
        layer.add(new StructureBlock(2, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(-2, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(1, 2, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, 2, Material.COBBLESTONE));
        layer.add(new StructureBlock(1, -2, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, -2, Material.COBBLESTONE));
        layer.add(new StructureBlock(2, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(2, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(-2, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(-2, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 0, Material.LADDER));
        layer.add(new StructureBlock(1, 1, Material.ENCHANTMENT_TABLE));
        layer.add(new StructureBlock(-1, 1, Material.ANVIL));
        layers.add(new StructureLayer(layer, 1));

        layer = new ArrayList<>();
        layer.add(new StructureBlock(0, 2, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, -2, Material.COBBLESTONE));
        layer.add(new StructureBlock(2, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(-2, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(1, 2, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, 2, Material.COBBLESTONE));
        layer.add(new StructureBlock(1, -2, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, -2, Material.COBBLESTONE));
        layer.add(new StructureBlock(2, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(2, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(-2, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(-2, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 0, Material.LADDER));
        layers.add(new StructureLayer(layer, 2));

        layer = new ArrayList<>();
        layer.add(new StructureBlock(1, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(1, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 2, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, -2, Material.COBBLESTONE));
        layer.add(new StructureBlock(2, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(-2, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 0, Material.LADDER));
        layers.add(new StructureLayer(layer, 1));

        layer = new ArrayList<>();
        layer.add(new StructureBlock(0, 1, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, -1, Material.COBBLESTONE));
        layer.add(new StructureBlock(1, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(-1, 0, Material.COBBLESTONE));
        layer.add(new StructureBlock(0, 0, Material.LADDER));
        layers.add(new StructureLayer(layer, 1));

        generateStructure(layers, x, z);
    }
}
