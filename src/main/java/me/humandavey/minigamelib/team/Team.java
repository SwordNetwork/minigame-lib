package me.humandavey.minigamelib.team;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

public enum Team {

    WHITE("White", ChatColor.WHITE, Material.WHITE_WOOL),
    ORANGE("Orange", ChatColor.GOLD, Material.ORANGE_WOOL),
    MAGENTA("Magenta", ChatColor.LIGHT_PURPLE, Material.MAGENTA_WOOL),
    LIGHT_BLUE("Light Blue", ChatColor.BLUE, Material.LIGHT_BLUE_BANNER),
    YELLOW("Yellow", ChatColor.YELLOW, Material.YELLOW_WOOL),
    LIME("Lime", ChatColor.GREEN, Material.LIME_WOOL),
    PINK("Pink", ChatColor.LIGHT_PURPLE, Material.PINK_WOOL),
    GRAY("Gray", ChatColor.DARK_GRAY, Material.GRAY_WOOL),
    LIGHT_GRAY("Light Gray", ChatColor.GRAY, Material.LIGHT_GRAY_WOOL),
    CYAN("Cyan", ChatColor.DARK_AQUA, Material.CYAN_WOOL),
    PURPLE("Purple", ChatColor.DARK_PURPLE, Material.PURPLE_WOOL),
    BLUE("Blue", ChatColor.DARK_BLUE, Material.BLUE_WOOL),
    BROWN("Brown", ChatColor.of("#A52A2A"), Material.BROWN_WOOL),
    GREEN("Green", ChatColor.DARK_GREEN, Material.GREEN_WOOL),
    RED("Red", ChatColor.RED, Material.RED_WOOL),
    BLACK("Black", ChatColor.BLACK, Material.BLACK_WOOL);

    private final String name;
    private final ChatColor color;
    private final Material material;

    Team(String name, ChatColor color, Material material) {
        this.name = name;
        this.color = color;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }
}
