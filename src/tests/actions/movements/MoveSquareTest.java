package tests.actions.movements;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import actions.movements.MoveSquare;
import actions.movements.enums.MovementDirection;
import entity.Entity;
import entity.Player;
import main.GamePanel;
import main.KeyHandler;

/**
 * MoveSquareTest
 */
public class MoveSquareTest {
    Random rand = new Random();
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler();
    Entity entity = new Player(gp, keyH);
    MoveSquare mvStrategy = new MoveSquare(0);

    @RepeatedTest(10)
    @DisplayName("Test move strategy")
    public void moveTest() {
        int mvConst = rand.nextInt(10000);
        if (mvConst < 0) mvConst *= -1;
        int rounds = mvConst * 4 * 5;   // 5 rounds
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

            switch (entity.getMovementDirection()) {
                case DOWN:
                    assertTrue(entity.mapX <= initialX + error && entity.mapX >= initialX - error);
                    assertTrue(entity.mapY >= initialY - error);
                    assertTrue(entity.mapY <= initialY + mvConst + error);
                    break;
                case LEFT:
                    assertTrue(entity.mapY <= initialY + mvConst + error && entity.mapY >= initialY + mvConst - error);
                    assertTrue(entity.mapX <= initialX + error);
                    assertTrue(entity.mapX >= initialX - mvConst - error);
                    break;
                case UP:
                    assertTrue(entity.mapX >= initialX - mvConst - error && entity.mapX <= initialX + error);
                    assertTrue(entity.mapY >= initialY - error);
                    assertTrue(entity.mapY <= initialY + mvConst + error);
                    break;
                case RIGHT:
                    assertTrue(entity.mapY <= initialY + error && entity.mapY >= initialY - error);
                    assertTrue(entity.mapX <= initialX + error);
                    assertTrue(entity.mapX >= initialX - mvConst - error);
                    break;
            }
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

        int distanceX;
        int initialDistanceX = (targetEntity.mapX) - (entity.mapX);
        if (initialDistanceX < 0) initialDistanceX *= -1;  
        double lastDistanceX = initialDistanceX;
        int distanceY;
        int initialDistanceY = (targetEntity.mapY) - (entity.mapY);
        if (initialDistanceY < 0) initialDistanceY *= -1;  
        double lastDistanceY = initialDistanceY;

        boolean following = true;
        int round = 0;
        while (following) {
            mvStrategy.follow(targetEntity, targetEntity, error);
            
            // Check if entity is approaching target in X axis
            distanceX = (targetEntity.mapX) - (entity.mapX);
            if (distanceX < 0) distanceX *= -1;  
            assertTrue(distanceX <= lastDistanceX);
            lastDistanceX = distanceX;

            // Check if entity is approaching target in Y axis
            distanceY = (targetEntity.mapY) - (entity.mapY);
            if (distanceY < 0) distanceY *= -1;  
            assertTrue(distanceY <= lastDistanceY);
            lastDistanceY = distanceY; 
            
            if (!entity.moving) {
                round++;
                if (round > 10) following = false;   // ten rounds without moving (on target)
            }
        }
    }
}