package me.betasterren.bsgame.files;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.Settings;
import me.betasterren.bsgame.level.Vector;

import java.io.*;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileManager {
    private String settingsPath = "";
    private String lvlPath = "";
    private String readLine = null;
    private String mainDir = "";

    private File mainDirectory;
    private File settingFile;
    private File lvlFile;
    private Settings settings;


    public ArrayList<String> worldFiles;

    public FileManager(Settings settings) {
        this.settings = settings;
        worldFiles = new ArrayList<>();

        System.out.println(" Loading files ...");

        try {
            mainDir = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/BS RPG";

            mainDirectory = new File(mainDir);
            settingFile = new File(mainDir + "/settings.txt/");
            lvlFile = new File(mainDir + "/lvl.txt/");

            if (!settingFile.exists()) {
                System.out.println("  Creating file: " + mainDir + "/settings.txt ...");

                settingFile.getParentFile().mkdirs();
                settingFile.createNewFile();

                settingsPath = exportSettingsFile("settings");

                settingFile = new File(mainDir + settingsPath);

                changeSetting("settings", "xOff", 0);
            }

            if (!lvlFile.exists()) {
                System.out.println("  Creating file: " + mainDir + "/lvl.txt ...");

                lvlFile.getParentFile().mkdirs();
                lvlFile.createNewFile();

                lvlPath = exportSettingsFile("lvl");

                lvlFile = new File(mainDir + lvlPath);

                changeSetting("lvl", "xOff", 0);
            }

            lvlPath = mainDir + "/lvl.txt";
            settingsPath = mainDir + "/settings.txt";

            readWorldFiles();
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        readSettingsFile("settings");
        System.out.println("\n");
        readSettingsFile("lvl");
    }

    public void changeSetting(String file, String setting, int value) {
        BufferedWriter bufferedWriter = null;

        try {
            FileWriter fileWriter = new FileWriter(new File((file.equals("settings") ? settingsPath : lvlPath)));
            bufferedWriter = new BufferedWriter(fileWriter);

            if (file.equals("settings")) {
                bufferedWriter.write("max_fps: " + (setting.equals("max_fps") ? value : settings.getMaxFPS()) + "\n");
                bufferedWriter.write("sound: " + (setting.equals("sound") ? value : settings.getSoundSetting()) + "\n");
                bufferedWriter.write("music: " + (setting.equals("music") ? value : settings.getMusicSetting()) + "\n");
                bufferedWriter.write("screen_size: " + (setting.equals("screen_size") ? value : settings.getScreenSize().getID()) + "\n");
                bufferedWriter.write("xOff: " + (setting.equals("xOff") ? value : (BSGame.getTileManager() == null ? 0 : BSGame.getTileManager().getLevel().xOff)) + "\n");
                bufferedWriter.write("yOff: " + (setting.equals("yOff") ? value : (BSGame.getTileManager() == null ? 0 : BSGame.getTileManager().getLevel().yOff)));
            } else if (file.equals("lvl")) {
                bufferedWriter.write("world: " + (setting.equals("world") ? value : (BSGame.getTileManager() == null ? "world" : BSGame.getTileManager().getCurrentWorld().getWorldName())) + "\n");
                bufferedWriter.write("xOff: " + (setting.equals("xOff") ? value : (BSGame.getTileManager() == null ? 0 : BSGame.getTileManager().getEntityManager().getPlayer().getxOff())) + "\n");
                bufferedWriter.write("yOff: " + (setting.equals("yOff") ? value : (BSGame.getTileManager() == null ? 0 : BSGame.getTileManager().getEntityManager().getPlayer().getyOff())));
            }

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

    private String exportSettingsFile(String file) throws Exception {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String mainDirectory;

        try {
            inputStream = FileManager.class.getResourceAsStream((file.equals("settings") ? settingsPath : lvlPath));

            if (inputStream == null)
                throw new Exception("Cannot fetch " + file + ".txt from Jar");

            int readBytes;
            byte[] byteBuffer = new byte[4096];
            mainDirectory = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/BS RPG";
            outputStream = new FileOutputStream(mainDirectory + "/" + file + ".txt");

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

        return mainDirectory + "/" + file + ".txt";
    }

    private void readSettingsFile(String file) {
        System.out.println("  Reading from: " + mainDir + "/" + file + ".txt ...");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File((file.equals("settings") ? settingsPath : lvlPath))));

            while ((readLine = bufferedReader.readLine()) != null)
                processSettings(file, readLine);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to open file '" + mainDir + (file.equals("settings") ? settingsPath : lvlPath) + "'");
        } catch (IOException exception) {
            System.out.println("Error reading file '" + mainDir + (file.equals("settings") ? settingsPath : lvlPath) + "'" + "\nError details");
            exception.printStackTrace();
        }
    }

    private void processSettings(String file, String rawSetting) {
        String[] stringValues = rawSetting.split(":");

        if (stringValues.length != 2) return;
        String setting = stringValues[0].replaceAll("\\s", "");
        String value = stringValues[1].replaceAll("\\s", "");
        int intValue = 0;

        if (!setting.equals("world")) {
            try {
                intValue = Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Failed to parse '" + value + "' into a integer!");
                return;
            }
        }

        if (file.equals("settings")) {
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
        } else if (file.equals("lvl")) {
            switch (setting) {
                case "world":
                    Vector.setWorldName(value);
                    break;
                case "xOff":
                    Vector.setPlayerVariables(intValue, Vector.playeryPos);
                    break;
                case "yOff":
                    Vector.setPlayerVariables(Vector.playerxPos, intValue);
                    break;
                default:
                    break;
            }
        }

        System.out.println("   Set '" + setting + "' from '" + file + ".txt' to '" + value + "'");
    }

    public HashMap<String, HashMap<String, String>> getFloraConflicts() {
        HashMap<String, HashMap<String, String>> floraConflicts = new HashMap<>();
        String rawLine;

        for (String worldName : worldFiles) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/conflicts/" + worldName + "_flora.txt")));
                HashMap<String, String> conflicts = new HashMap<>();

                while ((rawLine = bufferedReader.readLine()) != null) {
                    String[] values = rawLine.split(":");

                    if (values.length != 2) continue;

                    String coords = values[0].replaceAll("\\s", "");
                    String value = values[1].replaceAll("\\s", "");

                    conflicts.put(coords, value);
                    floraConflicts.put(worldName, conflicts);
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to open file '" + settingsPath + "'");
            } catch (IOException exception) {
                System.out.println("Error reading file '" + settingsPath + "'" + "\nError details");
                exception.printStackTrace();
            }
        }

        return floraConflicts;
    }

    private void readWorldFiles() throws IOException {
        System.out.println("  Looking for world files ...");

        CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();

        if (codeSource != null) {
            URL jar = codeSource.getLocation();
            ZipInputStream zip = new ZipInputStream(jar.openStream());

            while (true) {
                ZipEntry e = zip.getNextEntry();

                if (e == null)
                    break;

                String fileName = e.getName();

                if (fileName.startsWith("worlds/")
                        && !fileName.equals("worlds/")
                        && fileName.contains(".png")) {
                    String worldName = fileName.replaceAll("worlds/", "").replaceAll(".png", "");

                    System.out.println("   Found world: " + worldName);
                    System.out.println("    Looking for entities ...");
                    System.out.println("    Looking for conflicts ...");

                    if (getClass().getResourceAsStream("/entities/" + worldName + ".txt") != null &&
                            getClass().getResourceAsStream("/conflicts/" + worldName + "_flora.txt") != null)
                        worldFiles.add(worldName);
                    else
                        System.out.println("   Failed to find entities/conflicts for world '" + worldName + "'!");
                }
            }
        } else {
            System.out.println("Error while loading worlds!");
            System.exit(-1);
        }

        System.out.println(" ");
    }
}
