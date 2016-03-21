package me.betasterren.bsgame;

import me.betasterren.bsgame.display.Display;
import me.betasterren.bsgame.events.KeyHandler;
import me.betasterren.bsgame.files.FileManager;
import me.betasterren.bsgame.game.ThreadManager;

public class BSGame {
    private static Display mainDisplay;

    private static Settings settings;
    private static FileManager fileManager;
    private static KeyHandler keyHandler;
    private static ThreadManager threadManager;

    public static void main(String args[]) {
        keyHandler = new KeyHandler();
        settings = new Settings();
        fileManager = new FileManager(settings);
        mainDisplay = new Display("BS RPG", settings.getScreenSize().getWidth(), settings.getScreenSize().getHeight());

        // Start main thread
        BSGame.initThreads();
        /*EventQueue.invokeLater(() -> {
            mainDisplay = new Display("BS RPG", settings.getScreenSize().getWidth(), settings.getScreenSize().getHeight());
            if (mainDisplay == null) System.out.println("rip");
        });*/

    }

    public static void initThreads() {
       threadManager = new ThreadManager(mainDisplay.getGamePanel());
    }

    public static Display getMainDisplay() {
        return mainDisplay;
    }

    public static KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }
}
