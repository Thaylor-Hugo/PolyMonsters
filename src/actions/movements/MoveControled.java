package actions.movements;

import actions.movements.enums.MovementDirection;
import entity.Entity;
import main.KeyHandler;

public class MoveControled implements MovementStrategy {
    KeyHandler keyH;

    public MoveControled(KeyHandler keyH) {
        this.keyH = keyH;
    }

    @Override
    public void move(Entity entity) {
        entity.moving = true;
        if (keyH.downPressed) {
            entity.mapY += entity.speed;
            entity.setMovementDirection(MovementDirection.DOWN);
        } else if (keyH.leftPressed) {
            entity.mapX -= entity.speed;
            entity.setMovementDirection(MovementDirection.LEFT);
        } else if (keyH.rightPressed) {
            entity.mapX += entity.speed;   
            entity.setMovementDirection(MovementDirection.RIGHT);
        } else if (keyH.upPressed) {
            entity.mapY -= entity.speed;
            entity.setMovementDirection(MovementDirection.RIGHT);
        } else {
            entity.moving = false;
        }
    }

    @Override
    public void follow(Entity targetEntity, Entity entity, int error) {
        move(entity);
    }

    @Override
    public void setTotalToMove(int newTotalToMove) {
        // NOT USED FOR CONTROLED MOVEMENT
    }
    
}
