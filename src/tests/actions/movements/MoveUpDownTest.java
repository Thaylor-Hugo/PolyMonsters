package tests.actions.movements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import actions.movements.MoveUpDown;
import actions.movements.enums.MovementDirection;
import entity.Entity;
import entity.Player;
import main.GamePanel;
import main.KeyHandler;

/**
 * MoveUpDownTest
 */
public class MoveUpDownTest {
    Random rand = new Random();
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler();
    Entity entity = new Player(gp, keyH);
    MoveUpDown mvStrategy = new MoveUpDown(0);

    @RepeatedTest(10)
    @DisplayName("Test move strategy")
    public void moveTest() {
        int mvConst = rand.nextInt(10000);
        if (mvConst < 0) mvConst *= -1;
        int rounds = mvConst * 5;
        mvStrategy.setTotalToMove(mvConst);

        // 10000 bound to avoid overflow
        MovementDirection initialDirection = MovementDirection.DOWN;
        int initialX = rand.nextInt(10000);
        int initialY = rand.nextInt(10000);
        int error = entity.getSpeed();
        entity.mapX = initialX;
        entity.mapY = initialY;
        entity.setMovementDirection(initialDirection);

        for (int i = 0; i < rounds; i++) {
            mvStrategy.move(entity);
            assertEquals(entity.mapX, initialX);
            assertTrue(entity.mapY >= initialY - error);
            assertTrue(entity.mapY <= initialY + mvConst + error);
        }
    }

    @RepeatedTest(10)
    @DisplayName("Test follow strategy")
    public void followTest() {
        Entity targetEntity = new Player(gp, keyH);
        int targetX = rand.nextInt(10000);
        int targetY = rand.nextInt(10000);
        targetEntity.mapX = targetX;
        targetEntity.mapY = targetY;
        
        int mvConst = rand.nextInt(10000);
        if (mvConst < 0) mvConst *= -1;
        mvStrategy.setTotalToMove(mvConst);

        // 10000 bound to avoid overflow
        int initialX = rand.nextInt(10000);
        int initialY = rand.nextInt(10000);
        int error = entity.getSpeed();
        entity.mapX = initialX;
        entity.mapY = initialY;
        MovementDirection initialDirection = MovementDirection.DOWN;
        entity.setMovementDirection(initialDirection);

        int distanceY;
        int initialDistanceY = (targetEntity.mapY) - (entity.mapY);
        if (initialDistanceY < 0) initialDistanceY *= -1;  
        double lastDistance = initialDistanceY;

        boolean following = true;
        int round = 0;
        while (following) {
            mvStrategy.follow(targetEntity, targetEntity, error);
            distanceY = (targetEntity.mapY) - (entity.mapY);
            if (distanceY < 0) distanceY *= -1;  
            assertTrue(distanceY <= lastDistance);
            lastDistance = distanceY;
            assertEquals(entity.mapX, initialX); 
            if (!entity.moving) {
                round++;
                if (round > 10) following = false;   // ten rounds without moving (on target)
            }
        }
    }
}