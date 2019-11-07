package model;

import control.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import control.observer.Observer;
import control.observer.Subject;

public class Background extends GameFigure implements Subject{

    private boolean notifiedToSpawn = false;
    ArrayList<Observer> listeners = new ArrayList<>();

    public Background(int x, int y) {
        super.location.x = x;
        super.location.y = y;
    }

    private Image texture;
    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(texture, (int)Math.floor(location.x), (int)Math.floor(location.y),null);
    }

    @Override
    public void update(float dt) {
        super.location.x -= WORLD_PACE * dt;
        if(super.location.x <= -900 && !notifiedToSpawn) {
            notifyEvent();
            notifiedToSpawn = true;
        }
        if (super.location.x <= -1900) {
            this.done = true;
        }
    }

    @Override
    public int getCollisionRadius() {
        return 0;
    }

    @Override
    public void load() {
        try {
            File imageFile = new File("images/background.jpg");
            texture = ImageIO.read(imageFile);
        } catch (IOException e){
            System.out.println("Image error");
        }
    }
    @Override
    public void attachListener(Observer o) {
        listeners.add(o);
    }

    @Override
    public void detachListener(Observer o) {
        listeners.remove(o);
    }

    @Override
    public void notifyEvent() {
        for (var o: listeners) {
            o.eventReceived();
        }
    }
}
