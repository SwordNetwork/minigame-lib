package me.humandavey.minigamelib.game;

import me.humandavey.minigamelib.MinigameLib;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class Game implements Listener {

    private final GameInfo info;

    public Game(GameInfo info) {
        this.info = info;

        Bukkit.getPluginManager().registerEvents(this, MinigameLib.getInstance());
    }

    public abstract void onGameStart();

    public abstract void onGameEnd();

    public abstract boolean endCondition();
}
