package me.humandavey.minigamelib.map;

import me.humandavey.minigamelib.MinigameLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

public class SerializableLocation {

    private final FileConfiguration file;
    private final String path;

    public SerializableLocation(FileConfiguration file, String path) {
        this.file = file;
        this.path = path;
    }

    public void write(Map map) {
        file.set(path + ".supported-games", map.getSupportedGames());
        file.set(path + ".max-players", map.getMaxPlayers());
        file.set(path + ".min-players", map.getMinPlayers());
        file.set(path + ".autostart-players", map.getAutostartPlayers());
        file.set(path + ".locations.spawn.x", map.getSpawn().getX());
        file.set(path + ".locations.spawn.y", map.getSpawn().getY());
        file.set(path + ".locations.spawn.z", map.getSpawn().getZ());
        file.set(path + ".locations.corner1.x", map.getCorner1().getX());
        file.set(path + ".locations.corner1.z", map.getCorner1().getZ());
        file.set(path + ".locations.corner2.x", map.getCorner2().getX());
        file.set(path + ".locations.corner2.z", map.getCorner2().getZ());
        file.set(path + ".build-height", map.getBuildHeight());
        file.set(path + ".schematic", map.getSchematic());

        try {
            file.save(MinigameLib.getInstance().getMapsFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map read() {
        String name = path.substring(path.lastIndexOf("."));

        String[] supportedGames = file.getStringList(path + ".supported-games").toArray(new String[0]);
        int maxPlayers = file.getInt(path + ".max-players");
        int minPlayers = file.getInt(path + ".min-players");
        int autostartPlayers = file.getInt(path + ".autostart-players");

        double spawnX = file.getDouble(path + ".locations.spawn.x");
        double spawnY = file.getDouble(path + ".locations.spawn.y");
        double spawnZ = file.getDouble(path + ".locations.spawn.z");
        Location spawnLocation = new Location(Bukkit.getWorld("world"), spawnX, spawnY, spawnZ);

        double corner1X = file.getDouble(path + ".locations.corner1.x");
        double corner1Z = file.getDouble(path + ".locations.corner1.z");
        Location corner1 = new Location(Bukkit.getWorld("world"), corner1X, 0, corner1Z);

        double corner2X = file.getDouble(path + ".locations.corner2.x");
        double corner2Z = file.getDouble(path + ".locations.corner2.z");
        Location corner2 = new Location(Bukkit.getWorld("world"), corner2X, 0, corner2Z);

        int buildHeight = file.getInt(path + ".build-height");
        String schematic = file.getString(path + ".schematic");

        return new Map(
            name,
            supportedGames,
            maxPlayers,
            minPlayers,
            autostartPlayers,
            spawnLocation,
            corner1,
            corner2,
            buildHeight,
            schematic
        );
    }
}
