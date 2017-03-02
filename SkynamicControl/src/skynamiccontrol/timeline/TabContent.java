package skynamiccontrol.timeline;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import skynamiccontrol.model.Aircraft;

/**
 * Created by fabien on 01/03/17.
 */
public class TabContent extends Pane {

    private static final int SCROLLBAR_HEIGHT = 20;
    private static final int X_START = 55;
    private int height;

    private Line verticalAxis;
    private Text highText;
    private Text midText;
    private Text lowText;
    private Text altitudeText;
    private Content content;
    private ScrollPane scrollPane;


    public TabContent(Aircraft aircraft) {
        height = 120;
        altitudeText = new Text("Altitude");
        lowText = new Text("   0");
        midText = new Text("250");
        highText = new Text("500");
        verticalAxis = new Line(0, 0, 0, height);
        content = new Content(aircraft, this);
        scrollPane = new ScrollPane();
        scrollPane.setContent(content);
        this.setPrefWidth(500);
        this.getChildren().addAll(altitudeText, lowText, midText, highText, verticalAxis, scrollPane);
    }

    public void tuneLayout(double width) {
        //width = this.getPrefWidth()
        altitudeText.setTranslateX(20);
        altitudeText.setTranslateY( 15);

        highText.setTranslateX(20);
        //centered on 40
        highText.setTranslateY(40-highText.getLayoutBounds().getHeight()/2);

        midText.setTranslateX(20);
        midText.setTranslateY(40 + height /2 - midText.getLayoutBounds().getHeight()/2);

        lowText.setTranslateX(20);
        lowText.setTranslateY(40 + height - lowText.getLayoutBounds().getHeight()/2);

        verticalAxis.setTranslateX(50);
        verticalAxis.setTranslateY(27);

        scrollPane.setTranslateX(X_START);
        scrollPane.setTranslateY(27 - StackPaneInstruction.BLOC_HEIGHT/2);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefWidth(width - X_START);
        content.setPrefWidth(width - X_START);
        content.setPrefHeight(height + StackPaneInstruction.BLOC_HEIGHT + SCROLLBAR_HEIGHT);

    }

    public void setContentBackground(String style) {
        content.setStyle(style);
    }

    public void updateContent() {
        content.updateAircraftMissionBlock();
    }

    public void setMaxAltitude(int maxAlt) {
        highText.setText(Integer.toString(maxAlt));
        midText.setText(Integer.toString(maxAlt / 2));
    }

    public void adjustWidth(double desiredWidth) {
        scrollPane.setPrefWidth(desiredWidth - X_START);
        content.setPrefWidth(desiredWidth - X_START);
    }
}
