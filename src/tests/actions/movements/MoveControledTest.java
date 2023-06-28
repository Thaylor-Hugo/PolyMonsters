package tests.actions.movements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import actions.movements.MoveControled;
import actions.movements.enums.MovementDirection;
import entity.Entity;
import entity.Player;
import main.GamePanel;
import main.KeyHandler;

/**
 * MoveControledTest
 */
public class MoveControledTest {
    Random rand = new Random();
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler();
    Entity entity = new Player(gp, keyH);
    MoveControled mvStrategy = new MoveControled(keyH);

    @RepeatedTest(10)
    @DisplayName("Test move strategy")
    public void moveTest() {
        int mvConst = rand.nextInt(10000);
        if (mvConst < 0) mvConst *= -1;
        int rounds = mvConst * 10;
        mvStrategy.setTotalToMove(mvConst);

        // 10000 bound to avoid overflow
        MovementDirection initialDirection = MovementDirection.DOWN;
        int initialX = rand.nextInt(10000);
        int initialY = rand.nextInt(10000);
        int error = entity.speed;
        entity.mapX = initialX;
        entity.mapY = initialY;
        entity.setMovementDirection(initialDirection);

        for (int i = 0; i < rounds; i++) {
            int lastX = entity.mapX;
            int lastY = entity.mapY;
            keyH.downPressed = rand.nextBoolean();
            keyH.leftPressed = rand.nextBoolean();
            keyH.rightPressed = rand.nextBoolean();
            keyH.upPressed = rand.nextBoolean();
            mvStrategy.move(entity);
            
            if (!keyH.downPressed && !keyH.upPressed && !keyH.leftPressed && !keyH.rightPressed) {
                assertEquals(entity.mapX, lastX);
                assertEquals(entity.mapY, lastY);
                continue;
            } 
            switch (entity.getMovementDirection()) {
                case DOWN:
                    assertTrue(entity.mapX <= lastX + error && entity.mapX >= lastX - error);
                    assertEquals(entity.mapY, lastY + entity.speed);
                    break;
                case LEFT:
                    assertTrue(entity.mapY <= lastY + error && entity.mapY >= lastY - error);
                    assertEquals(entity.mapX, lastX - entity.speed);
                    break;
                case UP:
                    assertTrue(entity.mapX <= lastX + error && entity.mapX >= lastX - error);
                    assertEquals(entity.mapY, lastY - entity.speed);
                    break;
                case RIGHT:
                    assertTrue(entity.mapY <= lastY + error && entity.mapY >= lastY - error);
                    assertEquals(entity.mapX, lastX + entity.speed);
                    break;
            }
        }
    }
}