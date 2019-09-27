package model.zombie;

import model.GameFigure;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Zombie extends GameFigure {
    private static int width = 157;
    private static int height = 108;
    public static Image frame1;
    public static Image frame2;
    public static Image frame3;
    public static Image frame4;
    public static Image ded;
    public int walkCycle = 0;
    private int frames = 0;
    private int left;
    private int right;
    private ZombieAnimStrategy animStrategy;

    public Zombie(int x1, int x2) {
        super(x1, 477);
        left = x1;
        right = x2;
        animStrategy = new ZombieAnimWalking(this);
    }


    @Override
    public void render(Graphics2D g2) {
        if (!dead) {
            if (facing == 0) {
                g2.drawImage(animStrategy.animate(), (int) location.x, (int) location.y, width, height, null);
            } else {
                g2.drawImage(animStrategy.animate(), (int) location.x + 150, (int) location.y, -width, height, null);
            }
        }
        else {
            g2.drawImage(ded, (int) location.x + 150, (int) location.y + 40, -width, height, null);
        }
    }

    @Override
    public void update() {
        frames++;
        switch (frames) {
            case 5:
                walkCycle++;
                break;
            case 10:
                walkCycle++;
                break;
            case 15:
                walkCycle++;
            case 20:
                walkCycle = 0;
                frames = 0;
                break;
        }
        left -= UNITS_MOVED;
        right -= UNITS_MOVED;
        if (!dead) {
            if (facing == 0) {
                location.x += 5;
                if (location.x > right) {
                    facing = 1;
                }
            } else if (facing == 1) {
                location.x -= 10;
                if (location.x < left) {
                    facing = 0;
                }
            }
            if (right == 0) {
                this.done = true;
            }
        }
        else {
            location.x-=UNITS_MOVED;
        }

    }

    @Override
    public int getCollisionRadius() {
        return 108/2;
    }

    @Override
    public void load() {
        try {
            File imageFile = new File("images/Zombie1.png");
            frame1 = ImageIO.read(imageFile);
            imageFile = new File("images/Zombie2.png");
            frame2 = ImageIO.read(imageFile);
            imageFile = new File("images/Zombie3.png");
            frame3 = ImageIO.read(imageFile);
            imageFile = new File("images/Zombie4.png");
            frame4 = ImageIO.read(imageFile);
            imageFile = new File("images/bones.png");
            ded = ImageIO.read(imageFile);
        } catch (IOException e){
            System.out.println("Image error");
        }
    }
}
