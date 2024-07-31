package me.humandavey.minigamelib.game;

import me.humandavey.minigamelib.util.Util;

import java.util.ArrayList;

public record GameInfo(String name, ArrayList<String> aliases, ArrayList<String> description, GameType type, int numTeams) {

    public static GameInfo WATER_CLUTCHER = new GameInfo(
            "Water Clutcher",
            Util.listOf("wc", "waterclutcher"),
            Util.listOf("Try not to die from fall damage", "while being the last player alive!"),
            GameType.TEAM_DEATHMATCH,
            8
    );

    public static ArrayList<GameInfo> getAllGameInfo() {
        ArrayList<GameInfo> infos =  new ArrayList<>();

        infos.add(WATER_CLUTCHER);

        return infos;
    }
}