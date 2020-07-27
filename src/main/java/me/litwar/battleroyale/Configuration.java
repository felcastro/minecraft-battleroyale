package me.litwar.battleroyale;

import me.litwar.battleroyale.Models.CustomEnchantmentType;
import me.litwar.battleroyale.Models.CustomPotionType;
import me.litwar.battleroyale.Models.Match;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.TippedArrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Configuration {

    public static Scoreboard scoreboard;

    // Configuration file data
    public static int attackSpeed;
    public static int axeAttackSpeed;
    public static int worldBorder;
    public static int worldBorderShrinkTime;
    public static int worldTime;
    public static int chestCount;
    public static int enchantmentTablesCount;
    public static int structureCount;
    public static boolean allowOceanLevels;
    public static boolean allowMobs;
    public static List<Integer> lootPercentages;

    // Players status data
    public static boolean damageEnabled = false;
    public static boolean movementEnabled = false;

    // Match configuration
    public static Match currentMatch;

    // Loot configuration
    static private PotionEffect[] arrowTypes = new PotionEffect[] {
            new PotionEffect(PotionEffectType.POISON, 20*5, 0),
            new PotionEffect(PotionEffectType.LEVITATION, 20*3, 0),
            new PotionEffect(PotionEffectType.SLOW, 20*3, 1),
            new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 0),
            new PotionEffect(PotionEffectType.WEAKNESS, 20*5, 0)
    };

    public static ItemStack[][] blockLoot = new ItemStack[][] {
            {new ItemStack(Material.BRICK, 16)},
            {new ItemStack(Material.CONCRETE, 16)},
            {new ItemStack(Material.PACKED_ICE, 16)},
            {new ItemStack(Material.REDSTONE_BLOCK, 16)},
            {new ItemStack(Material.WOOD, 16)},
            {new ItemStack(Material.SEA_LANTERN, 16)},
            {new ItemStack(Material.RED_NETHER_BRICK, 16)},
            {new ItemStack(Material.PURPUR_BLOCK, 16)},
            {new ItemStack(Material.GLOWSTONE, 16)},
            {new ItemStack(Material.STAINED_GLASS, 16)},
    };
    public static ItemStack[][] extraLoot = new ItemStack[][] {
            {new ItemStack(Material.ENDER_PEARL, 1)},
            {new ItemStack(Material.GOLD_PICKAXE)},
            {new ItemStack(Material.FLINT_AND_STEEL, 1)},
            {new ItemStack(Material.INK_SACK, 2, (short) 4)},
            {new ItemStack(Material.EXP_BOTTLE, 5)},
            {new ItemStack(Material.IRON_INGOT, 2)},
            {new ItemStack(Material.GOLD_INGOT, 2)},
            {new ItemStack(Material.MILK_BUCKET)},
            {new ItemStack(Material.QUARTZ_BLOCK, 1)},
    };
    public static ItemStack[][] simpleLoot = new ItemStack[][] {
            {new ItemStack(Material.IRON_SWORD)},
            {new ItemStack(Material.IRON_AXE)},
            {new ItemStack(Material.IRON_HELMET)},
            {new ItemStack(Material.IRON_CHESTPLATE)},
            {new ItemStack(Material.IRON_LEGGINGS)},
            {new ItemStack(Material.IRON_BOOTS)},
            {new ItemStack(Material.LEATHER_HELMET)},
            {new ItemStack(Material.LEATHER_CHESTPLATE)},
            {new ItemStack(Material.LEATHER_LEGGINGS)},
            {new ItemStack(Material.LEATHER_BOOTS)},
            {new ItemStack(Material.STONE_SWORD)},
            {new ItemStack(Material.STONE_AXE)},
            {new ItemStack(Material.CHAINMAIL_HELMET)},
            {new ItemStack(Material.CHAINMAIL_CHESTPLATE)},
            {new ItemStack(Material.CHAINMAIL_LEGGINGS)},
            {new ItemStack(Material.CHAINMAIL_BOOTS)},
            {new ItemStack(Material.DIAMOND, 1)},
            {new ItemStack(Material.BOW)},
            {new ItemStack(Material.TNT, 1)},
            {new ItemStack(Material.EGG, 2)},
    };
    public static ItemStack[][] rareLoot = new ItemStack[][] {
            {new ItemStack(Material.DIAMOND_HELMET)},
            {new ItemStack(Material.DIAMOND_CHESTPLATE)},
            {new ItemStack(Material.DIAMOND_LEGGINGS)},
            {new ItemStack(Material.DIAMOND_BOOTS)},
            {new ItemStack(Material.DIAMOND_SWORD)},
            {new ItemStack(Material.DIAMOND_AXE)},
            {new ItemStack(Material.DIAMOND, 3)},
            {new ItemStack(Material.GOLDEN_APPLE, 1)},
    };

    static private CustomEnchantmentType[] customEnchantmentTypes = new CustomEnchantmentType[] {
            new CustomEnchantmentType(Enchantment.ARROW_DAMAGE, 1, 1),
            new CustomEnchantmentType(Enchantment.ARROW_FIRE, 1, 1),
            new CustomEnchantmentType(Enchantment.ARROW_INFINITE, 1, 1),
            new CustomEnchantmentType(Enchantment.ARROW_KNOCKBACK, 1, 2),
            new CustomEnchantmentType(Enchantment.DAMAGE_ALL, 1, 1),
            new CustomEnchantmentType(Enchantment.FIRE_ASPECT, 1, 1),
            new CustomEnchantmentType(Enchantment.KNOCKBACK, 1, 2),
            new CustomEnchantmentType(Enchantment.SWEEPING_EDGE, 1, 2),

            new CustomEnchantmentType(Enchantment.PROTECTION_ENVIRONMENTAL, 1, 2),
            new CustomEnchantmentType(Enchantment.PROTECTION_EXPLOSIONS, 1, 2),
            new CustomEnchantmentType(Enchantment.PROTECTION_PROJECTILE, 1, 2),
            new CustomEnchantmentType(Enchantment.PROTECTION_FIRE, 1, 2),
            new CustomEnchantmentType(Enchantment.PROTECTION_FALL, 1, 2),
            new CustomEnchantmentType(Enchantment.THORNS, 1, 2),
            new CustomEnchantmentType(Enchantment.OXYGEN, 1, 4),
            new CustomEnchantmentType(Enchantment.FROST_WALKER, 1, 1),
            new CustomEnchantmentType(Enchantment.DEPTH_STRIDER, 1, 4),

            new CustomEnchantmentType(Enchantment.VANISHING_CURSE, 1, 1),
            new CustomEnchantmentType(Enchantment.BINDING_CURSE, 1, 1),
    };

    static private CustomPotionType[] positivePotionTypes = new CustomPotionType[] {
            new CustomPotionType(PotionEffectType.ABSORPTION, 0, 1, 20 * 7, 20 * 15),
            new CustomPotionType(PotionEffectType.DAMAGE_RESISTANCE,0, 1, 20 * 7, 20 * 15),
            new CustomPotionType(PotionEffectType.INCREASE_DAMAGE,0, 0, 20 * 7, 20 * 15),
            new CustomPotionType(PotionEffectType.JUMP,0, 2, 20 * 7, 20 * 15),
            new CustomPotionType(PotionEffectType.REGENERATION,0, 1, 20 * 7, 20 * 15),
            new CustomPotionType(PotionEffectType.SPEED,0, 2, 20 * 7, 20 * 15),
    };

    static private CustomPotionType[] negativePotionTypes = new CustomPotionType[] {
            new CustomPotionType(PotionEffectType.BLINDNESS,0, 0, 20 * 7, 20 * 10),
            new CustomPotionType(PotionEffectType.LEVITATION,0, 1, 20 * 7, 20 * 15),
            new CustomPotionType(PotionEffectType.POISON,0, 1, 20 * 5, 20 * 10),
            new CustomPotionType(PotionEffectType.HARM,0, 0, 20 * 1, 20 * 1),
            new CustomPotionType(PotionEffectType.SLOW,0, 2, 20 * 7, 20 * 15),
            new CustomPotionType(PotionEffectType.WEAKNESS,0, 0, 20 * 7, 20 * 15),
            new CustomPotionType(PotionEffectType.WITHER,0, 0, 20 * 5, 20 * 10),
    };

    public static ItemStack[][] godLoot = new ItemStack[][] {
            {new ItemStack(Material.TOTEM)},
            {getEnchantedItem(Material.SHIELD, new Enchantment[] {Enchantment.DURABILITY, Enchantment.MENDING}, new int[] {10, 1}, "Aegis")},
            {getGodPotion()},
            {getEnchantedItem(Material.DIAMOND_HELMET, new Enchantment[] {Enchantment.OXYGEN}, new int[] {3}, "Crown of Immortality")},
            {getEnchantedItem(Material.DIAMOND_CHESTPLATE, new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_EXPLOSIONS}, new int[] {4, 10}, "Achilles Chestplate")},
            {getEnchantedItem(Material.DIAMOND_LEGGINGS, new Enchantment[] {Enchantment.PROTECTION_PROJECTILE, Enchantment.PROTECTION_FIRE}, new int[] {10, 10}, "Achilles Leggings")},
            {getEnchantedItem(Material.DIAMOND_BOOTS, new Enchantment[] {Enchantment.PROTECTION_FALL, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DEPTH_STRIDER, Enchantment.FROST_WALKER}, new int[] {10, 4, 10, 1}, "Hermes Boots")},
            {getEnchantedItem(Material.DIAMOND_SWORD, new Enchantment[] {Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT, Enchantment.SWEEPING_EDGE}, new int[] {2, 2, 2}, "Excalibur")},
            {getEnchantedItem(Material.DIAMOND_AXE, new Enchantment[] {Enchantment.KNOCKBACK}, new int[] {4}, "Mjölnir")},
            {getEnchantedItem(Material.BOW, new Enchantment[] {Enchantment.ARROW_DAMAGE, Enchantment.ARROW_KNOCKBACK}, new int[] {5, 2}, "Doom Bow"), new ItemStack(Material.SPECTRAL_ARROW, 7)},
            {getEnchantedItem(Material.BOW, new Enchantment[] {Enchantment.DURABILITY}, new int[] {3}, "BarraTP"), new ItemStack(Material.ARROW, 3)},
            {getEnchantedItem(Material.DIAMOND_HOE, new Enchantment[] {Enchantment.DAMAGE_ALL}, new int[] {5}, "Death's Scythe")},
    };

    static private ItemStack getGodPotion() {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta)potion.getItemMeta();
        meta.setDisplayName("ADMIN POTION");
        meta.setColor(Color.AQUA);

        for (CustomPotionType positivePotionType : positivePotionTypes) {
            meta.addCustomEffect(new PotionEffect(
                    positivePotionType.getType(),
                    positivePotionType.getMaxDuration(),
                    positivePotionType.getMaxAmplifier()
            ), true);
        }
        potion.setItemMeta((ItemMeta)meta);
        return potion;
    }

    static private ItemStack getEnchantedItem(Material item, Enchantment[] enchantments, int[] levels, String name) {
        ItemStack enchantedItem = new ItemStack(item);
        ItemMeta stackMeta = enchantedItem.getItemMeta();
        stackMeta.setDisplayName(name);
        for (int i = 0; i < enchantments.length; i++) {
            stackMeta.addEnchant(enchantments[i], levels[i], true);
        }
        enchantedItem.setItemMeta(stackMeta);
        return enchantedItem;
    }

    static public ItemStack getRandomBook() {
        int enchantmentCount = ThreadLocalRandom.current().nextInt(1, 3);
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();

        int level;
        CustomEnchantmentType enchantment;
        for (int i = 0; i < enchantmentCount; i++) {
            enchantment = customEnchantmentTypes[ThreadLocalRandom.current().nextInt(0, customEnchantmentTypes.length)];
            level = (enchantment.getMinLevel() >= enchantment.getMaxLevel()) ? enchantment.getMinLevel() : ThreadLocalRandom.current().nextInt(enchantment.getMinLevel(), enchantment.getMaxLevel());
            meta.addStoredEnchant(enchantment.getEnchantment(), level, true);
        }
        book.setItemMeta(meta);

        return book;
    }

    public static ItemStack getRandomPotion() {
        boolean isPositive = ThreadLocalRandom.current().nextBoolean();
        int effectCount = ThreadLocalRandom.current().nextInt(1, 4);
        ItemStack potion = isPositive ? new ItemStack(Material.POTION) : new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta)potion.getItemMeta();
        meta.setDisplayName(isPositive ? "POSITIVO" : "NEGATIVO");
        meta.setColor(isPositive ? Color.RED : Color.GREEN);

        int duration;
        int amplifier;
        CustomPotionType effect;
        for (int i = 0; i < effectCount; i++) {
            effect = isPositive ?
                    positivePotionTypes[ThreadLocalRandom.current().nextInt(0, positivePotionTypes.length)] :
                    negativePotionTypes[ThreadLocalRandom.current().nextInt(0, negativePotionTypes.length)];

            duration = (effect.getMinDuration() >= effect.getMaxDuration()) ? effect.getMinDuration() : ThreadLocalRandom.current().nextInt(effect.getMinDuration(), effect.getMaxDuration() + 1);
            amplifier = (effect.getMinAmplifier() >= effect.getMaxAmplifier()) ? effect.getMinAmplifier() : ThreadLocalRandom.current().nextInt(effect.getMinAmplifier(), effect.getMaxAmplifier() + 1);
            meta.addCustomEffect(new PotionEffect(
                    effect.getType(),
                    duration,
                    amplifier
            ), true);
        }

        potion.setItemMeta((ItemMeta)meta);
        return potion;
    }

    public static ItemStack getRandomArrow() {
        int randomType = ThreadLocalRandom.current().nextInt(0, arrowTypes.length + 1);
        if (randomType >= arrowTypes.length) {
            return new ItemStack(Material.ARROW, 10);
        } else {
            ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, 4);
            PotionMeta meta = (PotionMeta) arrow.getItemMeta();
            meta.addCustomEffect(arrowTypes[randomType],  true);
            meta.setDisplayName("Special Arrow");
            meta.setColor(Color.GREEN);
            arrow.setItemMeta(meta);
            return arrow;
        }
    }
}
