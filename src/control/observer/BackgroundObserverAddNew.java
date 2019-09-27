package control.observer;
import control.InputEvent;
import control.Main;

public class BackgroundObserverAddNew implements Observer {
    @Override
    public void eventReceived() {
        InputEvent event = new InputEvent();
        event.event = new BackgroundCreateEvent("Background", 1000,0);
        event.type = InputEvent.BACKGROUND_CREATED;
        Main.playerInputEventQueue.queue.add(event);
    }
}
