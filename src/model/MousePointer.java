package model;

import java.awt.*;

public class MousePointer extends GameFigure {

    public MousePointer(int x, int y) {
        super(x,y);
    }

    @Override
    public void render(Graphics2D g2) {

    }

    @Override
    public void update(float dt) {
        //NA
    }

    @Override
    public int getCollisionRadius() {
        return 0;
    }

    @Override
    public void load() {

    }
}
