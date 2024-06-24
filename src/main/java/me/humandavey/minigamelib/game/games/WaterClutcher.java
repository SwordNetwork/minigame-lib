package me.humandavey.minigamelib.game.games;

import me.humandavey.minigamelib.game.Game;
import me.humandavey.minigamelib.game.GameInfo;
import me.humandavey.minigamelib.game.GameType;

import java.util.ArrayList;
import java.util.List;

public class WaterClutcher extends Game {
    public WaterClutcher() {
        super(new GameInfo(
                "Water Clutcher",
                (ArrayList<String>) List.of("Try not to die from fall damage", "while being the last player alive!"),
                GameType.TEAM_DEATHMATCH,
                8)
        );
    }

    @Override
    public void onGameStart() {

    }

    @Override
    public void onGameEnd() {

    }

    @Override
    public boolean endCondition() {
        return false;
    }
}
