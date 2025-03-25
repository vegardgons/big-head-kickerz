package inf112.bigheadkickerz.model;

import java.util.ArrayList;

public class Collision {

    private ArrayList<Collideable> collideables;
    private ArrayList<ArrayList<Collideable>> collideablePairs;

    public Collision(ArrayList<Collideable> collideables) {
        this.collideables = collideables;
        this.collideablePairs = getCollideablePairs();
    }

    public void checkCollision() {

        for (ArrayList<Collideable> pair : collideablePairs) {
            Collideable collideable1 = pair.get(0);
            Collideable collideable2 = pair.get(1);
            if (collideable1.collides(collideable2)) {
                collideable1.collision(collideable2);
            }
        }

    }

    private ArrayList<ArrayList<Collideable>> getCollideablePairs() {
        ArrayList<ArrayList<Collideable>> pairs = new ArrayList<>();
        for (int i = 0; i < collideables.size(); i++) {
            for (int j = i + 1; j < collideables.size(); j++) {
                ArrayList<Collideable> pair = new ArrayList<>();
                pair.add(collideables.get(i));
                pair.add(collideables.get(j));
                pairs.add(pair);
            }
        }
        return pairs;
    }
}
