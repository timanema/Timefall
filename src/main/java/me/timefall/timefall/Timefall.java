package me.timefall.timefall;

import me.timefall.timefall.display.Display;
import me.timefall.timefall.events.keys.KeyHandler;
import me.timefall.timefall.events.mouse.MouseHandler;
import me.timefall.timefall.files.FileManager;
import me.timefall.timefall.game.game.Game;
import me.timefall.timefall.game.titlescreen.TitleScreen;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.handlers.ButtonHandler;
import me.timefall.timefall.level.TileManager;
import me.timefall.timefall.threads.ThreadManager;

import java.awt.*;

public class Timefall
{
    private Object lockObject;

    public static int GAME_X_RES = 320; // 640
    public static int GAME_Y_RES = 180; // 360
    public static final int MENU_X_RES = 640;
    public static final int MENU_Y_RES = 360;

    private static volatile Display mainDisplay;
    private static Settings settings;
    private static FileManager fileManager;
    private static KeyHandler keyHandler;
    private static MouseHandler mouseHandler;
    private static ThreadManager threadManager;
    private static TileManager tileManager;
    private static ButtonHandler buttonHandler;

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
        mouseHandler = new MouseHandler();

        System.out.println(" Reading files and readjusting settings ...");
        fileManager = new FileManager(settings);

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

        settings.setLightEnabled(false);
        this.internalStart();
    }

    private void internalStart()
    {
        System.out.println(" Initializing game threads ...");
        buttonHandler = new ButtonHandler();

        settings.setState(new TitleScreen(settings, new Screen(MENU_X_RES, MENU_Y_RES)));
        // Add temp gamestate
        // TODO: Remove this
        //startGame();

        // Start main thread
        System.out.println(" Initializing game threads ...");
        Timefall.initThreads();

        System.out.println("All Timefall components loaded!");
        System.out.println("\nTick updates:");
    }

    public static void startGame()
    {
        //TODO: Verander settings later nog vanuit files
        Game game = new Game(settings, new Screen(GAME_X_RES, GAME_Y_RES));
        settings.setState(game);

        tileManager = game.tileManager;
        settings.setLightEnabled(true);
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

    public static MouseHandler getMouseHandler()
    {
        return mouseHandler;
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

    public static ButtonHandler getButtonHandler()
    {
        return buttonHandler;
    }
}
