package me.humandavey.minigamelib.map;

import me.humandavey.minigamelib.MinigameLib;
import org.bukkit.Location;

public class Map {
    private final String name;
    private final String[] supportedGames;
    private final int maxPlayers;
    private final int minPlayers;
    private final int autostartPlayers;
    private final Location spawn;
    private final Location corner1;
    private final Location corner2;
    private final int buildHeight;
    private final String schematic;

    public Map(String name, String[] supportedGames, int maxPlayers, int minPlayers, int autostartPlayers,
               Location spawn, Location corner1, Location corner2, int buildHeight, String schematic) {
        this.name = name;
        this.supportedGames = supportedGames;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.autostartPlayers = autostartPlayers;
        this.spawn = spawn;
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.buildHeight = buildHeight;
        this.schematic = schematic;
    }

    public String getName() {
        return name;
    }

    public String[] getSupportedGames() {
        return supportedGames;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getAutostartPlayers() {
        return autostartPlayers;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getCorner1() {
        return corner1;
    }

    public Location getCorner2() {
        return corner2;
    }

    public int getBuildHeight() {
        return buildHeight;
    }

    public String getSchematic() {
        return schematic;
    }

    public void serialize() {
        new SerializableLocation(MinigameLib.getInstance().getMapsConfig(), "maps." + name).write(this);
    }

    public static Map deserialize(SerializableLocation location) {
        return location.read();
    }
}