package me.humandavey.minigamelib.util;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class Util {

    public static <E> ArrayList<E> listOf(E... list) {
        return new ArrayList<>(List.of(list));
    }

    // Checks X, Y, and Z
    public static boolean isInRegion(Location source, Location bound1, Location bound2) {
        return source.getX() >= Math.min(bound1.getX(), bound2.getX()) &&
                source.getY() >= Math.min(bound1.getY(), bound2.getY()) &&
                source.getZ() >= Math.min(bound1.getZ(), bound2.getZ()) &&
                source.getX() <= Math.max(bound1.getX(), bound2.getX()) &&
                source.getY() <= Math.max(bound1.getY(), bound2.getY()) &&
                source.getZ() <= Math.max(bound1.getZ(), bound2.getZ());
    }

    // Only checks X and Z
    public static boolean isInBounds(Location source, Location bound1, Location bound2) {
        return source.getX() >= Math.min(bound1.getX(), bound2.getX()) &&
                source.getZ() >= Math.min(bound1.getZ(), bound2.getZ()) &&
                source.getX() <= Math.max(bound1.getX(), bound2.getX()) &&
                source.getZ() <= Math.max(bound1.getZ(), bound2.getZ());
    }

    public static void resetPlayer(Player player) {
        player.setHealth(20);
        player.setHealthScale(20);
        player.setFoodLevel(20);
        player.setAllowFlight(false);
        player.setExp(0);
        player.setLevel(0);
        player.setFallDistance(0);
        player.getInventory().clear();
        player.setFireTicks(0);
        player.getActivePotionEffects().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
        player.setGameMode(GameMode.ADVENTURE);
        player.resetTitle();
        player.setSaturation(.6f);
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', translateHexColorCodes(message));
    }

    private static String translateHexColorCodes(String message) {
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }
}
