package model;


import java.util.ArrayList;

public class GameData {

    public ArrayList<GameFigure> fixedObject = new ArrayList<>();
    public ArrayList<GameFigure> friendObject = new ArrayList<>();
    public ArrayList<GameFigure> enemyObject = new ArrayList<>();

    public void update(float dt) {
        ArrayList<GameFigure> remove = new ArrayList<>();
        for (var fig: fixedObject) {
            if (fig.done) remove.add(fig);
            else fig.update(dt);
        }
        fixedObject.removeAll(remove);

        remove.clear();
        for (var fig: friendObject) {
            if (fig.done) remove.add(fig);
            else fig.update(dt);
        }
        friendObject.removeAll(remove);

        remove.clear();
        for (var fig: enemyObject) {
            if(fig.done) remove.add(fig);
            else fig.update(dt);
        }
        enemyObject.removeAll(remove);
    }
    public void clear() {
        fixedObject.clear();
        friendObject.clear();
        enemyObject.clear();
    }
}
