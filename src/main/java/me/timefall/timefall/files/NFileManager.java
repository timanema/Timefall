package me.timefall.timefall.files;

import me.timefall.timefall.Settings;
import me.timefall.timefall.Timefall;
import me.timefall.timefall.level.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;

import javax.xml.stream.*;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class NFileManager
{
    private String optionsPath = "";
    private String tfsavePath = "";
    private String readLine = null;
    private String mainDir = "";

    private File mainDirectory;
    private File optionFile;
    private File tfsaveFile;
    private Settings options;

    private ArrayList<String> toClose;


    public ArrayList<String> worldFiles;

    Save save;

    public NFileManager(/*Settings options*/)
    {
        /*this.options = options;
        worldFiles = new ArrayList<>();

        System.out.println(" Loading Nfiles ...");

        try
        {
            // Create files
            mainDir = new File(NFileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";

            mainDirectory = new File(mainDir);
            optionFile = new File(mainDir + "/options.txt/");
            tfsaveFile = new File(mainDir + "/tfsave.xml/");

            // Check if option file exists, if not create it
            if (!optionFile.exists())
            {
                System.out.println("  Creating file: " + mainDir + "/options.txt ...");

                optionFile.getParentFile().mkdirs();
                optionFile.createNewFile();

                optionsPath = exportOptionsFile("options");

                optionFile = new File(mainDir + optionsPath);

                //write defaults
            }

            // Check if level file exists, if not create it
            if (!tfsaveFile.exists())
            {
                System.out.println("  Creating file: " + mainDir + "/tfsave.xml ...");


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
        //readOptionsFile("tfsave");
        */
    }

    public void writeSave(Save save)
    {
        this.save = save;
        writeSave();
    }

    public void writeSave()
    {
        try {
            String outputDirectory = new File(NFileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";

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


            /*
            //open camera
            eventWriter.add(eventFactory.createStartElement("", "", "camera"));
            eventWriter.add(end);

            // Write the different nodes
            //createNode(eventWriter, "currentWorld", "1");
            //open currentWorld
            eventWriter.add(tab);
            eventWriter.add(eventFactory.createStartElement("", "", "currentWorld"));
            eventWriter.add(end);

            eventWriter.add(tab);
            createNode(eventWriter, "xOff", "0");
            eventWriter.add(tab);
            createNode(eventWriter, "yOff", "0");

            //end currentWorld
            eventWriter.add(tab);
            eventWriter.add(eventFactory.createEndElement("", "", "currentWorld"));
            eventWriter.add(end);

            //end camera
            eventWriter.add(eventFactory.createEndElement("", "", "camera"));
            eventWriter.add(end);

            //open player
            eventWriter.add(eventFactory.createStartElement("", "", "player"));
            eventWriter.add(end);

            //open player
            eventWriter.add(tab);
            eventWriter.add(eventFactory.createStartElement("", "", "currentWorld"));
            eventWriter.add(end);

            eventWriter.add(tab);
            createNode(eventWriter, "xOff", "0");
            eventWriter.add(tab);
            createNode(eventWriter, "yOff", "0");

            //end currentWorld
            eventWriter.add(tab);
            eventWriter.add(eventFactory.createEndElement("", "", "currentWorld"));
            eventWriter.add(end);

            createNode(eventWriter, "gender", "0");

            //end player
            eventWriter.add(eventFactory.createEndElement("", "", "player"));
            eventWriter.add(end);
            */

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

    private void crNds (XMLEventWriter eventWriter, HashMap<String, Object> nodeMap,
                        int indentation) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();

        for ( String name : nodeMap.keySet() ) {

            XMLEvent end = eventFactory.createDTD("\n");
            XMLEvent tab = eventFactory.createDTD("\t");
            for (int x = 0; x < indentation; x++)
            {
                eventWriter.add(tab);
            }
            // create Start node
            eventWriter.add(eventFactory.createStartElement("", "", name));
            // create Content
            eventWriter.add(eventFactory.createCharacters(nodeMap.get(name).toString()));
            // create End node
            eventWriter.add(eventFactory.createEndElement("", "", name));
            eventWriter.add(end);
        }
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
            String inputDirectory = new File(NFileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";

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
        String path = "/" + file + ".txt";

        // Try to open a BufferedWriter using the file paths set in the constructor
        try
        {
            FileWriter fileWriter = new FileWriter(new File(path));
            bufferedWriter = new BufferedWriter(fileWriter);

            System.out.println("val " + value);

            if (file.equals("options"))
            {
                bufferedWriter.write("max_fps: " + (option.equals("max_fps") ? value : options.getMaxFPS()) + "\n");
                bufferedWriter.write("sound: " + (option.equals("sound") ? value : options.getSoundSetting()) + "\n");
                bufferedWriter.write("music: " + (option.equals("src/main/resources-bin/music") ? value : options.getMusicSetting()) + "\n");
                bufferedWriter.write("screen_size: " + (option.equals("screen_size") ? value : options.getScreenSize().getID()) + "\n");
            }
            bufferedWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
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
        String path = "/" + file + ".txt";

        // Trying to open an InputStream using the paths set in the constructor and export them
        try
        {
            inputStream = NFileManager.class.getResourceAsStream(path);

            if (inputStream == null)
                throw new Exception("Cannot fetch " + file + ".txt from Jar");

            // Trying to get the path of the location of the JAR and creating the main directory and other files
            int readBytes;
            byte[] byteBuffer = new byte[4096];
            mainDirectory = new File(NFileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Timefall";
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
        String path = "/" + file + ".txt";

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
                case "gender":
                    System.out.println(intValue);
                    options.setGender(intValue);
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
