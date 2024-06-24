package me.humandavey.minigamelib.game;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.map.Map;
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
        this.map = MinigameLib.getInstance().getMapManager().createMap(info);
        this.players = new ArrayList<>();
        this.state = GameState.INIT;

        Bukkit.getPluginManager().registerEvents(this, MinigameLib.getInstance());
        MinigameLib.getInstance().getGameManager().registerGame(this);
    }

    public abstract void onGameStart();

    public abstract void onGameEnd();

    public abstract boolean endCondition();

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
}
