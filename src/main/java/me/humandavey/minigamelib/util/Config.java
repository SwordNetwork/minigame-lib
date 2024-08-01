package me.humandavey.minigamelib.util;

import me.humandavey.minigamelib.MinigameLib;

public class Config {

    public static String MESSAGES_COMMANDS_JOIN_USAGE = "";
    public static String MESSAGES_GAME_JOINED = "";
    public static String MESSAGES_GAME_NONEAVAILABLE = "&cThere are no games currently available! Please try again later.";
    public static String MESSAGES_GAME_COUNTDOWN_MESSAGE = "&eThe game will start in %color%%time% &eseconds!";
    public static String MESSAGES_GAME_COUNTDOWN_TITLE = "%color%%time%";
    public static String MESSAGES_GAME_COUNTDOWN_SUBTITLE = "&eGet ready to fight!";
    public static int SETTINGS_GAME_COUNTDOWN_TIME = 20;

    public static void refresh() {
        MESSAGES_COMMANDS_JOIN_USAGE = MinigameLib.getInstance().getConfig().getString("messages.commands.join.usage");
        MESSAGES_GAME_JOINED = MinigameLib.getInstance().getConfig().getString("messages.game.joined");
        MESSAGES_GAME_NONEAVAILABLE = MinigameLib.getInstance().getConfig().getString("messages.game.none-available");
        MESSAGES_GAME_COUNTDOWN_MESSAGE = MinigameLib.getInstance().getConfig().getString("messages.game.countdown.message");
        MESSAGES_GAME_COUNTDOWN_TITLE = MinigameLib.getInstance().getConfig().getString("messages.game.countdown.title");
        MESSAGES_GAME_COUNTDOWN_SUBTITLE = MinigameLib.getInstance().getConfig().getString("messages.game.countdown.subtitle");
        SETTINGS_GAME_COUNTDOWN_TIME = MinigameLib.getInstance().getConfig().getInt("settings.game.countdown.time");
    }
}
