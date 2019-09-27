package control;


import control.observer.BackgroundCreateEvent;
import model.Knight;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;


public class PlayerInputEventQueue {

    public LinkedList<InputEvent> queue = new LinkedList<>();

    public void processInputEvents() {

        while (!queue.isEmpty()) {
            InputEvent inputEvent = queue.removeFirst();

            switch (inputEvent.type) {
                case InputEvent.MOUSE_CLICKED:
                    MouseEvent e = (MouseEvent) inputEvent.event;
                    int x = e.getX();
                    int y = e.getY();
                    if (!Main.running && !Main.gameOver && !Main.gameWon) {
                        if (x < 584 && x > 386 && y > 325 && y < 376) {
                            Main.running = true;
                        }
                    }
                    else if (Main.gameOver) {
                        if (x < 456 && x > 263 && y > 320 && y < 375) {
                            Main.running = true;
                            Main.gameOver = false;
                        }
                        else if (x < 680 && x > 487 && y > 320 && y < 375) {
                            System.exit(0);
                        }
                    }
                    else if (Main.gameWon){
                        if (x < 550 && x > 370 && y > 580 && y <650) {
                            System.exit(0);
                        }
                    }
                    break;
                case InputEvent.KEY_PRESSED:
                    if (Main.running && !Main.gameOver) {
                        KeyEvent ke = (KeyEvent) inputEvent.event;
                        var Player = Main.gameData.friendObject.get(0);
                        switch (ke.getKeyCode()) {
                            case (KeyEvent.VK_D):
                                Player.facing = Knight.Right;
                                Player.moving = true;
                                break;
                            case (KeyEvent.VK_A):
                                Player.facing = Knight.Left;
                                Player.moving = true;
                                break;
                            case (KeyEvent.VK_SPACE):
                                Player.jumping = true;
                                break;
                            case (KeyEvent.VK_SHIFT):
                                if (!Player.jumping && !Player.falling)
                                Player.attacking = true;
                        }
                    }
                    break;
                case InputEvent.KEY_RELEASED:
                    if (Main.running && !Main.gameOver) {
                        KeyEvent ke = (KeyEvent) inputEvent.event;
                        var Player = Main.gameData.friendObject.get(0);
                        switch (ke.getKeyCode()) {
                            case (KeyEvent.VK_D):
                                Player.moving = false;
                                break;
                            case (KeyEvent.VK_A):
                                Player.moving = false;
                                break;
                        }
                    }
                    break;
                    case InputEvent.BACKGROUND_CREATED:
                        BackgroundCreateEvent bc = (BackgroundCreateEvent) inputEvent.event;
                        Main.addBackgroundwithListener(bc.getX(),bc.getY());
                        break;
            }
        }
    }
}
