package skynamiccontrol;


import javafx.scene.paint.Color;

/**
 * Created by fabien on 01/03/17.
 */
public class FxUtils {

    public static String toRGBACode(Color color )
    {
        return String.format( "rgba(%d, %d, %d)",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255),
                (int)(color.getOpacity() * 255));
    }

    public static String getCssColor(Color color) {
        String colorString = "-fx-background-color: " + toRGBACode(color) + ";";
        return colorString;
    }

}
