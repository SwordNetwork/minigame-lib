package me.humandavey.minigamelib.instance;

import me.humandavey.minigamelib.MinigameLib;
import me.humandavey.minigamelib.game.Game;
import me.humandavey.minigamelib.game.GameState;
import me.humandavey.minigamelib.util.Config;
import me.humandavey.minigamelib.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Countdown extends BukkitRunnable {

    private final Game game;
    private int countdownSeconds;
    private boolean cancel;

    public Countdown(Game game) {
        this.game = game;
        this.countdownSeconds = Config.SETTINGS_GAME_COUNTDOWN_TIME;
    }

    public void start() {
        game.setState(GameState.STARTING);
        runTaskTimer(MinigameLib.getInstance(), 0, 20);
    }

    public void setCountdownSeconds(int countdownSeconds) {
        this.countdownSeconds = countdownSeconds;
    }

    public ChatColor getNumberColor(int num) {
        if (num <= 5) {
            return ChatColor.RED;
        } else if (num <= 10) {
            return ChatColor.GOLD;
        }

        return ChatColor.GREEN;
    }


    @Override
    public void run() {
        if (countdownSeconds == 0 || cancel) {
            game.broadcastTitle(Util.colorize("&aGO!"), "", 0, 60, 20);

            ArrayList<String> startingMessage = getStartingMessage();

            for (String m : startingMessage) {
                game.broadcast(Util.colorize(m));
            }

            cancel();
            game.onStart();
            return;
        }

        if (countdownSeconds <= 5 || countdownSeconds % 5 == 0) {
            if (countdownSeconds == 10) {
                game.broadcast(Util.colorize(Config.MESSAGES_GAME_COUNTDOWN_MESSAGE
                        .replaceAll("%color%", getNumberColor(countdownSeconds) + "")
                        .replaceAll("%time%", countdownSeconds + "")));
            } else if (countdownSeconds <= 5) {
                game.broadcastTitle(Util.colorize(
                        Config.MESSAGES_GAME_COUNTDOWN_TITLE
                                .replaceAll("%color%", getNumberColor(countdownSeconds) + "")
                                .replaceAll("%time%", countdownSeconds + "")),
                        Util.colorize(Config.MESSAGES_GAME_COUNTDOWN_SUBTITLE), 0, 21, 0);

                game.broadcast(Util.colorize(Config.MESSAGES_GAME_COUNTDOWN_MESSAGE
                        .replaceAll("%color%", getNumberColor(countdownSeconds) + "")
                        .replaceAll("%time%", countdownSeconds + "")));
            } else {
                game.broadcast(Util.colorize(Config.MESSAGES_GAME_COUNTDOWN_MESSAGE
                        .replaceAll("%color%", getNumberColor(countdownSeconds) + "")
                        .replaceAll("%time%", countdownSeconds + "")));
            }
        }

        countdownSeconds--;
    }

    private ArrayList<String> getStartingMessage() {
        ArrayList<String> startingMessage = new ArrayList<>(Config.MESSAGES_GAME_STARTINGMESSAGE);
        startingMessage.replaceAll(message -> Util.colorize(message).replaceAll("%game%", game.getInfo().name()));

        for (int j = 0; j < startingMessage.size(); j++) {
            if (startingMessage.get(j).contains("%description%")) {
                if (!game.getInfo().description().isEmpty()) {
                    startingMessage.set(j, startingMessage.get(j).replaceAll("%description%", game.getInfo().description().getFirst()));
                }
                if (game.getInfo().description().size() > 1) {
                    startingMessage.addAll(j + 1, game.getInfo().description().subList(1, game.getInfo().description().size()));
                }
            }
        }
        return startingMessage;
    }
}
