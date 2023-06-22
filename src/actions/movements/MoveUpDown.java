package actions.movements;

import actions.movements.enums.MovementDirection;
import entity.Entity;

public class MoveUpDown implements MovementStrategy {
    int totalMoved = 0;
    int totalToMove;
    FollowState fState = FollowState.ON_TARGET;

    public MoveUpDown(int totalToMove) {
        this.totalToMove = totalToMove;
    }

    @Override
    public void move(Entity entity) {
        entity.moving = true;
        if (entity.getMovementDirection() == MovementDirection.DOWN)       entity.mapY += entity.speed;
        else if (entity.getMovementDirection() == MovementDirection.UP)    entity.mapY -= entity.speed;
        totalMoved += entity.speed;

        if (totalMoved >= totalToMove) {
            totalMoved = 0;
            changeDirection(entity);
        } 
    }

    private void changeDirection(Entity entity) {
        // Change the Movementdirection to next, forming a square
        if (entity.getMovementDirection() == MovementDirection.DOWN) 
            entity.setMovementDirection(MovementDirection.UP);
        else if (entity.getMovementDirection() == MovementDirection.UP)
            entity.setMovementDirection(MovementDirection.DOWN);
        else entity.setMovementDirection(MovementDirection.DOWN);
    }

    @Override
    public void follow(Entity targetEntity, Entity entity, int error) {
        setFollowState(entity, targetEntity, error);
        if (fState != FollowState.ON_TARGET) {
            entity.setMovementDirection(nextFollowDirection(targetEntity, entity));
            move(entity);
        }
    }

    private void setFollowState(Entity entity, Entity target, int error) {
        if ((entity.mapY > target.mapY + error || entity.mapY < target.mapY - error)) {
            fState = FollowState.FOLLOW_Y;
        } else {
            fState = FollowState.ON_TARGET;
            totalMoved = 0;
            entity.moving = false;
        }
    }

    private MovementDirection nextFollowDirection(Entity entity, Entity target) {
        MovementDirection mvDirect;
        if (target.mapY < entity.mapY) mvDirect = MovementDirection.DOWN;    
        else mvDirect = MovementDirection.UP;
        return mvDirect;
    }

    @Override
    public void setTotalToMove(int newTotalToMove) {
        this.totalToMove = newTotalToMove;
    }
    
}
