package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pit extends GameFigure {

    private Image Pit;

    public Pit(int x,int y){
        super(x,y);
        super.type = "Pit";
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(Pit,(int)super.location.x,(int)super.location.y,null);
    }

    @Override
    public void update() {
        super.location.x -= UNITS_MOVED;
    }

    @Override
    public int getCollisionRadius() {

        return 77;
    }

    @Override
    public void load() {
        try{
            File imageFile = new File("images/SpikePit.png");
            Pit = ImageIO.read(imageFile);

        }
        catch (IOException e){
            System.out.println("Pit image don't work right");
        }
    }
}
