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

  FitViewport getViewport();

  Player getPlayer1();

  Player getPlayer2();

  int getPlayer1Score();

  int getPlayer2Score();

  Ball getBall();

  Goal getRightGoal();

  Goal getLeftGoal();

  GameState getGameState();

  float getRemainingTime();

  PowerupPickup getCurrentPowerup();

  boolean isShowingControls();


}