/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynamiccontrol.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author bonnevfa
 */
public class ImageButton extends ImageView{

    private State state;
    private Image baseImg;
    private Image hoveredImg;
    private Image pressedImg;
    private EventHandler<MouseEvent> eventFilter;

    public ImageButton() {
    }

    public void initButton(String baseImgPath, String hoveredImgPath, String pressedImgPath) {
        state = State.OUT;
        try {
            this.baseImg = new Image(getClass().getResourceAsStream(baseImgPath));
            this.hoveredImg = new Image(getClass().getResourceAsStream(hoveredImgPath));
            this.pressedImg = new Image(getClass().getResourceAsStream(pressedImgPath));
        } catch(NullPointerException e) {
            e.printStackTrace();
        }

        this.setImage(this.baseImg);

        this.eventFilter = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        };


        this.addEventFilter(MouseEvent.MOUSE_CLICKED, eventFilter);

        this.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
            switch(state) {
                case OUT:
                    gotToIn();
                    break;
                case IN:
                    //forbidden
                    break;
                case PRESSED_IN:
                    ///forbidden
                    break;
                case PRESSED_OUT:
                    gotToPressedIn();
                    break;
            }

        });

        this.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
            switch(state) {
                case OUT:
                    //forbidden
                    break;
                case IN:
                    gotToOut();
                    break;
                case PRESSED_IN:
                    gotToPressedOut();
                    break;
                case PRESSED_OUT:
                    //forbidden
                    break;
            }

        });

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            switch(state) {
                case OUT:
                    //forbidden
                    break;
                case IN:
                    gotToPressedIn();
                    break;
                case PRESSED_IN:
                    //forbidden
                    break;
                case PRESSED_OUT:
                    //forbidden
                    break;
            }

        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
            switch(state) {
                case OUT:
                    //forbidden
                    break;
                case IN:
                    //forbidden
                    break;
                case PRESSED_IN:
                    gotToIn();
                    sendClickedEvent();
                    break;
                case PRESSED_OUT:
                    gotToOut();
                    break;
            }

        });
    }
    
    private void sendClickedEvent() {
        this.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventFilter);
        Event.fireEvent(this, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null));
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, eventFilter);
    }
    
    
    private enum State {
        OUT,
        IN,
        PRESSED_IN,
        PRESSED_OUT
    }
    
    private void gotToOut() {
        this.setImage(baseImg);
        this.state = State.OUT;
    }
    
    private void gotToIn() {
        this.setImage(hoveredImg);
        this.state = State.IN;
    }
    
    private void gotToPressedIn() {
        this.setImage(pressedImg);
        this.state = State.PRESSED_IN;
    }
    
    private void gotToPressedOut() {
        this.setImage(pressedImg);
        this.state = State.PRESSED_OUT;
    }
    
    public void setMaximumSize(int w, int h) {
        double w0 = this.getImage().getWidth();
        double h0 = this.getImage().getHeight(); 
        double ratio = Math.min(w/w0, h/h0);
        this.setScaleX(ratio);
        this.setScaleY(ratio);
    }
    
}
