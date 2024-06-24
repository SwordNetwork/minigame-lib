package me.humandavey.minigamelib.game;

import java.util.ArrayList;

public record GameInfo(String name, ArrayList<String> description, GameType type, int numTeams) {}