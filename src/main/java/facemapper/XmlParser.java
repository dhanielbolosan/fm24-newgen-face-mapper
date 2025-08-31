package facemapper;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for parsing an XML file containing player image mappings
 */
public class XmlParser {
    /**
     * Parses an XML file and extracts the player's UID, nationality, and image
     *
     * @param filePath Path to the XML file to parse
     * @return hash map: UID -> {nationality, image}
     */
    public Map<String, Map<String, String>> parseXml(String filePath) {
        Map<String, Map<String, String>> result = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            // Initialize factory and reader
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(fis);

            // Read the file
            while (reader.hasNext()) {
                int event = reader.next();

                if (event == XMLStreamConstants.START_ELEMENT) {
                    if ("record".equals(reader.getLocalName())) {
                        String from = reader.getAttributeValue(null, "from");
                        String to = reader.getAttributeValue(null, "to");

                        if (from != null && to != null) {
                            // Extract UID from "to"
                            String[] toParts = to.split("/");
                            String uid = toParts[toParts.length - 2].replace("r-", "");

                            // Extract Nationality and Image from "from"
                            String[] fromParts = from.split("/");
                            if (fromParts.length == 2) {
                                String nationality = fromParts[0];
                                String image = fromParts[1];

                                Map<String, String> data = new HashMap<>();
                                data.put("nationality", nationality);
                                data.put("image", image);
                                result.put(uid, data);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
        }
        return result;
    }
}