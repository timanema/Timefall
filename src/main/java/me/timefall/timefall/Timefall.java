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
    private Object lockObject;

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
        new Timefall();
    }

    public Timefall()
    {
        System.out.println("Loading Timefall components ...\n Creating settings ...\n Enabling listeners ...");

        lockObject = new Object();
        settings = new Settings();
        keyHandler = new KeyHandler();

        System.out.println(" Initializing display ...");

        EventQueue.invokeLater(() -> mainDisplay = new Display("Timefall", settings.getScreenSize().getWidth(), settings.getScreenSize().getHeight(), lockObject));
        lock();
    }

    private void lock()
    {
        try
        {
            synchronized (lockObject)
            {
                lockObject.wait();
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        this.internalStart();
    }

    private void internalStart()
    {
        System.out.println(" Reading files and readjusting settings ...");
        fileManager = new FileManager(settings);

        // Add temp gamestate
        // TODO: Remove this
        Game game = new Game(settings);
        settings.setState(game);

        tileManager = game.tileManager;

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
