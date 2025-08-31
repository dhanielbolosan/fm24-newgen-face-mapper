package facemapper;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    /**
     * Generates and writes an XML file mapping player UIDs to image paths
     * Each player record includes a "from" (folder/image) and "to" (graphics path)
     * @param players List of Player objects to process
     */
    public void writeXml(List<Player> players) {
        try {
            // NEED TO IMPLEMENT CHOOSING PATH
            String basePath = "/Users/dhanielbolosan/Library/Application Support/Sports Interactive/Football Manager 2024/graphics/NG_Regens";

            // Create folder if not exist
            File folder = new File(basePath);
            if (!folder.exists()) folder.mkdirs();

            String outputPath = basePath + "/config.xml";

            // Initialize XML document builder
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Create root <record> element
            Element record = doc.createElement("record");
            doc.appendChild(record);

            // Create <list id="maps"> element inside root
            Element list = doc.createElement("list");
            list.setAttribute("id", "maps");
            record.appendChild(list);

            // Iterate through all players to add records
            for (Player p : players) {
                // Determine folder based on player nationality
                String folderName = NationalityMapper.getFolder(p.getNationality(), p.getSecondNationality());
                p.setFolder(folderName);

                // Get a random image from the folder
                String imagePath = getRandomImage(basePath + "/" + folderName);

                // Remove ".png" if image exists, else use UID
                String imageWithoutExt = imagePath != null
                        ? new File(imagePath).getName().replace(".png", "")
                        : p.getUid();

                // Create and list <record> element for this player using "from" and "to" data
                Element playerRecord = doc.createElement("record");
                playerRecord.setAttribute("from", folderName + "/" + imageWithoutExt);
                playerRecord.setAttribute("to", "graphics/pictures/person/r-" + p.getUid() + "/portrait");
                list.appendChild(playerRecord);
            }

            // Initialize transformer to write XML to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            // Write XML to file
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputPath));
            transformer.transform(source, result);

            // Print completion
            System.out.println("XML written to " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main testing
    public static void main(String[] args) {
        String rtfPath = "/Users/dhanielbolosan/Library/Application Support/Sports Interactive/rtf/2030andorra.rtf";
        RtfParser parser = new RtfParser();
        List<Player> players = parser.parseRtf(rtfPath);

        for (Player p : players) {
            String folder = NationalityMapper.getFolder(p.getNationality(), p.getSecondNationality());
            p.setFolder(folder);
        }

        XmlWriter processor = new XmlWriter();
        processor.writeXml(players);
    }
}