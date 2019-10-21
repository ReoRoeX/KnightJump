package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Castle extends GameFigure {

    Image Castle;

    public Castle(int x,int y){
        super(x,y);
        super.type = "Castle";
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(Castle, (int)Math.floor(location.x), (int)Math.floor(location.y), 400, 500,null);
    }

    @Override
    public void update(float dt) {
        super.location.x -= WORLD_PACE * dt;
    }

    @Override
    public int getCollisionRadius() {
        return 370;
    }

    @Override
    public void load() {
        try{
            File imageFile = new File("images/Castle.png");
            Castle = ImageIO.read(imageFile);

        }
        catch (IOException e){
            System.out.println("Pit image don't work right");
        }
    }
}
