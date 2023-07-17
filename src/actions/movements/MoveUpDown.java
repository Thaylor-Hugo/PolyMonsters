package actions.movements;

import actions.movements.enums.MovementDirection;
import entity.Entity;

/**
 * Class {@code MoveUpDown} implements a movement strategy for movement in a vertical line <p>
 */
public class MoveUpDown implements MovementStrategy {
    int totalMoved = 0;
    int totalToMove;
    FollowState fState = FollowState.ON_TARGET;

    /**
     * Create a new UpDown movement strategy
     * @param totalToMove vertical line size
     * @throws IllegalArgumentException if totalToMove is not positive or zero
     */
    public MoveUpDown(int totalToMove) throws IllegalArgumentException {
        if (totalToMove < 0) throw new IllegalArgumentException("Total to move can't be negative");
        this.totalToMove = totalToMove;
    }

    @Override
    public void move(Entity entity) {
        entity.moving = true;
        if (entity.getMovementDirection() == MovementDirection.DOWN)       entity.mapY += entity.getSpeed();
        else if (entity.getMovementDirection() == MovementDirection.UP)    entity.mapY -= entity.getSpeed();
        totalMoved += entity.getSpeed();

        if (totalMoved >= totalToMove) {
            totalMoved = 0;
            changeDirection(entity);
        } 
    }

    /**
     * Change entity direction after reaching the line edge
     * @param entity to change direction 
     */
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

    /**
     * Set the follow state of the persecution. Only follow verticaly
     * @param entity that is following
     * @param target that is being followed
     * @param error diference in target and entity position that can be considered {@code ON_TARGET}
     */
    private void setFollowState(Entity entity, Entity target, int error) {
        if ((entity.mapY > target.mapY + error || entity.mapY < target.mapY - error)) {
            fState = FollowState.FOLLOW_Y;
        } else {
            fState = FollowState.ON_TARGET;
            totalMoved = 0;
            entity.moving = false;
        }
    }

    /**
     * Get the next entity direction based on follow state.
     * @param entity that is following
     * @param target that is being followed
     * @return entity next {@code MovementDirection}
     */
    private MovementDirection nextFollowDirection(Entity entity, Entity target) {
        MovementDirection mvDirect;
        if (target.mapY < entity.mapY) mvDirect = MovementDirection.DOWN;    
        else mvDirect = MovementDirection.UP;
        return mvDirect;
    }

    @Override
    public void setTotalToMove(int newTotalToMove) throws IllegalArgumentException{
        if (newTotalToMove < 0) throw new IllegalArgumentException("Total to move can't be negative");
        this.totalToMove = newTotalToMove;
    }
    
}
