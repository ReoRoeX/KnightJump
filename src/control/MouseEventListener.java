package control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEventListener extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("mouse pressed at " +e.getX() + " " + e.getY());
        InputEvent inputEvent = new InputEvent();
        inputEvent.event = e;
        inputEvent.type = InputEvent.MOUSE_CLICKED;
        Main.playerInputEventQueue.queue.add(inputEvent);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
