package me.litwar.battleroyale.Structures;

import java.util.ArrayList;

public class StructureLayer {

    private ArrayList<StructureBlock> blocks;
    private int count;

    public StructureLayer(ArrayList<StructureBlock> blocks, int count) {
        this.blocks = blocks;
        this.count = count;
    }

    public ArrayList<StructureBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<StructureBlock> blocks) {
        this.blocks = blocks;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
