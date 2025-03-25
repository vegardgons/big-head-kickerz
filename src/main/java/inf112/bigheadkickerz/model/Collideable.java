package inf112.bigheadkickerz.model;

import com.badlogic.gdx.math.Vector2;

public interface Collideable {

    float getWeight();

    float getWidth();

    Vector2 getVelocity();

    Vector2 getPosition();

    void setPosition(Vector2 pos);

    /**
     * Set the speed of the game object in the x direction
     *
     * @param velocity
     */
    void setVelocity(Vector2 velocity);

    void collision(Collideable other);

    boolean collides(Collideable other);

}