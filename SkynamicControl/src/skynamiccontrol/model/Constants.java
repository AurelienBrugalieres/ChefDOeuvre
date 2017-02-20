package skynamiccontrol.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by fabien on 18/02/17.
 */
public class Constants {
    public static String USER_DIR = "./";
    public static int MAX_INDEX_VALUE;
    public static int DELAY_BETWEEN_SEND;
    public static int SEND_TIMEOUT;
    public static int NB_MAX_INSTRUCTIONS;
    public static String MISSION_CIRCLE_LOCAL;
    public static String MISSION_CIRCLE_LLA;
    public static String MISSION_GOTOWP_LOCAL;
    public static String MISSION_GOTOWP_LLA;
    public static String MISSION_SURVEY_LOCAL;
    public static String MISSION_SURVEY_LLA;
    public static String MISSION_PATH_LOCAL;
    public static String MISSION_PATH_LLA;
    public static String MISSION_SEGMENT_LOCAL;
    public static String MISSION_SEGMENT_LLA;
    public static String DEFAULT_AIRCRAFT_COLOR;

    public static void loadConstants(String filename) {
        BufferedReader br = null;
        FileReader fr = null;

        try {
            String sCurrentLine;
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            while ((sCurrentLine = br.readLine()) != null) {
                String[] elts = sCurrentLine.split(" :");
                switch (elts[0]){
                    case "MAX_INDEX_VALUE":
                        MAX_INDEX_VALUE = Integer.parseInt(elts[1]);
                    case "DELAY_BETWEEN_SEND":
                        DELAY_BETWEEN_SEND = Integer.parseInt(elts[1]);
                    case "SEND_TIMEOUT":
                        SEND_TIMEOUT = Integer.parseInt(elts[1]);
                    case "NB_MAX_INSTRUCTIONS":
                        NB_MAX_INSTRUCTIONS = Integer.parseInt(elts[1]);
                    case "MISSION_CIRCLE_LOCAL":
                        MISSION_CIRCLE_LOCAL = elts[1];
                    case "MISSION_CIRCLE_LLA":
                        MISSION_CIRCLE_LLA = elts[1];
                    case "MISSION_GOTOWP_LOCAL":
                        MISSION_GOTOWP_LOCAL = elts[1];
                    case "MISSION_GOTOWP_LLA":
                        MISSION_GOTOWP_LLA = elts[1];
                    case "MISSION_SURVEY_LOCAL":
                        MISSION_SURVEY_LOCAL = elts[1];
                    case "MISSION_SURVEY_LLA":
                        MISSION_SURVEY_LLA = elts[1];
                    case "MISSION_PATH_LOCAL":
                        MISSION_PATH_LOCAL = elts[1];
                    case "MISSION_PATH_LLA":
                        MISSION_PATH_LLA = elts[1];
                    case "MISSION_SEGMENT_LOCAL":
                        MISSION_SEGMENT_LOCAL = elts[1];
                    case "MISSION_SEGMENT_LLA":
                        MISSION_SEGMENT_LLA = elts[1];
                    case "DEFAULT_AIRCRAFT_COLOR":
                        DEFAULT_AIRCRAFT_COLOR = elts[1];
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null){
                    br.close();
                }
                if (fr != null){
                    fr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
