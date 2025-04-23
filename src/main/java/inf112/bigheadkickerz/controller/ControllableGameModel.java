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

  /**
   * Checks if the controls are currently being shown.
   *
   * @return boolean indicating if the controls are visible
   */
  boolean isShowingControls();

  /**
   * Dismisses the controls, hiding them from view.
   */
  void dismissControls();

}
