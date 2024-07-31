package me.humandavey.minigamelib.game;

public enum GameType {
    TEAM_OBJECTIVE, // Once a team reaches an objective, the game is over
    TEAM_DEATHMATCH, // Once there is one team left, the game is over
    TIMED_OBJECTIVE // After the time runs out, the team with the most points wins
}
