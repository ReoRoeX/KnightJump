package model;

import java.util.ArrayList;

public class LevelLayoutSpawn {
    public float spawnTime;
    public boolean triggered;
    public ArrayList<GameFigure> figures;

    public LevelLayoutSpawn(float time, ArrayList<GameFigure> figures) {
        spawnTime = time;
        triggered = false;
        this.figures = figures;
    }
    public LevelLayoutSpawn(float time, GameFigure figure) {
        spawnTime = time;
        triggered = false;
        figures = new ArrayList<>();
        figures.add(figure);
    }

    public void spawn(GameData gameData) {
        for (var fig: figures) {
            if (fig.type == "Castle") {
                gameData.friendObject.add(fig);
            }
            else {
                gameData.enemyObject.add(fig);
            }
            fig.load();
        }
    }
}
