package me.betasterren.bsgame;

import me.betasterren.bsgame.display.Display;
import me.betasterren.bsgame.events.KeyHandler;
import me.betasterren.bsgame.files.FileManager;
import me.betasterren.bsgame.game.game.Game;
import me.betasterren.bsgame.level.TileManager;
import me.betasterren.bsgame.threads.ThreadManager;

import java.awt.*;

public class Timefall {
    private static volatile Display mainDisplay;
    private static Settings settings;
    private static FileManager fileManager;
    private static KeyHandler keyHandler;
    private static ThreadManager threadManager;
    private static TileManager tileManager;

    public static void main(String args[]) {
        System.out.println("Loading Timefall components ...");
        keyHandler = new KeyHandler();
        settings = new Settings();
        fileManager = new FileManager(settings);

        // Add temp gamestate
        // TODO: Remove this
        settings.setState(new Game(settings));

        tileManager = new TileManager();

        System.out.println(" Initializing display ...");
        EventQueue.invokeLater(() -> mainDisplay = new Display("Timefall", settings.getScreenSize().getWidth(), settings.getScreenSize().getHeight()));

        System.out.println(" Initializing game threads ...");

        // Start main thread
        Timefall.initThreads();

        System.out.println("All Timefall components loaded!");
        System.out.println("\nTick updates:");
    }

    public static void initThreads() {
       threadManager = new ThreadManager();
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

    public static ThreadManager getThreadManager() {
        return threadManager;
    }

    public static TileManager getTileManager() {
        return tileManager;
    }
}
