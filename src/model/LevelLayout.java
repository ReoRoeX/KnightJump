package model;

import java.util.ArrayList;

public class LevelLayout {
    private GameData gameData;
    private float spawnTimer = 0f;
    private ArrayList<LevelLayoutSpawn> spawns;

    public LevelLayout(GameData gameData) {
        this.gameData = gameData;
        spawns = new ArrayList<>();
    }

    public void addSpawn(LevelLayoutSpawn spawn) {
        spawns.add(spawn);
    }

    public void update(float dt) {
        spawnTimer += dt;
        for (int i = 0; i < spawns.size(); i++) {
            if (spawns.get(i).spawnTime <= spawnTimer && !spawns.get(i).triggered) {
                spawns.get(i).spawn(gameData);
                spawns.get(i).triggered = true;
            }
        }
    }

    public void reset() {
        for (int i = 0; i < spawns.size(); i++) {
            spawns.get(i).triggered = false;
        }
    }
}
