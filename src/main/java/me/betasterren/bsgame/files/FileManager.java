package me.betasterren.bsgame.files;

import me.betasterren.bsgame.Settings;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;

public class FileManager {
    private String settingsFile = "/settings.txt";
    private String floraConflicFile = "/floraConflicts.txt";
    private String readLine = null;

    private Settings settings;

    public FileManager(Settings settings) {
        this.settings = settings;

        readSettingsFile();
    }

    public void changeSetting(String setting, int value) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(getClass().getResource(settingsFile).toURI()))));

            bufferedWriter.write("max_fps: " + (setting.equals("max_fps") ? value : settings.getMaxFPS()) + "\n");
            bufferedWriter.write("sound: " + (setting.equals("sound") ? value : settings.getSoundSetting()) + "\n");
            bufferedWriter.write("music: " + (setting.equals("music") ? value : settings.getMusicSetting()) + "\n");
            bufferedWriter.write("screen_size: " + (setting.equals("screen_size") ? value : settings.getScreenSize().getID()));

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.out.println("Unable to open file '" + settingsFile + "'");
            e.printStackTrace();
        }
    }

    private void readSettingsFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(settingsFile)));

            while ((readLine = bufferedReader.readLine()) != null)
                processSettings(readLine);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to open file '" + settingsFile + "'");
        } catch (IOException exception) {
            System.out.println("Error reading file '" + settingsFile + "'" + "\nError details");
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
            default:
                break;
        }

        System.out.println("Set '" + setting + "' to '" + value + "'");
    }

    public HashMap<String, String> getFloraConflicts() {
        HashMap<String, String> floraConflicts = new HashMap<>();
        String rawLine;

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(floraConflicFile)));

            while ((rawLine = bufferedReader.readLine()) != null) {
                String[] values = rawLine.split(":");

                if (values.length != 2) continue;

                String coords = values[0].replaceAll("\\s", "");
                String value = values[1].replaceAll("\\s", "");

                floraConflicts.put(coords, value);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to open file '" + settingsFile + "'");
        } catch (IOException exception) {
            System.out.println("Error reading file '" + settingsFile + "'" + "\nError details");
            exception.printStackTrace();
        }

        return floraConflicts;
    }
}
