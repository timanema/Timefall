package me.timefall.timefall;

import me.timefall.timefall.display.Display;
import me.timefall.timefall.display.GamePanel;
import me.timefall.timefall.events.keys.KeyHandler;
import me.timefall.timefall.events.mouse.MouseHandler;
import me.timefall.timefall.files.FileManager;
import me.timefall.timefall.game.game.Game;
import me.timefall.timefall.game.menu.TextOverlay;
import me.timefall.timefall.game.titlescreen.TitleScreen;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.handlers.ButtonHandler;
import me.timefall.timefall.level.TileManager;
import me.timefall.timefall.sounds.SoundHandler;
import me.timefall.timefall.threads.ThreadManager;
import me.timefall.timefall.time.Time;

import java.awt.*;
import java.util.HashMap;

public class Timefall
{
    private static HashMap<String, Object> arguments;

    private Object lockObject;

    public static int GAME_X_RES = 320; // 640
    public static int GAME_Y_RES = 180; // 360
    public static final int MENU_X_RES = 1280;
    public static final int MENU_Y_RES = 720;

    private static volatile Display mainDisplay;
    private static Settings settings;
    private static FileManager fileManager;
    private static KeyHandler keyHandler;
    private static MouseHandler mouseHandler;
    private static ThreadManager threadManager;
    private static TileManager tileManager;
    private static ButtonHandler buttonHandler;
    private static Time time;
    private static SoundHandler soundHandler;

    public static GameState textOverlay;

    public static void main(String args[])
    {
        arguments =  new HashMap<>();
        arguments.put("disableSounds", false);

        for (String argument : args)
        {
            if (argument.contains("="))
            {
                arguments.put(argument.split("=")[0], Integer.parseInt(argument.split("=")[1]));
            } else {
                arguments.keySet().stream().filter(arg -> arg.equals(argument)).forEach(arg -> arguments.put(arg, true));
            }
        }

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

        if (arguments.containsKey("screenSize"))
        {
            // todo: fix this
            //settings.setScreenSize(settings.getScreenSize((Integer) arguments.get("screenSize")));
        }
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
        //TODO: Add real time from file
        System.out.println(" Initializing game threads ...");
        buttonHandler = new ButtonHandler();
        time = new Time(6, 23, 58, 58, 0);

        //TODO: Load sounds from files
        soundHandler = new SoundHandler((Boolean) arguments.get("disableSounds"));

        settings.setState(new TitleScreen(settings, new Screen(MENU_X_RES, MENU_Y_RES)));

        // Setting up text overlay
        textOverlay = new TextOverlay(settings, new Screen(1280, 720));
        EventQueue.invokeLater(() -> Timefall.getMainDisplay().getMenuCanvas().updateScreen(textOverlay.getScreen()));

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

    public static Time getTime()
    {
        return time;
    }

    public static SoundHandler getSoundHandler()
    {
        return soundHandler;
    }

    public static TextOverlay getTextOverlay()
    {
        return (TextOverlay) textOverlay;
    }
}
