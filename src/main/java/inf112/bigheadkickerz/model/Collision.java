package inf112.bigheadkickerz.model;

import java.util.ArrayList;

public class Collision {

    private ArrayList<Collideable> collideables;

    public Collision(ArrayList<Collideable> collideables) {
        this.collideables = collideables;
    }

    public void checkCollision() {
        for (int i = 0; i < collideables.size(); i++) {
            for (int j = i + 1; j < collideables.size(); j++) {
                Collideable a = collideables.get(i);
                Collideable b = collideables.get(j);
                if (a.collides(b)) {
                    a.collision(b);
                    b.collision(a);
                }
            }
        }
    }

}
