package model;

import java.awt.*;

public class Text extends GameFigure {

    String text;
    Color color;
    Font font;

    public Text(String text, int y, int x, Color color, Font font) {
        super(x,y);
        this.text = text;
        this.color = color;
        this.font = font;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString(text, (int) location.x, (int) location.y);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public int getCollisionRadius() {
        return 0;
    }

    @Override
    public void load() {

    }
}
