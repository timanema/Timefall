package me.timefall.timefall.files;

import me.timefall.timefall.Settings;
import me.timefall.timefall.Timefall;
import me.timefall.timefall.level.Vector;

import java.io.FileOutputStream;

import javax.xml.stream.*;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.XMLEvent;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileManager
{
    private String optionsPath = "";
    private String savePath = "";
    private String readLine = null;
    private String mainDir = "";

    private File mainDirectory;
    private File optionFile;
    private File saveFile;
    private Settings options;

    private ArrayList<String> toClose;


    public ArrayList<String> worldFiles;

    Save save;

    public FileManager(Settings options)
    {
        this.options = options;
        worldFiles = new ArrayList<>();

        System.out.println(" Loading Nfiles ...");

        try
        {
            // Create files
            mainDir = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";

            mainDirectory = new File(mainDir);
            optionFile = new File(mainDir + "/options.txt/");
            saveFile = new File(mainDir + "/save.xml/");

            // Check if option file exists, if not create it
            if (!optionFile.exists())
            {
                System.out.println("  Creating file: " + mainDir + "/options.txt ...");

                optionFile.getParentFile().mkdirs();
                optionFile.createNewFile();

                optionsPath = exportOptionsFile("options");

                optionFile = new File(mainDir + optionsPath);

                //write defaults
                changeOption("options", "max_fps", "60");
                changeOption("options", "sound", "100");
                changeOption("options", "music", "100");
                changeOption("options", "screen_size", "2");
            }

            if (!saveFile.exists())
            {
                Save newSave = new Save("world", 0, 0, 0, 0, 0);
                writeSave(newSave);
            }

            // Start reading world files
            readWorldFiles();
        } catch (Exception exception)
        {
            exception.printStackTrace();
            return;
        }

        // Read option and level files
        readOptionsFile("options");
        System.out.println("\n");
        //TODO xml tfsave
        loadSave();

    }

    public void writeSave(Save save)
    {
        this.save = save;
        writeSave();
    }

    public void writeSave()
    {
        try {
            String outputDirectory = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";

            // create an XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            // create XMLEventWriter
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(outputDirectory + "/" + "save.xml"));
            // create an EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();

            //add types
            XMLEvent end = eventFactory.createDTD("\n");
            XMLEvent tab = eventFactory.createDTD("\t");
            //start
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);
            eventWriter.add(eventFactory.createStartElement("", "", "data"));
            eventWriter.add(end);

            toClose = new ArrayList<>();

            //for now location can be -1, one back, 0, same height or 1, one further

            crEl(eventWriter, "camera", 1);
            crEl(eventWriter, "currentWorld", 1);
            crNd(eventWriter, "name", save.getWorldName());
            crNd(eventWriter, "xOff", String.valueOf(save.getCameraXOff()));
            crNd(eventWriter, "yOff", String.valueOf(save.getCameraYOff()));
            crEl(eventWriter, "player", -1);
            crEl(eventWriter, "currentWorld", 1);
            crNd(eventWriter, "xOff", String.valueOf(save.getPlayerXOff()));
            crNd(eventWriter, "yOff", String.valueOf(save.getPlayerYOff()));
            clsEls(eventWriter, 1);
            crNd(eventWriter, "gender", String.valueOf(save.getGender()));
            clsEls(eventWriter, toClose.size());



            //end
            eventWriter.add(eventFactory.createEndElement("", "", "data"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crEl(XMLEventWriter eventWriter, String name, int location) throws XMLStreamException
    {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        if (location == 1)
        {
            for (int x = 0; x < toClose.size(); x++)
            {
                eventWriter.add(tab);
            }

            //System.out.println("start:"+ name);

            eventWriter.add(tab);
            eventWriter.add(eventFactory.createStartElement("", "", name));
            eventWriter.add(end);

            toClose.add(name);
        }

        if (location == 0)
        {
            clsEls(eventWriter, 1);

            for (int x = 0; x < toClose.size(); x++)
            {
                eventWriter.add(tab);
            }

            //System.out.println("start:"+ name);

            eventWriter.add(tab);
            eventWriter.add(eventFactory.createStartElement("", "", name));
            eventWriter.add(end);

            toClose.add(name);
        }

        if (location == -1)
        {
            clsEls(eventWriter, 2);

            for (int x = 0; x < toClose.size(); x++)
            {
                eventWriter.add(tab);
            }

            //System.out.println("start:"+ name);

            eventWriter.add(tab);
            eventWriter.add(eventFactory.createStartElement("", "", name));
            eventWriter.add(end);

            toClose.add(name);
        }
    }

    private void crNd(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException
    {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        for (int x = 0; x < toClose.size() + 1; x++)
        {
            eventWriter.add(tab);
        }
        // create Start node
        eventWriter.add(eventFactory.createStartElement("", "", name));
        // create Content
        eventWriter.add(eventFactory.createCharacters(value));
        // create End node
        eventWriter.add(eventFactory.createEndElement("", "", name));
        eventWriter.add(end);

    }

    private void clsEls(XMLEventWriter eventWriter, int amount) throws XMLStreamException
    {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        Collections.reverse(toClose);

        int indentation = toClose.size();
        //System.out.println(indentation);

        for (int i = 0; i < amount; i++)
        {
            String name = toClose.get(i);

            //System.out.println("indent:"+ indentation);
            //System.out.println("end:"+ name);
            for (int x = 0; x < indentation; x++)
            {
                eventWriter.add(tab);
            }
            eventWriter.add(eventFactory.createEndElement("", "", name));
            eventWriter.add(end);
            //System.out.println("toClose:"+ toClose);
            indentation--;
        }

        for (int i = amount - 1; i >= 0; i--)
        {
            toClose.remove(i);
        }

        if (toClose != null)
        {
            Collections.reverse(toClose);
        }
    }

    public void loadSave()
    {
        Save saveToLoad = readSave();

        Vector.setGlobalWorldName(saveToLoad.getWorldName());
        Vector.setWorldVariables(saveToLoad.getCameraXOff(), Vector.worldyPos);
        Vector.setWorldVariables(Vector.worldxPos, saveToLoad.getCameraYOff());
        Vector.setPlayerVariables(saveToLoad.getPlayerXOff(), Vector.playeryPos);
        Vector.setPlayerVariables(Vector.playerxPos, saveToLoad.getPlayerYOff());
        Timefall.getSettings().setGender(saveToLoad.getGender());
    }

    public HashMap<String, Object> getSaveMap()
    {
        String worldName = "";
        int cameraXOff = 0;
        int cameraYOff = 0;
        int playerXOff = 0;
        int playerYOff = 0;
        int gender = 0;

        try {
            String inputDirectory = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";

            // create an XMLOutputFactory
            XMLInputFactory inputFactory= XMLInputFactory.newInstance();
            // create XMLEventWriter
            XMLEventReader eventReader = inputFactory.createXMLEventReader(new FileInputStream(inputDirectory + "/" + "save.xml"));

            HashMap<String, Object> allData = new HashMap<>();
            LinkedHashMap<String, Object> elementData = new LinkedHashMap<>();
            ArrayList<String> elementList = new ArrayList<>();
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            LinkedHashMap<String, Object> newData = new LinkedHashMap<>();
            LinkedHashMap<String, Object> newAllData = new LinkedHashMap<>();
            int dataLocation = 0;
            String previousEnd = "";

            LinkedHashMap<String, Object> nestElementData = new LinkedHashMap<>();

            LinkedHashMap<String, Object> childList = new LinkedHashMap<String, Object>();



            while (eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();

                //checks if its start element and not data; gets all the element data
                /*if (event.isStartElement() && !event.asStartElement().getName().getLocalPart().equals("data")) {
                    //if it contains whitespace it doesnt have data
                    if (eventReader.peek().asCharacters().isWhiteSpace())
                    {
                        //set as elements with no data, so false
                        data.put(event.asStartElement().getName().getLocalPart(), false);
                    }
                    //does with nonwhitespace have data
                    else if (!eventReader.peek().asCharacters().isWhiteSpace())
                    {
                        //System.out.println(event.asStartElement().getName().getLocalPart());

                        //add tag name and its data to hashmap
                        elementData.put(event.asStartElement().getName().getLocalPart(), eventReader.peek().asCharacters().getData());
                        //set as element with data
                        data.put(event.asStartElement().getName().getLocalPart(), true);
                    }
                    elementList.add(event.asStartElement().getName().getLocalPart());
                }*/



                if (event.isStartElement())
                {
                    String parent = event.asStartElement().getName().getLocalPart();
                    //System.out.println(parent);

                    boolean loop = true;
                    while (loop && eventReader.hasNext())
                    {
                        event = eventReader.nextEvent();

                        if (event.isStartElement())
                        {
                            String child = event.asStartElement().getName().getLocalPart();
                            LinkedHashMap<String, Object> childChildList = new LinkedHashMap<>();
                            System.out.println(child);
                            childList.put(child, childChildList);

                            boolean childloop = true;
                            while (childloop && eventReader.hasNext())
                            {
                                event = eventReader.nextEvent();

                                if (event.isStartElement())
                                {
                                    String childChild = event.asStartElement().getName().getLocalPart();
                                    //System.out.println("childChild: " + childChild);
                                    childChildList.put(childChild, "");
                                    LinkedHashMap<String, Object> childChildChildList = new LinkedHashMap<>();
                                    boolean childChildLoop = true;
                                    while (childChildLoop && eventReader.hasNext())
                                    {
                                        event = eventReader.nextEvent();
                                        if (event.isStartElement()) {
                                            String childChildChild = event.asStartElement().getName().getLocalPart();
                                            //System.out.println("childChildChild: " + childChildChild);
                                            childChildChildList.put(childChildChild, "");
                                            //System.out.println("childChildChildList: " + childChildChildList);
                                            boolean childChildChildLoop = true;
                                            while (childChildChildLoop && eventReader.hasNext())
                                            {
                                                event = eventReader.nextEvent();

                                                if (event.isCharacters())
                                                {
                                                    if (!event.asCharacters().isWhiteSpace())
                                                    {
                                                        childChildChildList.replace(childChildChild, event.asCharacters().getData());
                                                    }
                                                }

                                                //System.out.println("childChildChildList: " + childChildChildList);

                                                if (event.isEndElement())
                                                {
                                                    if (event.asEndElement().getName().getLocalPart().equals(childChildChild))
                                                    {
                                                        childChildChildLoop = false;
                                                    }
                                                }
                                            }
                                            childChildList.replace(childChild, childChildChildList);
                                        }
                                        if (event.isCharacters())
                                        {
                                            if (!event.asCharacters().isWhiteSpace())
                                            {
                                                childChildList.replace(childChild, event.asCharacters().getData());
                                            }
                                        }

                                        if (event.isEndElement())
                                        {
                                            if (event.asEndElement().getName().getLocalPart().equals(childChild))
                                            {
                                                childChildLoop = false;
                                            }
                                        }
                                    }
                                }
                                if (event.isEndElement())
                                {
                                    if (event.asEndElement().getName().getLocalPart().equals(child))
                                    {
                                        childloop = false;
                                    }
                                }

                            }
                        }
                        if (event.isEndElement())
                        {
                            if (event.asEndElement().getName().getLocalPart().equals(parent))
                            {
                                loop = false;
                            }
                        }
                    }
                }

                /*if (elementList.size() != 0)
                {
                    if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(elementList.get(0))) {
                        allData.put(event.asEndElement().getName().getLocalPart(), currentData.clone());
                        currentData.clear();
                        for (int i = elementList.size() - 1; i > 0; i--)
                        {
                            elementList.remove(i);
                        }
                    }
                }*/

                /*if (event.isCharacters() && !previousEnd.equals(""))
                {
                    if (!(boolean) data.get(previousEnd) && eventReader.peek().isEndElement())
                    {
                        System.out.println("nondataelement: " + previousEnd);
                        System.out.println("nextnon: " + eventReader.peek().asEndElement().getName().getLocalPart());
                    }
                }*/

                //checks if endelement and not data
                /*if (event.isEndElement() && !event.asEndElement().getName().getLocalPart().equals("data"))
                {
                    previousEnd = event.asEndElement().getName().getLocalPart();

                    //checks if it's an element without data
                    if (!(boolean) data.get(event.asEndElement().getName().getLocalPart()))
                    {
                        if (nestElementData.get(0) instanceof LinkedHashMap)
                        {

                        }

                        nestElementData.put(event.asEndElement().getName().getLocalPart(), elementData.clone());
                        elementData.clear();
                    }


                    System.out.println(event.asEndElement().getName().getLocalPart());
                    System.out.println("1st: " + data.get(event.asEndElement().getName().getLocalPart()));
                    if (eventReader.peek().isCharacters())
                    {
                        XMLEvent nextElement = eventReader.nextTag();
                        if (nextElement.isEndElement())
                        {
                            System.out.println("2: " + nextElement.isEndElement());
                            System.out.println("3: " + data.get(nextElement.asEndElement().getName().getLocalPart()));
                        } else {
                            System.out.println("2: " + nextElement.isEndElement());
                        }
                    } else {
                        System.out.println("2: " + false);
                    }
                    if (!(boolean) data.get(event.asEndElement().getName().getLocalPart()) && eventReader.peek().isEndElement() && !(boolean) data.get(eventReader.peek().asEndElement().getName().getLocalPart()))
                    {
                        newAllData.put(eventReader.peek().asEndElement().getName().getLocalPart(), newData.clone());
                        //System.out.println(currentData);
                        currentData.clear();
                        newData.clear();
                    }

                    if (!(boolean) data.get(event.asEndElement().getName().getLocalPart()))
                    {
                        newData.put(event.asEndElement().getName().getLocalPart(), currentData.clone());
                        //System.out.println(currentData);
                        currentData.clear();
                    }

                      if (!(boolean) data.get(event.asEndElement().getName().getLocalPart()))
                      {
                        System.out.println("nondataelement: " + event.asEndElement().getName().getLocalPart());

                        if (eventReader.peek().isCharacters())
                        {
                            System.out.println("nextevent is characters: "+ true);
                            if (eventReader.nextTag().isEndElement())
                            {
                                System.out.println("nextag is endelement: "+ true);
                            }
                            System.out.println("nextevent: " + eventReader.peek().asEndElement().getName().getLocalPart());
                        }
                      }

                } else {
                    previousEnd = "";
                }/*



                /*if (elementList.size() != 0)
                {

                    if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(elementList.get(0))) {
                        allData.put(event.asEndElement().getName().getLocalPart(), currentData.clone());
                        currentData.clear();
                        for (int i = elementList.size() - 1; i > 0; i--)
                        {
                            elementList.remove(i);
                        }
                    }
                }*/
            }
            //System.out.println(data);
            //System.out.println(newData);
            //System.out.println(newAllData);
            //return null;
            System.out.println(childList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public Save readSave()
    {
        String worldName = "";
        int cameraXOff = 0;
        int cameraYOff = 0;
        int playerXOff = 0;
        int playerYOff = 0;
        int gender = 0;

        try {
            String inputDirectory = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";

            // create an XMLOutputFactory
            XMLInputFactory inputFactory= XMLInputFactory.newInstance();
            // create XMLEventWriter
            XMLEventReader eventReader = inputFactory.createXMLEventReader(new FileInputStream(inputDirectory + "/" + "save.xml"));

            HashMap<String, Object> currentData = new HashMap<>();

            while (eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equals("name")) {
                        event = eventReader.nextEvent();
                        currentData.put("name", event.asCharacters().getData());
                    }
                }
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equals("xOff")) {
                        event = eventReader.nextEvent();
                        currentData.put("xOff", event.asCharacters().getData());
                    }
                }
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equals("yOff")) {
                        event = eventReader.nextEvent();
                        currentData.put("yOff", event.asCharacters().getData());
                    }
                }
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equals("xOff")) {
                        event = eventReader.nextEvent();
                        currentData.put("xOff", event.asCharacters().getData());
                    }
                }
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equals("yOff")) {
                        event = eventReader.nextEvent();
                        currentData.put("yOff", event.asCharacters().getData());
                    }
                }
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equals("gender")) {
                        event = eventReader.nextEvent();
                        currentData.put("gender", event.asCharacters().getData());
                    }
                }

                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart().equals("camera")) {
                        worldName = String.valueOf(currentData.get("name"));
                        cameraXOff = Integer.parseInt(String.valueOf(currentData.get("xOff")));
                        cameraYOff = Integer.parseInt(String.valueOf(currentData.get("yOff")));
                        currentData.clear();
                    }

                    if (event.asEndElement().getName().getLocalPart().equals("player")) {
                        playerXOff = Integer.parseInt(String.valueOf(currentData.get("xOff")));
                        playerYOff = Integer.parseInt(String.valueOf(currentData.get("yOff")));
                        gender = Integer.parseInt(String.valueOf(currentData.get("gender")));
                        currentData.clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Save(worldName, cameraXOff, cameraYOff, playerXOff, playerYOff, gender);
    }

    public void changeOption(String file, String option, String value)
    {
        BufferedWriter bufferedWriter = null;
        String mainDirectory;

        // Try to open a BufferedWriter using the file paths set in the constructor
        try
        {
            mainDirectory = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";
            String path = mainDirectory + "/" + file + ".txt";

            FileWriter fileWriter = new FileWriter(new File(path));
            bufferedWriter = new BufferedWriter(fileWriter);

            if (file.equals("options"))
            {
                bufferedWriter.write("max_fps: " + (option.equals("max_fps") ? value : options.getMaxFPS()) + "\n");
                bufferedWriter.write("sound: " + (option.equals("sound") ? value : options.getSoundSetting()) + "\n");
                bufferedWriter.write("music: " + (option.equals("src/main/resources-bin/music") ? value : options.getMusicSetting()) + "\n");
                bufferedWriter.write("screen_size: " + (option.equals("screen_size") ? value : options.getScreenSize().getID()) + "\n");
            }
            bufferedWriter.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String exportOptionsFile(String file) throws Exception
    {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String mainDirectory;

        // Trying to open an InputStream using the paths set in the constructor and export them
        try
        {
            inputStream = FileManager.class.getResourceAsStream((file.equals("options") ? optionsPath : null));

            if (inputStream == null)
                throw new Exception("Cannot fetch " + file + ".txt from Jar");

            // Trying to get the path of the location of the JAR and creating the main directory and other files
            int readBytes;
            byte[] byteBuffer = new byte[4096];
            mainDirectory = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";
            outputStream = new FileOutputStream(mainDirectory + "/" + file + ".txt");

            // Write bytes to the files in the main directory from the files provided by the JAR
            while ((readBytes = inputStream.read(byteBuffer)) > 0)
            {
                outputStream.write(byteBuffer, 0, readBytes);
            }

        } catch (Exception exception)
        {
            exception.printStackTrace();
            throw exception;
        } finally
        {
            if (inputStream != null)
                inputStream.close();

            if (outputStream != null)
                outputStream.close();
        }

        return mainDirectory + "/" + file + ".txt";
    }

    private void readOptionsFile(String file)
    {
        System.out.println("  Reading from: " + mainDir + "/" + file + ".txt ...");
        String path = mainDir + "/" + file + ".txt";

        // Trying to open a BufferedReader to read the files in the main directory outside the JAR
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));

            // Read each line an process it
            while ((readLine = bufferedReader.readLine()) != null)
                processOptions(file, readLine);

        } catch (FileNotFoundException exception)
        {
            System.out.println("Unable to open file '" + mainDir + path + "'");
        } catch (IOException exception)
        {
            System.out.println("Error reading file '" + mainDir + path + "'" + "\nError details");
            exception.printStackTrace();
        }
    }

    private void processOptions(String file, String rawOption)
    {
        String[] stringValues = rawOption.split(":");

        // Check if the string can be a option string
        if (stringValues.length != 2) return;
        String option = stringValues[0].replaceAll("\\s", "");
        String value = stringValues[1].replaceAll("\\s", "");
        int intValue = 0;

        // Parse the string to an int if the option isn't the 'world' option
        if (!option.equals("world"))
        {
            try
            {
                intValue = Integer.parseInt(value);
            } catch (NumberFormatException exception)
            {
                System.out.println("Failed to parse '" + value + "' into a integer!");
                return;
            }
        }

        // Check which in file the options is and take appropriate 'action'
        if (file.equals("options")) {
            switch (option) {
                case "max_fps":
                    options.setMaxFPS(intValue);
                    break;
                case "sound":
                    options.setSoundSetting(intValue);
                    break;
                case "src/main/resources-bin/music":
                    options.setMusicSetting(intValue);
                    break;

                case "screen_size":
                    options.setScreenSize(options.getScreenSize(intValue));
                    break;
                default:
                    break;
            }
        }

        System.out.println("   Set '" + option + "' from '" + file + ".txt' to '" + value + "'");
    }

    //UNSURE about these
    public HashMap<String, HashMap<String, String>> getFloraConflicts()
    {
        HashMap<String, HashMap<String, String>> floraConflicts = new HashMap<>();
        String rawLine;

        // Loop through all the found worlds
        for (String worldName : worldFiles)
        {
            // Trying to open a BufferedReader to read the file
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/conflicts/" + worldName + "_flora.txt")));
                HashMap<String, String> conflicts = new HashMap<>();

                // Read the strings from the file and process it
                while ((rawLine = bufferedReader.readLine()) != null)
                {
                    String[] values = rawLine.split(":");

                    if (values.length != 2) continue;

                    String coords = values[0].replaceAll("\\s", "");
                    String value = values[1].replaceAll("\\s", "");

                    conflicts.put(coords, value);
                    floraConflicts.put(worldName, conflicts);
                }
            } catch (FileNotFoundException exception)
            {
                System.out.println("Unable to open file '" + optionsPath + "'");
            } catch (IOException exception)
            {
                System.out.println("Error reading file '" + optionsPath + "'" + "\nError details");
                exception.printStackTrace();
            }
        }

        return floraConflicts;
    }

    private void readWorldFiles() throws IOException
    {
        System.out.println("  Looking for world files ...");

        // Get the CodeSource of the JAR
        CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();

        // Check if the CodeSource isn't null
        if (codeSource != null)
        {
            // Get the URL and open a ZipInputStream
            URL jar = codeSource.getLocation();
            ZipInputStream zip = new ZipInputStream(jar.openStream());

            // Loop through all the zip entries (files in jar)
            while (true)
            {
                ZipEntry e = zip.getNextEntry();

                if (e == null)
                    break;

                String fileName = e.getName();

                // Check if the current file is a world map
                if (fileName.startsWith("worlds/")
                        && !fileName.equals("worlds/")
                        && fileName.contains(".png")
                        && !fileName.contains("_flora.png"))
                {
                    String worldName = fileName.replaceAll("worlds/", "").replaceAll(".png", "");

                    System.out.println("   Found world: " + worldName);
                    System.out.println("    Looking for entities ...");
                    System.out.println("    Looking for conflicts ...");

                    // Check if there are entity and conflict files for this world (shouldn't happen but who knows)
                    if (getClass().getResourceAsStream("/entities/" + worldName + ".txt") != null &&
                            getClass().getResourceAsStream("/conflicts/" + worldName + "_flora.txt") != null &&
                            getClass().getResourceAsStream("/worlds/" + worldName + "_flora.png") != null)
                        worldFiles.add(worldName);
                    else
                        System.out.println("   Failed to find entities/conflicts for world '" + worldName + "'!");
                }
            }
        } else
        {
            System.out.println("Error while loading worlds!");
            System.exit(-1);
        }

        // Check if we have loaded worlds
        if (worldFiles.isEmpty())
        {
            System.out.println("No worlds found!");
            System.exit(-1);
        }

        System.out.println(" ");
    }
}
