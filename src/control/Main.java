package control;
import control.observer.BackgroundObserverAddNew;
import model.*;
import model.zombie.Zombie;
import view.MyWindow;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static MyWindow win;
    public static GameData gameData;
    public static LevelLayout levelLayout;
    public static PlayerInputEventQueue playerInputEventQueue;
    public static boolean running = false;
    public static boolean gameOver = false;
    public static boolean gameWon = false;

    public static int FPS = 60; //frames per second

    public static void main (String[] args) {
        win = new MyWindow();
        win.init();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);

        gameData = new GameData();
        levelLayout = new LevelLayout(gameData);
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
        gameOver = false;
        running = true;

        // initialize level layout
        levelLayout.addSpawn(new LevelLayoutSpawn(0,
            new ArrayList<GameFigure>() {{
                add(new Pit(600, 575));
                add(new Zombie(900, 1200));
            }}
        ));

        // spawn time is divided by a const divisor based on the original game's spawn system

        float divisor = 20f;
        levelLayout.addSpawn(new LevelLayoutSpawn(400 / divisor, new Pit(1000, 575)));
        levelLayout.addSpawn(new LevelLayoutSpawn(900 / divisor, new Zombie(900, 1200)));
        levelLayout.addSpawn(new LevelLayoutSpawn(1000 / divisor, new Zombie(900, 1200)));
        levelLayout.addSpawn(new LevelLayoutSpawn(1300 / divisor, new Pit(1000, 575)));
        levelLayout.addSpawn(new LevelLayoutSpawn(2000 / divisor, new Pit(1000, 575)));
        levelLayout.addSpawn(new LevelLayoutSpawn(2500 / divisor, new Zombie(900, 1200)));
        levelLayout.addSpawn(new LevelLayoutSpawn(2700 / divisor, new Zombie(900, 1200)));
        levelLayout.addSpawn(new LevelLayoutSpawn(2900 / divisor, new Zombie(900, 1200)));
        levelLayout.addSpawn(new LevelLayoutSpawn(3200 / divisor, new Pit(1000, 575)));
        levelLayout.addSpawn(new LevelLayoutSpawn(3500 / divisor, new Zombie(900, 1200)));
        levelLayout.addSpawn(new LevelLayoutSpawn(3600 / divisor, new Zombie(900, 1200)));
        levelLayout.addSpawn(new LevelLayoutSpawn(3900 / divisor, new Pit(1000, 575)));
        levelLayout.addSpawn(new LevelLayoutSpawn(4600 / divisor, new Pit(1000, 575)));
        levelLayout.addSpawn(new LevelLayoutSpawn(5300 / divisor, new Pit(1000, 575)));
        levelLayout.addSpawn(new LevelLayoutSpawn(6000 / divisor, new Castle(960, 80)));
    }

    public static void addBackgroundwithListener (int x, int y) {
        var background = new Background (x,y);
        background.attachListener(new BackgroundObserverAddNew());
        background.load();
        gameData.fixedObject.add(background);
    }


    static void gameLoop() {
        long startTime = System.currentTimeMillis();

        //game loop
        while (running) {
            long currentTime = System.currentTimeMillis();

            // use deltatime to update each frame based on a set fps
            if ((currentTime - startTime) >= 1000 / FPS) {
                float dt = (currentTime - startTime) * 0.001f; // delta time for physics simulation
                startTime = currentTime;

                levelLayout.update(dt);
                playerInputEventQueue.processInputEvents();
                processCollisions();
                gameData.update(dt);
                win.canvas.render();
            }
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
