package actions.movements;

import actions.movements.enums.MovementDirection;
import entity.Entity;
import main.KeyHandler;

/**
 * Class {@code MoveControle} implements a movement strategy for movement controled by a keyboard 
 */
public class MoveControled implements MovementStrategy {
    KeyHandler keyH;

    /**
     * Create a new controled movement strategy
     * @param keyH key handler that controls the keyboard inputs 
     */
    public MoveControled(KeyHandler keyH) {
        this.keyH = keyH;
    }

    @Override
    public void move(Entity entity) {
        entity.moving = true;
        if (keyH.downPressed) {
            entity.mapY += entity.getSpeed();
            entity.setMovementDirection(MovementDirection.DOWN);
        } else if (keyH.leftPressed) {
            entity.mapX -= entity.getSpeed();
            entity.setMovementDirection(MovementDirection.LEFT);
        } else if (keyH.rightPressed) {
            entity.mapX += entity.getSpeed();   
            entity.setMovementDirection(MovementDirection.RIGHT);
        } else if (keyH.upPressed) {
            entity.mapY -= entity.getSpeed();
            entity.setMovementDirection(MovementDirection.UP);
        } else {
            entity.moving = false;
        }
    }

    @Override
    public void follow(Entity targetEntity, Entity entity, int error) {
        move(entity);
    }

    /**
     * Not used for controled strategy. Do nothing
     */
    @Override
    public void setTotalToMove(int newTotalToMove) {
        // NOT USED FOR CONTROLED MOVEMENT
    }
    
}
