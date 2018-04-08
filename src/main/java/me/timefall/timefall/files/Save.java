package me.timefall.timefall.files;

public class Save {

    private String worldName;
    private int cameraXOff;
    private int cameraYOff;
    private int playerXOff;
    private int playerYOff;
    private int gender;

    public Save(String worldName, int cameraXOff, int cameraYOff, int playerXOff, int playerYOff, int gender)
    {
        this.worldName = worldName;
        this.cameraXOff = cameraXOff;
        this.cameraYOff = cameraYOff;
        this.playerXOff = playerXOff;
        this.playerYOff = playerYOff;
        this.gender = gender;
    }

    public String getWorldName() {
        return worldName;
    }

    public int getCameraXOff() {
        return cameraXOff;
    }

    public int getCameraYOff() {
        return cameraYOff;
    }

    public int getPlayerXOff() {
        return playerXOff;
    }

    public int getPlayerYOff() {
        return playerYOff;
    }

    public int getGender() {
        return gender;
    }
}
