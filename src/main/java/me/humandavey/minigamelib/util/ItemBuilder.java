package me.humandavey.minigamelib.util;

import me.humandavey.minigamelib.MinigameLib;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ItemBuilder implements Listener {

    private final ItemStack item;
    private final ItemMeta meta;
    private Consumer<Player> rightClick;
    private Consumer<Player> leftClick;

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        meta = item.getItemMeta();

        Bukkit.getPluginManager().registerEvents(this, MinigameLib.getInstance());
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        item.setType(material);
        return this;
    }

    public ItemBuilder setItemName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        List<String> l = new ArrayList<>(Arrays.asList(lore));
        meta.setLore(l);
        return this;
    }

    public ItemBuilder setLore(String prefix, String... lore) {
        List<String> l = new ArrayList<>(Arrays.asList(lore));
        l.add(0, prefix);
        meta.setLore(l);
        return this;
    }

    public ItemBuilder onRightClick(Consumer<Player> func) {
        this.rightClick = func;
        return this;
    }

    public ItemBuilder onLeftClick(Consumer<Player> func) {
        this.leftClick = func;
        return this;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (rightClick != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (Objects.equals(event.getItem(), item)) {
                rightClick.accept(event.getPlayer());
            }
        }
        if (leftClick != null && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            if (Objects.equals(event.getItem(), item)) {
                leftClick.accept(event.getPlayer());
            }
        }
    }
}
