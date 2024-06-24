package me.humandavey.minigamelib.map;

import org.bukkit.configuration.file.FileConfiguration;

public class SerializableLocation {

    private final FileConfiguration file;
    private final String path;

    public SerializableLocation(FileConfiguration file, String path) {
        this.file = file;
        this.path = path;
    }

    public void write(Map map) {

    }

    public Map read() {

    }
}
