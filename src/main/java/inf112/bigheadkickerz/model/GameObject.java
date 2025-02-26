package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface GameObject {
  void update(Viewport viewport, float delta );

  void draw(SpriteBatch batch);
}
