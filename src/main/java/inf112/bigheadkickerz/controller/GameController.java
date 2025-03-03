package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import inf112.bigheadkickerz.model.GameModel;
import inf112.bigheadkickerz.view.GameViewImpl;

/**
 * GameController is responsible for handling user input and coordinating model and view
 */
public class GameController implements ApplicationListener {
    private GameModel gameModel;
    private GameViewImpl gameView;
    
    /**
     * Constructor initializes model and view
     */
    public GameController() {
        // Will be initialized in create()
    }
    
    @Override
    public void create() {
        // Initialize model and view
        gameModel = new GameModel();
        gameView = new GameViewImpl(gameModel);
    }
    
    @Override
    public void render() {
        // Update model
        float delta = Gdx.graphics.getDeltaTime();
        gameModel.update(delta);
        
        // Render view
        gameView.render();
    }
    
    @Override
    public void resize(int width, int height) {
        gameView.resize(width, height);
    }
    
    @Override
    public void pause() {
        // Not implemented
    }
    
    @Override
    public void resume() {
        // Not implemented
    }
    
    @Override
    public void dispose() {
        gameView.dispose();
    }
}