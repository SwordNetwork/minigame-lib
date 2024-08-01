package me.humandavey.minigamelib.game;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.game.games.WaterClutcher;
import me.humandavey.minigamelib.map.Map;
import me.humandavey.minigamelib.util.Util;
import me.humandavey.minigame.instance.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class Game implements Listener {

    private final GameInfo info;
    private final Map map;
    private final ArrayList<Player> players;
    private GameState state;
    private Countdown countdown;

    public Game(GameInfo info) {
        this.info = info;
        this.map = MinigameLib.getInstance().getMapManager().getMap(this);
        this.players = new ArrayList<>();
        this.countdown = new Countdown(this);
        this.state = GameState.INIT;

        Bukkit.getPluginManager().registerEvents(this, MinigameLib.getInstance());
        MinigameLib.getInstance().getGameManager().registerGame(this);

        this.state = GameState.WAITING;
    }

    public abstract void onGameStart();

    public abstract void onGameEnd();

    public abstract boolean endCondition();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (players.contains(player)) {
                if (state != GameState.LIVE) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (players.contains(event.getPlayer())) {
            if (state != GameState.LIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (players.contains(event.getPlayer())) {
            if (state != GameState.LIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (players.contains(event.getPlayer())) {
            if (state != GameState.LIVE) {
                if (!event.getFrom().getBlock().equals(event.getTo().getBlock())) {
                    if (event.getTo().getBlockY() < 0) {
                        event.getPlayer().teleport(map.getSpawn());
                        return;
                    }

                    if (!Util.isInBounds(event.getTo(), map.getCorner1(), map.getCorner2())) {
                        event.setCancelled(true);
                    }
                }
            } else if (!event.getFrom().getBlock().equals(event.getTo().getBlock())) {
                if (!Util.isInBounds(event.getTo(), map.getCorner1(), map.getCorner2())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public void onPlayerJoin(Player player) {
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

    public void onGameStarting() {

    }

    public void onStart() {
        onGameStart();
    }

    public void addPlayer(Player player) {
        if (isJoinable()) {
            players.add(player);
            player.teleport(map.getSpawn());

            onPlayerJoin(player);

            if (players.size() >= map.getAutostartPlayers()) {
                state = GameState.STARTING;
                countdown.start();

                onGameStarting();
            }
        }
    }

    public void broadcast(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    public void broadcastTitle(String title) {
        broadcastTitle(title, "");
    }

    public void broadcastTitle(String title, String subtitle) {
        broadcastTitle(title, subtitle, 20, 60, 20);
    }

    public void broadcastTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player player : players) {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    public boolean isJoinable() {
        return map != null && (state == GameState.WAITING || (state == GameState.STARTING && players.size() < map.getMaxPlayers()));
    }

    public GameInfo getInfo() {
        return info;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    public static Game of(GameInfo gameInfo) {
        switch (gameInfo.name()) {
            case "Water Clutcher" -> {
                return new WaterClutcher();
            }
            default -> {
                return null;
            }
        }
    }
}
