package me.humandavey.minigamelib;

import me.humandavey.minigamelib.command.commands.GameCommand;
import me.humandavey.minigamelib.command.commands.JoinCommand;
import me.humandavey.minigamelib.command.commands.StartCommand;
import me.humandavey.minigamelib.listeners.JoinListener;
import me.humandavey.minigamelib.managers.GameManager;
import me.humandavey.minigamelib.managers.MapManager;
import me.humandavey.minigamelib.util.Config;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class MinigameLib extends JavaPlugin {

    private static MinigameLib instance;

    private File mapsFile;
    private FileConfiguration mapsConfig;

    private MapManager mapManager;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;

        setupConfigs();
        setupManagers();
        registerListeners();
        registerCommands();

        Config.refresh();
    }

    @Override
    public void onDisable() {

    }

    public void setupConfigs() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        mapsFile = new File(getDataFolder(), "maps.yml");
        if (!mapsFile.exists()) {
            mapsFile.getParentFile().mkdirs();
            saveResource("maps.yml", false);
        }

        mapsConfig = new YamlConfiguration();
        try {
            mapsConfig.load(mapsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void setupManagers() {
        mapManager = new MapManager();
        gameManager = new GameManager();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    public void registerCommands() {
        new JoinCommand();
        new GameCommand();
        new StartCommand();
    }

    public FileConfiguration getMapsConfig() {
        return mapsConfig;
    }

    public File getMapsFile() {
        return mapsFile;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public static MinigameLib getInstance() {
        return instance;
    }
}
