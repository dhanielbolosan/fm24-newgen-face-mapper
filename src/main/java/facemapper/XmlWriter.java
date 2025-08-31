package facemapper;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Utility class for processing player data and writing a config.xml
 */
public class XmlWriter {
    private static final Random RANDOM = new Random();
    private static final Map<String, File[]> folderCache = new HashMap<>();

    /**
     * Returns a random PNG image file path from specified folder
     * Caches folder's content on repeated calls
     * @param folderPath Path to the folder containing images
     * @return Path of a randomly selected image, or null if none found
     */
    private static String getRandomImage(String folderPath) {
        // Try to get cached image list for folder
        File[] images = folderCache.get(folderPath);

        // If folder is not cached, read its contents
        if (images == null) {
            File folder = new File(folderPath);

            // Return null if the folder doesn't exist or is not a directory
            if (!folder.exists() || !folder.isDirectory()) return null;

            // List all PNGs in the folder
            images = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

            if (images == null || images.length == 0) return null;

            // Cache the image list
            folderCache.put(folderPath, images);
        }
        return images[RANDOM.nextInt(images.length)].getAbsolutePath();
    }

    public void writeXml(List<Player> players){
        // NEED TO IMPLEMENT CHOOSING PATH
        String basePath = "/Users/dhanielbolosan/Library/Application Support/Sports Interactive/Football Manager 2024/graphics/NG_Regens";
        String outputPath = basePath + "/config.xml";

        // Create folder if not exist
        File folder = new File(basePath);
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Failed to create directory: " + folder.getAbsolutePath());
        }

        // Initialize factory
        XMLOutputFactory factory = XMLOutputFactory.newInstance();

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            // Initialize writer
            XMLStreamWriter writer = factory.createXMLStreamWriter(fos, "UTF-8");

            // Create root element <record>
            writer.writeStartElement("record");
            writer.writeCharacters("\n      ");

            // Create element <list id="maps">
            writer.writeStartElement("list");
            writer.writeAttribute("id", "maps");
            writer.writeCharacters("\n");

            // Iterate through all players to add records
            for (Player player: players) {
                // Determine folder based on player nationality
                String folderName = NationalityMapper.getFolder(player.getNationality(), player.getSecondNationality());
                player.setFolder(folderName);

                String imagePath = getRandomImage(basePath + "/" + folderName);

                // Remove ".png" extension if image exists, else use player UID
                String imageWithoutExt = imagePath != null
                        ? new File(imagePath).getName().replace(".png", "")
                        : player.getUid();

                // Create element <record from="..." to="..."/>
                writer.writeCharacters("        ");
                writer.writeStartElement("record");
                writer.writeAttribute("from", folderName + "/" + imageWithoutExt);
                writer.writeAttribute("to", "graphics/pictures/person/r-" + player.getUid() + "/portrait");
                writer.writeEndElement();
                writer.writeCharacters("\n");
            }

            // Close elements
            writer.writeCharacters("     ");
            writer.writeEndElement();
            writer.writeCharacters("\n");
            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();

            // IMPROVE
            System.out.println("config.xml successfully written to" + outputPath);

        } catch (Exception e){
            System.err.println("Error writing to XML file: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String rtfPath = "/Users/dhanielbolosan/Library/Application Support/Sports Interactive/rtf/2030andorra.rtf";
        RtfParser parser = new RtfParser();
        List<Player> players = parser.parseRtf(rtfPath);

        XmlWriter writer = new XmlWriter();
        writer.writeXml(players);
    }
}
