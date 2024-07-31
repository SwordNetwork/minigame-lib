package me.humandavey.minigamelib.command.commands;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.command.Command;
import me.humandavey.minigamelib.game.Game;
import org.bukkit.entity.Player;

import java.util.Optional;

public class GameCommand extends Command {

    public GameCommand() {
        super("game", null, null);
    }

    @Override
    public void execute(Player player, String[] args) {
        Game game = MinigameLib.getInstance().getGameManager().getGame(player);

        if (game != null) {
            player.sendMessage(game.getMap().getName() + " - " + game.getState().name());

            player.sendMessage("players:");
            for (Player p : game.getPlayers()) {
                player.sendMessage("- " + p.getName());
            }
        } else {
            player.sendMessage("you are not in a game. heres a little more info...");
            player.sendMessage("amount of games alive rn:" + MinigameLib.getInstance().getGameManager().getGames().size());
        }
    }
}
