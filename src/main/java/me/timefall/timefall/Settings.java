package me.timefall.timefall;

import java.awt.*;
import java.util.Stack;

public class Settings
{
    private boolean lightEnabled;
    private boolean dynamicLightsEnabled;
    private int maxFPS;
    private int soundSetting;
    private int musicSetting;
    private int gender;
    private ScreenSize screenSize;

    private Stack<GameState> states;

    public enum ScreenSize
    {
        SMALL(640, 360, false, 0),
        MEDIUM(960, 540, false, 1),
        NORMAL(1280, 720, false, 2),
        LARGE(1920, 1080, false, 3),
        FULLSCREEN(1920, 1080, true, 4);

        private final int width;
        private final int height;

        private final boolean fullscreen;

        private final int ID;

        private ScreenSize(int width, int height, boolean fullscreen, int ID)
        {
            this.width = width;
            this.height = height;
            this.fullscreen = fullscreen;
            this.ID = ID;
        }

        public int getWidth()
        {
            return width;
        }

        public int getHeight()
        {
            return height;
        }

        public int getID()
        {
            return ID;
        }

        public boolean isFullscreen()
        {
            return fullscreen;
        }
    }

    public Settings()
    {
        // Setting default values just in case
        lightEnabled = true;
        dynamicLightsEnabled = true;
        maxFPS = 60;
        soundSetting = 100;
        musicSetting = 100;
        screenSize = ScreenSize.NORMAL;
        gender = 0;

        states = new Stack<>();
    }

    public void setMaxFPS(int maxFPS)
    {
        this.maxFPS = maxFPS;
    }

    public void setSoundSetting(int soundSetting)
    {
        this.soundSetting = soundSetting;
    }

    public void setMusicSetting(int musicSetting)
    {
        this.musicSetting = musicSetting;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public void setScreenSize(ScreenSize screenSize)
    {
        this.screenSize = screenSize;
    }

    public void setState(GameState gameState)
    {
        if (!states.isEmpty())
            if (states.peek() == gameState)
            {
                states.pop();
                return;
            }

        states.push(gameState);

        EventQueue.invokeLater(() -> Timefall.getMainDisplay().getGameCanvas().updateScreen(gameState.getScreen()));
    }

    public void switchState (GameState newState)
    {
        getCurrentState().saveState();

        System.out.println("SWITCHING TO: " + newState);

        Timefall.getSoundHandler().switchScreen();

        setState(newState);
    }

    public void removeState(GameState gameState)
    {
        this.setState(gameState);
    }

    public void setLightEnabled(boolean lightEnabled)
    {
        this.lightEnabled = lightEnabled;
    }

    public void setDynamicLightsEnabled(boolean dynamicLightsEnabled)
    {
        this.dynamicLightsEnabled = dynamicLightsEnabled;
    }

    public int getMaxFPS()
    {
        return maxFPS;
    }

    public int getSoundSetting()
    {
        return soundSetting;
    }

    public int getMusicSetting()
    {
        return musicSetting;
    }

    public int getGender()
    {
        return gender;
    }

    public ScreenSize getScreenSize(int ID)
    {
        for (ScreenSize screenSize : ScreenSize.values())
            if (screenSize.getID() == ID) return screenSize;
        return null;
    }

    public ScreenSize getScreenSize()
    {
        return screenSize;
    }

    public GameState getCurrentState()
    {
        return states.peek();
    }

    public float getScale()
    {
        if (Timefall.getMainDisplay() == null || Timefall.getMainDisplay().getScreen() == null)
        {
            return -1;
        }

        int screenWidth = Timefall.getMainDisplay().getScreen().width, screenHeight = Timefall.getMainDisplay().getScreen().height;
        int windowWidth = this.getScreenSize().width, windowHeight = this.getScreenSize().height;

        float xScale = windowWidth / screenWidth, yScale = windowHeight / screenHeight;

        return xScale == yScale ? xScale : 1;
    }

    public boolean isLightEnabled()
    {
        return lightEnabled;
    }

    public boolean isDynamicLightEnabled()
    {
        return dynamicLightsEnabled;
    }
}
