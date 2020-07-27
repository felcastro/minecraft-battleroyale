package me.litwar.battleroyale.Structures;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class StructureBlock {

    private int xDifference;
    private int zDifference;
    private Material material;

    public StructureBlock(int xDifference, int zDifference, Material material) {
        this.xDifference = xDifference;
        this.zDifference = zDifference;
        this.material = material;
    }

    public int getxDifference() {
        return xDifference;
    }

    public void setxDifference(int xDifference) {
        this.xDifference = xDifference;
    }

    public int getzDifference() {
        return zDifference;
    }

    public void setzDifference(int zDifference) {
        this.zDifference = zDifference;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
