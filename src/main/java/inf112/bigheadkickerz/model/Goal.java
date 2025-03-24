package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Goal implements GameObject, Collideable {

    private Texture texture;
    private Vector2 pos;
    private static final float WIDTH = 2f;
    private static final float HEIGHT = 3f;

    public Goal(String texturePath, float x, float y, boolean rightGoal) {
        texture = new Texture(texturePath);
        pos = new Vector2(x, y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, WIDTH, HEIGHT);
    }

    public float getWidth() {
        return WIDTH;
    }

    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public float getWeight() {
        return 0;
    }

    @Override
    public Vector2 getVelocity() {
        return new Vector2(0, 0);
    }

    @Override
    public void collision(Collideable other) {

    }

    @Override
    public boolean collides(Collideable other) {
        return true;
    }

    @Override
    public void update(Viewport viewport, float delta) {

    }

    @Override
    public void setVelocity(Vector2 velocity) {

    }

    @Override
    public void setPosition(Vector2 pos) {
        this.pos = pos;
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

}
