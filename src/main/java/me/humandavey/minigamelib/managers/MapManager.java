package me.humandavey.minigamelib.managers;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.game.Game;
import me.humandavey.minigamelib.game.GameInfo;
import me.humandavey.minigamelib.map.Map;
import me.humandavey.minigamelib.map.SerializableLocation;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class MapManager {

    private final ArrayList<Map> maps = new ArrayList<>();

    public MapManager() {
        init();
    }

    public void init() {
        FileConfiguration config = MinigameLib.getInstance().getMapsConfig();

        for (String section : config.getConfigurationSection("maps").getKeys(false)) {
            Map map = Map.deserialize(new SerializableLocation(config, "maps." + section));
            maps.add(map);
            MinigameLib.getInstance().getLogger().info("Successfully created map '" + map.getName() + "'");
        }
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public boolean isMapAvailableFor(GameInfo info) {
        for (Map map : maps) {
            if (map.isAvailable()) {
                for (String supportedGame : map.getSupportedGames()) {
                    if (supportedGame.equals(info.name())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public Map getMap(Game game) {
        ArrayList<Map> availableMaps = new ArrayList<>();
        for (Map map : maps) {
            if (map.isAvailable()) {
                for (String supportedGame : map.getSupportedGames()) {
                    if (supportedGame.equals(game.getInfo().name())) {
                        availableMaps.add(map);
                        break;
                    }
                }
            }
        }
        if (availableMaps.isEmpty()) return null;

        Map map = availableMaps.get((int)(Math.random() * availableMaps.size()));
        map.setGame(game);
        return map;
    }
}
