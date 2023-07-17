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
        entity.setCollisionOn(false);
        if (keyH.downPressed) {
            entity.setMovementDirection(MovementDirection.DOWN);
            if (!entity.getCollisionChecker().checkTile(entity)){
                entity.mapY += entity.getSpeed();
            }
        } else if (keyH.leftPressed) {
            entity.setMovementDirection(MovementDirection.LEFT);
            if (!entity.getCollisionChecker().checkTile(entity)){
                entity.mapX -= entity.getSpeed();
            }
        } else if (keyH.rightPressed) {  
            entity.setMovementDirection(MovementDirection.RIGHT);
            if (!entity.getCollisionChecker().checkTile(entity)){
                entity.mapX += entity.getSpeed();
            }
        } else if (keyH.upPressed) {
            entity.setMovementDirection(MovementDirection.UP);
            if (!entity.getCollisionChecker().checkTile(entity)){
                entity.mapY -= entity.getSpeed();
            }
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
