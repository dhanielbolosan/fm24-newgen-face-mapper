package facemapper;

import java.util.*;

/**
 * Utility class for mapping nationalities to players
 */
public class NationalityMapper {
    private static final Map<String, String> nationalityMap = new HashMap<>();

    static {
        // europe
        nationalityMap.put("FRA", "France");
        nationalityMap.put("POL", "Poland");
        nationalityMap.put("ITA", "Italia");
        nationalityMap.put("ESP", "Spain");
        nationalityMap.put("AND", "Spain");
        nationalityMap.put("IRL", "Ireland");
        nationalityMap.put("ISL", "Iceland");
        nationalityMap.put("POR", "Portugal");
        nationalityMap.put("BEL", "CentralEurope");
        nationalityMap.put("NED", "Netherlands");
        nationalityMap.put("CYP", "CentralEurope");
        nationalityMap.put("AUT", "CentralEurope");
        nationalityMap.put("GER", "CentralEurope");
        nationalityMap.put("DEN", "Scandinavian");
        nationalityMap.put("FIN", "Finstonia");
        nationalityMap.put("SWE", "Scandinavian");
        nationalityMap.put("NOR", "Scandinavian");
        nationalityMap.put("LUX", "CentralEurope");
        nationalityMap.put("CZE", "CentralEurope");
        nationalityMap.put("SVK", "CentralEurope");
        nationalityMap.put("SVN", "CentralEurope");
        nationalityMap.put("LIE", "CentralEurope");
        nationalityMap.put("MCO", "CentralEurope");
        nationalityMap.put("HUN", "Hungary");
        nationalityMap.put("SUI", "CentralEurope");
        nationalityMap.put("GRE", "Albaniangreek");
        nationalityMap.put("ALB", "Albaniangreek");
        nationalityMap.put("ROU", "EastBalkan");
        nationalityMap.put("BUL", "EastBalkan");
        nationalityMap.put("MDA", "EastBalkan");
        nationalityMap.put("SRB", "WestBalkan");
        nationalityMap.put("CRO", "WestBalkan");
        nationalityMap.put("BIH", "WestBalkan");
        nationalityMap.put("MNE", "WestBalkan");
        nationalityMap.put("MKD", "WestBalkan");
        nationalityMap.put("KOS", "WestBalkan");
        nationalityMap.put("RUS", "EastSlavic");
        nationalityMap.put("UKR", "EastSlavic");
        nationalityMap.put("BLR", "EastSlavic");
        nationalityMap.put("EST", "Baltics");
        nationalityMap.put("LVA", "Baltics");
        nationalityMap.put("LTU", "Baltics");

        // asia
        nationalityMap.put("JPN", "Japan");
        nationalityMap.put("KOR", "Korea");
        nationalityMap.put("PRK", "Korea");
        nationalityMap.put("CHN", "China");
        nationalityMap.put("TPE", "China");
        nationalityMap.put("MAS", "Malaysia");
        nationalityMap.put("IDN", "Indonesia");
        nationalityMap.put("PHI", "Filipino");
        nationalityMap.put("VIE", "Vietnam");
        nationalityMap.put("THA", "MainlandSEA");
        nationalityMap.put("LAO", "MainlandSEA");
        nationalityMap.put("KHM", "MainlandSEA");
        nationalityMap.put("MMR", "MainlandSEA");
        nationalityMap.put("SGP", "Singapore");
        nationalityMap.put("UZB", "Uzbekistan");
        nationalityMap.put("KGZ", "CentralAsia");
        nationalityMap.put("TJK", "CentralAsia");
        nationalityMap.put("KAZ", "CentralAsia");
        nationalityMap.put("MNG", "Mongolia");
        nationalityMap.put("IRN", "Iran");

        // africa
        nationalityMap.put("NGA", "WestAfrica");
        nationalityMap.put("GHA", "WestAfrica");
        nationalityMap.put("SEN", "WestAfrica");
        nationalityMap.put("CIV", "WestAfrica");
        nationalityMap.put("LBR", "WestAfrica");
        nationalityMap.put("BFA", "WestAfrica");
        nationalityMap.put("TGO", "WestAfrica");
        nationalityMap.put("BEN", "WestAfrica");
        nationalityMap.put("SLE", "WestAfrica");
        nationalityMap.put("GMB", "WestAfrica");
        nationalityMap.put("GNB", "WestAfrica");
        nationalityMap.put("CPV", "WestAfrica");
        nationalityMap.put("NER", "WestAfrica");
        nationalityMap.put("MLI", "WestAfrica");
        nationalityMap.put("MRT", "WestAfrica");
        nationalityMap.put("GIN", "WestAfrica");

        nationalityMap.put("KEN", "EastAfrica");
        nationalityMap.put("UGA", "EastAfrica");
        nationalityMap.put("TAN", "EastAfrica");
        nationalityMap.put("RWA", "EastAfrica");
        nationalityMap.put("BDI", "EastAfrica");
        nationalityMap.put("ETH", "HornOfAfrica");
        nationalityMap.put("SOM", "HornOfAfrica");
        nationalityMap.put("ERI", "HornOfAfrica");
        nationalityMap.put("DJI", "HornOfAfrica");

        nationalityMap.put("ZAF", "SouthernAfrica");
        nationalityMap.put("ZIM", "SouthernAfrica");
        nationalityMap.put("MOZ", "SouthernAfrica");
        nationalityMap.put("SWZ", "SouthernAfrica");
        nationalityMap.put("NAM", "SouthernAfrica");
        nationalityMap.put("BOT", "SouthernAfrica");
        nationalityMap.put("LSO", "SouthernAfrica");
        nationalityMap.put("MWI", "SouthernAfrica");
        nationalityMap.put("ZMB", "SouthernAfrica");
        nationalityMap.put("AGO", "SouthernAfrica");
        nationalityMap.put("TCD", "CentralAfrica");
        nationalityMap.put("GAB", "CentralAfrica");
        nationalityMap.put("CMR", "CentralAfrica");
        nationalityMap.put("COD", "CentralAfrica");
        nationalityMap.put("CAF", "CentralAfrica");
        nationalityMap.put("COG", "CentralAfrica");

        nationalityMap.put("MAR", "Maghreb");
        nationalityMap.put("ALG", "Maghreb");
        nationalityMap.put("TUN", "Maghreb");
        nationalityMap.put("LBY", "Maghreb");

        // 'mericas
        nationalityMap.put("ARG", "SouthConeSA");
        nationalityMap.put("CHL", "SouthConeSA");
        nationalityMap.put("URU", "SouthConeSA");
        nationalityMap.put("PAR", "SouthConeSA");
        nationalityMap.put("BRA", "BrazilMixed");
        nationalityMap.put("MEX", "Mestizo");
        nationalityMap.put("PER", "IndigenousSA");
        nationalityMap.put("BOL", "IndigenousSA");
        nationalityMap.put("ECU", "IndigenousSA");
        nationalityMap.put("COL", "IndigenousSA");
        nationalityMap.put("VEN", "IndigenousSA");
        nationalityMap.put("CAN", "CaucasianNA");
        nationalityMap.put("USA", "Afroamerican");
        nationalityMap.put("CUB", "Afrocaribbean");
        nationalityMap.put("JAM", "Afrocaribbean");
        nationalityMap.put("BRB", "Afrocaribbean");
        nationalityMap.put("DMA", "Afrocaribbean");
        nationalityMap.put("VIN", "Afrocaribbean");
        nationalityMap.put("ANT", "Afrocaribbean");
        nationalityMap.put("SUR", "Afrocaribbean");
        nationalityMap.put("GUY", "Afrocaribbean");
        nationalityMap.put("TTO", "Afrocaribbean");
        nationalityMap.put("DOM", "Afrocaribbean");
        nationalityMap.put("HTI", "Afrocaribbean");
        nationalityMap.put("BHS", "Afrocaribbean");

        // middle east
        nationalityMap.put("BHR", "ArabGulf");
        nationalityMap.put("QAT", "ArabGulf");
        nationalityMap.put("UAE", "ArabGulf");
        nationalityMap.put("SAU", "ArabGulf");
        nationalityMap.put("OMN", "ArabGulf");
        nationalityMap.put("IRQ", "Mashriq");
        nationalityMap.put("SYR", "Mashriq");
        nationalityMap.put("JOR", "Mashriq");
        nationalityMap.put("LBN", "Mashriq");
        nationalityMap.put("ISR", "Israel");
        nationalityMap.put("TUR", "Turkish");

        // oceania
        nationalityMap.put("PNG", "PacificIslanders");
        nationalityMap.put("TGA", "PacificIslanders");
        nationalityMap.put("VAN", "PacificIslanders");
        nationalityMap.put("FJI", "PacificIslanders");
        nationalityMap.put("SAM", "PacificIslanders");
        nationalityMap.put("SLB", "PacificIslanders");
        nationalityMap.put("KIR", "PacificIslanders");
        nationalityMap.put("NRU", "PacificIslanders");
        nationalityMap.put("PLW", "PacificIslanders");
        nationalityMap.put("FSM", "PacificIslanders");
        nationalityMap.put("TUV", "PacificIslanders");
    }

    /**
     * Uses nationality hash map to get folders for players
     * Unique cases for mixed nationalities
     *
     * @param primary   The primary nationality
     * @param secondary The secondary nationality
     * @return Folder for the player
     */
    public static String getFolder(String primary, String secondary) {
        String primaryFolder = nationalityMap.get(primary);
        String secondaryFolder = nationalityMap.get(secondary);

        if (primaryFolder != null) {
            // Handle mixed cases
            if (secondaryFolder != null) {
                // Mestizo: European + IndigenousSA
                if ((primaryFolder.equals("France") || primaryFolder.equals("Spain") ||
                        primaryFolder.equals("Portugal") || primaryFolder.equals("Italia")) &&
                        secondaryFolder.equals("IndigenousSA")) {
                    return "Mestizo";
                }

                // Afroamerican: African + Americas
                if ((primaryFolder.contains("Africa") || primaryFolder.equals("HornOfAfrica") || primaryFolder.equals("SouthernAfrica")) &&
                        secondaryFolder.equals("CaucasianNA")) {
                    return "Afroamerican";
                }

                // Afrocaribbean: African + Caribbean
                if ((primaryFolder.contains("Africa") || primaryFolder.equals("HornOfAfrica") || primaryFolder.equals("SouthernAfrica")) &&
                        secondaryFolder.equals("Afrocaribbean")) {
                    return "Afrocaribbean";
                }

                // Otherwise mixed race
                return "MixedRace";
            }
            return primaryFolder;
        }

        if (secondaryFolder != null) {
            return secondaryFolder;
        }

        return "MixedRace";
    }
}