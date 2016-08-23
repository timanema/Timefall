package me.timefall.timefall;

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
        SMALL(640, 360, false, 0, 1),
        MEDIUM(960, 540, false, 1, 1.5f),
        NORMAL(1280, 720, false, 2, 2),
        LARGE(1920, 1080, false, 3, 3),
        FULLSCREEN(1920, 1080, true, 4, 3);

        private final int width;
        private final int height;
        private final float scale;

        private final boolean fullscreen;

        private final int ID;

        private ScreenSize(int width, int height, boolean fullscreen, int ID, float scale)
        {
            this.width = width;
            this.height = height;
            this.scale  = scale;
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

        public float getScale()
        {
            return scale;
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

    public boolean isLightEnabled()
    {
        return lightEnabled;
    }

    public boolean isDynamicLightEnabled()
    {
        return dynamicLightsEnabled;
    }
}
