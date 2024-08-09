package me.humandavey.minigamelib.game.games;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.game.Game;
import me.humandavey.minigamelib.game.GameInfo;
import me.humandavey.minigamelib.game.GameState;
import me.humandavey.minigamelib.util.Util;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class WaterClutcher extends Game {

    private final HashMap<Player, Double> knockback = new HashMap<>();
    private final ArrayList<Block> waters = new ArrayList<>();
    private final HashMap<Player, Integer> kills = new HashMap<>();

    public WaterClutcher() {
        super(GameInfo.WATER_CLUTCHER);
    }

    @Override
    public void onGameStart() {
        for (Player player : getAlivePlayers()) {
            player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
            player.setGameMode(GameMode.SURVIVAL);
            knockback.put(player, 1.0);
            kills.put(player, 0);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (getState() != GameState.LIVE) return;

        if (event.getEntity() instanceof Player victim && event.getDamager() instanceof Player attacker) {
            if (getPlayers().contains(victim) && getPlayers().contains(attacker)) {
                victim.setVelocity(attacker.getLocation().getDirection().setY(knockback.get(victim) - 1).normalize().multiply(knockback.get(victim)));
                knockback.replace(victim, knockback.get(victim) + 0.1);
                event.setDamage(0.0000001);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (getState() != GameState.LIVE) return;

        if (getPlayers().contains(event.getEntity())) {
            event.getDrops().clear();
            event.setDroppedExp(0);
            new BukkitRunnable() {
                @Override
                public void run() {
                    event.getEntity().spigot().respawn();
                }
            }.runTaskLater(MinigameLib.getInstance(), 1L);
            addSpectator(event.getEntity());
            event.setDeathMessage(null);
            for (Player player : getPlayers()) {
                if (event.getEntity().getKiller() == null) {
                    player.sendMessage(Util.colorize("&7" /*+ arena.getTeam(event.getEntity()).getColor()*/ + event.getEntity().getName() + " &ehas died!"));
                } else if (event.getEntity().getKiller() != null) {
                    player.sendMessage(Util.colorize("&7" /*+ arena.getTeam(event.getEntity()).getColor()*/ + event.getEntity().getName() + " &ewas killed by &7" /*+ arena.getTeam(event.getEntity().getKiller()).getColor()*/ + event.getEntity().getKiller().getName() + "&e!"));
                } else {
                    player.sendMessage(Util.colorize("&7" /*+ arena.getTeam(event.getEntity()).getColor()*/ + event.getEntity().getName() + " &ehas died!"));
                }
            }
            if (event.getEntity().getKiller() != null) {
                kills.put(event.getEntity().getKiller(), kills.getOrDefault(event.getEntity().getKiller(), 0) + 1);
            }
//            if (arena.getAliveTeams().size() == 1) {
//                end(arena.getAliveTeams().get(0));
//            } else if (arena.getAliveTeams().size() < 1) {
//                end();
//            }
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (getState() != GameState.LIVE) return;

        if (getPlayers().contains(event.getPlayer())) {
            waters.add(event.getBlockClicked().getRelative(event.getBlockFace()));
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        if (getState() != GameState.LIVE) return;

        if (getPlayers().contains(event.getPlayer())) {
            waters.remove(event.getBlock());
        }
    }

    @EventHandler
    public void onWaterFlow(BlockFromToEvent event) {
        if (getState() != GameState.LIVE) return;

        if (event.getBlock().isLiquid() && waters.contains(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        if (getState() != GameState.LIVE) return;

        if (getPlayers().contains((Player) event.getEntity())) {
            event.setCancelled(true);
        }
    }
}