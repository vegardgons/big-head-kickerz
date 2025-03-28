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
        for (int i = 0; i < collideables.size(); i++) {
            for (int j = i + 1; j < collideables.size(); j++) {
                Collideable a = collideables.get(i);
                Collideable b = collideables.get(j);
                // Debug print: show which objects are being checked
                System.out.println("Checking collision: " + a + " vs " + b);
                if (a.collides(b)) {
                    System.out.println("Collision detected between: " + a + " and " + b);
                    a.collision(b);
                    b.collision(a);
                }
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
