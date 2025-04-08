package inf112.bigheadkickerz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import inf112.bigheadkickerz.app.BigHeadKickerzGame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test class for the GameModel class. */
public class GameModelTest {

  private GameModel gameModel;

  /**
   * Set up the test environment before all tests.
   * This method initializes the GDX application and mocks the GL20 object.
   */
  @BeforeAll
  public static void setUpBeforeAll() {
    HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
    ApplicationListener listener = new ApplicationAdapter() {

    };
    new HeadlessApplication(listener, config);
    GL20 glMock = mock(GL20.class);
    Gdx.gl = glMock;
    Gdx.gl20 = glMock;

    Assets.init();
  }

  /**
   * Set up the test environment before each test.
   * This method initializes the GameModel object with a mock game and sets the
   * game state to FIRST_TO_SEVEN.
   */
  @BeforeEach
  public void setUpFirstToSeven() {
    BigHeadKickerzGame gameMock = mock(BigHeadKickerzGame.class);
    gameModel = new GameModel(gameMock, GameState.FIRST_TO_SEVEN);
    gameModel.dismissControls();
  }

  @Test
  void testInitialization() {
    assertNotNull(gameModel);
    assertNotNull(gameModel.getPlayer1());
    assertNotNull(gameModel.getPlayer2());
    assertNotNull(gameModel.getBall());
    assertNotNull(gameModel.getLeftGoal());
    assertNotNull(gameModel.getRightGoal());
    assertNotNull(gameModel.getViewport());
    assertNotNull(gameModel.getGameState());
  }

  @Test
  void testDismissControls() {
    GameModel gameModelWithControls = new GameModel(
        mock(BigHeadKickerzGame.class), GameState.FIRST_TO_SEVEN);
    assertTrue(gameModelWithControls.isShowingControls());
    gameModelWithControls.dismissControls();
    assertFalse(gameModelWithControls.isShowingControls());
  }

  @Test
  void testResetPositions() {
    gameModel.getPlayer1().setPosition(new Vector2(0, 20));
    gameModel.getPlayer2().setPosition(new Vector2(100, 0));
    gameModel.getBall().setPosition(new Vector2(50, 50));
    assertEquals(new Vector2(0, 20), gameModel.getPlayer1().getPosition());
    assertEquals(new Vector2(100, 0), gameModel.getPlayer2().getPosition());
    assertEquals(new Vector2(50, 50), gameModel.getBall().getPosition());
    gameModel.getPlayer1().reset();
    gameModel.getPlayer2().reset();
    gameModel.getBall().reset();
    float player1StartPosX = gameModel.getViewport().getWorldWidth() / 8 * (8 - 6.5f)
        - gameModel.getPlayer1().getWidth();
    float player2StartPosX = gameModel.getViewport().getWorldWidth() / 8 * 6.5f;
    float ballStartPosX = gameModel.getViewport().getWorldWidth()
        / 2 - gameModel.getBall().getWidth() / 2;
    float ballStartPosY = gameModel.getViewport().getWorldHeight() / 2 + 1.5f;
    assertEquals(new Vector2(player1StartPosX, 0), gameModel.getPlayer1().getPosition());
    assertEquals(new Vector2(player2StartPosX, 0), gameModel.getPlayer2().getPosition());
    assertEquals(new Vector2(ballStartPosX, ballStartPosY), gameModel.getBall().getPosition());
  }

  @Test
  void testTimer() {
    GameModel gameModelTimed = new GameModel(mock(BigHeadKickerzGame.class), GameState.TIMED);
    gameModelTimed.dismissControls();
    assertEquals(60, gameModelTimed.getRemainingTime());
    gameModelTimed.update(1f);
    assertEquals(59, gameModelTimed.getRemainingTime());
  }

  @Test
  void testPlayerScore() {
    assertEquals(0, gameModel.getPlayer1Score());
    assertEquals(0, gameModel.getPlayer2Score());
    gameModel.getBall().setPosition(new Vector2(0, 0));
    gameModel.update(0.1f);
    assertEquals(1, gameModel.getPlayer2Score());
    assertEquals(0, gameModel.getPlayer1Score());
    gameModel.update(2f); // Reset delayed by 2 seconds
    gameModel.getBall().setPosition(new Vector2(gameModel.getRightGoal().getPosition().x + 50, 0));
    gameModel.update(0.1f);
    assertEquals(1, gameModel.getPlayer2Score());
    assertEquals(1, gameModel.getPlayer1Score());
  }

  @Test
  void testGameOverTimed() {
    GameModel gameModelTimed = new GameModel(mock(BigHeadKickerzGame.class), GameState.TIMED);
    gameModelTimed.dismissControls();
    gameModelTimed.update(60f);
    assertEquals(GameState.GAME_OVER, gameModelTimed.getGameState());
  }

  @Test
  void testGameOverFirstToSeven() {
    for (int i = 0; i < 7; i++) {
      scoreGoal();
      assertEquals(GameState.FIRST_TO_SEVEN, gameModel.getGameState());
    }
    gameModel.update(2f); // End delay
    assertEquals(GameState.GAME_OVER, gameModel.getGameState());
  }

  private void scoreGoal() {
    gameModel.getBall().setPosition(new Vector2(0, 0));
    gameModel.update(2f);
  }

}
