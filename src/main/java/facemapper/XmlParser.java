package facemapper;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
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

        try {
            // Load XML file object and normalize XML
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Get and iterate through each <records> element
            NodeList records = doc.getElementsByTagName("record");
            for (int i = 0; i < records.getLength(); i++) {
                Node node = records.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Only process elements with "from" and "to"
                    if (element.hasAttribute("from") && element.hasAttribute("to")) {
                        String from = element.getAttribute("from");
                        String to = element.getAttribute("to");

                        // Extract UID from "to"
                        String[] toParts = to.split("/");
                        String uid = toParts[toParts.length - 2].replace("r-", "");

                        // Extract nationality and image from "from"
                        String[] fromParts = from.split("/");
                        if (fromParts.length == 2) {
                            String nationality = fromParts[0];
                            String image = fromParts[1];

                            // Store extracted data in nested map
                            Map<String, String> data = new HashMap<>();
                            data.put("nationality", nationality);
                            data.put("image", image);

                            result.put(uid, data);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing XML: " + e.getMessage());
        }
        return result;
    }
}