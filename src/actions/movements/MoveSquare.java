package actions.movements;

import actions.movements.enums.MovementDirection;
import entity.Entity;

/**
 * Class {@code MoveSquare} implements a movement strategy for movement as if it was in a square side in clockWise. <p>
 * Entity start position is a corner of the square.
 * Initial MovimentDirection defines the start of the moviment.
 */
public class MoveSquare implements MovementStrategy {
    int totalToMove;            // Square side length
    int totalMoved = 0;
    FollowState fState = FollowState.ON_TARGET;

    /**
     * Create a new square movement strategy
     * @param totalToMove square size
     */
    public MoveSquare(int totalToMove) {
        this.totalToMove = totalToMove;
    }

    @Override
    public void move(Entity entity) {
        entity.moving = true;
        if (entity.getMovementDirection() == MovementDirection.DOWN)       entity.mapY += entity.speed;
        else if (entity.getMovementDirection() == MovementDirection.UP)    entity.mapY -= entity.speed;
        else if (entity.getMovementDirection() == MovementDirection.RIGHT) entity.mapX += entity.speed;
        else if (entity.getMovementDirection() == MovementDirection.LEFT)  entity.mapX -= entity.speed;
        totalMoved += entity.speed;
        if (totalMoved >= totalToMove) {
            changeDirection(entity);
            totalMoved = 0;
        } 
    }

    /**
     * Change entity direction after reaching a corner of the square
     * @param entity to change direction 
     */
    private void changeDirection(Entity entity) {
        if (entity.getMovementDirection() == MovementDirection.DOWN) 
            entity.setMovementDirection(MovementDirection.LEFT);
        else if (entity.getMovementDirection() == MovementDirection.LEFT)
            entity.setMovementDirection(MovementDirection.UP);
        else if (entity.getMovementDirection() == MovementDirection.UP)
            entity.setMovementDirection(MovementDirection.RIGHT);
        else if (entity.getMovementDirection() == MovementDirection.RIGHT)
            entity.setMovementDirection(MovementDirection.DOWN);
    }

    @Override
    public void follow(Entity targetEntity, Entity entity, int error) {
        setFollowState(entity, targetEntity, error);
        if (fState != FollowState.ON_TARGET) {
            entity.setMovementDirection(nextFollowDirection(targetEntity, entity));
            move(entity);
        }
    }

    /**
     * Set the follow state of the persecution
     * @param entity that is following
     * @param target that is being followed
     * @param error diference in target and entity position that can be considered {@code ON_TARGET}
     */
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

    /**
     * Get the next entity direction based on follow state
     * @param entity that is following
     * @param target that is being followed
     * @return entity next {@code MovementDirection}
     */
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
