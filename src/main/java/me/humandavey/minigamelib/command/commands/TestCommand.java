package me.humandavey.minigamelib.command.commands;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.command.Command;
import me.humandavey.minigamelib.game.games.WaterClutcher;
import org.bukkit.entity.Player;

public class TestCommand extends Command {

    public TestCommand() {
        super("test", null, "");
    }

    @Override
    public void execute(Player player, String[] args) {
        new WaterClutcher();
    }
}
