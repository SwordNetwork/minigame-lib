package me.humandavey.minigamelib.game.games;

import me.humandavey.minigamelib.game.Game;
import me.humandavey.minigamelib.game.GameInfo;

public class WaterClutcher extends Game {

    public WaterClutcher() {
        super(GameInfo.WATER_CLUTCHER);
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
