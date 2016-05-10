package me.betasterren.bsgame;

import java.util.Stack;

public class Settings {
    private int maxFPS;
    private int soundSetting;
    private int musicSetting;
    private int gender;
    private ScreenSize screenSize;

    private Stack<GameState> states;

    public enum ScreenSize {
        SMALL(640, 360, false, 0),
        MEDIUM(960, 540, false, 1),
        NORMAL(1280, 720, false, 2),
        LARGE(1920, 1080, false, 3),
        FULLSCREEN(1920, 1080, true, 4);

        private final int width;
        private final int height;

        private final boolean fullscreen;

        private final int ID;

        private ScreenSize(int width, int height, boolean fullscreen, int ID) {
            this.width = width;
            this.height = height;
            this.fullscreen = fullscreen;
            this.ID = ID;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getID() {
            return ID;
        }

        public boolean isFullscreen() {
            return fullscreen;
        }
    }

    public Settings() {
        // Setting default values just in case
        maxFPS = 60;
        soundSetting = 100;
        musicSetting = 100;
        screenSize = ScreenSize.NORMAL;
        gender = 1;

        states = new Stack<>();
    }

    public void setMaxFPS(int maxFPS) {
        this.maxFPS = maxFPS;
    }

    public void setSoundSetting(int soundSetting) {
        this.soundSetting = soundSetting;
    }

    public void setMusicSetting(int musicSetting) {
        this.musicSetting = musicSetting;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    public void setState(GameState gameState) {
        if (!states.isEmpty())
            if (states.peek() == gameState) {
                states.pop();
                return;
            }

        states.push(gameState);
    }

    public void removeState(GameState gameState) {
        this.setState(gameState);
    }

    public int getMaxFPS() {
        return maxFPS;
    }

    public int getSoundSetting() {
        return soundSetting;
    }

    public int getMusicSetting() {
        return musicSetting;
    }

    public int getGender() {
        return gender;
    }

    public ScreenSize getScreenSize(int ID) {
        for (ScreenSize screenSize : ScreenSize.values())
            if (screenSize.getID() == ID) return screenSize;
        return null;
    }

    public ScreenSize getScreenSize() {
        return screenSize;
    }

    public GameState getCurrentState() {
        return states.peek();
    }
}
