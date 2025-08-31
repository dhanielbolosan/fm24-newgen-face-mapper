package facemapper;

import java.io.*;
import java.util.*;

/**
 * Utility class for parsing RTF files containing newgen information (uid, nationality, etc.)
 */
public class RtfParser {
    /**
     * Parses an RTF file to extract Player objects
     * Each line should contain player data in a specific table format
     *
     * @param filePath Path to the RTF file
     * @return A list of Player objects extracted from the RTF file
     */
    public List<Player> parseRtf(String filePath) {
        List<Player> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line in the RTF file
            while ((line = br.readLine()) != null) {
                // Split line into columns
                String[] columns = line.split("\\|");

                // Validate the length of columns & if the first column is a uid
                if (columns.length >= 5 && columns[1].trim().matches("\\d{8,}")) {
                    String uid = columns[1].trim();
                    String nationality = columns[2].trim();
                    String secondNationality = columns[3].trim();
                    String name = columns[4].trim();

                    // Only add players with non-empty names and nationalities
                    if (!name.isEmpty() && !nationality.isEmpty()) {
                        players.add(new Player(uid, nationality, secondNationality, name));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
        }
        return players;
    }
}