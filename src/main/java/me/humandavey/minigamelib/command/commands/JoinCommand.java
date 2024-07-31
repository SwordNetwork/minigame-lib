package me.humandavey.minigamelib.command.commands;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.command.Command;
import me.humandavey.minigamelib.game.Game;
import me.humandavey.minigamelib.game.GameInfo;
import me.humandavey.minigamelib.util.Config;
import me.humandavey.minigamelib.util.Util;
import org.bukkit.entity.Player;

public class JoinCommand extends Command {

    public JoinCommand() {
        super("join", new String[]{"play"}, "");
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length > 0) {
            String gameName = args[0];

            Game game = null;
            for (GameInfo info : GameInfo.getAllGameInfo()) {
                if (info.aliases().contains(gameName)) {
                    game = MinigameLib.getInstance().getGameManager().getJoinableGame(info.name());
                    break;
                }
            }

            if (game != null) {
                game.addPlayer(player);
                player.sendMessage(Util.colorize(Config.MESSAGES_GAME_JOINED));
            } else {
                player.sendMessage(Util.colorize(Config.MESSAGES_GAME_NONEAVAILABLE));
            }
        } else {
            player.sendMessage(Util.colorize(Config.MESSAGES_COMMANDS_JOIN_USAGE));
        }
    }
}
