package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.GameState;
import inf112.bigheadkickerz.model.Player;

/**
 * Interface for a controllable game model.
 */
public interface ControllableGameModel {
  /**
   * Update the game state.
   *
   * @param delta time since last update
   */
  void update(float delta);

  /**
   * Get player 1.
   *
   * @return Player object for player 1
   */
  Player getPlayer1();

  /**
   * Get player 2.
   *
   * @return Player object for player 2
   */
  Player getPlayer2();

  /**
   * Get the ball.
   *
   * @return Ball object
   */
  Ball getBall();

  /**
   * Get the viewport.
   *
   * @return FitViewport for the game
   */
  FitViewport getViewport();

  /**
   * Get the current score for player 1.
   *
   * @return int representing player 1's score
   */
  int getPlayer1Score();

  /**
   * Get the current score for player 2.
   *
   * @return int representing player 2's score
   */
  int getPlayer2Score();

  /**
   * Gets the game state.
   *
   * @return boolean indicating if the game is paused
   */
  GameState getGameState();

}
