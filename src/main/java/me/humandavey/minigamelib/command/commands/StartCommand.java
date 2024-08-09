package me.humandavey.minigamelib.command.commands;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.command.Command;
import me.humandavey.minigamelib.game.Game;
import org.bukkit.entity.Player;

public class StartCommand extends Command {

    public StartCommand() {
        super("start", null, null);
    }

    @Override
    public void execute(Player player, String[] args) {
        Game game = MinigameLib.getInstance().getGameManager().getGame(player);

        if (game != null) {
            player.sendMessage("attempting to start");

            if (game.attemptStart()) {
                player.sendMessage("starting!");
            } else {
                player.sendMessage("failed.");
            }
        }
    }
}
