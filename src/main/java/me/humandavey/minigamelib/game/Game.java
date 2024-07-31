package me.humandavey.minigamelib.game;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.game.games.WaterClutcher;
import me.humandavey.minigamelib.map.Map;
import me.humandavey.minigamelib.util.Config;
import me.humandavey.minigamelib.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public abstract class Game implements Listener {

    private final GameInfo info;
    private final Map map;
    private final ArrayList<Player> players;
    private GameState state;

    public Game(GameInfo info) {
        this.info = info;
        this.map = MinigameLib.getInstance().getMapManager().getMap(this);
        this.players = new ArrayList<>();
        this.state = GameState.INIT;

        Bukkit.getPluginManager().registerEvents(this, MinigameLib.getInstance());
        MinigameLib.getInstance().getGameManager().registerGame(this);
    }

    public abstract void onGameStart();

    public abstract void onGameEnd();

    public abstract boolean endCondition();

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

    public void addPlayer(Player player) {
        if (isJoinable()) {
            players.add(player);
            player.teleport(map.getSpawn());
            player.sendMessage(Util.colorize(Config.MESSAGES_GAME_JOINED));
        }
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    public static Game of(String gameName) {
        switch (gameName) {
            case "Water Clutcher" -> {
                return new WaterClutcher();
            }
            default -> {
                return null;
            }
        }
    }
}
