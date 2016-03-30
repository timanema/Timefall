package me.betasterren.bsgame.files;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.Settings;
import me.betasterren.bsgame.level.Vector;

import java.io.*;
import java.util.HashMap;

public class FileManager {
    private String settingsPath = "";
    private String floraConflictPath = "/floraConflicts.txt";
    private String readLine = null;
    private String mainDir = "";

    private File settingFile;
    private Settings settings;

    public FileManager(Settings settings) {
        this.settings = settings;

        try {
            mainDir = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/BS RPG";
            settingFile = new File(mainDir + "/settings.txt/");

            if (!settingFile.exists()) {
                System.out.println("Creating file: " + mainDir + "/settings.txt ...");

                settingFile.getParentFile().mkdirs();
                settingFile.createNewFile();

                settingsPath = exportSettingsFile();

                settingFile = new File(mainDir + settingsPath);

                changeSetting("xOff", 0);
            }

            settingsPath = mainDir + "/settings.txt";
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        readSettingsFile();
    }

    public void changeSetting(String setting, int value) {
        BufferedWriter bufferedWriter = null;

        try {
            FileWriter fileWriter = new FileWriter(new File(settingsPath));
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("max_fps: " + (setting.equals("max_fps") ? value : settings.getMaxFPS()) + "\n");
            bufferedWriter.write("sound: " + (setting.equals("sound") ? value : settings.getSoundSetting()) + "\n");
            bufferedWriter.write("music: " + (setting.equals("music") ? value : settings.getMusicSetting()) + "\n");
            bufferedWriter.write("screen_size: " + (setting.equals("screen_size") ? value : settings.getScreenSize().getID()) + "\n");
            bufferedWriter.write("xOff: " + (setting.equals("xOff") ? value : (BSGame.getTileManager() == null ? 0 : BSGame.getTileManager().getLevel().xOff)) + "\n");
            bufferedWriter.write("yOff: " + (setting.equals("yOff") ? value : (BSGame.getTileManager() == null ? 0 : BSGame.getTileManager().getLevel().yOff)));

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String exportSettingsFile() throws Exception {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String mainDirectory;

        try {
            inputStream = FileManager.class.getResourceAsStream(settingsPath);

            if (inputStream == null)
                throw new Exception("Cannot fetch settings.txt from Jar");

            int readBytes;
            byte[] byteBuffer = new byte[4096];
            mainDirectory = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/BS RPG";
            outputStream = new FileOutputStream(mainDirectory + "/settings.txt");

            while ((readBytes = inputStream.read(byteBuffer)) > 0) {
                outputStream.write(byteBuffer, 0, readBytes);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        } finally {
            inputStream.close();
            outputStream.close();
        }

        return mainDirectory + "/settings.txt";
    }

    private void readSettingsFile() {
        System.out.println("Reading from: " + mainDir + "/settings.txt ...");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(settingsPath)));

            while ((readLine = bufferedReader.readLine()) != null)
                processSettings(readLine);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to open file '" + mainDir + settingsPath + "'");
        } catch (IOException exception) {
            System.out.println("Error reading file '" + mainDir + settingsPath + "'" + "\nError details");
            exception.printStackTrace();
        }
    }

    private void processSettings(String rawSetting) {
        String[] stringValues = rawSetting.split(":");

        if (stringValues.length != 2) return;

        String setting = stringValues[0].replaceAll("\\s", "");
        String value = stringValues[1].replaceAll("\\s", "");
        int intValue;

        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            System.out.println("Failed to parse '" + value + "' into a integer!");
            return;
        }

        switch (setting) {
            case "max_fps":
                settings.setMaxFPS(intValue);
                break;
            case "sound":
                settings.setSoundSetting(intValue);
                break;
            case "music":
                settings.setMusicSetting(intValue);
                break;
            case "screen_size":
                settings.setScreenSize(settings.getScreenSize(intValue));
                break;
            case "xOff":
                Vector.setWorldVariables(intValue, Vector.worldyPos);
                break;
            case "yOff":
                Vector.setWorldVariables(Vector.worldxPos, intValue);
                break;
            default:
                break;
        }

        System.out.println("Set '" + setting + "' to '" + value + "'");
    }

    public HashMap<String, String> getFloraConflicts() {
        HashMap<String, String> floraConflicts = new HashMap<>();
        String rawLine;

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(floraConflictPath)));

            while ((rawLine = bufferedReader.readLine()) != null) {
                String[] values = rawLine.split(":");

                if (values.length != 2) continue;

                String coords = values[0].replaceAll("\\s", "");
                String value = values[1].replaceAll("\\s", "");

                floraConflicts.put(coords, value);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to open file '" + settingsPath + "'");
        } catch (IOException exception) {
            System.out.println("Error reading file '" + settingsPath + "'" + "\nError details");
            exception.printStackTrace();
        }

        return floraConflicts;
    }
}
