package inf112.bigheadkickerz.controller;


import inf112.bigheadkickerz.model.GameState;

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

  void setGameOver(boolean gameOver);

  /**
   * Gets the game state.
   *
   * @return boolean indicating if the game is paused
   */
  GameState getGameState();

  boolean isShowingControls();

  void dismissControls();

}
