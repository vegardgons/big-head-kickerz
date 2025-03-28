package inf112.bigheadkickerz.model;

public interface IPowerup {
    // Movement Speed
    float getMovementSpeed();

    float setMovementSpeed(float movementSpeed);

    // Gravity
    float getGravity();

    float setGravity(float gravity);

    // Kick Power
    float getKickPower();

    float setKickPower(float kickPower);

    // Size: Width
    float getWidth();

    float setWidth(float width);

    // Size: Height
    float getHeight();

    float setHeight(float height);

    // Jump Height
    float getJumpHeight();
    
    float setJumpHeight(float jumpHeight);
}
