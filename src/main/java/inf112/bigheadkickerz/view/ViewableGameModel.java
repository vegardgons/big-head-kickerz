package inf112.bigheadkickerz.view;

import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.GameState;
import inf112.bigheadkickerz.model.Goal;
import inf112.bigheadkickerz.model.Player;
import inf112.bigheadkickerz.model.powerups.PowerupPickup;

/**
 * This interface is used to represent a game model that can be viewed.
 */
public interface ViewableGameModel {

  /**
   * Returns the viewport used for rendering the game.
   *
   * @return the FitViewport used for rendering
   */
  FitViewport getViewport();

  /**
   * Returns the player object for player 1.
   *
   * @return the Player object for player 1
   */
  Player getPlayer1();

  /**
   * Returns the player object for player 2.
   *
   * @return the Player object for player 2
   */
  Player getPlayer2();

  /**
   * Returns the score of player 1.
   *
   * @return the score of player 1
   */
  int getPlayer1Score();

  /**
   * Returns the score of player 2.
   *
   * @return the score of player 2
   */
  int getPlayer2Score();

  /**
   * Returns the ball object.
   *
   * @return the Ball object
   */
  Ball getBall();

  /**
   * Returns the right goal object.
   *
   * @return the Goal object for the right goal
   */
  Goal getRightGoal();

  /**
   * Returns the left goal object.
   *
   * @return the Goal object for the left goal
   */
  Goal getLeftGoal();

  /**
   * Returns the current game state.
   *
   * @return the current GameState
   */
  GameState getGameState();

  /**
   * Returns the remaining time in the game.
   *
   * @return the remaining time in seconds
   */
  float getRemainingTime();

  /**
   * Returns the current powerup pickup.
   *
   * @return the PowerupPickup object
   */
  PowerupPickup getCurrentPowerup();

  /**
   * Checks if the controls are currently being displayed.
   *
   * @return true if the controls are being shown, false otherwise
   */
  boolean isShowingControls();

  /**
   * Returns the text displayed for the goal.
   *
   * @return the goal text
   */
  String getGoalText();

  /**
   * Checks if the goal text is currently active.
   *
   * @return true if the goal text is active, false otherwise
   */
  boolean isGoalTextActive();
}