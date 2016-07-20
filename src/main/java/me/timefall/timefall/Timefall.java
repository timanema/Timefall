package me.timefall.timefall;

import me.timefall.timefall.display.Display;
import me.timefall.timefall.events.KeyHandler;
import me.timefall.timefall.files.FileManager;
import me.timefall.timefall.game.game.Game;
import me.timefall.timefall.level.TileManager;
import me.timefall.timefall.threads.ThreadManager;

import java.awt.*;

public class Timefall
{
    public static final int X_RES = 640;
    public static final int Y_RES = 360;

    private static volatile Display mainDisplay;
    private static Settings settings;
    private static FileManager fileManager;
    private static KeyHandler keyHandler;
    private static ThreadManager threadManager;
    private static TileManager tileManager;

    public static void main(String args[])
    {
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

    public static void initThreads()
    {
        threadManager = new ThreadManager();
    }

    public static Display getMainDisplay()
    {
        return mainDisplay;
    }

    public static KeyHandler getKeyHandler()
    {
        return keyHandler;
    }

    public static Settings getSettings()
    {
        return settings;
    }

    public static FileManager getFileManager()
    {
        return fileManager;
    }

    public static ThreadManager getThreadManager()
    {
        return threadManager;
    }

    public static TileManager getTileManager()
    {
        return tileManager;
    }
}
