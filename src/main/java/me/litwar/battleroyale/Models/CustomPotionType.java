package me.litwar.battleroyale.Models;

import org.bukkit.potion.PotionEffectType;

public class CustomPotionType {

    private PotionEffectType type;
    private int minAmplifier;
    private int maxAmplifier;
    private int minDuration;
    private int maxDuration;

    public CustomPotionType(PotionEffectType type, int minAmplifier, int maxAmplifier, int minDuration, int maxDuration) {
        this.type = type;
        this.minAmplifier = minAmplifier;
        this.maxAmplifier = maxAmplifier;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    public PotionEffectType getType() {
        return type;
    }

    public void setType(PotionEffectType type) {
        this.type = type;
    }

    public int getMinAmplifier() {
        return minAmplifier;
    }

    public void setMinAmplifier(int minAmplifier) {
        this.minAmplifier = minAmplifier;
    }

    public int getMaxAmplifier() {
        return maxAmplifier;
    }

    public void setMaxAmplifier(int maxAmplifier) {
        this.maxAmplifier = maxAmplifier;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }
}
