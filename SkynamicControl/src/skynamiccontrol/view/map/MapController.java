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
import skynamiccontrol.view.map.events.JavaBridge;
import skynamiccontrol.view.map.events.MapListener;
import skynamiccontrol.view.map.events.MapEvent;


/**
 * FXML Controller class MapController
 *
 * @author Aurelien Brugalieres
 */
public class MapController implements Initializable {

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
                (ov, oldState, newState) -> {
                        final JSObject jsobj = (JSObject) webView.getEngine().executeScript("window");
                        jsobj.setMember("java", bridge);
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
        bridge.setMapListener(mapListener);
    }
}