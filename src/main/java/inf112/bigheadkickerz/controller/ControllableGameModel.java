package inf112.bigheadkickerz.controller;

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

  /**
   * Makes the player jump.
   */
  void jump(boolean isPlayer1);

  /**
   * Makes the player kick.
   */
  void kick(boolean isPlayer1);

  /**
   * Sets the player direction.
   *
   * @param i the direction to set the player to.
   */
  void setPlayerDirection(boolean isPlayer1, int i);
}
