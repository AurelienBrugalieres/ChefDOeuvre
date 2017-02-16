package skynamiccontrol.view.map;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;


/**
 * FXML Controller class MapController
 *
 * @author Aurelien Brugalieres
 */
public class MapController implements Initializable {

    // Expose interface
    public interface MapListener {
        void onMapClickListener(MapPoint pt);
    }

    private MapListener mapListener = null;

    @FXML
    private WebView webView;

    private JavaBridge bridge = new JavaBridge();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                            final JSObject jsobj = (JSObject) webView.getEngine().executeScript("window");
                            jsobj.setMember("java", bridge);
                    }
                });

        File file = new File("SkynamicControl/src/skynamiccontrol/view/map/map_api/google/index.html");
        if (file.exists()) {
            try {
                String urlText = file.toURI().toURL().toExternalForm();
                webEngine.load(urlText);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMapListener(MapListener listener) {
        this.mapListener = listener;
    }

    public class JavaBridge {
        public void onMapClick(JSObject point) {
            Pattern locationPattern = Pattern.compile("\\(([0-9]+.?[0-9]*), *([0-9]+.?[0-9]*)\\)");
            Matcher m = locationPattern.matcher(point.toString());
            double lat = 0;
            double lng = 0;
            if (m.matches()) {
                lat = Double.parseDouble(m.group(1));
                lng = Double.parseDouble(m.group(2));
            }
            if (mapListener != null)
                mapListener.onMapClickListener(new MapPoint(lat, lng));
        }

        public void log(String message) {
            System.out.println(message);
        }
    }

    public class MapPoint {
        private double latitude;
        private double longitude;

        public MapPoint() {
            this(0, 0);
        }

        public MapPoint(double lat, double lng) {
            this.latitude = lat;
            this.longitude = lng;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }


}