package me.humandavey.minigamelib.util;

import me.humandavey.minigamelib.MinigameLib;

public class Config {

    public static String MESSAGES_COMMANDS_JOIN_USAGE = "";
    public static String MESSAGES_GAME_JOINED = "";
    public static String MESSAGES_GAME_NONEAVAILABLE = "";

    public static void refresh() {
        MESSAGES_COMMANDS_JOIN_USAGE = MinigameLib.getInstance().getConfig().getString("messages.commands.join.usage");
        MESSAGES_GAME_JOINED = MinigameLib.getInstance().getConfig().getString("messages.game.joined");
        MESSAGES_GAME_NONEAVAILABLE = MinigameLib.getInstance().getConfig().getString("messages.game.none-available");
    }
}
