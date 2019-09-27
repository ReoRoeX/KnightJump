package control;

import java.util.EventObject;

public class InputEvent {

    public final static int MOUSE_CLICKED = 0;
    public final static int KEY_RELEASED = 1;
    public final static int KEY_PRESSED = 2;
    public final static int BACKGROUND_CREATED = 3;

    public EventObject event;
    public int type;
}
