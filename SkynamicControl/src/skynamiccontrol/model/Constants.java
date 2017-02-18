package skynamiccontrol.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by fabien on 18/02/17.
 */
public class Constants {
    public int MAX_INDEX_VALUE;
    public int DELAY_BETWEEN_SEND;
    public int SEND_TIMEOUT;
    public int NB_MAX_INSTRUCTIONS;
    public String MISSION_CIRCLE_LOCAL;
    public String MISSION_CIRCLE_LLA;
    public String MISSION_GOTOWP_LOCAL;
    public String MISSION_GOTOWP_LLA;
    public String MISSION_SURVEY_LOCAL;
    public String MISSION_SURVEY_LLA;
    public String MISSION_PATH_LOCAL;
    public String MISSION_PATH_LLA;
    public String MISSION_SEGMENT_LOCAL;
    public String MISSION_SEGMENT_LLA;

    private static Constants ourInstance = new Constants();

    public static Constants getInstance() {
        return ourInstance;
    }

    public void loadConstants(String filename) {
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

    private Constants() {
        loadConstants("./src/resources/constants.conf");
    }
}
