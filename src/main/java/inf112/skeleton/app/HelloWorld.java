package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.Texture;

public class HelloWorld implements ApplicationListener {
	private Texture backgroundTexture;
	private Texture dropTexture;
	private Sound dropSound;	
	private SpriteBatch spriteBatch;
	private FitViewport viewport;
	private Texture playerTexture;
	private Sprite playerSprite; // Declare a new Sprite variable
	private float velocityY = 0;  
    private float gravity = -9.81f; 
    private boolean isJumping = false; 





	@Override
	public void create() {
		// Called at startup
		spriteBatch = new SpriteBatch(); 
		viewport = new FitViewport(8, 5); 
		playerTexture = new Texture(Gdx.files.internal("PlayerImage.png"));
		backgroundTexture = new Texture("background.png");
		playerSprite = new Sprite(playerTexture);
		playerSprite.setSize(1, 1);


	}

	@Override
	public void dispose() {
		// Called at shutdown

		// Graphics and sound resources aren't managed by Java's garbage collector, so
		// they must generally be disposed of manually when no longer needed. But,
		// any remaining resources are typically cleaned up automatically when the
		// application exits, so these aren't strictly necessary here.
		// (We might need to do something like this when loading a new game level in
		// a large game, for instance, or if the user switches to another application
		// temporarily (e.g., incoming phone call on a phone, or something).

	}

@Override
public void render() {
    // organize code into three methods
    input();
    logic();
    draw();
}

private void input() {
	float speed = 4f;
    float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        playerSprite.translateX(speed * delta); // Move the bucket right
    }
	else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        playerSprite.translateX(-speed * delta); // Move the bucket right
    }
	if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !isJumping) {
		velocityY = 4f; // Initial jump speed
		isJumping = true;
	}

}

private void logic() {
	float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta
	float worldWidth = viewport.getWorldWidth();
    float worldHeight = viewport.getWorldHeight();

	float playerWidth = playerSprite.getWidth();
    float playerHeight = playerSprite.getHeight();
	// Clamp x to values between 0 and worldWidth
	playerSprite.setX(MathUtils.clamp(playerSprite.getX(), 0, worldWidth-playerWidth));
	playerSprite.setY(MathUtils.clamp(playerSprite.getY(), 0, worldHeight-playerHeight));

	 // Apply gravity
	 velocityY += gravity * delta;
	 playerSprite.translateY(velocityY * delta);

	 // Check if player hits the "ground"
	 if (playerSprite.getY() <= 0) {
		 playerSprite.setY(0);
		 velocityY = 0;
		 isJumping = false;
	 }
}

private void draw() {
	ScreenUtils.clear(Color.BLACK);
    viewport.apply();
    spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    spriteBatch.begin();

	float worldWidth = viewport.getWorldWidth();
    float worldHeight = viewport.getWorldHeight();

	spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); // draw the background
    playerSprite.draw(spriteBatch); // Sprites have their own draw method



    spriteBatch.end();
}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
