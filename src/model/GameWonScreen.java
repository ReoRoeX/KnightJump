package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameWonScreen extends GameFigure {

    private Image texture;

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(texture,0,0,950,650,null);
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
        try {
            File imageFile = new File("images/GameWon.jpg");
            texture = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.println("Image error");
        }
    }
}
