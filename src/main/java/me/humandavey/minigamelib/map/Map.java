package me.humandavey.minigamelib.map;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import me.humandavey.minigamelib.MinigameLib;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    public void pasteSchematic(Location offset) {
        File file = new File(MinigameLib.getInstance().getDataFolder(), schematic);
        ClipboardFormat format = ClipboardFormats.findByFile(file);

        Clipboard clipboard = null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (clipboard == null) return;

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(spawn.getWorld()), -1)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(spawn.getX() + offset.getX(), spawn.getY() + offset.getY(), spawn.getZ() + offset.getZ()))
                    .ignoreAirBlocks(false)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public void serialize() {
        new SerializableLocation(MinigameLib.getInstance().getMapsConfig(), "maps." + name).write(this);
    }

    public static Map deserialize(SerializableLocation location) {
        return location.read();
    }
}