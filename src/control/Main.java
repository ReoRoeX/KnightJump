package control;
import control.observer.BackgroundObserverAddNew;
import model.*;
import model.zombie.Zombie;
import view.MyWindow;

import javax.swing.*;

public class Main {

    public static MyWindow win;
    public static GameData gameData;
    public static PlayerInputEventQueue playerInputEventQueue;
    public static boolean running = false;
    public static int xMoved = 0;
    public static boolean gameOver = false;
    public static boolean gameWon = false;

    public static int FPS = 20; //frames per second

    public static void main (String[] args) {
        win = new MyWindow();
        win.init();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);

        gameData = new GameData();
        playerInputEventQueue = new PlayerInputEventQueue();


        startScreen();
        do {
            initGame();
            gameLoop();
            if (gameOver) {
                gameOverScreen();
            }
        } while (running);
        gameWonScreen();
    }

    // TODO: Let's abstract away screens to simplify rendering, consider using GameStates
    static void startScreen() {
        gameData.fixedObject.add(new MousePointer(0,0));
        StartScreen begin = new StartScreen();
        begin.load();
        gameData.fixedObject.add(begin);
        while(!running) {
            Main.win.canvas.render();
            playerInputEventQueue.processInputEvents();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void initGame() {
        gameData.clear();
        gameData.fixedObject.add(new MousePointer(0,0));
        addBackgroundwithListener(0,0);
        Knight Player = new Knight();
        Player.load();
        gameData.friendObject.add(Player);
        xMoved = 0;
        gameOver = false;
    }

    public static void addBackgroundwithListener (int x, int y) {
        var background = new Background (x,y);
        background.attachListener(new BackgroundObserverAddNew());
        background.load();
        gameData.fixedObject.add(background);
    }


    static void gameLoop() {

        running = true;

        //game loop
        while (running) {
            long startTime = System.currentTimeMillis();
            levelLayout();
            playerInputEventQueue.processInputEvents();
            processCollisions();
            gameData.update();
            win.canvas.render();
            long endTime = System.currentTimeMillis();

            long timeSpent = endTime - startTime;
            long sleepTime = (long) (1000.0 / FPS - timeSpent);

            // TODO: Look to replace this with a timer utilizing deltatime and not use Thread.sleep()
            try {
                if(sleepTime > 0) Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: Look to implement a level file format (json is easy) to simplify enemy placements
    static void levelLayout() {
        Zombie zombie = new Zombie(900,1200);
        Pit pit = new Pit(1000,575);
        switch (xMoved) {
            case 0:
                Pit Pit1 = new Pit(600,575);
                gameData.enemyObject.add(Pit1);
                Pit1.load();
                gameData.enemyObject.add(zombie);
                zombie.load();
                break;
            case 400:
                gameData.enemyObject.add(pit);
                pit.load();
                break;
            case 900:
                gameData.enemyObject.add(zombie);
                zombie.load();
                break;
            case 1000:
                gameData.enemyObject.add(zombie);
                zombie.load();
                break;
            case 1300:
                gameData.enemyObject.add(pit);
                pit.load();
                break;
            case 2000:
                gameData.enemyObject.add(pit);
                pit.load();
                break;
            case 2500:
                gameData.enemyObject.add(zombie);
                zombie.load();
                break;
            case 2700:
                gameData.enemyObject.add(zombie);
                zombie.load();
                break;
            case 2900:
                gameData.enemyObject.add(zombie);
                zombie.load();
                break;
            case 3200:
                gameData.enemyObject.add(pit);
                pit.load();
                break;
            case 3500:
                gameData.enemyObject.add(zombie);
                zombie.load();
                break;
            case 3600:
                gameData.enemyObject.add(zombie);
                zombie.load();
                break;
            case 3900:
                gameData.enemyObject.add(pit);
                pit.load();
                break;
            case 4600:
                gameData.enemyObject.add(pit);
                pit.load();
                break;
            case 5300:
                gameData.enemyObject.add(pit);
                pit.load();
                break;
            case 6000:
                Castle castle = new Castle(960, 80);
                gameData.friendObject.add(castle);
                 castle.load();
                break;
        }
    }

    // TODO: Each entity should handle collisions with other entities, rather than hardcoding them here.
    static void processCollisions() {
        var Player = (Knight) Main.gameData.friendObject.get(0);
        for (var enemy: Main.gameData.enemyObject) {
            if(Player.collideWith(enemy)) {
                if(enemy.type == "Pit"){
                    gameOver = true;
                    running = false;
                }
                else if (Player.attacking) {
                    enemy.dead = true;
                }
                else if (!Player.damage && !enemy.dead) {
                    ++Player.hitCount;
                    Player.damage = true;
                }
            }
        }
        if (gameData.friendObject.size() == 2) {
            var castle = (Castle) Main.gameData.friendObject.get(1);
            if (Player.collideWith(castle)) {
                running = false;
                gameWon = true;
            }
        }
    }

    static void gameOverScreen() {
        gameData.friendObject.clear();
        gameData.enemyObject.clear();
        gameData.fixedObject.clear();
        gameData.fixedObject.add(new MousePointer(0,0));
        GameOverScreen end = new GameOverScreen();
        end.load();
        gameData.fixedObject.add(end);
        while(!running) {
            Main.win.canvas.render();
            playerInputEventQueue.processInputEvents();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static void gameWonScreen() {
        gameData.friendObject.clear();
        gameData.enemyObject.clear();
        gameData.fixedObject.clear();
        gameData.fixedObject.add(new MousePointer(0,0));
        GameWonScreen end = new GameWonScreen();
        end.load();
        gameData.fixedObject.add(end);
        while(!running) {
            Main.win.canvas.render();
            playerInputEventQueue.processInputEvents();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
