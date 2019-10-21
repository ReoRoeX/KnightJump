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
    private final int UNIT_MOVE = 250;

    public Zombie(int x1, int x2) {
        super(x1, 477);
        left = x1;
        right = x2;
        animStrategy = new ZombieAnimWalking(this);
    }


    @Override
    public void render(Graphics2D g2) {

        // fixes graphical jitter
        int x = (int)Math.floor(location.x);
        int y = (int)Math.floor(location.y);

        if (!dead) {
            if (facing == 0) {
                g2.drawImage(animStrategy.animate(), x, y, width, height, null);
            } else {
                g2.drawImage(animStrategy.animate(), x + 150, y, -width, height, null);
            }
        }
        else {
            g2.drawImage(ded, x + 150, y + 40, -width, height, null);
        }
    }

    @Override
    public void update(float dt) {
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
        left -= WORLD_PACE;
        right -= WORLD_PACE;
        if (!dead) {
            if (facing == 0) {
                location.x += (UNIT_MOVE - WORLD_PACE) * dt;
                if (location.x > right) {
                    facing = 1;
                }
            } else if (facing == 1) {
                location.x -= UNIT_MOVE * dt;
                if (location.x < left) {
                    facing = 0;
                }
            }
            if (right == 0) {
                //this.done = true;
            }
        }
        else {
            location.x -= WORLD_PACE * dt;
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
