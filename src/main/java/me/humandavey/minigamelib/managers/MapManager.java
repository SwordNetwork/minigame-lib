package me.humandavey.minigamelib.managers;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.game.GameInfo;
import me.humandavey.minigamelib.map.Map;
import me.humandavey.minigamelib.map.SerializableLocation;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class MapManager {

    private final ArrayList<Map> maps = new ArrayList<>();
    private int i;

    public MapManager() {
        init();
        i = 0;
    }

    public void init() {
        FileConfiguration config = MinigameLib.getInstance().getMapsConfig();

        for (String section : config.getConfigurationSection("maps").getKeys(false)) {
            maps.add(Map.deserialize(new SerializableLocation(config, "maps." + section)));
        }
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public Map createMap(GameInfo info) {
        ArrayList<Map> availableMaps = new ArrayList<>();
        for (Map map : maps) {
            for (String game : map.getSupportedGames()) {
                if (game.equals(info.name())) {
                    availableMaps.add(map);
                    break;
                }
            }
        }
        if (availableMaps.isEmpty()) return null;

        return availableMaps.get((int)(Math.random() * availableMaps.size()));
    }

    public Location getNextGameLocation() {
        int x = 500 * i++;
        return new Location(maps.getFirst().getSpawn().getWorld(), x, 65, 0);
    }
}
