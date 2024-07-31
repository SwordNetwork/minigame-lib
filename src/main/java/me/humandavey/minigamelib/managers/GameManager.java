package me.humandavey.minigamelib.managers;

import me.humandavey.minigamelib.game.Game;
import me.humandavey.minigamelib.game.GameInfo;
import me.humandavey.minigamelib.game.GameState;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class GameManager {

    private final ArrayList<Game> games;

    public GameManager() {
        this.games = new ArrayList<>();
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public Game getGame(Player player) {
        for (Game game : games) {
            if (game.getPlayers().contains(player)) {
                return game;
            }
        }
        return null;
    }

    public ArrayList<Game> getGames(GameInfo info) {
        ArrayList<Game> filtered = new ArrayList<>();
        for (Game game : games) {
            if (game.getInfo().equals(info)) {
                filtered.add(game);
            }
        }
        return filtered;
    }

    public Game getJoinableGame(String gameName) {
        ArrayList<Game> filtered = new ArrayList<>();
        for (Game game : games) {
            if (game.getInfo().name().equals(gameName)) {
                if (game.isJoinable()) {
                    filtered.add(game);
                }
            }
        }

        if (filtered.isEmpty()) {
            return null;
        }

        Game mostPlayers = filtered.getFirst();
        for (Game game : filtered) {
            if (game.getPlayers().size() > mostPlayers.getPlayers().size()) {
                mostPlayers = game;
            }
        }
        return mostPlayers;
    }

    public void registerGame(Game game) {
        games.add(game);
    }

    public void unregisterGame(Game game) {
        games.remove(game);
    }
}
