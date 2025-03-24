package inf112.bigheadkickerz.model;

import com.badlogic.gdx.math.Vector2;

public interface Collideable {

    float getWeight();

    Vector2 getVelocity();

    void collision(Collideable other);

    boolean collides(Collideable other);
}