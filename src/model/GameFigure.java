package model;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class GameFigure {

    public Point2D.Float location;
    public boolean done = false;
    public boolean dead = false;
    public int hitCount = 0;
    public int facing = 0;
    public boolean moving = false;
    public boolean jumping = false;
    public boolean falling = false;
    public boolean attacking = false;
    public boolean damage = false;
    public String type;
    public static final int UNITS_MOVED = 5;

    public GameFigure(float x, float y) {
        location = new Point2D.Float(x,y);
    }

    public GameFigure() {
        this(0,0);
    }

    public void setLocation(float x, float y) {
        location.x = x;
        location.y = y;
    }

    public boolean collideWith(GameFigure o) {
        double dist = this.location.distance(o.location);
        if(dist <= this.getCollisionRadius() + o.getCollisionRadius()) {
            return true;
        } else
            return false;
    }

    public abstract void render(Graphics2D g2);
    public abstract void update();
    public abstract int getCollisionRadius();
    public abstract void load();
}
