package actions.movements;

import actions.movements.enums.MovementDirection;
import entity.Entity;

// Move entity as if it was in a square side in clockWise
// Entity start position is a corner o the square
// Initial MovimentDirection defines the start of the moviment

public class MoveSquare implements MovementStrategy {
    int totalToMove;            // Square side length
    int totalMoved = 0;
    FollowState fState = FollowState.ON_TARGET;

    public MoveSquare(int totalToMove) {
        this.totalToMove = totalToMove;
    }

    @Override
    public void move(Entity entity) {
        entity.moving = true;
        if (entity.getMovimentDirection() == MovementDirection.DOWN)       entity.mapY += entity.speed;
        else if (entity.getMovimentDirection() == MovementDirection.UP)    entity.mapY -= entity.speed;
        else if (entity.getMovimentDirection() == MovementDirection.RIGHT) entity.mapX += entity.speed;
        else if (entity.getMovimentDirection() == MovementDirection.LEFT)  entity.mapX -= entity.speed;
        totalMoved += entity.speed;
        if (totalMoved >= totalToMove) {
            changeDirection(entity);
            totalMoved = 0;
        } 
    }

    private void changeDirection(Entity entity) {
        // Change the Movementdirection to next, forming a square
        if (entity.getMovimentDirection() == MovementDirection.DOWN) 
            entity.setMovimentDirection(MovementDirection.LEFT);
        else if (entity.getMovimentDirection() == MovementDirection.LEFT)
            entity.setMovimentDirection(MovementDirection.UP);
        else if (entity.getMovimentDirection() == MovementDirection.UP)
            entity.setMovimentDirection(MovementDirection.RIGHT);
        else if (entity.getMovimentDirection() == MovementDirection.RIGHT)
            entity.setMovimentDirection(MovementDirection.DOWN);
    }

    @Override
    public void follow(Entity targetEntity, Entity entity, int error) {
        setFollowState(entity, targetEntity, error);
        if (fState != FollowState.ON_TARGET) {
            entity.setMovimentDirection(nextFollowDirection(targetEntity, entity));
            move(entity);
        }
    }

    private void setFollowState(Entity entity, Entity target, int error) {
        boolean tryX;   // if false, skips the followState == x (To follow like climbing a ladder)
        if (fState == FollowState.FOLLOW_X) tryX = true;
        else tryX = false;

        if (totalMoved == 0) tryX = !tryX;

        if ((entity.mapX > target.mapX + error || entity.mapX < target.mapX - error) && tryX) {
            fState = FollowState.FOLLOW_X;
        } else
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
        if (fState == FollowState.FOLLOW_X) {
            if (target.mapX < entity.mapX) mvDirect = MovementDirection.RIGHT;    
            else mvDirect = MovementDirection.LEFT;
        } else {
            if (target.mapY < entity.mapY) mvDirect = MovementDirection.DOWN;    
            else mvDirect = MovementDirection.UP;
        }
        return mvDirect;
    }

    @Override
    public void setTotalToMove(int newTotalToMove) {
        this.totalToMove = newTotalToMove;
    }
}
